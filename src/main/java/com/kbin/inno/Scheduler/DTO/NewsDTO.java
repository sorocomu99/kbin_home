/**
 * 파일명     : NewsDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 뉴스 정보 변수
 * 관련 DB    : KB_API_NEWS_INFO
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
public class NewsDTO {

    private String ent_cd;     //기업 코드
    private String news_id;    //뉴스 아이디
    private String provider;   //제공자
    private String news_ttl;   //기사 제목
    private String news_link;  //기사 링크
    private String thumb_url;  //썸네일 이미지 URL
}
