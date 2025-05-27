package org.example.Storage.repositories;

import org.example.Storage.entities.AccountEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountEventRepository extends JpaRepository<AccountEventEntity, Long> {
    List<AccountEventEntity> findByAccountId(Long accountId);

}
