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
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.UserMapper;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.User;
import site.wanjiahao.service.UserService;
import site.wanjiahao.utils.SpringContextUtil;

@Service
@Transactional
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Cacheable(key = "'users-page-' + #p0 + '-' + #p1")
    @Override
    public Page4Navigator<User> findAll(int start, int size, int navigateNums) {
        Sort sort = Sort.by(Sort.Direction.DESC, "uid");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<User> page = userMapper.findAll(pageable);
        return new Page4Navigator<>(page, navigateNums);
    }

    @Override
    public Page4Navigator<User> findAll(int start, int size) {
        UserService userService = SpringContextUtil.getBean(UserService.class);
        return userService.findAll(start, size, 5);
    }

    @Override
    public boolean isExist(String name) {
        UserService userService = SpringContextUtil.getBean(UserService.class);
        User user = userService.findByName(name);
        return null != user;
    }

    @Cacheable(key = "'users-username-' + #p0")
    @Override
    public User findByName(String name) {
        return userMapper.findByUsername(name);
    }

    @CacheEvict(allEntries = true)
    @Override
    public User save(User user) {
        return userMapper.save(user);
    }

    @Cacheable(key = "'users-username-' + #p0 + '-password-' + #p1")
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password);
    }
}
