package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KYCRepository extends
        JpaRepository<KYC, String> {

    boolean existsByNationalCardId(String nationalCardId);

}
