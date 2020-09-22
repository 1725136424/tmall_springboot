package site.wanjiahao.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.OrderItem;
import site.wanjiahao.pojo.User;
import site.wanjiahao.service.CategoryService;
import site.wanjiahao.service.OrderItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class DataInterceptor implements HandlerInterceptor {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderItemService orderItemService;

    /*
    *   对于多个页面，并且需要同一样的数据，可以使用拦截器来处理，只需处理一处，就可以达到多出变化的效果
    * */
    // 还未渲染页面处理
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        int  cartTotalItemNumber = 0;
        if(null != user) {
            List<OrderItem> ois = orderItemService.findByUserAndOrderIsNull(user);
            for (OrderItem oi : ois) {
                cartTotalItemNumber += oi.getNum();
            }

        }
        List<Category> cs = categoryService.findAll();
        String contextPath=httpServletRequest.getServletContext().getContextPath();
        // 分类搜索数据结
        httpServletRequest.getServletContext().setAttribute("categories_below_search", cs);
        // 购物车总数
        session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
        // 当前工程路径
        httpServletRequest.getServletContext().setAttribute("contextPath", contextPath);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
