/**
 * 파일명     : KeywdDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 키워드 정보 변수
 * 관련 DB    : KB_API_KEYWD_INFO
 * 최초개발일 : 2024.11.15
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kbin.inno.Startup.DTO;

import lombok.Data;

@Data
public class KeywdDTO {
    private String keywd;   //키워드
    private long nocs;       //건수
}
