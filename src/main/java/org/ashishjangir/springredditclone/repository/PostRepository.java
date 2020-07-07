package org.ashishjangir.springredditclone.repository;

import org.ashishjangir.springredditclone.model.Post;
import org.ashishjangir.springredditclone.model.Subreddit;
import org.ashishjangir.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
