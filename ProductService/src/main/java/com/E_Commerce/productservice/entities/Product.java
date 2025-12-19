package com.E_Commerce.productservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Entity(name = "Products")
@Data
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String productId;
  private String name;
  private String description;
  private BigDecimal price;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "categoryId")
  private Category category;
}
