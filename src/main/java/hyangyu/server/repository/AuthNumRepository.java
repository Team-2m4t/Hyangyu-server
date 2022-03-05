package hyangyu.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hyangyu.server.domain.AuthNum;
import hyangyu.server.domain.User;

@Repository
public interface AuthNumRepository extends JpaRepository<AuthNum, String> {

	Optional<AuthNum> findByEmail(String email);

}
