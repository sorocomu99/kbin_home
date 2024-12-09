/**
 * 파일명     : StartupController.java
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
package com.kbin.inno.Startup.Controller;

import com.kbin.inno.Startup.DTO.StartupSearchDTO;
import com.kbin.inno.Startup.Service.StartupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/startup/*")
public class StartupController {
    @Autowired
    StartupService startupService;
    
    @RequestMapping("/list")
    public String startUpList(Model model, @RequestParam(value="option1", required=false, defaultValue = "") String option1,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                              StartupSearchDTO startupSearchDTO) {

        List<String> seriesList = new ArrayList<>();
        if (option1 == null || option1.equals("")) {
        } else {
            seriesList = Arrays.asList(option1.split(","));
        }

        String empSerach = "";
        //if (option3 == null || option3.equals("")) {
        //} else {
        //}

        HashMap<String,Object> resMap = new HashMap<>();

        //최상단 단어검색
        resMap.put("keyWord", startupSearchDTO.getKeyWord());
        resMap.put("keyWord1", startupSearchDTO.getKeyWord1());
        //최종 투자단계
        resMap.put("seriesList", seriesList);
        //누적투자금액
        if (startupSearchDTO.getOption2() != null && !startupSearchDTO.getOption2().equals("")) {
            resMap.put("investAmt", startupSearchDTO.getOption2());
        }
        int minAmt = 0;
        int maxAmt = 0;
        if (startupSearchDTO.getMinAmt() == null || startupSearchDTO.getMinAmt().equals("")) {
        } else {
            minAmt = Integer.parseInt(startupSearchDTO.getMinAmt())  * 100000000;
            resMap.put("minAmt", Integer.toString(minAmt));

        }
        if (startupSearchDTO.getMaxAmt() == null || startupSearchDTO.getMaxAmt().equals("")) {
        } else {
            maxAmt = Integer.parseInt(startupSearchDTO.getMaxAmt()) * 100000000;
            resMap.put("maxAmt", Integer.toString(maxAmt));
        }
        //근무자
        resMap.put("emplo", startupSearchDTO.getOption3());
        resMap.put("minEmp", startupSearchDTO.getMinEmp());
        resMap.put("maxEmp", startupSearchDTO.getMaxEmp());
        //설립일
        resMap.put("date", startupSearchDTO.getOption4());
        //날짜선택 추가
        if (startupSearchDTO.getDateRange() != null && !startupSearchDTO.getDateRange().equals("")) {
            String dateRange = startupSearchDTO.getDateRange();
            String fromDate = dateRange.substring(0, 10).replace("-", "");
            String toDate = dateRange.substring(13, 23).replace("-", "");
            resMap.put("fromDate", fromDate);
            resMap.put("toDate", toDate);
        }
        //KB 스타터스 추가

        startupService.startUpList(model, resMap, pageNum, startupSearchDTO);

        //System.out.println("form to=============="+startupSearchDTO);

        return "startup/list";
    }

    @RequestMapping("/keySearch")
    public String keySearch(Model model) {

        return "startup/list";
    }

    @PostMapping("/detail")
    public String startupDetail(Model model, String ent_cd) {
        //System.out.println("ent_cd================="+ent_cd);
        startupService.startupDetail(model, ent_cd);
        return "startup/view";
    }
    
    //test
    @RequestMapping("/main")
    public String main () {
    	return "index1";
    }
}
