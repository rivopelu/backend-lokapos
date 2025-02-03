package com.lokapos.services;

import com.lokapos.model.response.ResponseListWalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TransactionService {

    Page<ResponseListWalletTransaction> getWalletTransaction(Pageable pageable);
}
