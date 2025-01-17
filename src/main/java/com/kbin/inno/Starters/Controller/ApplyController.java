package com.kbin.inno.Starters.Controller;

import com.kbin.inno.Starters.DTO.ApplyDTO;
import com.kbin.inno.Starters.Service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/starters/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @RequestMapping("/apply_main")
    public String main(Model model) {
        ApplyDTO surveyInfo = applyService.getSurveyInfo(model);
        model.addAttribute("info", surveyInfo);
        return "apply/apply_main";
    }

    @RequestMapping("/apply_intro")
    public String applyIntro(Model model, @RequestParam String srvy_sn) {
        model.addAttribute("srvy_sn", srvy_sn);
        return "apply/apply_intro";
    }

    @RequestMapping("/agree")
    public String agree(Model model, @RequestParam String srvy_sn) {
        int questCnt = applyService.getQuestCnt(srvy_sn);   //문항 개수 가져옴
        model.addAttribute("srvy_sn", srvy_sn);
        model.addAttribute("questCnt", questCnt);
        model.addAttribute("currentCnt", 1);
        return "apply/apply_agree";
    }

    @RequestMapping("/input")
    public String input(Model model, @RequestParam String srvy_sn, @RequestParam int currentCnt, @RequestParam int questCnt) {
        System.out.println("srvy_sn                ======> " + srvy_sn);
        System.out.println("현재 문항 번호 currentCnt ======> " + currentCnt);
        System.out.println("전체 문항 수 questCnt     ======> " + questCnt);

        String url = "";
        boolean exitLoop = false;

        for (int i = currentCnt; i <= questCnt; i++) {
            ApplyDTO applyDTO = new ApplyDTO();
            applyDTO.setSrvy_sn((srvy_sn));
            applyDTO.setCurrent_cnt(currentCnt);

            ApplyDTO questionInfo = applyService.questionInfo(applyDTO);
            model.addAttribute("questionInfo", questionInfo);

            System.out.println(i+"번째 타입 ======> "+questionInfo.getQstn_type());

            // Null 체크 테스트용
            if (questionInfo == null) {
                System.out.println("questionInfo is null for srvy_sn: " + srvy_sn + " and currentCnt: " + currentCnt);
            }

            switch (questionInfo.getQstn_type()) {
                case "1":    // 객관식
                    List<ApplyDTO> checkboxList = applyService.questionCheckboxList(questionInfo);  //객관식 보기 리스트 가져옴
                    model.addAttribute("checkboxList", checkboxList);
                    url = "apply/apply_check";
                    exitLoop = true;
                    break;
                case "2":    // 주관식
                    url = "apply/apply_input";
                    exitLoop = true;
                    break;
                case "3":    // 첨부파일
                    url = "apply/apply_file";
                    exitLoop = true;
                    break;
                default:     // 추후 에디터
                    exitLoop = true;
                    break;
            }

            // exitLoop가 true면 for문 종료
            if (exitLoop) {
                break;
            }

            System.out.println("for문 나옴");
            // 총 문항 수와 현재 수가 같으면 종료
            if (i == questCnt) {
                break;
            }
            currentCnt = i++;
        }

        System.out.println("설문 ID(srvy_sn) ======> " + srvy_sn);
        System.out.println("총 문항 수 questCnt ======> " + questCnt);
        System.out.println("다음 문항 번호 currentCnt ======> " + currentCnt);

        model.addAttribute("srvy_sn", srvy_sn);
        model.addAttribute("questCnt", questCnt);
        model.addAttribute("currentCnt", currentCnt);
        return url;
    }

    @RequestMapping("/check")
    public String check() {
        return "apply/apply_check";
    }

    @RequestMapping("/file")
    public String file() {
        return "apply/apply_file";
    }

    @RequestMapping("/complete")
    public String complete() {
        return "apply/apply_complete";
    }
}