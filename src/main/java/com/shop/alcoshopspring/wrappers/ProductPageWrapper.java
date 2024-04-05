package com.shop.alcoshopspring.wrappers;

import com.shop.alcoshopspring.models.Product;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ProductPageWrapper {
    @NotNull
    List<Product> products;
    @NotNull
    long itemsAmount;
}
