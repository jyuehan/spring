package com.defense.repository;

import com.defense.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepo extends JpaRepository<Policy, Long> {

    Policy findByName(String name);
}
