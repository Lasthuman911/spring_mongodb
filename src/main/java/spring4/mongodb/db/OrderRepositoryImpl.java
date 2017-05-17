package spring4.mongodb.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import spring4.mongodb.Order;

import java.util.List;

/**
 * Name: admin
 * Date: 2017/5/17
 * Time: 16:27
 */
public class OrderRepositoryImpl  {

    @Autowired
    MongoOperations mongoOperations;

    public List<Order> findByCustomer(String customer) {
        Query query = Query.query(Criteria.where("customer").is(customer));

        return mongoOperations.find(query,Order.class);
    }

    public List<Order> findByCustomerLike(String customer) {
        return null;
    }

    public List<Order> findByCustomerAndType(String customer, String type) {
        return null;
    }

    public List<Order> getByType(String type) {
        return null;
    }

    public List<Order> findChucksOrders() {
        return null;
    }
}
