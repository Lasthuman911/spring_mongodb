package spring4.mongodb;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @EnableMongoRepositories(basePackages = "spring4.mongodb.db") 启动自动化MongoDB 的 Repository生成功能
 */
@Configuration
@EnableMongoRepositories(basePackages = "spring4.mongodb.db")
public class MongoConfigOld {

    /**
     *MongoClient bean，这个bean将 Spring Data MongoDB与数据库本身连接了起来
     */
    @Bean
    public MongoFactoryBean mongo(){
        MongoFactoryBean mongoFactoryBean = new MongoFactoryBean();
        mongoFactoryBean.setHost("localhost");
        return mongoFactoryBean;
    }

    /**
     * MongoTemplate bean
     * @param mongo
     */
    @Bean
    public MongoOperations mongoTemplate(Mongo mongo){
        return new MongoTemplate(mongo,"OrdersDB");
    }
}
