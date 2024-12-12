package com.kbin.inno.Community.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class NoticeDTO {
    private int ntc_sn;              // 공지사항 일련번호
    private String ntc_ttl;          // 공지사항 제목
    private String ntc_cn;           // 공지사항 내용
    private int atch_file_sn;        // 첨부파일 일련번호
    private String expsr_yn;         // 노출 여부
    private int sort_no;             // 정렬 번호
    private String mngr_nm;          // 최초 등록자
    private String frst_reg_dt;      // 최초 등록 일시
    private String ntc_yn;           // 공지사항 등록 여부
    private MultipartFile ntc_file;  // 공지사항 파일
    private String ntc_file_name;    // 공지사항 파일명
    private String origin_file_name; // 공지사항 오리지널 파일명
    private String ntc_path;         // 공지사항 파일 경로
    private int pageCount;           // 글 갯수
    private int ntc_sn_bef;          // 이전 게시글 번호   
    private String ntc_ttl_bef;      // 이전 게시글 제목
    private int atch_file_sn_bef;    // 이전 게시글 파일번호
    private int ntc_sn_aft;          // 다음 게시글 번호
    private String ntc_ttl_aft;      // 다음 게시글 제목
    private int atch_file_sn_aft;    // 다음 게시글 파일 번호
}
