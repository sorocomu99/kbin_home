package com.kbin.inno.Starters.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/starters/apply")
public class ApplyController {
    // 지원하기 페이지로 이동
    @RequestMapping("/index")
    public String index() {
        return "apply/apply_index";
    }

    // 동의 페이지로 이동
    @RequestMapping("/agree")
    public String agree() {
        return "apply/apply_agree";
    }

    // 입력 페이지로 이동
    @RequestMapping("/input")
    public String input() {
        return "apply/apply_input";
    }

    // 체크 페이지로 이동
    @RequestMapping("/check")
    public String check() {
        return "apply/apply_check";
    }
    
    // 파일 추가 페이지로 이동
    @RequestMapping("/file")
    public String file() {
        return "apply/apply_file";
    }
        
    // 완료 페이지로 이동
    @RequestMapping("/complete")
    public String complete() {
        return "apply/apply_complete";
    }
}
