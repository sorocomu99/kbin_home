package com.kbin.inno.Starters.Service;

import com.kbin.inno.Starters.DAO.ApplyDAO;
import com.kbin.inno.Starters.DAO.KbStartersSurvey;
import com.kbin.inno.Starters.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {
    @Autowired
    private ApplyDAO applyDAO;

    @Autowired
    private KbStartersSurvey surveyRepository;

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
        return applyDAO.questionCheckboxList(questionInfo);
    }

    public void saveAnswer(ApplyDTO saveApplyInfo) {
        applyDAO.saveAnswer(saveApplyInfo);
    }

    public int finalSubmit(ApplyDTO saveApplyInfo) {
        return applyDAO.finalSubmit(saveApplyInfo);
    }

    public int getQuestionCount(KbStartersSurveyDTO survey) {
        return surveyRepository.getQuestionCount(survey);
    }

    public KbStartersSurveyDTO getLastSurvey() {
        return surveyRepository.getLastSurvey();
    }

    public KbStartersSurveyDTO getSurveyInfo(KbStartersSurveyDTO survey) {
        return surveyRepository.getSurveyInfo(survey);
    }


    public KbStartersSurveyRequestDTO getLastSurveyData() {
        KbStartersSurveyRequestDTO survey = new KbStartersSurveyRequestDTO();
        KbStartersSurveyDTO lastSurvey = surveyRepository.getLastSurvey();
        KbStartersSurveyDTO lastSurveyInfo = surveyRepository.getSurveyInfo(lastSurvey);
        survey.setSurvey(lastSurvey);
        survey.setSurveyInfo(lastSurveyInfo);

        List<KbStartersQuestionDTO> questions = surveyRepository.getQuestion(lastSurvey);
        for (KbStartersQuestionDTO question : questions) {
            KbStartersQuestionRequestDTO questionRequest = new KbStartersQuestionRequestDTO();
            questionRequest.setQuestion_no(question.getQuestion_no());
            questionRequest.setSurvey_no(lastSurvey.getSurvey_no());
            questionRequest.setQuestion_type_no(question.getQuestion_type_no());
            questionRequest.setQuestion_title(question.getQuestion_title());
            questionRequest.setQuestion_description(question.getQuestion_description());
            questionRequest.setRequired_yn(question.getRequired_yn());
            questionRequest.setQuestion_order(question.getQuestion_order());
            List<KbStartersQuestionChoiceDTO> choices = surveyRepository.getQuestionChoice(question);
            for(KbStartersQuestionChoiceDTO choice : choices) {
                KbStartersQuestionChoiceRequestDTO choiceRequest = new KbStartersQuestionChoiceRequestDTO();
                choiceRequest.setQuestion_choice_no(choice.getQuestion_choice_no());
                choiceRequest.setQuestion_no(choice.getQuestion_no());
                choiceRequest.setChoice_content(choice.getChoice_content());
                choiceRequest.setMove_question_no(choice.getMove_question_no());
                questionRequest.getChoices().add(choiceRequest);
            }
            survey.getData().add(questionRequest);
        }

        return survey;
    }
}
