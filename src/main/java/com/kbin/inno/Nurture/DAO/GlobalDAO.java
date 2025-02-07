package com.kbin.inno.Nurture.DAO;

import com.kbin.inno.Nurture.DTO.InterchangeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GlobalDAO {
    // 글로벌 현지 교류 리스트 조회
    List<InterchangeDTO> selectInterchange();
}
