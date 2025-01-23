package com.lokapos.services.impl;

import com.lokapos.entities.City;
import com.lokapos.entities.District;
import com.lokapos.entities.Province;
import com.lokapos.entities.SubDistrict;
import com.lokapos.enums.RESPONSE_ENUM;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.response.ResponseArea;
import com.lokapos.model.response.ResponseFullAddress;
import com.lokapos.repositories.CityRepository;
import com.lokapos.repositories.DistrictRepository;
import com.lokapos.repositories.ProvinceRepository;
import com.lokapos.repositories.SubDistrictRepository;
import com.lokapos.services.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final SubDistrictRepository subDistrictRepository;

    @Override
    public List<ResponseArea> getProvince() {
        try {
            List<Province> provinces = provinceRepository.findAll();
            List<ResponseArea> ResponseAreaList = new ArrayList<>();
            for (Province province : provinces) {
                ResponseArea.ResponseAreaBuilder responseArea = ResponseArea.builder();
                responseArea.id(province.getId()).name(province.getName());
                ResponseAreaList.add(responseArea.build());
            }
            return ResponseAreaList;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseArea> getCity(BigInteger provinceId) {
        try {
            List<City> cityList = cityRepository.findAllByProvinceId(provinceId);
            List<ResponseArea> responseAreas = new ArrayList<>();
            for (City e : cityList) {
                ResponseArea.ResponseAreaBuilder responseArea = ResponseArea.builder();
                responseArea.id(e.getId()).name(e.getName());
                responseAreas.add(responseArea.build());
            }
            return responseAreas;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseArea> getDistrict(BigInteger cityId) {
        try {
            List<District> districtList = districtRepository.findAllByCityId(cityId);
            List<ResponseArea> responseAreas = new ArrayList<>();
            districtList.forEach(e -> {
                ResponseArea.ResponseAreaBuilder responseArea = ResponseArea.builder();
                responseArea.id(e.getId()).name(e.getName());
                responseAreas.add(responseArea.build());
            });
            return responseAreas;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseArea> getSubDistrict(BigInteger districtId) {
        try {
            List<SubDistrict> subDistricts = subDistrictRepository.findAllByDistrictId(districtId);
            List<ResponseArea> responseAreas = new ArrayList<>();
            subDistricts.forEach(e -> {
                ResponseArea.ResponseAreaBuilder responseArea = ResponseArea.builder();
                responseArea.id(e.getId()).name(e.getName());
                responseAreas.add(responseArea.build());
            });
            return responseAreas;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String getFullAddress(BigInteger provinceId, BigInteger cityId, BigInteger districtId, BigInteger subDistrictId) {
        StringBuilder addressBuilder = new StringBuilder();

        provinceRepository.findById(provinceId).ifPresent(province -> addressBuilder.append(province.getName()).append(", "));
        cityRepository.findById(cityId).ifPresent(city -> addressBuilder.append(city.getName()).append(", "));
        districtRepository.findById(districtId).ifPresent(district -> addressBuilder.append(district.getName()).append(", "));
        subDistrictRepository.findById(subDistrictId).ifPresent(subDistrict -> addressBuilder.append(subDistrict.getName()));

        return addressBuilder.toString();
    }



    @Override
    public String getFullAddress(Province province, City city, District district, SubDistrict subDistrict) {

        try {
            return province.getName() + ", " +
                    city.getName() + ", " +
                    district.getName() + ", " +
                    subDistrict.getName();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseFullAddress> getAreaSubDistrict(String q) {
        try {

            List<ResponseFullAddress> responses = new ArrayList<>();
            List<SubDistrict> subDistricts = subDistrictRepository.findByQuerySearch(q);
            for (SubDistrict subDistrict : subDistricts) {
                ResponseFullAddress responseFullAddress = ResponseFullAddress.builder()
                        .fullAddress(getFullAddress(
                                subDistrict.getProvinceId(),
                                subDistrict.getCityId(),
                                subDistrict.getDistrictId(),
                                subDistrict.getId()
                        )).province(subDistrict.getProvinceId())
                        .City(subDistrict.getCityId())
                        .district(subDistrict.getDistrictId())
                        .SubDistrict(subDistrict.getDistrictId())

                        .build();
                responses.add(responseFullAddress);
            }
            return responses;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Province getProvinceById(BigInteger id) {
        return provinceRepository.findById(id).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.PROVINCE_NOT_FOUND.name()));
    }

    @Override
    public City getCityById(BigInteger id) {
        return cityRepository.findById(id).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.CITY_NOT_FOUND.name()));
    }

    @Override
    public District getDistrictById(BigInteger id) {
        return districtRepository.findById(id).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.DISTRICT_NOT_FOUND.name()));

    }

    @Override
    public SubDistrict getSubDistrictById(BigInteger id) {
        return subDistrictRepository.findById(id).orElseThrow(() -> new NotFoundException(RESPONSE_ENUM.SUB_DISTRICT_NOT_FOUND.name()));

    }


}
