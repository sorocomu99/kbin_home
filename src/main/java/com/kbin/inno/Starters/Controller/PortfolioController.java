package com.kbin.inno.Starters.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/starters/portfolio")
public class PortfolioController {
    // 포트폴리오 페이지 이동
    @RequestMapping("/list")
    public String list() {
        return "portfolio/portfolio";
    }
}
