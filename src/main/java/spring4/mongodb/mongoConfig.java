package spring4.mongodb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @EnableMongoRepositories(basePackages = "spring4.mongodb.db") 启动MongoDB 的 Repository功能
 */
@Configuration
@EnableMongoRepositories(basePackages = "spring4.mongodb.db")
public class mongoConfig {

}
