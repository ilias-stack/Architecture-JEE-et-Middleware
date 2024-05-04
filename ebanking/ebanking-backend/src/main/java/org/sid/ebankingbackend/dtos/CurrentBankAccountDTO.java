package org.sid.ebankingbackend.dtos;

import lombok.Data;
import org.sid.ebankingbackend.enums.AccountStatus;

import java.util.Date;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO {
    private double overDraft;
}
