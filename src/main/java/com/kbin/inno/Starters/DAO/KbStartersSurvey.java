package com.kbin.inno.Starters.DAO;

import com.kbin.inno.Starters.DTO.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KbStartersSurvey {

    KbStartersSurveyDTO getLastSurvey();

    KbStartersSurveyDTO getSurveyInfo(KbStartersSurveyDTO survey);

    List<KbStartersQuestionDTO> getQuestion(KbStartersSurveyDTO survey);

    KbStartersQuestionDTO getOneQuestion(KbStartersQuestionDTO question);

    List<KbStartersQuestionChoiceDTO> getQuestionChoice(KbStartersQuestionDTO question);

    KbStartersQuestionChoiceDTO getOneQuestionChoice(KbStartersQuestionChoiceDTO choice);

    int getQuestionCount(KbStartersSurveyDTO survey);

    int getMaxApplyNo();

    int saveApply(KbStartersApplyDTO apply);

    int getMaxApplyAnswerNo();

    int saveApplyAnswer(KbStartersApplyAnswerDTO answer);

}
