package oksa.marek.eshop.controllers.repositories;

import oksa.marek.eshop.entities.AnimalCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnimalCategoryRepository extends JpaRepository<AnimalCategory, Long> {
}
