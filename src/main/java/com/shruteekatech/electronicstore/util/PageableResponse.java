package com.shruteekatech.electronicstore.util;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T> {
    private List<T> content;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElement;
    private Integer totalPages;
    private Boolean lastPage;


    public static <T> PageableResponse<T> of(List<T> content, Page<?> page) {

        return PageableResponse.<T>builder()
                .content(content)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElement(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .lastPage(page.isLast())
                .build();
    }
}
