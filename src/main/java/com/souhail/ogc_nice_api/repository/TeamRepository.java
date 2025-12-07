package com.souhail.ogc_nice_api.repository;

import com.souhail.ogc_nice_api.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
