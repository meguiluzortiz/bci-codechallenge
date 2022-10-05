package com.example.bci.bussiness.repository;

import com.example.bci.bussiness.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByEmail(String email);
}
