package com.lokapos.utils;

import com.lokapos.model.response.ResponseParseFlagId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

public class UtilsTest {

    @Test
    public void testParseFlagId() {
        String flagId = "TU";
        String flagId2 = "PD";
        String id = UUID.randomUUID().toString();
        String parseFlag = UtilsHelper.generateIdFlag(flagId, id);
        String parseFlag2 = UtilsHelper.generateIdFlag(flagId2, id);
        ResponseParseFlagId parseId = UtilsHelper.parseIdFromFlg(parseFlag);
        ResponseParseFlagId parseId2 = UtilsHelper.parseIdFromFlg(parseFlag2);
        if (Objects.equals(parseId.getFlag(), flagId)){
            Assertions.assertEquals(id, parseId.getId());
            Assertions.assertEquals(flagId, parseId.getFlag());
        }
        if (Objects.equals(parseId2.getFlag(), flagId2)){
            Assertions.assertEquals(id, parseId2.getId());
            Assertions.assertEquals(flagId2, parseId2.getFlag());
        }
    }

    @Test
    public void testNoFlag() {
        String flagId = "TU";
        String id = UUID.randomUUID().toString();
        String parseFlag = UtilsHelper.generateIdFlag(flagId, id);
        ResponseParseFlagId parseId = UtilsHelper.parseIdFromFlg(parseFlag);
        if (Objects.equals(parseId.getFlag(), flagId)){
            Assertions.assertEquals(id, parseId.getId());
        }

    }
}
