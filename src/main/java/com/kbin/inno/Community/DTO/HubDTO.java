/**
 * 파일명     : HubDTO.java
 * 화면명     : HUB 센터 소식 관리
 * 설명       : HUB 센터 소식 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.14
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kbin.inno.Community.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class HubDTO {
    private int hub_sn;             // 허브 일련번호
    private String hub_ttl;         // 허브 제목
    private String hub_cn;          // 허브 내용
    private String expsr_yn;        // 노출 여부
    private int sort_no;            // 정렬 번호
    private String ctgry;           // 카테고리
    private int atch_file_sn1;      // 첨부파일 일련번호(썸네일)
    private int atch_file_sn2;      // 첨부파일 일련번호(동영상)
    private int atch_file_sn3;      // 첨부파일 일련번호
    private int frst_rgtr;          // 최초 등록자
    private String frst_reg_dt;     // 최초 등록일시

    // 썸네일
    private String hub_img_name;    // 공지사항 파일명
    private String origin_img_name; // 공지사항 오리지널 파일명
    private String hub_path_img;    // 공지사항 파일 경로

    // 동영상
    private String hub_mov_name;    // 공지사항 파일명
    private String origin_mov_name; // 공지사항 오리지널 파일명
    private String hub_path_mov;    // 공지사항 파일 경로

    // 파일
    private MultipartFile hub_file;  // 공지사항 파일
    private String hub_file_name;    // 공지사항 파일명
    private String origin_file_name; // 공지사항 오리지널 파일명
    private String hub_path;         // 공지사항 파일 경로

    // before, after
    private int hub_sn_bef;           // 이전 게시글 번호
    private String hub_ttl_bef;       // 이전 게시글 제목
    private int atch_file_sn1_bef;    // 이전 게시글 파일번호
    private int hub_sn_aft;           // 다음 게시글 번호
    private String hub_ttl_aft;       // 다음 게시글 제목
    private int atch_file_sn1_aft;    // 다음 게시글 파일 번호
}
