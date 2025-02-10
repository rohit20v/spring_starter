package it.objectmethod.esercizi.spring_starter.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {

    private List<T> data;
    private Integer pageNumber;
    private Integer pageSize;
    private Boolean last;
    private Boolean first;
    private Integer totalPages;

    public PaginationResponse(Page<T> page) {
        this.data = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.last = page.isLast();
        this.first = page.isFirst();
        totalPages = page.getTotalPages();

    }
}