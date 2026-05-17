package com.crown.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Catalog table: box sizes (Chica, Grande, etc.)
 * Extensible - new sizes can be added via DB insert.
 */
@Entity
@Table(name = "box_sizes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoxSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;
}
