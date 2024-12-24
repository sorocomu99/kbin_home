/**
 * 파일명     : AffiliateDTO.java
 * 화면명     : 국내 프로그램 - 제휴 사례 관리
 * 설명       : 국내 프로그램 - 제휴 사례 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.31
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kbin.inno.Nurture.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
public class AffiliateDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;
    
	private int affiliate_sn;             // 제휴 일련번호
    private String affiliate_nm;          // 계열사명
    private String starter_nm;            // 스타터스명
    private String srvc_nm;               // 서비스명
    private String ctgry_nm;              // 카테고리명
    private String expsr_yn;              // 노출 여부
    private int sort_no;                  // 정렬 번호
    private int atch_file_sn;             // 첨부파일 일련번호
    private MultipartFile affiliate_file; // 제휴 사례 파일
    private String affiliate_file_name;   // 제휴 사례 파일명
    private String origin_file_name;      // 제휴 사례 오리지널 파일명
    private String affiliate_path;        // 제휴 사례 파일 경로
}
