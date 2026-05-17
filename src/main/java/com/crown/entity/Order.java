package com.crown.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer order.
 * Contains address snapshot (so historical orders aren't affected by profile changes).
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50)
    @Builder.Default
    private String status = "PENDIENTE_DEMO";

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    // --- Address Snapshot ---
    @Column(name = "calle_numero", nullable = false)
    private String calleNumero;

    @Column(nullable = false, length = 150)
    private String colonia;

    @Column(name = "codigo_postal", nullable = false, length = 20)
    private String codigoPostal;

    @Column(nullable = false, length = 150)
    private String ciudad;

    @Column(nullable = false, length = 150)
    private String estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
