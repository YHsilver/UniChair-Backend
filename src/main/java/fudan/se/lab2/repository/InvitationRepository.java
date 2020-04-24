package fudan.se.lab2.repository;

import fudan.se.lab2.domain.conference.Invitation;
import fudan.se.lab2.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

/**
 * @author hyf
 * 邀请函仓库找邀请函
 */

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    Invitation findByInvitationId(Long id);
    Set<Invitation> findByConferenceId(Long conferenceId);
    Set<Invitation> findByReviewer(User reviewer);
}
