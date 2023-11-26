package com.bikkadit.electronic.store.helper;

import com.bikkadit.electronic.store.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.stream.Collectors;

public class PageableHelper {

    public static  <U,V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> classType){
        List<U> pageContent = page.getContent();
        List<V> dtoList = pageContent.stream().map(object -> new ModelMapper().map(object,classType)).collect(Collectors.toList());
        PageableResponse<V> response= new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }
}
