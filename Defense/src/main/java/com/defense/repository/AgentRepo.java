package com.defense.repository;

import com.defense.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

public interface AgentRepo extends JpaRepository<Agent, Long>{

    Agent findBySourceId(String sourceId);
}
