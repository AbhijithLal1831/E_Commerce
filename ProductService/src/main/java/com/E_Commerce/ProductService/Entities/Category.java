package com.E_Commerce.ProductService.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Categories")
public class Category {
  @Id
  private String id;
  private String name;
}
