package spring4.mongodb;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *@Document 类似于JPA的@Entitiy :这是一个文档，这样它就能够借助MongoTemplate或自动生成的Repository进行持久化
 */
@Document
public class Order {

    /**
     * 用来指定它作为文档的ID
     */
    @Id
    private String id;

    /**
     * customer属性上使用了@Field注解， 这样的话， 当文档持久化的时
     候customer属性将会映射为名为client的域
     */
    @Field("customer")
    private String customer;

    private String type;

    private Collection<Item> items = new LinkedHashSet<Item>();

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

}
