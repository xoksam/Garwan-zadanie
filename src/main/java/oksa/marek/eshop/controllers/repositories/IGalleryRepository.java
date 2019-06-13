package oksa.marek.eshop.controllers.repositories;

import oksa.marek.eshop.entities.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGalleryRepository extends JpaRepository<Gallery, Long> {
}
