package com.crown.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Catalog table: fillings (Choco avellana, Fresa, etc.)
 * Extensible - new fillings can be added via DB insert.
 */
@Entity
@Table(name = "fillings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;
}
