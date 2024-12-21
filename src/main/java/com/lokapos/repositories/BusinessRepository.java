package com.lokapos.repositories;

import com.lokapos.entities.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, String> {
}
