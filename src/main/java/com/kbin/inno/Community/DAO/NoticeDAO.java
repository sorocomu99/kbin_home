package com.kbin.inno.Community.DAO;

import com.kbin.inno.Community.DTO.NoticeDTO;
import com.kbin.inno.Community.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface NoticeDAO {
    // 공지사항 리스트 갯수 조회
    int selectPageCount(SearchDTO search);
    // 공지사항 리스트 조회
    List<NoticeDTO> selectList(SearchDTO search);
    // 공지사항 상세 조회
    NoticeDTO select(HashMap<String, Object> map);
}
