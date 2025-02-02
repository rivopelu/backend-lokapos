package com.lokapos.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseParseFlagId {
    private String flag;
    private String id;
}
