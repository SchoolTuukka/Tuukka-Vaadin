package com.tuk.prj.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpeedRunsRepository extends JpaRepository<SpeedRuns, Long>, JpaSpecificationExecutor<SpeedRuns> {

}
