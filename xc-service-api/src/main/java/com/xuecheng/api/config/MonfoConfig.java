package com.xuecheng.api.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mongodb 配置类
 */
@Configuration
public class MonfoConfig {

    @Value("${spring.data.mongodb.database}")
    String mongodb;

    /**
     * GridFS 存取文件
     * @param mongoClient
     * @return
     */
    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(mongodb);
        return GridFSBuckets.create(database);
    }


}
