package com.lokapos.controller.impl;

import com.lokapos.annotations.BaseControllerImpl;
import com.lokapos.controller.TransactionController;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.services.TransactionService;
import com.lokapos.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@BaseControllerImpl
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;

    @Override
    public BaseResponse getWalletTransaction(Pageable pageable) {
        return ResponseHelper.createBaseResponse(transactionService.getWalletTransaction(pageable));
    }
}
