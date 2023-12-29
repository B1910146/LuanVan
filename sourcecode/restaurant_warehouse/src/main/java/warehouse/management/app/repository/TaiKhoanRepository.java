package warehouse.management.app.repository;

import io.netty.util.AsyncMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.TaiKhoan;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the TaiKhoan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Long> {
    Optional<TaiKhoan> findByUsername(String username);

    Optional<TaiKhoan> findById(UUID id);

    boolean existsById(UUID id);
}
