package org.sid.ebankingbackend.web;


import org.sid.ebankingbackend.dtos.*;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.service.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


    @GetMapping("/accounts/{accountId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public List<BankAccountDTO> getAllAccounts() throws BankAccountNotFoundException {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{customerId}/accounts")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public List<BankAccountDTO> getAccounts(@PathVariable Long customerId){
        return bankAccountService.getCustomerAccounts(customerId);
    }

    @GetMapping("/accounts/{accountId}/operations")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "10") int size
                                                       ) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }

    @PostMapping("/accounts/debit")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(), debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("/accounts/credit")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public CreditDTO debit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfer")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(transferRequestDTO.getAccountSource(), transferRequestDTO.getAccountDestination(), transferRequestDTO.getAmount());
    }

}
