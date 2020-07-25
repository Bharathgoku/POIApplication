package com.poi.config;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Value("${rest.template.connect.timeout}")
  private Integer CONNECT_TIMEOUT;

  @Value("${rest.template.connect.request.timeout}")
  private Integer CONNECT_REQUEST_TIMEOUT;

  @Value("${rest.template.read.timeout}")
  private Integer READ_TIMEOUT;

  @Value("${rest.template.max.conn.per.route}")
  private Integer MAX_CONN_PER_ROUTE;

  @Value("${rest.template.max.conn}")
  private Integer MAX_CONN;

  @Bean
  @Primary
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    HttpComponentsClientHttpRequestFactory requestFactory = getConnectionSettings();
    requestFactory.setReadTimeout(READ_TIMEOUT);
    RestTemplate restTemplate = builder.build();
    restTemplate.setRequestFactory(requestFactory);
    return restTemplate;
  }

  private HttpComponentsClientHttpRequestFactory getConnectionSettings() {
    PoolingHttpClientConnectionManager connectionManager =
        new PoolingHttpClientConnectionManager();
    connectionManager.setDefaultMaxPerRoute(MAX_CONN_PER_ROUTE);
    connectionManager.setMaxTotal(MAX_CONN);
    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder.create().setConnectionManager(connectionManager).build());
    requestFactory.setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT);
    requestFactory.setConnectTimeout(CONNECT_TIMEOUT);
    return requestFactory;
  }

}
