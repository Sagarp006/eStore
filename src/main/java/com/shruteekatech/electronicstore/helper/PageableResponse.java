package com.shruteekatech.electronicstore.helper;
import lombok.*;


import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PageableResponse<T> {
    private List<T> content;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElement;
    private Integer totalPages;
    private Boolean lastPage;
}
