package com.crown.repository;

import com.crown.entity.Filling;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FillingRepository extends JpaRepository<Filling, Long> {
    Optional<Filling> findByName(String name);
}
