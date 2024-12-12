package com.kbin.inno.Main.Controller;

import com.kbin.inno.Main.DTO.ResultDTO;
import com.kbin.inno.Main.DTO.VisualDTO;
import com.kbin.inno.Main.Service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    // 서비스 연결
    private final MainService mainService;

    // 메인 페이지 이동
    @RequestMapping("/")
    public String main(Model model) {
//        // 육성 현황 조회
//        ResultDTO result = mainService.selectResult();
//        model.addAttribute("result", result);
//        // 비주얼 조회
//        List<VisualDTO> selectList = mainService.selectVisual();
//        model.addAttribute("selectList", selectList);
        return "main/main";
    	//return "redirect:/startup/list";
    }
}
