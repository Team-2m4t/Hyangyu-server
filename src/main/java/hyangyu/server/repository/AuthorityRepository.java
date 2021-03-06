package hyangyu.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hyangyu.server.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
