package org.knulikelion.challengers_backend.data.dao;

import org.knulikelion.challengers_backend.data.entity.Club;
import org.knulikelion.challengers_backend.data.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> selectUserById(Long id);
    User getByEmail(String email);
    User createUser(User user);
    User updateUser(Long id,User user) throws Exception;
    void removeUser(Long id);
    List<Club> getClubByUser(Long id);
}
