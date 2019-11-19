package com.shenke.service.impl;

import com.shenke.entity.JieSuanDan;
import com.shenke.repository.JieSuanDanRepository;
import com.shenke.service.JieSuanDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jieSuanDan")
public class JieSuanDanServiceImpl implements JieSuanDanService {

    @Autowired
    private JieSuanDanRepository jieSuanDanRepository;

    @Override
    public Integer add(JieSuanDan jieSuanDan) {
        return jieSuanDanRepository.save(jieSuanDan).getId();
    }
}
