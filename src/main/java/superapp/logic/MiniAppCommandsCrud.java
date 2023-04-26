package superapp.logic;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import superapp.data.MiniAppCommandEntity;

public interface MiniAppCommandsCrud extends ListCrudRepository <MiniAppCommandEntity, String> {
	
}
