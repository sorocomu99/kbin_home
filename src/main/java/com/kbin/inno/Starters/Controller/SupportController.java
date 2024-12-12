package com.kbin.inno.Starters.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/starters/support")
public class SupportController {
    // 지원하기 페이지 이동
    @RequestMapping("/list")
    public String list() {
        return "support/support";
    }
}
