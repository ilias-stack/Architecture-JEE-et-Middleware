package org.sid.ebankingbackend.dtos;

import lombok.Data;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private double interestRate;
}
