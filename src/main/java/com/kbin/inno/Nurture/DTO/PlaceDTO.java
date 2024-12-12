/**
 * 파일명     : PlaceDTO.java
 * 화면명     : 육성공간 관리
 * 설명       : 육성공간 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.06
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kbin.inno.Nurture.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlaceDTO {
    private int plc_sn;               // 공간(장소) 일련 번호
    private String plc_nm;            // 공간(장소) 명
    private String plc_addr;          // 공간(장소) 주소
    private int sort_no;              // 정렬 번호

    private int atch_file_sn1;        // 첨부파일 일련번호1
    private int atch_file_sn2;        // 첨부파일 일련번호2
    private int atch_file_sn3;        // 첨부파일 일련번호3

    private MultipartFile plc_file1;  // 공간(장소) 파일1
    private String plc_file_name1;    // 공간(장소) 파일명1
    private String origin_file_name1; // 공간(장소) 오리지널 파일명1
    private String plc_path1;         // 공간(장소) 파일 경로1

    private MultipartFile plc_file2;  // 공간(장소) 파일2
    private String plc_file_name2;    // 공간(장소) 파일명2
    private String origin_file_name2; // 공간(장소) 오리지널 파일명2
    private String plc_path2;         // 공간(장소) 파일 경로2

    private MultipartFile plc_file3;  // 공간(장소) 파일3
    private String plc_file_name3;    // 공간(장소) 파일명3
    private String origin_file_name3; // 공간(장소) 오리지널 파일명3
    private String plc_path3;         // 공간(장소) 파일 경로3
}
