package com.kbin.inno.Community.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoryDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;
	
	private int ctgry_sn;       // 카테고리 번호
    private String ctgry_nm;       // 카테고리 이름
}
