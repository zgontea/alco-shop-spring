package com.shop.alcoshopspring.wrappers;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ImageWrapper {
    @NotNull
    private final String image;
}
