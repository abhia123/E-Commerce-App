package com.bikkadit.electronic.store.dtos;

import com.bikkadit.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private String productId;

    @NotEmpty
    @Size(min = 4,message = "Title is required")
    private String title;

    @NotEmpty
    @Size(min=10,message = "Description is required")
    private String description;

    private Double price;

    private int discountedPrice;

    private Integer quantity;

    private Date addedDate;

    private Boolean isLive;

    private Boolean isStock;

    @ImageNameValid
    private String productImageName;

    private CategoryDto categoryDto;

}
