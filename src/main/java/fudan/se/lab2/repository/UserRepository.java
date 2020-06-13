package fudan.se.lab2.repository;

import fudan.se.lab2.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author LBW
 * 在仓库中找名字，大海捞针
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findUserById(Long id);
    Set<User> findAll();
}
