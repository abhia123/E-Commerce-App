package com.bikkadit.electronic.store.dtos;

import com.bikkadit.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {


    private String categoryId;

    @NotBlank
    @Size(min = 4, message = "title must be of minimum 4 characters")
    private String title;

    @NotBlank
    @Size(min = 4, message = "description must be of minimum 4 characters")
    private String description;

    @ImageNameValid
    private String coverImage;
}
