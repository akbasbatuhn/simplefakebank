package com.fakedevbankapp.account.dto.converter;

import com.fakedevbankapp.account.dto.TransactionDto;
import com.fakedevbankapp.account.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoConverter {

    public TransactionDto convert(Transaction from) {
        return new TransactionDto(
                from.getId(),
                from.getTransactionType(),
                from.getAmount(),
                from.getTransactionDate()
        );
    }
}
