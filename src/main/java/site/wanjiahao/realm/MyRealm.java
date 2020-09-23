package site.wanjiahao.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import site.wanjiahao.field.RealmConst;
import site.wanjiahao.pojo.User;
import site.wanjiahao.service.UserService;


public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 认证
    @Override
    protected SimpleAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        User user = userService.findByName(username);
        if (user == null) {
            // return null 抛出UnknownAccountException异常
            return null;
        } 
       return new SimpleAuthenticationInfo(user.getUsername(),
               user.getPassword(),
               ByteSource.Util.bytes(RealmConst.SALT),
               this.getName());
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 没有权限直接返回
        return new SimpleAuthorizationInfo();
    }

}
