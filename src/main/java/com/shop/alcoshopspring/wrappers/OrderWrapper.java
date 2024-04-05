package com.shop.alcoshopspring.wrappers;

import java.math.BigDecimal;

import com.shop.alcoshopspring.models.User;

import lombok.Data;

@Data
public class OrderWrapper {
    private final Long id;
    private final String createdDate;
    private final String shipAddress;
    private final String shipCity;
    private final String shipEmail;
    private final String shipPhoneNo;
    private final String shipPostalCode;
    private final String status;
    private final BigDecimal totalPrice;
    private final User user;
}
