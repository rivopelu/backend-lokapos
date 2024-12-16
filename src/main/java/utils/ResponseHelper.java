package utils;

import com.lokapos.model.response.BaseResponse;
import com.lokapos.model.response.ResponsePaginatedData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public class ResponseHelper {
    public static <T> BaseResponse createBaseResponse(Page<T> data) {
        ResponsePaginatedData paginatedData = ResponsePaginatedData.builder()
                .size(data.getPageable().getPageSize())
                .page(data.getPageable().getPageNumber())
                .pageCount(((int) (data.getTotalElements() / data.getPageable().getPageSize()))+ 1)
                .totalData((int) data.getTotalElements())
                .build();
        return BaseResponse.builder()
                .responseData(data.getContent())
                .paginatedData(paginatedData)
                .build();
    }

    public static <T> BaseResponse createBaseResponse(Slice<T> data) {
        return BaseResponse.builder()
                .isFirst(data.isFirst())
                .isLast(data.isLast())
                .hasNext(data.hasNext())
                .responseData(data.getContent())
                .totalData(Long.parseLong(String.valueOf(data.getNumberOfElements())))
                .currentPage(data.getPageable().getPageNumber())
                .build();
    }

    public static <T> BaseResponse createBaseResponse(List<T> data) {
        return BaseResponse.builder()
                .responseData(data)
                .build();
    }

    public static <T> BaseResponse createBaseResponse(T data) {
        return BaseResponse.builder()
                .responseData(data)
                .build();
    }

    public static BaseResponse createBaseResponse(String massage) {
        return BaseResponse.builder()
                .message(massage)
                .build();
    }

    public static BaseResponse createBaseResponse() {
        return BaseResponse.builder()
                .build();
    }
}

