package com.kbin.inno.Main.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
//public class ResultDTO implements Serializable {
public class ResultDTO {
	
	//private static final long serialVersionUID = 1L;
    
	private int ent_nocs;               // 육성 건수
    private String ent_crtr_ymd;        // 육성 기준년월일
    private int invest_nocs;            // 투자 건수
    private String invest_crtr_ymd;     // 투자 기준년월일
    private int affiliate_nocs;         // 제휴 건수
    private String affiliate_crtr_ymd;  // 제휴 기준년월일
}
