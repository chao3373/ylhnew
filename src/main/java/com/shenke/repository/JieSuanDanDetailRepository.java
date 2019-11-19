package com.shenke.repository;

import com.shenke.entity.JieSuanDanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JieSuanDanDetailRepository extends JpaSpecificationExecutor<JieSuanDanDetail>, JpaRepository<JieSuanDanDetail, Integer> {

}
