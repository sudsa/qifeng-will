package com.qifeng.will.es.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String hostname;
    @Value("${elasticsearch.port}")
    private int port;
    @Value("${elasticsearch.user}")
    private String user;
    @Value("${elasticsearch.password}")
    private String password;
    @Value("${elasticsearch.timeout}")
    private long socketTimeout;
    @Value("${elasticsearch.scheme}")
    private String scheme;

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    /**
     * 无账号密码连接方式
     **/
    @Bean(name = "esUnSafeClient")
    public RestHighLevelClient esUnSafeRestClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        //集群配置法
                        new HttpHost("localhost", 9201, "http")));
    }

    /**
     *  使用账号密码连接
     **/
    @Bean(name = "esSafeClient")
    public RestHighLevelClient esSafeRestClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(hostname, port, scheme));
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, password));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
        return new RestHighLevelClient(builder);
    }

    public Settings settings(){
        //指定ES集群
        Settings setting = Settings.builder().put("cluster.name",
                "my-application").build();

        return setting;
    }

    public Settings indexSettings(){
        //
        Settings.Builder builder = Settings.builder();

        return builder
                .put("number-of-shards",1)//es 分片数量，通常对应es集群节点数量
                .put("number-of-replicas",0)//副本数量
                .put("max_result_window", Integer.MAX_VALUE)
                .build();

    }
}
