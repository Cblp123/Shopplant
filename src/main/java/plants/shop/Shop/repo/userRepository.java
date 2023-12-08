package plants.shop.Shop.repo;

import org.springframework.data.repository.CrudRepository;
import plants.shop.Shop.models.user;
public interface userRepository extends CrudRepository<user, Long> {

    user findByUsername(String username);
}
