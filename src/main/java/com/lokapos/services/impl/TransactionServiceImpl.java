package com.lokapos.services.impl;

import com.lokapos.entities.WalletTransaction;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.response.ResponseListWalletTransaction;
import com.lokapos.repositories.WalletTransactionRepository;
import com.lokapos.services.AccountService;
import com.lokapos.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final WalletTransactionRepository walletTransactionRepository;
    private final AccountService accountService;

    public TransactionServiceImpl(WalletTransactionRepository walletTransactionRepository, AccountService accountService) {
        this.walletTransactionRepository = walletTransactionRepository;
        this.accountService = accountService;
    }

    @Override
    public Page<ResponseListWalletTransaction> getWalletTransaction(Pageable pageable) {

        String businessId = accountService.getCurrentBusinessIdOrNull();

        List<ResponseListWalletTransaction> responseListWalletTransactions = new ArrayList<>();
        Page<WalletTransaction> walletTransactionPage = walletTransactionRepository.findByBusinessIdAndActiveIsTrue(pageable, businessId);

        for (WalletTransaction walletTransaction : walletTransactionPage.getContent()) {
            ResponseListWalletTransaction responseListWalletTransaction = ResponseListWalletTransaction.builder()
                    .id(walletTransaction.getId())
                    .status(walletTransaction.getStatus())
                    .type(walletTransaction.getType())
                    .amount(walletTransaction.getAmount())
                    .createdDate(walletTransaction.getUpdatedDate())
                    .paymentMethod(walletTransaction.getPaymentMethod())
                    .build();
            responseListWalletTransactions.add(responseListWalletTransaction);
        }
        try {
            return new PageImpl<>(responseListWalletTransactions, pageable, responseListWalletTransactions.size());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
