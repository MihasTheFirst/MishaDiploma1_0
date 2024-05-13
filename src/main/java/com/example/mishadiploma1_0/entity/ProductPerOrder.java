package com.example.mishadiploma1_0.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class ProductPerOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private BigDecimal pricePerOne;
  private Long amount;
  @Enumerated(value = EnumType.STRING)
  private Measure measure;
  @ManyToOne
  private Order order;

}
