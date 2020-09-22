package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Order;
import site.wanjiahao.pojo.User;

import java.util.List;

public interface OrderMapper extends JpaRepository<Order, Integer> {

    // 查询订单，但状态又不是删除
    List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);
}

