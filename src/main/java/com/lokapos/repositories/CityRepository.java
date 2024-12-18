package com.lokapos.repositories;

import com.lokapos.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CityRepository extends JpaRepository<City, BigInteger> {
}
