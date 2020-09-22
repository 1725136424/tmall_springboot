package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Order;
import site.wanjiahao.pojo.OrderItem;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.User;

import java.util.List;

public interface OrderItemMapper extends JpaRepository<OrderItem, Integer> {

    // 查询某个订单下的订单项
    List<OrderItem> findByOrderOrderByIdDesc(Order order);

    // 查询某个产品下的订单项
    List<OrderItem> findByProduct(Product product);

    // 查询当前用户未生成订单的订单项
    List<OrderItem> findByUserAndOrderIsNull(User user);
}
