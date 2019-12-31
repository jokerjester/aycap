package com.bay.repositories;

import com.bay.models.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Long countByUsername(String username);

    public User findByUsername(String username);
}
