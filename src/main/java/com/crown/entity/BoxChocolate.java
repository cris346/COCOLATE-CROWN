package com.crown.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a single chocolate inside a box (order_item).
 * Links the flavor + filling combination chosen by the customer.
 */
@Entity
@Table(name = "box_chocolates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoxChocolate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chocolate_flavor_id", nullable = false)
    private ChocolateFlavor chocolateFlavor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filling_id", nullable = false)
    private Filling filling;
}
