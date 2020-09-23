package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import site.wanjiahao.pojo.User;
import site.wanjiahao.service.OrderItemService;
import site.wanjiahao.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "orders")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemService orderItemService;

    @Cacheable(key = "'orders-page-' + #p0 + '-' + #p1")
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
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(null);
        }
    }

    @Cacheable(key = "'orders-one-' + #p0")
    @Override
    public Order findOne(int oid) {
        return orderMapper.findById(oid).get();
    }

    @CacheEvict(allEntries = true)
    @Override
    public Order update(Order bean) {
        return orderMapper.save(bean);
    }

    @CacheEvict(allEntries = true)
    @Override
    public BigDecimal save(Order order, List<OrderItem> ois) {
        BigDecimal total = BigDecimal.ZERO;
        save(order);
        for (OrderItem oi: ois) {
            oi.setOrder(order);
            orderItemService.update(oi);
            total =
                    total.add(oi.getProduct().getPromotePrice().multiply(BigDecimal.valueOf(oi.getNum())));
        }
        return total;
    }

    @CacheEvict(allEntries = true)
    @Override
    public Order save(Order order) {
        return orderMapper.save(order);
    }

    @Cacheable(key = "'orders-uid-' + #p0.uid")
    @Override
    public List<Order> listByUserWithoutDelete(User user) {
        // 查询未删除的订单
        return orderMapper.findByUserAndStatusNotOrderByIdDesc(user, OrderService.delete);
    }

    @Override
    public void cacl(Order o) {
        List<OrderItem> orderItems = o.getOrderItems();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            total =
                    total.add(orderItem.getProduct().getPromotePrice().multiply(BigDecimal.valueOf(orderItem.getNum())));
        }
        o.setTotal(total);
    }
}
