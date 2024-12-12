package com.kbin.inno.Nurture.Service;

import com.kbin.inno.Nurture.DAO.GlobalDAO;
import com.kbin.inno.Nurture.DTO.InterchangeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalService {

    // DAO 연결
    private final GlobalDAO globalDAO;

    // 글로벌 현지 교류 리스트 조회
    public List<InterchangeDTO> selectInterchange() {
        return globalDAO.selectInterchange();
    }
}
