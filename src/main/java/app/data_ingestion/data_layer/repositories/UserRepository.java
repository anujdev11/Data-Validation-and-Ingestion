package app.data_ingestion.data_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.data_ingestion.data_layer.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

}
