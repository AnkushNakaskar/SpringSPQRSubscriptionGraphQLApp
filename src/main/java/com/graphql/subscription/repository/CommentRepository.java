package com.graphql.subscription.repository;

import com.graphql.subscription.bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ankushnakaskar
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);

    void deleteById(Long id);
}
