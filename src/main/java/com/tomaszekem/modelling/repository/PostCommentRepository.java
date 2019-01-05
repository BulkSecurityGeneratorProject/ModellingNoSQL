package com.tomaszekem.modelling.repository;

import com.tomaszekem.modelling.domain.PostComment;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the PostComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostCommentRepository extends MongoRepository<PostComment, String> {

}
