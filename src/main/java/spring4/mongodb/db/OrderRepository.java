package spring4.mongodb.db;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import spring4.mongodb.Order;

/**
 *  任何扩展Repository的
 接口将会在运行时自动生成实现
 */
public interface OrderRepository extends MongoRepository<Order, String> {
	
	List<Order> findByCustomer(String customer);
	
	List<Order> findByCustomerLike(String customer);

	List<Order> findByCustomerAndType(String customer, String type);

	List<Order> getByType(String type);
		
	@Query("{customer:'Chuck Wagon'}")
	List<Order> findChucksOrders();

}
