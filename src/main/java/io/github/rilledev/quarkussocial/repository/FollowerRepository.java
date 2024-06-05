package io.github.rilledev.quarkussocial.repository;

import io.github.rilledev.quarkussocial.model.Follower;
import io.github.rilledev.quarkussocial.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean follows(User follower, User user){
        Map<String, Object> params = Parameters.with("follower", follower).and("user", user).map();
        PanacheQuery<Follower> query = find("follower = :follower and user = :user",params);
        Optional<Follower> result = query.firstResultOptional();

        return result.isPresent();
    }

    public List<Follower> findByUser(Long userId){
        PanacheQuery<Follower> query = find("user.id", userId);
        return query.list();
    }

    public void deleteByFallowerAndUser(Long followerId, Long userId) {
        Map<String, Object> params = Parameters
                .with("userId", userId)
                .and("followerId", followerId)
                .map();

        delete("follower.id = :followerId and user.id = :userId", params);
    }
}
