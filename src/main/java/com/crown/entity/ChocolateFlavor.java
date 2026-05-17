package com.crown.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Catalog table: chocolate base flavors (Dulce, Amargo, Blanco, etc.)
 * Extensible - new flavors can be added via DB insert.
 */
@Entity
@Table(name = "chocolate_flavors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChocolateFlavor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;
}
