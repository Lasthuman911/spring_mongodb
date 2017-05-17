import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring4.mongodb.Item;
import spring4.mongodb.MongoConfig;
import spring4.mongodb.Order;
import spring4.mongodb.db.OrderRepository;

/**
 * Name: admin
 * Date: 2017/5/17
 * Time: 11:28
 */
@ContextConfiguration(classes =MongoConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoDBTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    MongoOperations mongoOperations;

    @Before
    public void cleanup(){
        orderRepository.deleteAll();//Deletes all entities managed by the repository.
    }


    /**
     * 方式1： 通过mongoTemplate 进行操作
     * 通常来讲， 我们会将MongoOperations注入到自己设计的Repository类中， 并使用它的操作来实现Repository方法
     */
    @Test
    public void testMongoOperations(){
         //getCollection(String collectionName) : Get a collection by name, creating it if it doesn't exist.
         //count()计算文档集合中有多少条文档
        long orderCount = mongoOperations.getCollection("order").count();

        Order order = createAnOrder();

        //save(Object objectToSave, String collectionName) : Save the object to the specified collection.
        mongoOperations.save(order,"order");

        //findById(Object id, Class<T> entityClass) : Returns a document with the given id mapped onto the given class.
        mongoOperations.findById("1",Order.class);

        /**
         * 查找所有client域等于“Chuck Wagon”的订单
         * find(Query query, Class<T> entityClass) : Map the results of an ad-hoc query on the collection for the entity class to a List of the specified type.
         * query(CriteriaDefinition criteriaDefinition) : Static factory method to create a Query using the provided CriteriaDefinition.
         * where(String key) : Static factory method to create a Criteria using the provided key
         * is(Object o): Creates a criterion using equality
         */
        List<Order> chucksOrder = mongoOperations.find(Query.query(Criteria.where("client").is("Chuck Wagon")),Order.class);

        //查询Chuck所有通过Web创建的订单
        List<Order> chucksWebOrder = mongoOperations.find(Query.query(Criteria.where("Customer").is("Chuck Wagon").and("type").is("WEB")),Order.class);

        //移除一个order
        mongoOperations.remove(order);
    }

    /**
     * 方式2 ： 通过自定义Reposity 进行操作
     */
    @Test
    public void testReposity(){
        assertEquals(0, orderRepository.count());//Returns the number of entities available.
        Order order = createAnOrder();

        //保存一个实体,Saves a given entity.
        Order savedOrder = orderRepository.save(order);
        assertEquals(1,orderRepository.count());

        //Retrieves an entity by its id
        Order secondOrder = orderRepository.findOne(savedOrder.getId());
        assertEquals("Chuck Wagon",secondOrder.getCustomer());
        assertEquals(2,secondOrder.getItems().size());

        // Finding an order by a single field value,要由具体的实现类去实现findByCustomer
        List<Order> chucksOrders = orderRepository.findByCustomer("Chuck Wagon");
        assertEquals(1, chucksOrders.size());
        assertEquals("Chuck Wagon", chucksOrders.get(0).getCustomer());
        assertEquals(2, chucksOrders.get(0).getItems().size());

        // Finding an order by a single field value like
        List<Order> chuckLikeOrders = orderRepository.findByCustomerLike("Chuck");
        assertEquals(1, chuckLikeOrders.size());
        assertEquals("Chuck Wagon", chuckLikeOrders.get(0).getCustomer());
        assertEquals(2, chuckLikeOrders.get(0).getItems().size());

        // Finding an order by multiple field values
        List<Order> chucksWebOrders = orderRepository.findByCustomerAndType("Chuck Wagon", "WEB");
        assertEquals(1, chucksWebOrders.size());
        assertEquals("Chuck Wagon", chucksWebOrders.get(0).getCustomer());
        assertEquals(2, chucksWebOrders.get(0).getItems().size());

        List<Order> chucksPhoneOrders = orderRepository.findByCustomerAndType("Chuck Wagon", "PHONE");
        assertEquals(0, chucksPhoneOrders.size());

        // Finding an order by a custom query method
        List<Order> chucksOrders2 = orderRepository.findChucksOrders();
        assertEquals(1, chucksOrders2.size());
        assertEquals("Chuck Wagon", chucksOrders2.get(0).getCustomer());
        assertEquals(2, chucksOrders2.get(0).getItems().size());


        // Deleting an order, Deletes a given entity.
        orderRepository.delete(savedOrder.getId());
        assertEquals(0, orderRepository.count());

    }

    private Order createAnOrder() {
        Order order = new Order();
        order.setCustomer("Chuck Wagon");
        order.setType("WEB");
        Item item1 = new Item();
        item1.setProduct("Spring in Action");
        item1.setQuantity(2);
        item1.setPrice(29.99);
        Item item2 = new Item();
        item2.setProduct("Module Java");
        item2.setQuantity(31);
        item2.setPrice(29.95);
        order.setItems(Arrays.asList(item1, item2));//数组--》 list
        return order;
    }
}
