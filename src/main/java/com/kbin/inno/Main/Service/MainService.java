package com.kbin.inno.Main.Service;

import com.kbin.inno.Main.DAO.MainDAO;
import com.kbin.inno.Main.DTO.HistoryDTO;
import com.kbin.inno.Main.DTO.MenuDTO;
import com.kbin.inno.Main.DTO.PopupDTO;
import com.kbin.inno.Main.DTO.ResultDTO;
import com.kbin.inno.Main.DTO.VisualDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainService {

    // DAO 연결
    private final MainDAO mainDAO;
    
    // 육성 현황 조회
    public ResultDTO selectResult() {
        return mainDAO.selectResult();
    }

    // 메인 비주얼 조회
    public List<VisualDTO> selectVisual() {
        return mainDAO.selectVisual();
    }

    // 팝업조회
    public List<PopupDTO> selectPopup() {
        return mainDAO.selectPopup();
    }

    // 연혁조회
    public void selectHistory(Model model) {
        List<HistoryDTO> historyList = mainDAO.selectHistory();

        // 연도별, 대분류별로 그룹화
        Map<String, List<HistoryGroup>> historyGroups = new HashMap<String, List<HistoryGroup>>();

        // 임시로 대분류 정보를 저장할 Map
        Map<String, HistoryGroup> tempGroups = new HashMap<String, HistoryGroup>();
        List<String> yearList = new ArrayList<>();
        for (HistoryDTO history : historyList) {
            String year = history.getHstry_yr();
            String key = year + "_" + history.getHstry_lclsf_sort_no();

            // 해당 연도의 그룹 리스트가 없으면 생성
            if (!historyGroups.containsKey(year)) {
                historyGroups.put(year, new ArrayList<HistoryGroup>());
                yearList.add(year);
            }

            // 대분류 그룹이 없으면 생성
            if (!tempGroups.containsKey(key)) {
                HistoryGroup group = new HistoryGroup();
                group.setHstry_lclsf_ttl(history.getHstry_lclsf_ttl());
                group.setHstry_lclsf_sort_no(history.getHstry_lclsf_sort_no());
                group.setHstry_lclsf_ttlList(new ArrayList<String>());
                tempGroups.put(key, group);
                historyGroups.get(year).add(group);
            }

            // 소분류 정보 추가
            tempGroups.get(key).getHstry_lclsf_ttlList().add(history.getHstry_sclsf_ttl());
        }

        model.addAttribute("yearList", yearList);
        model.addAttribute("historyGroups", historyGroups);
    }

    @Getter
    @Setter
    public static class HistoryGroup {
        private String hstry_lclsf_ttl;
        private int hstry_lclsf_sort_no;
        private List<String> hstry_lclsf_ttlList;
    }

    // 헤더 메뉴 조회
    public List<MenuDTO> selectMenu() {
        return mainDAO.selectMenu();
    }
}
