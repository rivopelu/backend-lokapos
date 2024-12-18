package com.lokapos.repositories;

import com.lokapos.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface DistrictRepository extends JpaRepository<District, BigInteger> {
}
