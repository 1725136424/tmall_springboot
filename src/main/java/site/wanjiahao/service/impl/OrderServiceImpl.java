package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;;
import site.wanjiahao.mapper.OrderMapper;
import site.wanjiahao.pojo.Order;
import site.wanjiahao.pojo.OrderItem;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.service.OrderService;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Page4Navigator<Order> list(int start, int size, int navigatePages) {
        Sort sort =  Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Order> pageFromJPA = orderMapper.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    // 移出订单项中的订单，保证出现无限递归问题
    @Override
    public void removeOrderFromOrderItem(List<Order> orders) {
        for (Order order : orders) {
            removeOrderFromOrderItem(order);
        }
    }

    @Override
    public void removeOrderFromOrderItem(Order order) {
        List<OrderItem> orderItems= order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(null);
        }
    }

    @Override
    public Order findOne(int oid) {
        return orderMapper.findById(oid).get();
    }

    @Override
    public Order update(Order bean) {
        return orderMapper.save(bean);
    }
}
