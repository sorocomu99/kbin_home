package com.kbin.inno.Nurture.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
public class CooperationDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;
    
	private int coope_sn;                 // 협력 일련번호
    private String bzenty_nm;             // 업체명
    private String expsr_yn;              // 노출 여부
    private int sort_no;                  // 정렬 번호
    private int atch_file_id;             // 첨부파일 아이디
    private MultipartFile coope_file;     // 협력 기관 파일
    private String coope_file_name;       // 협력 기관 파일명
    private String origin_file_name;      // 협력 기관 오리지널 파일명
    private String coope_path;            // 협력 기관 파일 경로
}
