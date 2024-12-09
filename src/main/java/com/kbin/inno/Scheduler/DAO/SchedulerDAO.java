/**
 * 파일명     : SchedulerDAO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : 설정된 시간에 스타터 기업 API를 호출하여 DB에 저장한다.
 * 관련 DB    : KB_API_STARTER_INFO, KB_API_BIZ_SRVC_INFO
 *              KB_API_EMPLO_INFO, KB_API_INVEST_INFO
 *              KB_API_SLS_INFO, KB_API_AST_INFO
 *              KB_API_NEWS_INFO, KB_API_KEYWD_INFO
 * 로그 DB    : KB_API_BACH_JOB_INSTCE, KB_API_BACH_JOB_EXCN
 *              KB_API_BACH_JOB_EXCN_PARAM, KB_API_BACH_JOB_EXCN_CONTXT
 *              KB_API_BACH_STEP_EXCN, KB_API_BACH_STEP_EXCN_CONTXT
 * 최초개발일 : 2024.11.21
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kbin.inno.Scheduler.DAO;

import com.kbin.inno.Scheduler.DTO.StaterDTO;
import com.kbin.inno.Scheduler.DTO.BizSrvcDTO;
import com.kbin.inno.Scheduler.DTO.EmploDTO;
import com.kbin.inno.Scheduler.DTO.InvestDTO;
import com.kbin.inno.Scheduler.DTO.SlsDTO;
import com.kbin.inno.Scheduler.DTO.AstDTO;
import com.kbin.inno.Scheduler.DTO.NewsDTO;
import com.kbin.inno.Scheduler.DTO.KeywdDTO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchedulerDAO {

    //API 기본정보 등록
    int staterIns(StaterDTO staterDTO);
    //API 사업 서비스 정보 삭제
    int bizSrvcDel(String ent_cd);
    //API 사업 서비스 정보 등록
    int bizSrvcIns(BizSrvcDTO bizSrvcDTO);
    //API 고용정보 등록
    int emploIns(EmploDTO emploDTO);
    //API 투자정보 삭제
    int investDel(String ent_cd);
    //API 투자정보 등록
    int investIns(InvestDTO investDTO);
    //API 매출정보(손익계산서) 등록
    int slsIns(SlsDTO slsDTO);
    //API 자산정보(재무상태) 등록
    int astIns(AstDTO astDTO);
    //API 뉴스정보 등록
    int newsIns(NewsDTO newsDTO);
    //API 키워드정보 삭제
    int keywdDel(String ent_cd);
    //API 키워드정보 등록
    int keywdIns(KeywdDTO keywdDTO);
}
