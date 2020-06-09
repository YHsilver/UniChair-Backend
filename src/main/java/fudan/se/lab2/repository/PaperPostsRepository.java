package fudan.se.lab2.repository;


import fudan.se.lab2.domain.conference.PaperPosts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperPostsRepository extends CrudRepository<PaperPosts, Long> {
    PaperPosts findByPostsId(Long id);
}
