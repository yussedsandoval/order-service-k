package com.logitrack.order.entity;

import com.logitrack.order.util.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    public Orders(UUID userId) {
        this.userId = userId;
        this.status = OrderStatus.CREATED;
    }

    public void addItem(Product product, Long quantity) {
        OrderItem orderItem = new OrderItem(
                this, product, quantity, product.getPrice()
        );
        items.add(orderItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orders other)) return false;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Orders.class.hashCode();
    }
}
