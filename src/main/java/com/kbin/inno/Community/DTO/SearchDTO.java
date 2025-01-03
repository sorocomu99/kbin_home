package com.kbin.inno.Community.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class SearchDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;
    
	private int start;      // 시작 페이지
    private int end;        // 끝 페이지
    private String type;    // 검색 타입
    private String keyword; // 검색 조건
    private int ctgry;      // FAQ 검색을 위한 카테고리 번호
}
