package com.kbin.inno.Nurture.Controller;

import com.kbin.inno.Nurture.DTO.InterchangeDTO;
import com.kbin.inno.Nurture.Service.GlobalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nurture/global")
public class GlobalController {

    private final GlobalService globalService;

    // 글로벌 프로그램 이동
    @RequestMapping("/info")
    public String info(Model model) {
        // 글로벌 현지 교류 리스트 조회
        List<InterchangeDTO> interchange = globalService.selectInterchange();
        model.addAttribute("interchange", interchange);
        return "/global/global";
    }

}
