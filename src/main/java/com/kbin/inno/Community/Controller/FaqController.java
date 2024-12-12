package com.kbin.inno.Community.Controller;

import com.kbin.inno.Community.Service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community/faq")
public class FaqController {

    // 서비스 연결
    private final FaqService faqService;

    // 공통 경로 설정
    @Value("/faq")
    private String directory;

    // FAQ 리스트 조회
    @RequestMapping("/list")
    public String selectList(Model model, @RequestParam(value = "ctgry" , required = false , defaultValue = "0") int ctgry,
                             @RequestParam(value = "type", required = false, defaultValue = "") String type,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        faqService.selectList(ctgry, model, type, keyword, page);
        return directory + "/faq";
    }
}
