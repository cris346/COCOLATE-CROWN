package com.crown.repository;

import com.crown.entity.ChocolateFlavor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChocolateFlavorRepository extends JpaRepository<ChocolateFlavor, Long> {
    Optional<ChocolateFlavor> findByName(String name);
}
