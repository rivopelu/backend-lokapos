package com.lokapos.repositories;

import com.lokapos.entities.SubscriptionPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackage, String> {
    List<SubscriptionPackage> findAllByActiveIsTrue();

}
