package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.domain.AccountType;
import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.mapper.AccountMapper;
import kh.edu.cstad.mbapi.repository.AccountRepository;
import kh.edu.cstad.mbapi.repository.AccountTypeRepository;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.service.AccountService;
import kh.edu.cstad.mbapi.util.CurrencyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountTypeRepository accountTypeRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponse createNew(CreateAccountRequest createAccountRequest) {

        Account account = new Account();

        // Validate account type
        AccountType accountType = accountTypeRepository
                .findByType(createAccountRequest.accountType())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account Type Not Found"));

        // Validation customer phone number
        Customer customer = customerRepository
                .findByPhoneNumber(createAccountRequest.phoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer phone number not found"));

        switch (createAccountRequest.actCurrency()) {
            case CurrencyUtil.USD -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.valueOf(10)) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 10 USD");
                }
                // Set over limit base on customer segment
                if (customer.getCustomerSegment().getSegment().equals("REGULAR")) {
                    account.setOverLimit(BigDecimal.valueOf(5000));
                } else if (customer.getCustomerSegment().getSegment().equals("SILVER")) {
                    account.setOverLimit(BigDecimal.valueOf(10000));
                } else {
                    account.setOverLimit(BigDecimal.valueOf(50000));
                }
            }
            case CurrencyUtil.KHR -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.valueOf(40000)) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 40,000 KHR");
                }
                // Set over limit base on customer segment
                if (customer.getCustomerSegment().getSegment().equals("REGULAR")) {
                    account.setOverLimit(BigDecimal.valueOf(5000 * 4000));
                } else if (customer.getCustomerSegment().getSegment().equals("SILVER")) {
                    account.setOverLimit(BigDecimal.valueOf(10000 * 4000));
                } else {
                    account.setOverLimit(BigDecimal.valueOf(50000 * 4000));
                }
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Currency is not supported");
        }

        // Validate account no
        if (createAccountRequest.actNo() != null) {
            if (accountRepository.existsByActNo(createAccountRequest.actNo())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Account with Act No %s already exists", createAccountRequest.actNo()));
            }
            account.setActNo(createAccountRequest.actNo());
        } else {
            String actNo;
            do {
                actNo = String.format("%09d", new Random().nextInt(1_000_000_000)); // Max: 999,999,999
            } while (accountRepository.existsByActNo(actNo));
            account.setActNo(actNo);
        }

        // Set data logic
        account.setActName(createAccountRequest.actName());
        account.setActCurrency(createAccountRequest.actCurrency().name());
        account.setBalance(createAccountRequest.balance());
        account.setIsHide(false);
        account.setIsDeleted(false);
        account.setCustomer(customer);
        account.setAccountType(accountType);

        account = accountRepository.save(account);

        return accountMapper.toAccountResponse(account);
    }

}
