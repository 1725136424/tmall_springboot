package site.wanjiahao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import site.wanjiahao.service.OrderItemService;

@RestController
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;
}
