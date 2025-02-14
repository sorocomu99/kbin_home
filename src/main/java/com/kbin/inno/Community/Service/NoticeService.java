package com.kbin.inno.Community.Service;

import com.kbin.inno.Community.DAO.NoticeDAO;
import com.kbin.inno.Community.DTO.NoticeDTO;
import com.kbin.inno.Community.DTO.SearchDTO;
import com.kbin.inno.common.PropertiesValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    // DAO 연결
    private final NoticeDAO noticeDAO;

    // 공지사항 리스트 조회
    public void selectList(Model model, String type, String keyword, int page) {
        // Search DTO에 담기
        SearchDTO search = new SearchDTO();
        search.setType(type);
        search.setKeyword(keyword);

        // 페이지의 전체 글 갯수
        int allCount = noticeDAO.selectPageCount(search);

        // 한 페이지당 글 갯수
        int pageLetter = 10;

        // 전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        // 나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            // 더하기
            repeat += 1;
        }

        // repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        // 만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        // 만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        search.setEnd(end);
        // 시작 페이지
        int start = end + 1 - pageLetter;

        search.setStart(start);

        // 리스트 조회
        // 상단 공지 리스트는 첫 페이지에만 조회
        model.addAttribute("selectNotiList", page == 1 ? noticeDAO.selectNotiList(search) : "");

        List<NoticeDTO> selectList = noticeDAO.selectList(search);

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("pageCount", allCount);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
    }

    // 공지사항 상세 페이지 조회
    public void select(int ntc_sn, Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ntc_sn", ntc_sn);
        map.put("staticPath", PropertiesValue.staticPath.equals("/") ? PropertiesValue.staticPath : PropertiesValue.staticPath + "/");

        NoticeDTO notice = noticeDAO.select(map);
        model.addAttribute("notice", notice);
    }
}
