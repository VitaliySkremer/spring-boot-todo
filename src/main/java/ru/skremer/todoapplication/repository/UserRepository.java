package ru.skremer.todoapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skremer.todoapplication.entity.User;
import ru.skremer.todoapplication.entity.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    List<User> findAllByRoleInOrderById(Collection<UserRole> role);

    List<User> id(int id);

    @Modifying
    @Query("UPDATE User u set u.role = :role WHERE u.id = :id")
    void updateRole(@Param("id") int id, @Param("role") UserRole role);
}
