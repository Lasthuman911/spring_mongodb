package spring4.mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

/**
 * @EnableMongoRepositories(basePackages = "spring4.mongodb.db") 启动自动化MongoDB 的 Repository生成功能
 */
@Configuration
@EnableMongoRepositories(basePackages = "spring4.mongodb.db")
public class MongoConfig extends AbstractMongoConfiguration {

    /**
     * 指定数据库名称
     */
    @Override
    protected String getDatabaseName() {
        return "OrdersDB";
    }

    /**
     * 创建Mongo客户端
     */
    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient();

        /** 如果MongoDB服务器运行在其他 的机器上， 那么可以在创建MongoClient的时候进行指定*/
        // return new MongoClient("mongodbserver");

        /** MongoDB服务器有可能监听的端口并不是默认的27017。 如果是这样的话， 在创建
         MongoClient的时候， 还需要指定端口*/
        // return new MongoClient("mongodbserver",37017);
    }

/*    //创建MongoClient来访问需要认证的MongoDB服务器
    @Autowired
    private Environment environment;

    @Override
    public Mongo mongo() throws Exception {
        MongoCredential credential =
                MongoCredential.createMongoCRCredential(
                        environment.getProperty("mongo.username"),
                        "OrdersDB",
                        environment.getProperty("mongo.password").toCharArray()
                );
        return new MongoClient(
                new ServerAddress("localhost", 37017),
                Arrays.asList(credential)
        );
    }*/
}
