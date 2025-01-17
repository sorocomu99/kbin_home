package com.kbin.inno.Starters.DAO;

import com.kbin.inno.Starters.DTO.ApplyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
@Repository
public interface ApplyDAO {
    ApplyDTO getSurveyInfo(Model model);

    int getQuestCnt(String srvy_sn);

    ApplyDTO questionInfo(ApplyDTO applyDTO);

    List<ApplyDTO> questionCheckboxList(ApplyDTO questionInfo);

    void saveAnswer(ApplyDTO saveApplyInfo);
}
