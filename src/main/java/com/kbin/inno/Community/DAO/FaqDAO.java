package com.kbin.inno.Community.DAO;

import com.kbin.inno.Community.DTO.CategoryDTO;
import com.kbin.inno.Community.DTO.FaqDTO;
import com.kbin.inno.Community.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface FaqDAO {
    // FAQ 리스트 갯수 조회
    int selectPageCount(SearchDTO search);
    // FAQ 리스트 조회
    //List<FaqDTO> selectList(SearchDTO search);
    List<FaqDTO> selectList(HashMap map);
    // 카테고리 조회
    List<CategoryDTO> selectCategory();
}
