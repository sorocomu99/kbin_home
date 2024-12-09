/**
 * 파일명     : StartupDTO.java
 * 화면명     : 스타트업 검색
 * 설명       : 스타트업 Search 용
 * 최초개발일 : 2024.11.25
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kbin.inno.Startup.DTO;

import lombok.Data;

@Data
public class StartupDTO {
    private String ent_cd;
    private String ent_nm;
    private String main_biz;
    private String tpbiz;
    private String kb_starters_yn;
    private String series_nm;
    private Long invest_amt;
    private int now_wrkr_nocs;
    private String invest_amt_str;
}