package com.lokapos.model.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BaseResponse {
    @Builder.Default
    private Boolean success = true;
    private String message;
    private Object responseData;
    private Boolean isFirst;
    private Boolean isLast;
    private Boolean hasNext;
    private Integer currentPage;
    private Integer totalPage;
    private Long totalData;
    private Integer pageSize;
    private ErrorResponse errors;
    private ResponsePaginatedData paginatedData;
}

