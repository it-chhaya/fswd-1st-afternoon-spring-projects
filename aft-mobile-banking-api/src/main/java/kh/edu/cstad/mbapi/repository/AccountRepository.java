package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
    extends JpaRepository<Account, Integer> {

    boolean existsByActNo(String actNo);

}
