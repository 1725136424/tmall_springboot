package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page4Navigator<User> findAll(int start, int size, int navigateNums) {
        Sort sort = Sort.by(Sort.Direction.DESC, "uid");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<User> page = userMapper.findAll(pageable);
        return new Page4Navigator<>(page, navigateNums);
    }

    @Override
    public Page4Navigator<User> findAll(int start, int size) {
        return findAll(start, size, 5);
    }

    @Override
    public boolean isExist(String name) {
        User user = findByName(name);
        return null != user;
    }

    @Override
    public User findByName(String name) {
        return userMapper.findByUsername(name);
    }

    @Override
    public User save(User user) {
        return userMapper.save(user);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password);
    }
}
