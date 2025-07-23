package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

}
