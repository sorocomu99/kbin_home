package com.kbin.inno.Starters.Service;

import com.kbin.inno.FileUploader;
import com.kbin.inno.Starters.DAO.ApplyDAO;
import com.kbin.inno.Starters.DAO.KbStartersSurvey;
import com.kbin.inno.Starters.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String question_description = question.getQuestion_description();
            if(question_description != null && question_description.length() > 0) {
                question_description = question_description.replaceAll("\n", "<br>");
                questionRequest.setQuestion_description(question_description);
            }
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

    @Transactional
    public Map<String, Object> apply(KbStartersApplyRequestWrapper wrapper) {
        try{
            Map<String, Object> result = new HashMap<>();
            KbStartersApplyDTO answer = new KbStartersApplyDTO();
            int insertApplyNo = surveyRepository.getMaxApplyNo();
            answer.setApply_no(insertApplyNo);
            answer.setSurvey_no(wrapper.getSurvey_no());
            answer.setApply_status("접수");
            answer.setEmail(wrapper.getEmail());
            answer.setCompany_name(wrapper.getCompany_name());
            answer.setFrst_rgtr(0);
            answer.setLast_mdfr(0);

            surveyRepository.saveApply(answer);

            FileUploader fileUploader = new FileUploader();

            for(KbStartersAnswerRequest request : wrapper.getAnswers()) {
                KbStartersApplyAnswerDTO applyAnswer = new KbStartersApplyAnswerDTO();
                int insertApplyAnswerNo = surveyRepository.getMaxApplyAnswerNo();
                applyAnswer.setApply_answer_no(insertApplyAnswerNo);
                applyAnswer.setApply_no(insertApplyNo);
                applyAnswer.setQuestion_no(request.getQuestion_no());
                if (request.getQuestion_choice_no() != null) {
                    applyAnswer.setQuestion_choice_no(request.getQuestion_choice_no());
                }
                if (request.getAnswer_content() != null) {
                    applyAnswer.setAnswer_content(request.getAnswer_content());
                }
                if (request.getAnswer_file() != null && request.getAnswer_file().getSize() > 0){
                    String filename = request.getAnswer_file().getOriginalFilename();
                    if(!filename.contains(".")){
                        throw new RuntimeException("파일 확장자가 없습니다.");
                    }
                    String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                    // TODO 확장자 정해서 체크로직 넣어주세요


                    Map<String, Object> fileResult = fileUploader.insertFile(request.getAnswer_file());
                    applyAnswer.setAnswer_file_path((String) fileResult.get("filePath"));
                    applyAnswer.setAnswer_filename((String) fileResult.get("filename"));
                    applyAnswer.setAnswer_original_filename((String) fileResult.get("originalFilename"));
                }
                surveyRepository.saveApplyAnswer(applyAnswer);
            }

            result.put("result", "success");
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
