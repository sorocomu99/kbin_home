package com.kbin.inno.Nurture.Controller;

import com.kbin.inno.Nurture.DTO.PlaceDTO;
import com.kbin.inno.Nurture.DTO.*;
import com.kbin.inno.Nurture.Service.DomesticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nurture/domestic")
public class DomesticController {

    // 서비스 연결
    private final DomesticService domesticService;

    // 국내 프로그램 이동
    @RequestMapping("/info")
    public String info(Model model) {
        // 육성 기업 그래프 조회
        PromoteDTO promote = domesticService.selectPromote();
        model.addAttribute("promote", promote);

        // 투자 그래프 조회
        InvestmentDTO investment = domesticService.selectInvestment();
        model.addAttribute("investment", investment);

        // 제휴 사례 조회
        List<AffiliateDTO> affiliate = domesticService.selectAffiliate();
        model.addAttribute("affCount", affiliate.size());
        model.addAttribute("affiliate", affiliate);

        // 협력 기관 조회
        List<CooperationDTO> cooperation = domesticService.selectCooperation();
        model.addAttribute("cooperation", cooperation);

        // 육성 공간 조회
        List<PlaceDTO> place = domesticService.selectPlace();
        model.addAttribute("place", place);

        // 채용 지원 조회
        RecruitDTO recruit = domesticService.selectRecruit();
        model.addAttribute("recruit", recruit);
        return "/domestic/domestic";
    }

}
