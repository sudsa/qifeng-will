package com.hanxiaozhang.config;


import com.hanxiaozhang.enums.DataSourceKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * 〈一句话功能简述〉<br>
 * 〈动态数据源的Transaction〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Slf4j
public class DynamicDataSourceTransaction implements Transaction {


    /**
     * 数据源
     */
    private final DataSource dataSource;

    /**
     *数据库标识
     */
    private DataSourceKey mainDataSourceKey;

    /**
     * 数据库连接
     */
    private Connection mainConnection;

    /**
     * 标识连接Map
     */
    private ConcurrentMap<DataSourceKey, Connection> otherConnectionMap;

    /**
     * 是否断开事务
     */
    private boolean isConnectionTransactional;

    /**
     * 是否自动提交
     */
    private boolean autoCommit;


    public DynamicDataSourceTransaction(DataSource dataSource) {
        notNull(dataSource, "No DataSource specified");
        this.dataSource = dataSource;
        otherConnectionMap = new ConcurrentHashMap<>();
        mainDataSourceKey=DynamicDataSourceContextHolder.get()==null?DataSourceKey.DB_MASTER:DynamicDataSourceContextHolder.get();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() throws SQLException {
        DataSourceKey dataSourceKey = DynamicDataSourceContextHolder.get()==null?DataSourceKey.DB_MASTER:DynamicDataSourceContextHolder.get();
        if (dataSourceKey.equals(mainDataSourceKey)) {
            if (mainConnection != null) {
                return mainConnection;
            } else {
                openMainConnection();
                mainDataSourceKey =dataSourceKey;
                return mainConnection;
            }
        } else {
            if (!otherConnectionMap.containsKey(dataSourceKey)) {
                try {
                    Connection conn = dataSource.getConnection();
                    otherConnectionMap.put(dataSourceKey, conn);
                } catch (SQLException ex) {
                    throw new CannotGetJdbcConnectionException("Could not get JDBC Connection", ex);
                }
            }
            return otherConnectionMap.get(dataSourceKey);
        }

    }


    private void openMainConnection() throws SQLException {
        this.mainConnection = DataSourceUtils.getConnection(this.dataSource);
        this.autoCommit = this.mainConnection.getAutoCommit();
        this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(this.mainConnection, this.dataSource);

        if (log.isDebugEnabled()) {
            log.debug(
                    "JDBC Connection ["
                            + this.mainConnection
                            + "] will"
                            + (this.isConnectionTransactional ? " " : " not ")
                            + "be managed by Spring");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commit() throws SQLException {
        if (this.mainConnection != null && !this.isConnectionTransactional && !this.autoCommit) {
            if (log.isDebugEnabled()) {
                log.debug("Committing JDBC Connection [" + this.mainConnection + "]");
            }
            this.mainConnection.commit();
            for (Connection connection : otherConnectionMap.values()) {
                connection.commit();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollback() throws SQLException {
        if (this.mainConnection != null && !this.isConnectionTransactional && !this.autoCommit) {
            if (log.isDebugEnabled()) {
                log.debug("Rolling back JDBC Connection [" + this.mainConnection + "]");
            }
            this.mainConnection.rollback();
            for (Connection connection : otherConnectionMap.values()) {
                connection.rollback();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws SQLException {
        DataSourceUtils.releaseConnection(this.mainConnection, this.dataSource);
        for (Connection connection : otherConnectionMap.values()) {
            DataSourceUtils.releaseConnection(connection, this.dataSource);
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        return null;
    }
}
