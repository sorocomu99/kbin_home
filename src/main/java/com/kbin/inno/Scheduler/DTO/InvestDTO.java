/**
 * 파일명     : InvestDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 투자 정보 변수
 * 관련 DB    : KB_API_INVEST_INFO
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
public class InvestDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;

    private String ent_cd;      //기업 코드
    private String investor;    //투자자
    private String series_nm;   //투자 시리즈명
    private long invest_amt;     //투자 금액(단위 : 억)
    private String invest_ymd;  //투자 일자
    private String news_link;   //기사 링크
}
