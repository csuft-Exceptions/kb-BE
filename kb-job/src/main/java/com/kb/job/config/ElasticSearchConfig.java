// package com.kb.job.config;
//
// import org.apache.http.HttpHost;
// import org.elasticsearch.client.RestClient;
// import org.elasticsearch.client.RestHighLevelClient;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// /**
//  * @author mawz
//  * @version 1.0
//  * @date 2022-07-24 - 17:14
//  */
// @Configuration
// public class ElasticSearchConfig {
//
//     @Bean
//     public RestHighLevelClient restHighLevelClient(){
//         RestHighLevelClient client=new RestHighLevelClient(
//                 RestClient.builder(
//                         new HttpHost("127.0.0.1",9200,"http")));
//         return client;
//     }
// }
