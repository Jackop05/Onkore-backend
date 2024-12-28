package com.onkore_backend.onkore.Repository;

import com.onkore_backend.onkore.Model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Object> findByEmail(String email);
}