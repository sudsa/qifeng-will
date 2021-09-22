package com.hanxiaozhang.config;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

/**
 * 〈一句话功能简述〉<br>
 * 〈动态数据源的TransactionFactory〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
public class DynamicDataSourceTransactionFactory extends SpringManagedTransactionFactory {
    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new DynamicDataSourceTransaction(dataSource);
    }
}