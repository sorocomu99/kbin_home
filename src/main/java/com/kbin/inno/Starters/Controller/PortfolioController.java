package com.kbin.inno.Starters.Controller;

import com.kbin.inno.Starters.Service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/starters/portfolio")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @RequestMapping("/list")
    public String list() {
        return "portfolio/portfolio";
    }

    @ResponseBody
    @RequestMapping("/getyear")
    public Map<String, Object> getPortYearList(Model model) {
        return portfolioService.getPortYearList(model);
    }

    @ResponseBody
    @RequestMapping("/selectList")
    public Map<String, Object> selectList(Model model, @RequestParam String keyword) {
        return portfolioService.selectList(model, keyword);
    }
}