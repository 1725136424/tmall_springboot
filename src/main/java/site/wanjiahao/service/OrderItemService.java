package site.wanjiahao.service;

import site.wanjiahao.pojo.Order;
import site.wanjiahao.pojo.OrderItem;
import site.wanjiahao.pojo.Product;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {

    void fill(List<Order> orders);

    // 设置订单的金额，总数，订单项
    void fill(Order order);

    List<OrderItem> listByOrder(Order order);

    void update(OrderItem orderItem);

    public OrderItem save(OrderItem orderItem);

    OrderItem findOne(int id);

    void delete(int id);

    int findSaleCount(Product product);

    List<OrderItem> listByProduct(Product product);

}
