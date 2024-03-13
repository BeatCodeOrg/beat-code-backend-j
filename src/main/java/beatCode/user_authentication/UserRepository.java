package beatCode.user_authentication;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findUserByUsername(String username);
}
