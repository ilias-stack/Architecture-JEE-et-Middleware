package org.sid.ebankingbackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.dtos.*;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.enums.AccountStatus;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.mappers.BankAccountMapperImpl;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl mapper;
    private PasswordEncoder passwordEncoder;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer = mapper.fromCustomerDTO(customerDTO);
        return mapper.fromCustomer(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO registerCustomer(Customer customer) {
        Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer == null) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            Customer savedCustomer = customerRepository.save(customer);
            return mapper.fromCustomer(savedCustomer);
        } else {
            throw new RuntimeException("Customer with email: " + customer.getEmail() + " already exists.");
        }
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null) throw new CustomerNotFoundException("Customer not found");
        CurrentAccount bankAccount = new CurrentAccount();

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setCustomer(customer);
        bankAccount.setOverDraft(overDraft);

        return mapper.fromCurrentBankAccount(bankAccountRepository.save(bankAccount));
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null) throw new CustomerNotFoundException("Customer not found");
        SavingAccount bankAccount = new SavingAccount();

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setCustomer(customer);
        bankAccount.setInterestRate(interestRate);

        return mapper.fromSavingBankAccount(bankAccountRepository.save(bankAccount));
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream()
                .map(customer -> mapper.fromCustomer(customer))
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        if(bankAccount instanceof SavingAccount)return mapper.fromSavingBankAccount((SavingAccount) bankAccount);
        else return mapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));
        if(bankAccount.getBalance()<amount) throw new BalanceNotSufficientException("Balance not sufficient");

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
        return bankAccountRepository.findAll().stream().map(bankAccount -> {
            if(bankAccount instanceof SavingAccount) return mapper.fromSavingBankAccount((SavingAccount) bankAccount);
            else return mapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
        }).collect(Collectors.toList());
    }

    @Override
    public List<BankAccountDTO> getCustomerAccounts(Long customerId){
        return bankAccountRepository.findBankAccountByCustomer_Id(customerId)
                .stream().map(bankAccount -> {
                    if(bankAccount instanceof SavingAccount) return mapper.fromSavingBankAccount((SavingAccount) bankAccount);
                    else return mapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
                }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        return mapper.
                fromCustomer(customerRepository.
                        findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found")));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Updating the Customer");
        Customer customer = mapper.fromCustomerDTO(customerDTO);
        return mapper.fromCustomer(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }


    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        return accountOperationRepository.findByBankAccountId(accountId).stream().map(accountOperation -> {
            return mapper.fromAccountOperation(accountOperation);
        }).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("No corresponding bank account"));
        Page<AccountOperation> accountOperationPage = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setAccountOperationDTOS(accountOperationPage
                .getContent().stream()
                .map(accountOperation -> mapper.fromAccountOperation(accountOperation)).
                collect(Collectors.toList()));
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperationPage.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        return customerRepository.searchCustomers(keyword).stream().map(customer -> mapper.fromCustomer(customer)).collect(Collectors.toList());
    }

}
