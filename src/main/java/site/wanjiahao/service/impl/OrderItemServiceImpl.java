package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.OrderItemMapper;
import site.wanjiahao.mapper.ProductImageMapper;
import site.wanjiahao.pojo.Order;
import site.wanjiahao.pojo.OrderItem;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.service.OrderItemService;
import site.wanjiahao.service.ProductImageService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductImageService productImageService;

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    // 设置订单的金额，总数，订单项
    @Override
    public void fill(Order order) {
        List<OrderItem> orderItems = listByOrder(order);
        BigDecimal total = BigDecimal.ZERO;
        int totalNumber = 0;
        for (OrderItem orderItem :orderItems) {
            Product product = orderItem.getProduct();
            productImageService.setFirstProductImages(product);
            BigDecimal promotePrice = orderItem.getProduct().getPromotePrice();
            Integer num = orderItem.getNum();
            // 计算总金额
            total = total.add(promotePrice.multiply(BigDecimal.valueOf(num)));
            // 计算总数
            totalNumber += num;
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
    }

    @Override
    public List<OrderItem> listByOrder(Order order) {
        return orderItemMapper.findByOrderOrderByIdDesc(order);
    }
}
