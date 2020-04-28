package fudan.se.lab2.repository;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author LBW
 * 在仓库中找名字，大海捞针
 */

@Repository
public interface PaperRepository extends CrudRepository<Paper, Long> {
    Paper findByPaperId(Long id);
    Set<Paper> findPapersByConference(Conference conference);
    Set<Paper> findPapersByAuthor(User author);
    Set<Paper> findPapersByAuthorAndConference(User author, Conference conference);
}
