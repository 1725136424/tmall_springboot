package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.User;

public interface UserMapper extends JpaRepository<User, Integer> {
}
