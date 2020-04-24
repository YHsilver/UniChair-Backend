package fudan.se.lab2.repository;

import fudan.se.lab2.domain.conference.Conference;

import fudan.se.lab2.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author PXY
 * 会议仓库找会议
 */

@Repository
public interface ConferenceRepository extends CrudRepository<Conference, Long> {
    Conference findByConferenceId(Long id);

    Set<Conference> findByConferenceAbbreviation(String conferenceAbbreviation);

    Set<Conference> findByConferenceFullName(String conferenceFullName);

    Set<Conference> findByChairMan(User chairMan);

    Set<Conference> findConferencesByAuthorSetContains(User author);

    Set<Conference> findConferencesByReviewerSetContains(User reviewer);
}
