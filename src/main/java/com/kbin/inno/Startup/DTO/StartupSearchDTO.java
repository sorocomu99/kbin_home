package com.kbin.inno.Startup.DTO;

import lombok.Data;

@Data
public class StartupSearchDTO {
    private String keyWord;    //최상단 단어검색
    private String option1;    //최종 투자단계
    private String option2;    //누적투자금액
    private String option3;    //근무자
    private String minAmt;     //최소투자금액 입력값
    private String maxAmt;     //최대투자금액 입력값
    private String minEmp;     //최소근무자 입력값
    private String maxEmp;     //최대근무자 입력값
    private String option4;    //설립일
    private String dateRange;  //날짜 입력
    private String keyWord1;   //마스터 폼의 입력값을 디테일폼에 세팅을 위한 변수
    private String detSearch;
    private String detSearch1;
    private String fromDate;
    private String toDate;
    private String pageSize;
}
