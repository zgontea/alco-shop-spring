package com.shop.alcoshopspring.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Data
@SuperBuilder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotBlank
    private BigDecimal unitPrice;

    @NotBlank
    private String description;

    @NotBlank
    private BigDecimal size;

    @NotBlank
    private BigDecimal concentration;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
