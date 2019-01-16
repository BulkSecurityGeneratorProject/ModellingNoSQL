package com.tomaszekem.modelling.repository;

import com.tomaszekem.modelling.domain.Post;
import com.tomaszekem.modelling.domain.enumeration.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Long countAllByCategoryEquals(Category category);

}
