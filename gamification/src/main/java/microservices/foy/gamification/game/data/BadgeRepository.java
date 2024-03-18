package microservices.foy.gamification.game.data;

import microservices.foy.gamification.game.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeCard, Long> {
    /**
     * Retrieve all badgeCards for a given user id
     *
     * @param userId id of the user
     * @return list of BadgeCards sorted by most recent
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}
