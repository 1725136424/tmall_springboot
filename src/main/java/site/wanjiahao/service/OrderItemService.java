package site.wanjiahao.service;

import site.wanjiahao.pojo.Order;
import site.wanjiahao.pojo.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {

    void fill(List<Order> orders);

    // 设置订单的金额，总数，订单项
    void fill(Order order);

    List<OrderItem> listByOrder(Order order);

}
