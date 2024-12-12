package com.kbin.inno.Community.Controller;

import com.kbin.inno.Community.DTO.NoticeDTO;
import com.kbin.inno.Community.Service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community/notice")
public class NoticeController {

    // 서비스 연결
    private final NoticeService noticeService;

    // 공통 경로 설정
    @Value("/notice")
    public String directory;

    // 공지사항 리스트 조회
    @RequestMapping("/list")
    public String selectList(Model model, @RequestParam(value = "type", required = false, defaultValue = "") String type,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        noticeService.selectList(model, type, keyword, page);
        return directory + "/notice";
    }

    // 공지사항 상세 페이지 조회
    @RequestMapping("/detail/{ntc_sn}")
    public String detail(@PathVariable int ntc_sn, Model model) {
        noticeService.select(ntc_sn, model);
        return directory + "/notice_detail";
    }
}
