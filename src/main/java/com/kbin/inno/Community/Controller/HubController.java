package com.kbin.inno.Community.Controller;

import com.kbin.inno.Community.DTO.HubDTO;
import com.kbin.inno.Community.Service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community/hub")
public class HubController {

    // 서비스 연결
    private final HubService hubService;

    // HUB 센터 소식 이동
    @ResponseBody
    @RequestMapping("/list/{more_num}")
    public Map<String, Object> moreList(Model model, @PathVariable int more_num,
                                        @RequestParam(value = "type", required = false, defaultValue = "") String type,
                                        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return hubService.selectListMore(model, type, keyword, more_num);
    }

    // HUB 센터 소식 이동(더보기 버튼용)
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "type", required = false, defaultValue = "") String type,
                                    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                    @RequestParam(value = "more_num", required = false, defaultValue = "1") int more_num) {
        hubService.selectList(model, type, keyword, more_num);
        return "/hub/hub";
    }

    // HUB 센터 소식 이동
    @RequestMapping("/detail/{hub_sn}")
    public String detail(@PathVariable int hub_sn, Model model) {
        HubDTO hub = hubService.select(hub_sn);
        model.addAttribute("hub", hub);
        return "/hub/hub_detail";
    }
}
