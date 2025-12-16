package com.E_Commerce.productservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "Categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
}
