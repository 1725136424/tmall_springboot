package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Order;

public interface OrderMapper extends JpaRepository<Order, Integer> {
}
