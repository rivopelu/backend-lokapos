package com.lokapos.controller;

import com.lokapos.annotations.BaseController;
import com.lokapos.model.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@BaseController("transaction")
public interface TransactionController {
    @GetMapping("v1/wallet-transaction")
    BaseResponse getWalletTransaction(Pageable pageable);
}
