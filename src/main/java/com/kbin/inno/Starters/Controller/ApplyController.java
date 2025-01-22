package com.kbin.inno.Starters.Controller;

import com.kbin.inno.Starters.DTO.ApplyDTO;
import com.kbin.inno.Starters.DTO.KbStartersApplyRequestWrapper;
import com.kbin.inno.Starters.DTO.KbStartersSurveyDTO;
import com.kbin.inno.Starters.Service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/starters/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @GetMapping("/banner")
    public ResponseEntity<Resource> getImage(String filename) throws IOException {
        // TODO: 파일 경로를 설정해주세요 또는 기존 파일경로를 설정했던 방식으로 사용해주세요
        String filePath = "/Users/johuiyang/Documents/web/uploads/kbinno/" + filename;

        // 파일이 존재하는지 확인
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 파일을 Resource로 감싸서 반환
        Resource resource = new FileSystemResource(file);

        // 이미지 파일의 헤더 설정
        String contentType = "image/jpeg";
        if (filename.toLowerCase().endsWith(".png")) {
            contentType = "image/png";
        } else if (filename.toLowerCase().endsWith(".gif")) {
            contentType = "image/gif";
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> apply(KbStartersApplyRequestWrapper wrapper) {
        return ResponseEntity.ok(applyService.apply(wrapper));
    }

    @RequestMapping("/apply_main")
    public String main(Model model) {
        KbStartersSurveyDTO lastSurvey = applyService.getLastSurvey();
        int questionCount = applyService.getQuestionCount(lastSurvey);
        model.addAttribute("questionCount", questionCount);

        KbStartersSurveyDTO surveyInfo = applyService.getSurveyInfo(lastSurvey);
        model.addAttribute("info", surveyInfo);
        return "apply/apply_main";
    }

    @RequestMapping("/apply_intro")
    public String applyIntro(Model model, @RequestParam Integer surveyNo) {
        model.addAttribute("surveyNo", surveyNo);
        return "apply/apply_intro";
    }

    @RequestMapping("/agree")
    public String agree(Model model, @RequestParam Integer surveyNo) {
        model.addAttribute("surveyNo", surveyNo);
        return "apply/apply_agree";
    }

    @RequestMapping("/saveCompany")
    public String saveCompany(Model model, @RequestParam Integer surveyNo) {
        model.addAttribute("surveyNo", surveyNo);
        return "apply/apply_company";
    }

    @RequestMapping("/saveEmail")
    public String saveEmail(Model model, @RequestParam Integer surveyNo, @RequestParam String companyName) {
        model.addAttribute("surveyNo", surveyNo);
        model.addAttribute("companyName", companyName);
        return "apply/apply_email";
    }
    @RequestMapping("/survey")
    public ModelAndView getQuest() {
        ModelAndView mv = new ModelAndView("apply/survey");
        mv.addObject("survey", applyService.getLastSurveyData());

        mv.addObject("newLineChar", "\n");

        return mv;
    }

    @RequestMapping("/saveAnswer")
    public String saveAnswer(@RequestParam("srvy_sn") String srvySn,
                             @RequestParam("rnum") int rnum,
                             @RequestParam("questCnt") int questCnt,
                             @RequestParam("currentCnt") int currentCnt,
                             @RequestParam("srvy_qstn_sn") String srvyQstnSn,
                             @RequestParam("srvy_ans_sn") String srvyAnsSn,
                             @RequestParam("rspns_cn") String rspnsCn,
                             @RequestParam("conm") String conm ) throws UnsupportedEncodingException {

        ApplyDTO saveApplyInfo = new ApplyDTO();
        saveApplyInfo.setSrvy_sn(srvySn);
        saveApplyInfo.setRnum(rnum);
        saveApplyInfo.setSrvy_qstn_sn(srvyQstnSn);
        saveApplyInfo.setSrvy_ans_sn(srvyAnsSn);
        saveApplyInfo.setRspns_cn(rspnsCn);
        saveApplyInfo.setConm(conm);
        applyService.saveAnswer(saveApplyInfo);

        //conm 회사명 한글인코딩
        String encodedConm = URLEncoder.encode(conm, StandardCharsets.UTF_8.toString());
        int nextCnt = ++currentCnt;
        return "redirect:/starters/apply/getQuest?srvy_sn=" + srvySn + "&currentCnt=" + nextCnt + "&questCnt=" + questCnt + "&conm=" + encodedConm;
    }

    @RequestMapping("/finalSubmit")
    public ResponseEntity<Map<String, Object>> finalSubmit(@RequestBody ApplyDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            ApplyDTO saveApplyInfo = new ApplyDTO();
            saveApplyInfo.setSrvy_sn(request.getSrvy_sn());
            saveApplyInfo.setRnum(request.getRnum());
            saveApplyInfo.setSrvy_qstn_sn(request.getSrvy_qstn_sn());
            saveApplyInfo.setSrvy_ans_sn(request.getSrvy_ans_sn());
            saveApplyInfo.setRspns_cn(request.getRspns_cn());
            saveApplyInfo.setConm(request.getConm());

            int result = applyService.finalSubmit(saveApplyInfo);

            if (result == 1) {
                response.put("success", true);
            } else {
                response.put("success", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}