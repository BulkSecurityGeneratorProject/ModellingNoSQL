package com.tomaszekem.modelling.repository;

import com.tomaszekem.modelling.domain.File;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the File entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileRepository extends MongoRepository<File, String> {

}
