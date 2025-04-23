package com.tuk.prj.data;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpeedRunsRepository extends JpaRepository<SpeedRuns, Long>, JpaSpecificationExecutor<SpeedRuns> {

    @Query("SELECT s FROM SpeedRuns s LEFT JOIN FETCH s.game")
    List<SpeedRuns> findAllWithGame();

    @Query("SELECT s FROM SpeedRuns s LEFT JOIN FETCH s.game")
    Page<SpeedRuns> findAllWithGame(Pageable pageable);

    @Query("SELECT s FROM SpeedRuns s LEFT JOIN FETCH s.game WHERE (:filter IS NULL OR :filter = s)")
    Page<SpeedRuns> findAllWithGame(Specification<SpeedRuns> filter, Pageable pageable);
}
