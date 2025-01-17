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
        int questCnt = applyService.getQuestCnt(srvy_sn);
        int currentCnt = 1;
        model.addAttribute("srvy_sn", srvy_sn);
        model.addAttribute("questCnt", questCnt);
        model.addAttribute("currentCnt", currentCnt);
        return "apply/apply_agree";
    }

    @RequestMapping("/getQuest")
    public String getQuest(Model model, @RequestParam String srvy_sn, @RequestParam int currentCnt, @RequestParam int questCnt) {
        model.asMap().clear();
        String url = "";
        boolean exitLoop = false;

        for (int i = currentCnt; i <= questCnt; i++) {
            ApplyDTO applyDTO = new ApplyDTO();
            applyDTO.setSrvy_sn((srvy_sn));
            applyDTO.setCurrent_cnt(currentCnt);
            ApplyDTO questionInfo = applyService.questionInfo(applyDTO);
            model.addAttribute("questionInfo", questionInfo);

            switch (questionInfo.getQstn_type()) {
                case "1":    //객관식
                    List<ApplyDTO> checkboxList = applyService.questionCheckboxList(questionInfo);
                    model.addAttribute("checkboxList", checkboxList);
                    url = "apply/apply_check";
                    exitLoop = true;
                    break;
                case "2":    //주관식
                    url = "apply/apply_input";
                    exitLoop = true;
                    break;
                case "3":    //첨부파일
                    url = "apply/apply_file";
                    exitLoop = true;
                    break;
                default:     //에디터
                    exitLoop = true;
                    break;
            }
            if (exitLoop) {
                break;
            }
        }
        model.addAttribute("srvy_sn", srvy_sn);
        model.addAttribute("questCnt", questCnt);
        model.addAttribute("currentCnt", currentCnt);
        return url;
    }

    @RequestMapping("/saveAnswer")
    public String saveAnswer(@RequestParam("srvy_sn") String srvySn,
                             @RequestParam("rnum") int rnum,
                             @RequestParam("questCnt") int questCnt,
                             @RequestParam("currentCnt") int currentCnt,
                             @RequestParam("srvy_qstn_sn") String srvyQstnSn,
                             @RequestParam("srvy_ans_sn") String srvyAnsSn,
                             @RequestParam("rspns_cn") String rspnsCn) {

        ApplyDTO saveApplyInfo = new ApplyDTO();
        saveApplyInfo.setSrvy_sn(srvySn);
        saveApplyInfo.setRnum(rnum);
        saveApplyInfo.setSrvy_qstn_sn(srvyQstnSn);
        saveApplyInfo.setSrvy_ans_sn(srvyAnsSn);
        saveApplyInfo.setRspns_cn(rspnsCn);

        applyService.saveAnswer(saveApplyInfo);

        if (currentCnt < questCnt) {
            int nextCnt = ++currentCnt;
            return "redirect:/starters/apply/getQuest?srvy_sn=" + srvySn + "&currentCnt=" + nextCnt + "&questCnt=" + questCnt;
        } else if (currentCnt == questCnt) {
            return "redirect:/starters/apply/apply_main";
        }
        return "redirect:/starters/apply/apply_main";
    }
}