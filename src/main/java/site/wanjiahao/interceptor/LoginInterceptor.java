package site.wanjiahao.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import site.wanjiahao.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        // 认证请求
        String[] requireAuthPages = new String[]{
                "buy",
                "alipay",
                "payed",
                "cart",
                "bought",
                "confirmPay",
                "orderConfirmed",
                "fore_buyone",
                "fore_buy",
                "fore_addCart",
                "fore_cart",
                "fore_changeOrderItem",
                "fore_deleteOrderItem",
                "fore_createOrder",
                "fore_payed",
                "fore_bought",
                "fore_confirmPay",
                "fore_orderConfirmed",
                "fore_deleteOrder",
                "fore_review",
                "fore_doreview"
        };
        String uri = request.getRequestURI();

        uri = StringUtils.delete(uri, contextPath+"/");
        String page = uri;
        if(begingWith(page, requireAuthPages)){
            User user = (User) session.getAttribute("user");
            if(user==null) {
                response.sendRedirect("login");
                return false;
            }
        }
        return true;
    }

    private boolean begingWith(String page, String[] requiredAuthPages) {
        boolean result = false;
        for (String requiredAuthPage : requiredAuthPages) {
            if(StringUtils.startsWithIgnoreCase(page, requiredAuthPage)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
