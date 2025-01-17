package com.kbin.inno.Starters.Service;

import com.kbin.inno.Starters.DAO.ApplyDAO;
import com.kbin.inno.Starters.DTO.ApplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {
    @Autowired
    private ApplyDAO applyDAO;

    public ApplyDTO getSurveyInfo(Model model) {
        return applyDAO.getSurveyInfo(model);
    }

    public int getQuestCnt(String srvy_sn) {
        return applyDAO.getQuestCnt(srvy_sn);
    }

    public ApplyDTO questionInfo(ApplyDTO applyDTO) {
        return applyDAO.questionInfo(applyDTO);
    }

    public List<ApplyDTO> questionCheckboxList(ApplyDTO questionInfo) {
        return  applyDAO.questionCheckboxList(questionInfo);
    }
}
