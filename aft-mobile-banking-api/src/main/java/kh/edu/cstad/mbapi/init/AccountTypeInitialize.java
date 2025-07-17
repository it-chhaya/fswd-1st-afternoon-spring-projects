package kh.edu.cstad.mbapi.init;

import jakarta.annotation.PostConstruct;
import kh.edu.cstad.mbapi.domain.AccountType;
import kh.edu.cstad.mbapi.repository.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountTypeInitialize {

    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    public void init() {
        if (accountTypeRepository.count() == 0) {
            AccountType payroll = new AccountType();
            payroll.setType("PAYROLL");
            payroll.setIsDeleted(false);

            AccountType saving = new AccountType();
            saving.setType("SAVING");
            saving.setIsDeleted(false);

            AccountType junior = new AccountType();
            junior.setType("JUNIOR");
            junior.setIsDeleted(false);

            accountTypeRepository.saveAll(List.of(payroll, saving, junior));
        }
    }
}
