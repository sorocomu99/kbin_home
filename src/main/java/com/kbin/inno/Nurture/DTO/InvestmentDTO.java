package com.kbin.inno.Nurture.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class InvestmentDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;
    
	private int graph_sn;       // 그래프 일련번호
    private String crtr_ym;     // 기준년월
    private String graph1_yr;   // 그래프 1 연도
    private int graph1_nocs;    // 그래프 1 기업 수
    private String graph2_yr;   // 그래프 2 연도
    private int graph2_nocs;    // 그래프 2 기업 수
    private String graph3_yr;   // 그래프 3 연도
    private int graph3_nocs;    // 그래프 3 기업 수
    private String graph4_yr;   // 그래프 4 연도
    private int graph4_nocs;    // 그래프 4 기업 수
    private String graph5_yr;   // 그래프 5 연도
    private int graph5_nocs;    // 그래프 5 기업 수
}
