package com.kbin.inno.Main.Controller;

import com.kbin.inno.Main.DTO.*;
import com.kbin.inno.Main.Service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    // 서비스 연결
    private final MainService mainService;

    // 메인 페이지 이동
    @RequestMapping("/")
    public String main(Model model) {
        // 육성 현황 조회
        ResultDTO result = mainService.selectResult();
        model.addAttribute("result", result);
        // 비주얼 조회
        List<VisualDTO> selectList = mainService.selectVisual();
        model.addAttribute("selectList", selectList);
        // 팝업조회
        List<PopupDTO> selectPopup = mainService.selectPopup();
        model.addAttribute("selectPopup", selectPopup);
        // 연혁 조회
        mainService.selectHistory(model);

        return "main/main";
    	//return "redirect:/startup/list";
    }

    @RequestMapping("/menu/list")
    @ResponseBody
    public ResponseEntity<List<MenuDTO>> getImage() {
        try{
            return ResponseEntity.ok().body(mainService.selectMenu());
        }catch (Exception ignored) {
        }
        return null;
    }
}
