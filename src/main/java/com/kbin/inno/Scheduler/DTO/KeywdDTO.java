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
package com.kbin.inno.Scheduler.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class KeywdDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;

    private String ent_cd;  //기업 코드
    private String keywd;   //키워드
    private long nocs;       //건수
}
