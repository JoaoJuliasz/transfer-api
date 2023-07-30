package com.transferproject.persistence.repository;

import com.transferproject.persistence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

    public interface IUserRepository extends ReactiveMongoRepository<User, String> {

    @Query("{ 'email' : ?0 }")
    Mono<User> findUserEmail(String email);

    @Query("{ 'document' : ?0 }")
    Mono<User> findUserDocument(String document);
}
