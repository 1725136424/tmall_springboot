package site.wanjiahao.service;

import site.wanjiahao.pojo.Order;
import site.wanjiahao.pojo.OrderItem;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.User;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    Page4Navigator<Order> list(int start, int size, int navigatePages);

    // 移出订单项中的订单，保证出现无限递归问题
    void removeOrderFromOrderItem(List<Order> orders);

    void removeOrderFromOrderItem(Order order);

    Order findOne(int oid);

    Order update(Order bean);

    Order save(Order order);

    BigDecimal save(Order order, List<OrderItem> ois);

    List<Order> listByUserWithoutDelete(User user);

    void cacl(Order o);
}
