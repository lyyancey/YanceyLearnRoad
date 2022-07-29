package com.yancey.elasticsearch.component;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

// 不带验证的客户端
/*@Component
public class EsClient {
    @Value("${elasticsearch.rest.hosts}")
    private String hosts; //读取ES的主机+端口配置
    public RestHighLevelClient initSimpleClient(){
        // 根据配置文件配置HttpHost数组
        HttpHost[] httpHosts = Arrays.stream(hosts.split(",")).map(
                host -> {
                    //分隔ES服务器的IP和端口
                    String[] hostParts = host.split(":");
                    String hostName = hostParts[0];
                    int port = Integer.parseInt(hostParts[1]);
                    return new HttpHost(hostName, port, HttpHost.DEFAULT_SCHEME_NAME);
                }).filter(Objects::nonNull).toArray(HttpHost[]::new);
        // 构建客户端
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}*/
@Component
public class EsClient {
    @Value("${elasticsearch.rest.hosts}")
    private String hosts;
    @Value("${elasticsearch.rest.username}")
    private String esUser;
    @Value("${elasticsearch.rest.password}")
    private String esPassword;
    @Bean
    public RestHighLevelClient initClient(){
        // 根据配置文件配置HttpHost数组
        HttpHost[] httpHosts = Arrays.stream(hosts.split(",")).map(
                host -> {
                    //分隔ES服务器的IP和端口
                    String[] hostParts = host.split(":");
                    String hostName = hostParts[0];
                    int port = Integer.parseInt(hostParts[1]);
                    return new HttpHost(hostName, port, HttpHost.DEFAULT_SCHEME_NAME);
                }
        ).filter(Objects::nonNull).toArray(HttpHost[]::new);
        // 生成凭证
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                // 明文凭证
                new UsernamePasswordCredentials(esUser, esPassword));
                //返回带验证的客户端
        return new RestHighLevelClient(
                RestClient.builder( httpHosts)
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            @Override
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        })
                );
    }
}
