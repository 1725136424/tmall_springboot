package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.User;

public interface UserMapper extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByUsernameAndPassword(String name, String password);
}
