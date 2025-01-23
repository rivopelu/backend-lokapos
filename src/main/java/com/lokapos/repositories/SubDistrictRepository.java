package com.lokapos.repositories;

import com.lokapos.entities.SubDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface SubDistrictRepository extends JpaRepository<SubDistrict, BigInteger> {
    List<SubDistrict> findAllByDistrictId(BigInteger districtId);

    @Query(value = "select sd from  SubDistrict as sd where sd.name LIKE %:q% order by sd.name limit 10")
    List<SubDistrict> findByQuerySearch(String q);
}
