package site.wanjiahao.service;

import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.User;

public interface UserService {

    Page4Navigator<User> findAll(int start, int size, int navigateNums);

    Page4Navigator<User> findAll(int start, int size);
}
