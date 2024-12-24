/**
 * 파일명     : AstDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 자산(재무상태) 정보 변수
 * 관련 DB    : KB_API_AST_INFO
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
public class AstDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;
    
	private String ent_cd;           //기업 코드
    private String yr;               //년도
    private Double current_assets;      //유동자산(단위 : 천원)
    private Double non_current_assets;  //비유동자산(단위 : 천원)
    private Double ast_gramt;           //자산총계(단위 : 천원)
    private Double debt_gramt;          //부채총계(단위 : 천원)
    private Double cptl;                //자본금(단위 : 천원)
    private Double cptl_gramt;          //자본총계(단위 : 천원)
    private String current_assets_str;
    private String non_current_assets_str;
    private String ast_gramt_str;
    private String debt_gramt_str;
    private String cptl_str;
    private String cptl_gramt_str;
    private String current_assets_col;
    private String non_current_assets_col;
    private String ast_gramt_col;
    private String debt_gramt_col;
    private String cptl_col;
    private String cptl_gramt_col;

}
