package com.fcgl.domain.repository;

import com.fcgl.domain.entity.Dorm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * Dorm的repository
 *
 * @author furg@senthink.com
 * @date 2019/1/25
 */
public interface DormRepository extends JpaRepository<Dorm, Long>, JpaSpecificationExecutor {

    Optional<Dorm> findTopByDid(String did);

    long countAllByDn(String dn);

    long deleteAllByDidIn(List<String> dids);
}
