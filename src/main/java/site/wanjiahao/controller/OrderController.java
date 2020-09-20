package site.wanjiahao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.wanjiahao.pojo.Order;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.RESTFULResult;
import site.wanjiahao.service.OrderItemService;
import site.wanjiahao.service.OrderService;
import site.wanjiahao.service.ProductImageService;

import java.util.Date;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private RESTFULResult restfulResult;

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/orders")
    public Page4Navigator<Order> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                      @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        Page4Navigator<Order> page = orderService.list(start, size, 5);
        // 填充订单下的额外数据
        orderItemService.fill(page.getContent());
        // orderService.removeOrderFromOrderItem(page.getContent());
        return page;
    }

    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) {
        try {
            Order order = orderService.findOne(oid);
            order.setDeliveryDate(new Date());
            order.setStatus(OrderService.waitConfirm);
            orderService.update(order);
            restfulResult.setSuccess(true);
            restfulResult.setMessage("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setSuccess(true);
            restfulResult.setMessage("修改成功");
        }
        return restfulResult;
    }
}
