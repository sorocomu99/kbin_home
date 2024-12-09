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
package com.kbin.inno.Scheduler.DTO;

import lombok.Data;

@Data
public class AstDTO {

    private String ent_cd;           //기업 코드
    private String yr;               //년도
    private long current_assets;      //유동자산(단위 : 천원)
    private long non_current_assets;  //비유동자산(단위 : 천원)
    private long ast_gramt;           //자산총계(단위 : 천원)
    private long debt_gramt;          //부채총계(단위 : 천원)
    private long cptl;                //자본금(단위 : 천원)
    private long cptl_gramt;          //자본총계(단위 : 천원)
}
