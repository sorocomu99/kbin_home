package com.kbin.inno.Nurture.Service;

import com.kbin.inno.Nurture.DTO.PlaceDTO;
import com.kbin.inno.Nurture.DAO.DomesticDAO;
import com.kbin.inno.Nurture.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomesticService {
    // DAO 연결
    private final DomesticDAO domesticDAO;
    
    // 육성 그래프 조회
    public PromoteDTO selectPromote() {
        return domesticDAO.selectPromote();
    }

    // 투자 그래프 조회
    public InvestmentDTO selectInvestment() {
        return domesticDAO.selectInvestment();
    }

    // 제휴 사례 조회
    public List<AffiliateDTO> selectAffiliate() {
        return domesticDAO.selectAffiliate();
    }

    // 협력 기관 조회
    public List<CooperationDTO> selectCooperation() {
        return domesticDAO.selectCooperation();
    }

    // 육성 공간 조회
    public List<PlaceDTO> selectPlace() {
        return domesticDAO.selectPlace();
    }
    
    // 채용 지원 조회
    public RecruitDTO selectRecruit() {
        return domesticDAO.selectRecruit();
    }
}
