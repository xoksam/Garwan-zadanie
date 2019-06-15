package oksa.marek.eshop.controller.repositories;

import oksa.marek.eshop.model.entities.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGalleryRepository extends JpaRepository<Gallery, Long> {
}
