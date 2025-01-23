package com.lokapos.services;

import com.lokapos.entities.City;
import com.lokapos.entities.District;
import com.lokapos.entities.Province;
import com.lokapos.entities.SubDistrict;
import com.lokapos.model.response.ResponseArea;
import com.lokapos.model.response.ResponseFullAddress;

import java.awt.geom.Area;
import java.math.BigInteger;
import java.util.List;

public interface AreaService {
    List<ResponseArea> getProvince();

    List<ResponseArea> getCity(BigInteger provinceId);

    List<ResponseArea> getDistrict(BigInteger cityId);

    List<ResponseArea> getSubDistrict(BigInteger districtId);

    String getFullAddress(BigInteger provinceId, BigInteger cityId, BigInteger districtId, BigInteger subDistrictId);

    String getFullAddress(Province province, City city, District district, SubDistrict subDistrict);

    List<ResponseFullAddress> getAreaSubDistrict(String q);

    Province getProvinceById(BigInteger id);

    City getCityById(BigInteger id);

    District getDistrictById(BigInteger id);

    SubDistrict getSubDistrictById(BigInteger id);

}
