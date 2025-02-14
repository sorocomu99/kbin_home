package com.kbin.inno.Starters.Service;

import com.kbin.inno.FileUploader;
import com.kbin.inno.Starters.DAO.ApplyDAO;
import com.kbin.inno.Starters.DAO.KbStartersSurvey;
import com.kbin.inno.Starters.DTO.*;
import com.kbin.inno.Startup.DTO.FileDTO;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public Map<String, Object> checkPreApply(KbStartersApplyRequestWrapper wrapper) {
        Map<String, Object> result = new HashMap<>();

        KbStartersApplyDTO answer = new KbStartersApplyDTO();
        answer.setSurvey_no(wrapper.getSurvey_no());
        answer.setEmail(wrapper.getEmail());

        int prevApplyCnt = surveyRepository.getPrevApplyCnt(answer);

        result.put("result", prevApplyCnt > 0 ? "dup" : "none");
        return result;
    }

    @Transactional
    public Map<String, Object> apply(KbStartersApplyRequestWrapper wrapper) {
        try{
            Set<String> allowExts = new HashSet<>();
            allowExts.add("jpg");
            allowExts.add("jpeg");
            allowExts.add("png");
            allowExts.add("pdf");
            allowExts.add("doc");
            allowExts.add("docx");
            allowExts.add("hwp");
            allowExts.add("zip");

            Set<String> allowedMimeTypes = new HashSet<>();
            allowedMimeTypes.add("image/jpeg"); // jpg, jpeg
            allowedMimeTypes.add("image/png");  // png
            allowedMimeTypes.add("application/pdf"); // pdf
            allowedMimeTypes.add("application/msword"); // doc
            allowedMimeTypes.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document"); // docx
            allowedMimeTypes.add("application/zip"); // zip
            allowedMimeTypes.add("application/x-hwp"); // hwp (한글 파일)

            Tika tika = new Tika();

            Map<String, Object> result = new HashMap<>();
            KbStartersApplyDTO answer = new KbStartersApplyDTO();
            int insertApplyNo = surveyRepository.getMaxApplyNo();
            String applyStatus = surveyRepository.getOneApplyStatus(wrapper.getSurvey_no());
            answer.setApply_no(insertApplyNo);
            answer.setSurvey_no(wrapper.getSurvey_no());
            answer.setApply_status(StringUtils.hasText(applyStatus) ? applyStatus : "접수");
            answer.setEmail(wrapper.getEmail());
            answer.setCompany_name(wrapper.getCompany_name());
            answer.setFrst_rgtr(0);
            answer.setLast_mdfr(0);

            surveyRepository.saveApply(answer);

            FileUploader fileUploader = new FileUploader();

            for(KbStartersAnswerRequest request : wrapper.getAnswers()) {
                if(request.getQuestion_no() != 0) {
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
                        String mimeType = tika.detect(request.getAnswer_file().getInputStream());
                        if(mimeType == null || !allowedMimeTypes.contains(mimeType)){
                            result.put("result", "허용되지 않는 파일 형식입니다.");
                            return result;
                        }

                        if(!filename.contains(".")){
                            result.put("result", "파일 확장자가 없습니다.");
                            return result;
                        }
                        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                        if(!allowExts.contains(extension)){
                            result.put("result", "허용되지 않는 파일 확장자입니다.");
                            return result;
                        }


                        FileDTO fileResult = fileUploader.insertFile(request.getAnswer_file(), "apply".concat(File.separator).concat(getToDay("yyyyMMdd")));
                        applyAnswer.setAnswer_file_path(fileResult.getFilePath());
                        applyAnswer.setAnswer_filename(fileResult.getFilename());
                        applyAnswer.setAnswer_original_filename(fileResult.getOriginalFilename());
                    }
                    surveyRepository.saveApplyAnswer(applyAnswer);
                }
            }

            result.put("result", "success");
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getToDay(String pattern) {
        String toDay = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.KOREA);
            Date d = new Date();
            toDay = sdf.format(d);
        } catch (Exception e) {

        }

        return toDay;
    }
}
