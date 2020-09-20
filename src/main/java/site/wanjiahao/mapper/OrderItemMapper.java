package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Order;
import site.wanjiahao.pojo.OrderItem;

import java.util.List;

public interface OrderItemMapper extends JpaRepository<OrderItem, Integer> {

    // 查询某个订单下的订单项
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
}
