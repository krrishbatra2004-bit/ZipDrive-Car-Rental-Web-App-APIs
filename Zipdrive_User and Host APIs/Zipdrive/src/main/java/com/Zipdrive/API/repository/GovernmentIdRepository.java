package com.Zipdrive.API.repository;
import com.Zipdrive.API.Model.GovernmentId;
import com.Zipdrive.API.Model.User;
import com.Zipdrive.API.enums.IdType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface GovernmentIdRepository extends JpaRepository<GovernmentId, Long> {
    List<GovernmentId> findByUser(User user);
    Optional<GovernmentId> findByUserAndIdType(User user, IdType idType);
}

