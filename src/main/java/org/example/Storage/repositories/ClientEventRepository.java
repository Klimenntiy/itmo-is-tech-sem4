package org.example.Storage.repositories;

import org.example.Storage.entities.ClientEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientEventRepository extends JpaRepository<ClientEventEntity, Long> {
    List<ClientEventEntity> findByClientId(Long clientId);

}
