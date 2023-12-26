package ru.trainee.creditmanager.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * DTO для оборачивания постраничных ответов
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableListResponseDTO<T> {
    @JsonProperty("page_number")
    Integer page;
    @JsonProperty("page_size")
    Integer size;
    @JsonProperty("total_items")
    Long totalItems;
    @JsonProperty("total_pages")
    Integer totalPages;
    @JsonProperty("content")
    List<T> items;
}
