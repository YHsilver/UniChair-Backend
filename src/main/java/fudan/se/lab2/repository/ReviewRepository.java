package fudan.se.lab2.repository;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    Review findByReviewId(Long reviewId);

    Set<Review> findReviewsByConference(Conference conference);
    Set<Review> findReviewsByReviewer(User reviewer);
    Review findReviewByConferenceAndReviewer(Conference conference, User reviewer);
}
