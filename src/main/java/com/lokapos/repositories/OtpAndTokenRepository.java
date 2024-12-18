package com.lokapos.repositories;

import com.lokapos.entities.OtpAndToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpAndTokenRepository extends JpaRepository<OtpAndToken, String> {
}
