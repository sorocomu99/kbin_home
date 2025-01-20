package com.kbin.inno.Main.Service;

import com.kbin.inno.Main.DAO.MainDAO;
import com.kbin.inno.Main.DTO.HistoryDTO;
import com.kbin.inno.Main.DTO.PopupDTO;
import com.kbin.inno.Main.DTO.ResultDTO;
import com.kbin.inno.Main.DTO.VisualDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<HistoryDTO> selectHistory() {
        return mainDAO.selectHistory();
    }
}
