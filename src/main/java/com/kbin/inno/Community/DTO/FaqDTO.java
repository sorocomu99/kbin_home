package com.kbin.inno.Community.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FaqDTO implements Serializable {

	//private static final long serialVersionUID = 1L;
	
	private int faq_sn;          // FAQ 일련 번호
    private String faq_ctgry;    // FAQ 카테고리
    private String ctgry_nm; // FAQ 카테고리명
    private String faq_qstn;     // FAQ 질문
    private String faq_ans;      // FAQ 답변
    private String expsr_yn;     // 노출 여부
    private int sort_no;         // 정렬 번호
    private int frst_rgtr;       // 최초 등록자
    private Date frst_reg_dt;    // 최초 등록 일시
    private String faq_id;
    private String faq_sec;
}
