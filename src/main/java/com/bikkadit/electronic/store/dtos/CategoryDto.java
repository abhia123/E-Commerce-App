package com.bikkadit.electronic.store.dtos;

import com.bikkadit.electronic.store.validate.ImageNameValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private String categoryId;

    @NotBlank
    @Min(value = 4, message = "title must be of minimum 4 characters")
    private String title;

    @NotBlank
    @Min(value = 4, message = "description must be of minimum 4 characters")
    private String description;

    @ImageNameValid
    private String coverImage;
}
