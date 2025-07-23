package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository
        extends JpaRepository<Role, Integer> {
}
