package it.objectmethod.esercizi.spring_starter.util;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;


@Data
public class PaginationResponse<T> {

    private final List<T> data;
    private final Integer pageNumber;
    private final Integer pageSize;
    private final Boolean last;
    private final Boolean first;
    private final Integer totalPages;

    public PaginationResponse(Page<T> page) {
        this.data = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.last = page.isLast();
        this.first = page.isFirst();
        totalPages = page.getTotalPages();

    }
}