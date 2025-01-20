/**
 * 파일명     : StartupService.java
 * 화면명     : 스타트업 검색
 * 설명       : API 통신으로 가져온 스타트업 검색을 한다.
 * 관련 DB    : KB_API_STARTER_INFO, KB_API_BIZ_SRVC_INFO
 *              KB_API_EMPLO_INFO, KB_API_INVEST_INFO
 *              KB_API_SLS_INFO, KB_API_AST_INFO
 *              KB_API_NEWS_INFO, KB_API_KEYWD_INFO
 * 최초개발일 : 2024.11.25
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kbin.inno.Startup.Service;

import com.kbin.inno.Startup.DAO.StartupDAO;
import com.kbin.inno.Startup.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class StartupService {
    @Autowired
    StartupDAO startupDAO;

    public void startUpList(Model model, HashMap<String, Object> map, @RequestParam(defaultValue = "1") int pageNum, StartupSearchDTO startupSearchDTO) {
        //map.put("start", 1);
        //map.put("end", 6);
        //전체 스타트업 갯수
        int totCnt = startupDAO.selectTotalCount(map);

        // 생성인자로  총 게시물 수, 현재 페이지를 전달
        PageDTO pagination = new PageDTO(totCnt, pageNum);
        //PageDTO pagination = new PageDTO(totCnt, pageNum, startupSearchDTO.getPageSize());

        if (startupSearchDTO.getPageSize() != null && startupSearchDTO.getPageSize() != "") {
            pagination.setPageSize(Integer.parseInt(startupSearchDTO.getPageSize()));
        }

        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        //System.out.println("pageSize======================="+pageSize);

        int end = pageNum * pageSize;
        map.put("end", end);
        int start = end + 1 - pageSize;
        map.put("start", start);

        //리스트 조회
        List<StartupDTO> selectList = startupDAO.selectPageList(map);

        //System.out.println("startupSearchDTO======================"+startupSearchDTO);

        //model 에 담아서 보내기
        model.addAttribute("totCnt", totCnt);
        model.addAttribute("pagination", pagination);

        model.addAttribute("keyWord", map.get("keyWord"));
        model.addAttribute("option1", map.get("seriesList"));
        //누적투자금액 선택 추가
        int minAmt = 0;
        int maxAmt = 0;
        model.addAttribute("minAmt", Integer.toString(minAmt));
        model.addAttribute("maxAmt", Integer.toString(maxAmt));
        model.addAttribute("option3", map.get("emplo"));
        model.addAttribute("minEmp", map.get("minEmp"));
        model.addAttribute("maxEmp", map.get("maxEmp"));
        model.addAttribute("option4", map.get("date"));
        model.addAttribute("option5", map.get("option5"));
        //날짜 선택 추가
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startUpList", selectList);
        model.addAttribute("searchList", startupSearchDTO);
    }

    public void startupDetail(Model model, String ent_cd) {
        //기본정보 가져오기
        StaterDTO startupInfo = startupDAO.select(ent_cd);
        //사업서비스 정보 조회 (리스트)
        List<BizSrvcDTO> selectBizList = startupDAO.selectBizList(ent_cd);
        //고용정보 조회 (리스트)
        List<EmploDTO> selectEmploList = startupDAO.selectEmploList(ent_cd);
        //투자정보 조회 (리스트)
        List<InvestDTO> selectInvestList = startupDAO.selectInvestList(ent_cd);
        //매출정보 조회 (리스트)
        List<SlsDTO> selectSlsList = startupDAO.selectSlsList(ent_cd);
        //SlsDTO selectSlsChgList1 = startupDAO.selectSlsChgList(ent_cd, "2019");
        SlsDTO slsDTO = new SlsDTO();
        slsDTO.setEnt_cd(ent_cd);
        slsDTO.setYr("2019");
        SlsDTO selectSlsChgList1 = startupDAO.selectSlsChgList(slsDTO);
        slsDTO.setYr("2020");
        SlsDTO selectSlsChgList2 = startupDAO.selectSlsChgList(slsDTO);
        slsDTO.setYr("2021");
        SlsDTO selectSlsChgList3 = startupDAO.selectSlsChgList(slsDTO);
        slsDTO.setYr("2022");
        SlsDTO selectSlsChgList4 = startupDAO.selectSlsChgList(slsDTO);
        slsDTO.setYr("2023");
        SlsDTO selectSlsChgList5 = startupDAO.selectSlsChgList(slsDTO);
        /*
        SlsDTO selectSlsChgList2 = startupDAO.selectSlsChgList(ent_cd, "2020");
        SlsDTO selectSlsChgList3 = startupDAO.selectSlsChgList(ent_cd, "2021");
        SlsDTO selectSlsChgList4 = startupDAO.selectSlsChgList(ent_cd, "2022");
        SlsDTO selectSlsChgList5 = startupDAO.selectSlsChgList(ent_cd, "2023");
        */
        //재무상태 조회 (리스트)
        List<AstDTO> selectAstList = startupDAO.selectAstList(ent_cd);
        AstDTO astDTO = new AstDTO();
        astDTO.setEnt_cd(ent_cd);
        astDTO.setYr("2019");
        AstDTO selectAstChgList1 = startupDAO.selectAstChgList(astDTO);
        astDTO.setYr("2020");
        AstDTO selectAstChgList2 = startupDAO.selectAstChgList(astDTO);
        astDTO.setYr("2021");
        AstDTO selectAstChgList3 = startupDAO.selectAstChgList(astDTO);
        astDTO.setYr("2022");
        AstDTO selectAstChgList4 = startupDAO.selectAstChgList(astDTO);
        astDTO.setYr("2023");
        AstDTO selectAstChgList5 = startupDAO.selectAstChgList(astDTO);
        /*
        AstDTO selectAstChgList1 = startupDAO.selectAstChgList(ent_cd, "2019");
        AstDTO selectAstChgList2 = startupDAO.selectAstChgList(ent_cd, "2020");
        AstDTO selectAstChgList3 = startupDAO.selectAstChgList(ent_cd, "2021");
        AstDTO selectAstChgList4 = startupDAO.selectAstChgList(ent_cd, "2022");
        AstDTO selectAstChgList5 = startupDAO.selectAstChgList(ent_cd, "2023");
        */
        //뉴스 메인 조회 (단건)
        NewsDTO selectNewsMain = startupDAO.selectNewsMain(ent_cd);
        //뉴스정보 조회 (리스트)
        List<NewsDTO> selectNewsList = startupDAO.selectNewsList(ent_cd);
        //키워드정보 조회 (리스트)
        List<KeywdDTO> selectKeywdList = startupDAO.selectKeywdList(ent_cd);
        model.addAttribute("startupInfo", startupInfo);
        
        model.addAttribute("selectBizList", selectBizList);
        model.addAttribute("selectEmploList", selectEmploList);
        model.addAttribute("selectInvestList", selectInvestList);
        
        model.addAttribute("selectSlsList", selectSlsList);
        model.addAttribute("selectSlsChgList1", selectSlsChgList1);
        model.addAttribute("selectSlsChgList2", selectSlsChgList2);
        model.addAttribute("selectSlsChgList3", selectSlsChgList3);
        model.addAttribute("selectSlsChgList4", selectSlsChgList4);
        model.addAttribute("selectSlsChgList5", selectSlsChgList5);
        
        model.addAttribute("selectAstChgList1", selectAstChgList1);
        model.addAttribute("selectAstChgList2", selectAstChgList2);
        model.addAttribute("selectAstChgList3", selectAstChgList3);
        model.addAttribute("selectAstChgList4", selectAstChgList4);
        model.addAttribute("selectAstChgList5", selectAstChgList5);
        
        model.addAttribute("selectAstList", selectAstList);
        model.addAttribute("selectNewsMain", selectNewsMain);
        model.addAttribute("selectNewsList", selectNewsList);
        model.addAttribute("selectKeywdList", selectKeywdList);
    }
}
