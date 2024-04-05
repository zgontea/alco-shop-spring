package com.shop.alcoshopspring.wrappers;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductWrapper {
    @NotBlank
    private final Long id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String image;
    @NotBlank
    private final String description;
    @NotNull
    @Min(0)
    private final BigDecimal unitPrice;
    @NotNull
    @Min(0)
    private final BigDecimal concentration;
    @NotNull
    @Min(0)
    private final BigDecimal size;
    @NotNull
    private final String categoryName;
}
