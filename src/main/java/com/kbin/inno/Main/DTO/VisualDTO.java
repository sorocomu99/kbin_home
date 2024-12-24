package com.kbin.inno.Main.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
//public class VisualDTO implements Serializable {
public class VisualDTO {
	
	//private static final long serialVersionUID = 1L;
    
	private int main_sn;             // 메인 일련번호
    private String main_cn;          // 메인 내용
    private String expsr_yn;         // 노출 여부
    private int sort_no;             // 정렬 번호
    private int atch_file_sn;        // 첨부파일 일련번호
    private MultipartFile main_file; // 메인 비주얼 파일
    private String main_file_name;   // 메인 비주얼 파일명
    private String origin_file_name; // 메인 비주얼 오리지널 파일명
    private String main_path;        // 메인 비주얼 파일 경로
}
