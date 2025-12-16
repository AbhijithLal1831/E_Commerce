package com.E_Commerce.ProductService.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "Products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id; // UUID recommended
  private String name;
  private String description;
  private BigDecimal price;

  // We store minimal category info here or a reference ID
  private String categoryId;
}
