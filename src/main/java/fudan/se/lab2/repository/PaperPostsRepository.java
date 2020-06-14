package fudan.se.lab2.repository;


import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.PaperPosts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PaperPostsRepository extends CrudRepository<PaperPosts, Long> {
    PaperPosts findByPostsId(Long id);
    Set<PaperPosts> findByUser(User user);
}
