package com.fcgl.domain.repository;

import com.fcgl.domain.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * 校区的repository
 *
 * @author furg@senthink.com
 * @date 2019/1/24
 */
public interface CampusRepository extends JpaRepository<Campus, Long>, JpaSpecificationExecutor {

    Optional<Campus> findTopByCid(String cid);

    long countAllByName(String name);

    long deleteAllByCidIn(List<String> cids);
}
