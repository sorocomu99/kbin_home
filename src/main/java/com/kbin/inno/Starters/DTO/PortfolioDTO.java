package com.kbin.inno.Starters.DTO;

import lombok.Data;
import java.io.Serializable;

@Data
public class PortfolioDTO implements Serializable {

    private String port_sn;                 //포트폴리오 일련번호
    private String port_yr;                 //포트폴리오 년도
    private String bzenty_nm;               //포트폴리오 업체명
    private String intro_cn;                //포트폴리오 소개 내용
    private String hmpg_link;               //포트폴리오 홈페이지 링크
    private String frst_rgtr;               //최초등록자
    private String frst_reg_dt;             //최초등록일시
    private String last_mdfr;               //최종수정자
    private String last_mdfcn_dt;           //최종수정일시
    private String sort_no;                 //정렬번호
    private String atch_file_sn;            //포트폴리오 첨부파일 일련번호
    private String ori_file_name;           //오리지널 파일명
    private String file_name;               //파일명
    private String file_path;               //파일경로

}
