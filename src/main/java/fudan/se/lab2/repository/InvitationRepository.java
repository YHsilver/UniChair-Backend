package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Invitation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hyf
 * 邀请函仓库找邀请函
 */

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    Invitation findByInvitationId(Long id);
}
