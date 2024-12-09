/**
 * 파일명     : EmploDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 고용 정보 변수
 * 관련 DB    : KB_API_EMPLO_INFO
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
public class EmploDTO {

    private String ent_cd;    //기업 코드
    private String crtr_ym;   //기준 년월
    private long jncmp_nocs;   //입사자 수
    private long rsgntn_nocs;  //퇴사자 수
    private long hdof_nocs;    //재직자 수
}
