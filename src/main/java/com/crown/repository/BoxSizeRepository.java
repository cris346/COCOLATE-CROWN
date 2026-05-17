package com.crown.repository;

import com.crown.entity.BoxSize;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BoxSizeRepository extends JpaRepository<BoxSize, Long> {
    Optional<BoxSize> findByName(String name);
}
