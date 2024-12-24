/**
 * 파일명     : SlsDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 매출 정보(손익계산서) 변수
 * 관련 DB    : KB_API_SLS_INFO
 * 최초개발일 : 2024.11.15
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kbin.inno.Startup.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class SlsDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;
    
	private String ent_cd;             //기업 코드
    private String yr;                 //년도
    private Double sls_amt;            //매출액
    private Double operating_revenue;  //영업수익(단위 : 천원)
    private Double sls_cost_amt;       //매출원가(단위 : 천원)
    private Double sls_gramt;          //매출총이익(단위 : 천원)
    private Double sga_amt;            //판관비(단위 : 천원)
    private Double operating_profit;   //영업이익(단위 : 천원)
    private Double net_profit;         //당기순이익(단위 : 천원)
    private String sls_amt_str;
    private String operating_revenue_str;
    private String sls_cost_amt_str;
    private String sls_gramt_str;
    private String sga_amt_str;
    private String operating_profit_str;
    private String net_profit_str;
    private String sls_amt_col;
    private String operating_revenue_col;
    private String sls_cost_amt_col;
    private String sls_gramt_col;
    private String sga_amt_col;
    private String operating_profit_col;
    private String net_profit_col;
}
