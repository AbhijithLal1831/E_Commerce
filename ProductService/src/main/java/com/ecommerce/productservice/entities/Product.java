package com.ecommerce.productservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity(name = "Products")
@Data
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String productId;

  @Column(nullable = false)
  private String name;

  private String description;
  @Column(nullable = false)
  private BigDecimal price;

  @CreationTimestamp
  @Column(name = "created_timestamp", nullable = false, updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_timestamp", nullable = false)
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "categoryId")
  private Category category;
}
