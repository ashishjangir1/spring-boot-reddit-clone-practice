package org.ashishjangir.springredditclone.repository;

import org.ashishjangir.springredditclone.model.Comment;
import org.ashishjangir.springredditclone.model.Post;
import org.ashishjangir.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
