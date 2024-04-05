package com.shop.alcoshopspring.wrappers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.shop.alcoshopspring.models.OrderDetail;

import lombok.Data;

@Data
public class ShoppingCartItemWrapper {
    @NotBlank
    private final Long userId;
    @NotNull
    private final OrderDetail orderDetail;
}
