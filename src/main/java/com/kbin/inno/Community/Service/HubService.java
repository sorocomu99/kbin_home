package com.kbin.inno.Community.Service;

import com.kbin.inno.Community.DAO.HubDAO;
import com.kbin.inno.Community.DTO.HubDTO;
import com.kbin.inno.Community.DTO.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HubService {
    
    // DAO 연결
    private final HubDAO hubDAO;
    
    // HUB 리스트 조회
    public Map<String, Object> selectListMore(Model model, String type, String keyword, int more_num) {
    //public void selectListMore(Model model, String type, String keyword, int more_num) {
        // Search DTO에 담기
        SearchDTO search = new SearchDTO();
        search.setType(type);
        search.setKeyword(keyword);

        // 페이지 전체 글 갯수
        int allCount = hubDAO.selectPageCount(search);

        // 한 페이지당 글 갯수
        int pageLetter = 3;

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
        if(repeat < more_num) {
            more_num = repeat;
        }

        // 만약 가져온 페이지가 0이라면
        if(more_num < 1) {
            more_num = 1;
        }

        // 끝 페이지
        int end = more_num * pageLetter;
        search.setEnd(end);

        // 시작 페이지
        int start = end + 1 - pageLetter;
        search.setStart(start);

        List<HubDTO> selectList = hubDAO.selectList(search);

        Map<String, Object> result = new HashMap<>();

        result.put("repeat", repeat);
        result.put("more_num", more_num);
        result.put("selectList", selectList);
        result.put("allCount", allCount);
        model.addAttribute("repeat", repeat);
        model.addAttribute("more_num", more_num);
        model.addAttribute("selectList", selectList);
        model.addAttribute("allCount", allCount);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);

        return result;
    }

    public void selectList(Model model, String type, String keyword, int more_num) {
        // Search DTO에 담기
        SearchDTO search = new SearchDTO();
        search.setType(type);
        search.setKeyword(keyword);

        // 페이지 전체 글 갯수
        int allCount = hubDAO.selectPageCount(search);

        // 한 페이지당 글 갯수
        int pageLetter = 3;

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
        if(repeat < more_num) {
            more_num = repeat;
        }

        // 만약 가져온 페이지가 0이라면
        if(more_num < 1) {
            more_num = 1;
        }

        // 끝 페이지
        int end = more_num * pageLetter;
        search.setEnd(end);

        // 시작 페이지
        int start = end + 1 - pageLetter;
        search.setStart(start);

        List<HubDTO> selectList = hubDAO.selectList(search);
        /*
        model.addAttribute("repeat", repeat);
        model.addAttribute("more_num", more_num);
        model.addAttribute("selectList", selectList);
        model.addAttribute("allCount", allCount);
       */
        model.addAttribute("repeat", repeat);
        model.addAttribute("more_num", more_num);
        model.addAttribute("selectList", selectList);
        model.addAttribute("allCount", allCount);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
    }

    // HUB 센터 소식 상세 조회
    public HubDTO select(int hub_sn) {
        return hubDAO.select(hub_sn);
    }
}
