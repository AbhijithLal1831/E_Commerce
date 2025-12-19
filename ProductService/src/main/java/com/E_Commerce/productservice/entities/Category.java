package com.E_Commerce.productservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity(name = "Categories")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String categoryId;
  private String categoryName;

  @OneToMany(mappedBy = "category")
  private List<Product> products;

}
