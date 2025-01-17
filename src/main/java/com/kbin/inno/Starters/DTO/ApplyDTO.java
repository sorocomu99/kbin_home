package com.kbin.inno.Starters.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplyDTO implements Serializable {

    //KB_SRVY_EXMN_INFO 설문
    private String srvy_sn;	        //설문 일련번호
    private String srvy_ttl;	    //설문 제목
    private String bane_file_sn;	//배너 파일 일련번호
    private String tmpr_del_yn;	    //임시 삭제 여부
    private String frst_rgtr;	    //최초등록자
    private String frst_reg_dt;	    //최초등록일시
    private String last_mdfr;	    //최종수정자
    private String last_mdfcn_dt;	//최종수정일시

    //KB_SRVY_QSTN_INFO 문항
    private String srvy_qstn_sn;    //설문 질문 일련번호
    private String qstn_type;	    //질문 유형
    private String srvy_qstn;	    //설문 질문
    private String srvy_dtl_qstn;   //설문 질문 상세 내용
    private String esntl_vl_yn;	    //필수값 여부

    //KB_SRVY_ANS_INFO 문항에 대한 보기(객관식,주관식)
    private String srvy_ans_sn;	    //설문 답변 일련번호
    private String ans_cn;	        //답변 내용
    private String aftr_mvmn;	    //이후 이동(다음 단계)
    private String rspns_ymd;	    //응답일
    private String prgrs_stts;	    //진행 상태 (0 : 접수, 1 : 1차 심사, 2 : 2차 심사, 3 : 3차 심사, 4 : 4차 심사, 5 : 5차 심사, 6 : 합격, 9 : 불합격)
    private String conm;	        //업체명

    //KB_SRVY_RSPNS_INFO 응답
    private String srvy_rspns_sn;       //설문 응답 일련번호
    private String rspns_cn;	        //응답 내용
    private String atch_file_sn;        //첨부파일 일련번호

    //KB_SPRT_EXPLN_INFO 모집
    private String sprt_expln_sn;        //지원 안내 일련번호
    private String recru_se;             //모집 구분
    private String recru_bgng_ymd;       //모집 시작 일자
    private String recru_end_ymd;        //모집 종료 일자
    private String recru_end_hr;         //모집 종료 시간
    private String recru_trgt_m;         //모집 대상 메인
    private String recru_trgt_s;         //모집 대상 서브
    private String recru_fld_m;          //모집 분야 메인
    private String recru_fld_s;          //모집 분야 서브
    private String slctn_benef_cn1;      //선정 혜택 내용1
    private String slctn_benef_cn2;      //선정 혜택 내용2
    private String slctn_benef_cn3;      //선정 혜택 내용3
    private String slctn_benef_cn4;      //선정 혜택 내용4
    private String recru_schdl;          //모집일정(기간)
    private String slctn_prcs;           //선별 절차

    private int current_cnt;
    private int rnum;
}
