package com.ecommerce.productservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.Instant;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Entity(name = "Categories")
@Data
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String categoryId;
  private String categoryName;
  @CreationTimestamp
  @Column(name = "created_timestamp", nullable = false, updatable = false)
  private Instant createdAt;
  @UpdateTimestamp
  @Column(name = "updated_timestamp", nullable = false)
  private Instant updatedAt;
  @OneToMany(mappedBy = "category")
  private List<Product> products;

}
