/**
 * 파일명     : SchedulerController.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : 설정된 시간에 스타터 기업 API를 호출하여 DB에 저장한다.
 * 관련 DB    : KB_API_STARTER_INFO, KB_API_BIZ_SRVC_INFO
 *              KB_API_EMPLO_INFO, KB_API_INVEST_INFO
 *              KB_API_SLS_INFO, KB_API_AST_INFO
 *              KB_API_NEWS_INFO, KB_API_KEYWD_INFO
 * 로그 DB    : KB_API_BACH_JOB_INSTCE, KB_API_BACH_JOB_EXCN
 *              KB_API_BACH_JOB_EXCN_PARAM, KB_API_BACH_JOB_EXCN_CONTXT
 *              KB_API_BACH_STEP_EXCN, KB_API_BACH_STEP_EXCN_CONTXT
 * 최초개발일 : 2024.11.07
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kbin.inno.Scheduler.Controller;

import com.kbin.inno.Scheduler.Service.SchedulerService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
public class SchedulerController {

    @Autowired
    SchedulerService schedulerService;

    //@Scheduled(cron="0 0 2 * * ?")
    @GetMapping("/api")
    public String scheduleRun() {
        int result = 200;

        /*
        try {
            result = schedulerService.getApi();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */

        if (result == 200) {
            //DB에 정상 처리
            System.out.println("정상코드================"+result);
        } else {
            //DB에 오류 처리
            System.out.println("에러코드================"+result);
        }

        return "index";
    }

    @GetMapping("/test")
    public String getPage() {

        return "admin/login/login";
    }
}
