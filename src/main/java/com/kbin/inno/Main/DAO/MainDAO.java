package com.kbin.inno.Main.DAO;

import com.kbin.inno.Main.DTO.PopupDTO;
import com.kbin.inno.Main.DTO.ResultDTO;
import com.kbin.inno.Main.DTO.VisualDTO;
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
    List<PopupDTO> selectPopup();
}
