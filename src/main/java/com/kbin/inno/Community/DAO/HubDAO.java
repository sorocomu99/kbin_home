package com.kbin.inno.Community.DAO;

import com.kbin.inno.Community.DTO.HubDTO;
import com.kbin.inno.Community.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface HubDAO {
    // HUB 센터 소식 갯수 조회
    int selectPageCount(SearchDTO search);
    // HUB 센터 소식 리스트 조회
    List<HubDTO> selectList(SearchDTO search);
    // HUB 센터 소식 상세 조회
    HubDTO select(HashMap<String, Object> map);
}
