package com.kbin.inno.Main.DAO;

import com.kbin.inno.Main.DTO.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MainDAO {
    // 육성 현황 조회
    ResultDTO selectResult();
    // 메인 비주얼 조회
    List<VisualDTO> selectVisual();
    // 팝업 조회
    List<PopupDTO> selectPopup(String staticPath);
    // 연혁 조회
    List<HistoryDTO> selectHistory();
    // 헤더 메뉴 조회
    List<MenuDTO> selectMenu();
}
