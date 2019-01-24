package com.fcgl.domain.repository;

import com.fcgl.domain.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 校区的repository
 *
 * @author furg@senthink.com
 * @date 2019/1/24
 */
public interface CampusRepository extends JpaRepository<Campus, Long>, JpaSpecificationExecutor {
}
