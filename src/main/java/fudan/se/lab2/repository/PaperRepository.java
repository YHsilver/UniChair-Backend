package fudan.se.lab2.repository;

import fudan.se.lab2.domain.conference.Paper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LBW
 * 在仓库中找名字，大海捞针
 */

@Repository
public interface PaperRepository extends CrudRepository<Paper, Long> {
    Paper findByConferenceId(Long id);
}
