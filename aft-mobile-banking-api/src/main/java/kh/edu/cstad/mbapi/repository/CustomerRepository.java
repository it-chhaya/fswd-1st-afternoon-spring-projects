package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends
        JpaRepository<Customer, Integer> {

    List<Customer> findAllByIsDeletedFalse();

    @Modifying
    @Query(value = """
            UPDATE Customer c
            SET c.isDeleted = TRUE
            WHERE c.phoneNumber = ?1
            """)
    void disableByPhoneNumber(String phoneNumber);

    // SELECT * FROM customers WHERE phone_number = ?1;
    Optional<Customer> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
