/**
 * 파일명     : StaterDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 스타터스 기본 정보 변수
 * 관련 DB    : KB_API_STARTER_INFO
 * 최초개발일 : 2024.11.15
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kbin.inno.Scheduler.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class StaterDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;

    private String ent_cd;                //기업 코드(UNIQUE)
    private String brno;                  //사업자번호
    private String corp_no;               //법인 번호
    private String rprsv_nm;              //대표자 명(CEO)
    private String ent_nm;                //기업 이름
    private String tpbiz;                 //업종
    private String ent_shape;             //기업 형태
    private String ent_scale;             //기업 규모
    private String addr;                  //주소
    private String fndn_ymd;              //설립일자
    private String ksic_cd;               //표준산업분류코드
    private String telno;                 //전화번호
    private String fxno;                  //팩스번호
    private String zip;                   //우편번호
    private String public_ent_yn;         //공기업 여부
    private String indiv_bzmn_yn;         //개인사업자 여부
    private String hdofc_yn;              //본사 여부
    private String venture_cert_yn;       //벤처인증 여부
    private String ipo_yn;                //IPO 여부
    private String foreign_invest_yn;     //외국인투자법인 여부
    private String fnst_yn;               //금융회사 여부
    private String listing_ymd;           //상장 일자
    private String listing_co;            //상장주관사
    private String major_ent_yn;          //대기업 여부
    private String medium_ent_yn;         //중견기업 여부
    private String kospi_yn;              //코스피상장 여부
    private String kosdaq_yn;             //코스닥상장 여부
    private String konex_yn;              //코넥스상장 여부
    private String kotc_yn;               //KOTC 여부
    private String external_audit_yn;     //외감기업 여부
    private String general_ent_yn;        //일반기업 여부
    private String non_profit_yn;         //비영리단체 여부
    private String absorption_merged_yn;  //피흡수합병 여부
    private String clsbiz_yn;             //폐업 여부
    private String tcbiz_yn;              //휴업 여부
    private String bankruptcy_yn;         //파산 여부
    private String main_biz;              //주요 사업
    private String prdct;                 //제품
    private String hmpg;                  //홈페이지
    private String crtr_ym;               //기준년월
    private long mm12_jncmp_nocs;         //최근 12개월간 신규 입사자 수
    private long mm12_rsgntn_nocs;        //최근 12개월간 신규 퇴사자 수
    private double mm12_rsgntn_rt;        //최근 12개월간 퇴사율
    private long now_wrkr_nocs;            //현재 근무자 수
    private long cptl_amt;                 //자본잉여금(단위 : 원)
    private long profit_amt;               //이익잉여금(단위 : 원)
}
