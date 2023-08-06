package com.shruteekatech.electronicstore.util;

import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination<T> {
    private List<T> content;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElement;
    private Integer totalPages;
    private Boolean lastPage;

    public static Pageable from(Integer pageNo, Integer pageSize, String order, String sortUsing) {
        Sort sort = (order.equalsIgnoreCase("desc")) ?
                Sort.by(sortUsing).descending() : Sort.by(sortUsing).ascending();

        return PageRequest.of(pageNo, pageSize, sort);
    }

    public static <T, E> Pagination<T> of(Page<E> page, Class<T> dtoClass) {
        Page<T> dtoPage = page.map(entity -> new ModelMapper().map(entity, dtoClass));

        return Pagination.<T>builder()
                .content(dtoPage.getContent())
                .pageNo(dtoPage.getNumber())
                .pageSize(dtoPage.getSize())
                .totalElement(dtoPage.getTotalElements())
                .totalPages(dtoPage.getTotalPages())
                .lastPage(dtoPage.isLast())
                .build();
    }
}
