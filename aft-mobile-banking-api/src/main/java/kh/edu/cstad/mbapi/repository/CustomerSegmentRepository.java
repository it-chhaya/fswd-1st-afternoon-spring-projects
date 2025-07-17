package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerSegmentRepository extends
        JpaRepository<CustomerSegment, Integer> {

    Optional<CustomerSegment> findBySegment(String s);

}
