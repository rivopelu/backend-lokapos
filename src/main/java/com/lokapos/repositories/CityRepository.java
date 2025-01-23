package com.lokapos.repositories;

import com.lokapos.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface CityRepository extends JpaRepository<City, BigInteger> {
    List<City> findAllByProvinceId(BigInteger provinceId);
}
