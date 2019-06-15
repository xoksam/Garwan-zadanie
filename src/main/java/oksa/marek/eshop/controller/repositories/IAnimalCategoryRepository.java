package oksa.marek.eshop.controller.repositories;

import oksa.marek.eshop.model.entities.AnimalCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnimalCategoryRepository extends JpaRepository<AnimalCategory, Long> {
}
