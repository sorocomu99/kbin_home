package com.kbin.inno.Nurture.DAO;

import com.kbin.inno.Nurture.DTO.PlaceDTO;
import com.kbin.inno.Nurture.DTO.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DomesticDAO {
    // 육성 그래프 조회
    PromoteDTO selectPromote();
    // 투자 그래프 조회
    InvestmentDTO selectInvestment();
    // 제휴 사례 조회
    List<AffiliateDTO> selectAffiliate();
    // 협력 기관 조회
    List<CooperationDTO> selectCooperation();
    // 육성 공간 조회
    List<PlaceDTO> selectPlace();
    // 채용 지원 조회
    RecruitDTO selectRecruit();
}
