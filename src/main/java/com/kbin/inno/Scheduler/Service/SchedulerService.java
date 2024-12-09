/**
 * 파일명     : SchedulerService.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : 설정된 시간에 스타터 기업 API를 호출하여 DB에 저장한다.
 * 관련 DB    : KB_API_STARTER_INFO, KB_API_BIZ_SRVC_INFO
 *              KB_API_EMPLO_INFO, KB_API_INVEST_INFO
 *              KB_API_SLS_INFO, KB_API_AST_INFO
 *              KB_API_NEWS_INFO, KB_API_KEYWD_INFO
 * 로그 DB    : KB_API_BACH_JOB_INSTCE, KB_API_BACH_JOB_EXCN
 *              KB_API_BACH_JOB_EXCN_PARAM, KB_API_BACH_JOB_EXCN_CONTXT
 *              KB_API_BACH_STEP_EXCN, KB_API_BACH_STEP_EXCN_CONTXT
 * 최초개발일 : 2024.11.07
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kbin.inno.Scheduler.Service;

import com.kbin.inno.Scheduler.DAO.SchedulerDAO;
import com.kbin.inno.Scheduler.DTO.StaterDTO;
import com.kbin.inno.Scheduler.DTO.BizSrvcDTO;
import com.kbin.inno.Scheduler.DTO.EmploDTO;
import com.kbin.inno.Scheduler.DTO.InvestDTO;
import com.kbin.inno.Scheduler.DTO.SlsDTO;
import com.kbin.inno.Scheduler.DTO.AstDTO;
import com.kbin.inno.Scheduler.DTO.NewsDTO;
import com.kbin.inno.Scheduler.DTO.KeywdDTO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class SchedulerService {
    @Autowired
    SchedulerDAO schedulerDAO;

    //API ACCESS TOKEN (API 개발 업체에서 제공)
    private String accessToken = "9fb315f867904ab36e45a2bce280cb411f0941e9b067b15bcd96a639b5321313";

    /**
     * API를 호출하여 해당 DB에 저장
     * param
     * return int 200 : 정상, 200 이 아니면 오류
     */
    public int getApi() throws IOException, ParseException {
        int result = 200;

        //try {
            //totalCount를 구하기 위해 변수 세팅 (최초 1회만 생성 for 문에서 반복 호출)

            int page = 1;
            int countPerPage = 5;
            String getUrl = "https://api.pdeck.co.kr/api/v1/venture-companies?page=" + page + "&countPerPage=" + countPerPage;

            HttpClient client = HttpClientBuilder.create().build();  //HttpClient 생성
            HttpGet getRequest = new HttpGet(getUrl);  //GET 메소드 URL 생성

            //Header 세팅
            getRequest.addHeader("accept", "application/json;charset=utf-8");
            getRequest.addHeader("PDECK-ACCESS-TOKEN", accessToken);

            //API 호출
            HttpResponse response = client.execute(getRequest);
            //Response 출력 statusCode가 200이면 정상
            //이후 500건씩 for문으로 상세 정보 가져온다
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                //API 리턴 값을 String 변수에 세팅
                String body = handler.handleResponse(response);

                //리턴한 값을 JSONObject로 변환하기 위한 초기 세팅
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(body);

                //data의 값을 Map으로 변환
                Map<String,Object> jsonMap = (Map) jsonObject.get("data");
                String totalCnt = jsonMap.get("totalCount").toString();  //총 건수를가지고 온다

                //for문을 몇번 돌릴지 구한다.
                double totCnt = (double) Integer.parseInt(totalCnt);  //반올림을 위해 double로 변환
                double totCnt1 = (totCnt / 500);  //1번에 가져올수 건수는 500건 임
                int loop = (int) Math.ceil(totCnt1);  //반올림 처리하여 for문의 횟수를 구한다.

                //구한 횟수를 가지고 for문으로 UNIQUE 한 코드를 가지고 온다.
                int forPage = 1;
                //UNIQUE 한 코드값을 500건씩 가져와 DB 에 저장
                for (int i = 1; i <= loop; i++) {
                    getUrl = "https://api.pdeck.co.kr/api/v1/venture-companies?page=" + i + "&countPerPage=500";

                    HttpClient requestClient = HttpClientBuilder.create().build();  //HttpClient 생성
                    HttpGet requestApi = new HttpGet(getUrl);  //GET 메소드 URL 생성

                    //리스트 Header 세팅
                    requestApi.addHeader("accept", "application/json;charset=utf-8");
                    requestApi.addHeader("PDECK-ACCESS-TOKEN", accessToken);

                    HttpResponse responseApi = requestClient.execute(requestApi);
                    //500건이 정상으로 오면 수행
                    if (responseApi.getStatusLine().getStatusCode() == 200) {
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        String responseBody = responseHandler.handleResponse(responseApi);

                        //가져온 500건을 json 으로 파싱
                        JSONParser resJsonParser = new JSONParser();
                        JSONObject resJsonObject = new JSONObject();
                        resJsonObject = (JSONObject) resJsonParser.parse(responseBody);

                        Map<String,Object> resJsonMapApi = new HashMap<>();
                        resJsonMapApi = (Map) resJsonObject.get("data");

                        //리스트 안에 기업의 UNIQUE 코드를 가지고 상세정보를 호출한다.
                        ArrayList<Map<String, Object>> resList = new ArrayList<>();
                        resList = (ArrayList) resJsonMapApi.get("list");

                        //List의 size 만큼 for문으로 상세정보 가져온다.
                        String getDetailUrl = "";
                        if (resList.size() > 0) {
                            String corpCd = "";  //UNIQUE 한 기업코드
                            for (int det = 0; det < resList.size(); det++) {
                                //상세 정보 호출
                                getDetailUrl = "https://api.pdeck.co.kr/api/v1/venture-companies/" + resList.get(det);
                                HttpClient detailClient = HttpClientBuilder.create().build();
                                HttpGet getDetail = new HttpGet(getDetailUrl);

                                getDetail.addHeader("accept", "application/json;charset=utf-8");
                                getDetail.addHeader("PDECK-ACCESS-TOKEN", accessToken);

                                HttpResponse resDetail = requestClient.execute(getDetail);
                                if (resDetail.getStatusLine().getStatusCode() == 200) {
                                    //상세정보를 가지고 해당 Map과 List에 담고 DB에 저장
                                    ResponseHandler<String> detailHandler = new BasicResponseHandler();
                                    String detailBody = detailHandler.handleResponse(resDetail);

                                    JSONParser detailParser = new JSONParser();
                                    JSONObject detailObject = new JSONObject();
                                    detailObject = (JSONObject) detailParser.parse(detailBody);

                                    //상세정보를 Map 에 담는다.
                                    Map<String, Object> detailData = new HashMap<>();
                                    detailData = (Map) detailObject.get("data");

                                    //상세정보를 담은 Map에서 Detail 정보를 다시 Map으로 담는다.
                                    /*
                                     * Detail 정보는 아래와 같이 이루어져 있다
                                     * outline -> Map 으로 변환
                                     * outline 에서 필수는 code, bizNo, name, ceo 그외는 키 존재 유무 체크
                                     * business -> Map 으로 변환
                                     * business.service -> List 로 변환
                                     * employment -> Map 으로 변환
                                     * employment.dataByMonth -> List 로 변환
                                     * invest -> List 로 변환
                                     * finance -> Map 으로 변환
                                     * finance.majorIncomeStatement -> List 로 변환
                                     * finance.majorBalanceSheet -> List 로 변환
                                     * news -> Map 으로 변환
                                     * news.newsList -> List 로 변환
                                     * news.newsKeywordList -> List 로 변환
                                     */
                                    //outline -> Map 으로 변환
                                    Map<String, Object> detOutLine = new HashMap<>();
                                    detOutLine = (Map) detailData.get("outline");
                                    //변환된 outline을 DTO 변수에 세팅 (API 값 -> DTO 값)
                                    StaterDTO staterDTO = new StaterDTO();
                                    /* 필수여서 키 존재 유무 체크 필요 없음 시작 */
                                    staterDTO.setEnt_cd((String) detOutLine.get("code"));            //기업코드 code -> ent_cd
                                    staterDTO.setBrno((String) detOutLine.get("bizNo"));             //사업자번호 bizNo -> brno
                                    staterDTO.setRprsv_nm((String) detOutLine.get("ceo"));           //대표자 명 ceo -> rprsv_nm
                                    staterDTO.setEnt_nm((String) detOutLine.get("name"));            //기업이름 name -> ent_nm
                                    /* 필수여서 키 존재 유무 체크 필요 없음 종료 */

                                    /* 값이 없으면 key 자체가 없으므로 key 존재 유무 체크 필요 시작 */
                                    /* 기본정보 시작 */
                                    //법인번호 corporationNo -> corp_no
                                    if (detOutLine.containsKey("corporationNo")) {
                                        staterDTO.setCorp_no((String) detOutLine.get("corporationNo"));
                                    } else {
                                        staterDTO.setCorp_no("");
                                    }
                                    //업종 bzType1 -> tpbiz
                                    if (detOutLine.containsKey("bzType1")) {
                                        staterDTO.setTpbiz((String) detOutLine.get("bzType1"));
                                    } else {
                                        staterDTO.setTpbiz("");
                                    }
                                    //기업형태 bzType2 -> ent_shape
                                    if (detOutLine.containsKey("bzType2")) {
                                        staterDTO.setEnt_shape((String) detOutLine.get("bzType2"));
                                    } else {
                                        staterDTO.setEnt_shape("");
                                    }
                                    //기업규모 scale -> end_scale
                                    if (detOutLine.containsKey("scale")) {
                                        staterDTO.setEnt_scale((String) detOutLine.get("scale"));
                                    } else {
                                        staterDTO.setEnt_scale("");
                                    }
                                    //주소 address -> addr
                                    if (detOutLine.containsKey("address")) {
                                        staterDTO.setAddr((String) detOutLine.get("address"));
                                    } else {
                                        staterDTO.setAddr("");
                                    }
                                    //설립일자 beginDateYmd -> fndn_ymd
                                    if (detOutLine.containsKey("beginDateYmd")) {
                                        staterDTO.setFndn_ymd((String) detOutLine.get("beginDateYmd"));
                                    }
                                    //표준산업분류코드 industryCode -> ksic_cd
                                    if (detOutLine.containsKey("industryCode")) {
                                        staterDTO.setKsic_cd((String) detOutLine.get("industryCode"));
                                    } else {
                                        staterDTO.setKsic_cd("");
                                    }
                                    //전화번호 tel -> telno
                                    if (detOutLine.containsKey("tel")) {
                                        staterDTO.setTelno((String) detOutLine.get("tel"));
                                    } else {
                                        staterDTO.setTelno("");
                                    }
                                    //팩스번호 fax -> fxno
                                    if (detOutLine.containsKey("fax")) {
                                        staterDTO.setFxno((String) detOutLine.get("fax"));
                                    } else {
                                        staterDTO.setFxno("");
                                    }
                                    //우편번호 zipCode -> zip
                                    if (detOutLine.containsKey("zipCode")) {
                                        staterDTO.setZip((String) detOutLine.get("zipCode"));
                                    } else {
                                        staterDTO.setZip("");
                                    }
                                    //공기업 여부 isGov -> public_ent_yn
                                    if (detOutLine.containsKey("isGov")) {
                                        if ((boolean) detOutLine.get("isGov")) {
                                            staterDTO.setPublic_ent_yn("Y");
                                        } else {
                                            staterDTO.setPublic_ent_yn("N");
                                        }
                                    }
                                    //개인사업자 여부 isPrivateOwned -> indiv_bzmn_yn
                                    if (detOutLine.containsKey("indiv_bzmn_yn")) {
                                        if ((boolean) detOutLine.get("isPrivateOwned")) {
                                            staterDTO.setIndiv_bzmn_yn("Y");
                                        } else {
                                            staterDTO.setIndiv_bzmn_yn("N");
                                        }
                                    }
                                    //본사 여부 isMainOffice -> hdofc_yn
                                    if (detOutLine.containsKey("hdofc_yn")) {
                                        if ((boolean) detOutLine.get("isMainOffice")) {
                                            staterDTO.setHdofc_yn("Y");
                                        } else {
                                            staterDTO.setHdofc_yn("N");
                                        }
                                    }
                                    //벤처인증 여부 isVenture -> venture_cert_yn
                                    if (detOutLine.containsKey("isVenture")) {
                                        if ((boolean) detOutLine.get("isVenture")) {
                                            staterDTO.setVenture_cert_yn("Y");
                                        } else {
                                            staterDTO.setVenture_cert_yn("N");
                                        }
                                    }
                                    //IPO 여부 isIpo -> ipo_yn
                                    if (detOutLine.containsKey("isIpo")) {
                                        if ((boolean) detOutLine.get("isIpo")) {
                                            staterDTO.setIpo_yn("Y");
                                        } else {
                                            staterDTO.setIpo_yn("N");
                                        }
                                    }
                                    //외국인투자법인 여부 isForeignInvest -> foreign_invest_yn
                                    if (detOutLine.containsKey("isForeignInvest")) {
                                        if ((boolean) detOutLine.get("isForeignInvest")) {
                                            staterDTO.setForeign_invest_yn("Y");
                                        } else {
                                            staterDTO.setForeign_invest_yn("N");
                                        }
                                    }
                                    //금융회사 여부 isFinancialCompany -> fnst_yn
                                    if (detOutLine.containsKey("isFinancialCompany")) {
                                        if ((boolean) detOutLine.get("isFinancialCompany")) {
                                            staterDTO.setFnst_yn("Y");
                                        } else {
                                            staterDTO.setFnst_yn("N");
                                        }
                                    }
                                    //상장일자 ipoDateYmd -> listing_ymd
                                    if (detOutLine.containsKey("ipoDateYmd")) {
                                        staterDTO.setListing_ymd((String) detOutLine.get("ipoDateYmd"));
                                    }
                                    //상장주관사 ipoUnderwriter -> listing_co
                                    if (detOutLine.containsKey("ipoUnderwriter")) {
                                        staterDTO.setListing_co((String) detOutLine.get("ipoUnderwriter"));
                                    }
                                    //대기업 여부 isLargeSizeCorp -> major_ent_yn
                                    if (detOutLine.containsKey("isLargeSizeCorp")) {
                                        if ((boolean) detOutLine.get("isLargeSizeCorp")) {
                                            staterDTO.setMajor_ent_yn("Y");
                                        } else {
                                            staterDTO.setMajor_ent_yn("N");
                                        }
                                    }
                                    //중견기업 여부 isMiddleBigSizeCorp -> medium_ent_yn
                                    if (detOutLine.containsKey("isMiddleBigSizeCorp")) {
                                        if ((boolean) detOutLine.get("isMiddleBigSizeCorp")) {
                                            staterDTO.setMedium_ent_yn("Y");
                                        } else {
                                            staterDTO.setMedium_ent_yn("N");
                                        }
                                    }
                                    //코스피상장 여부 isKospi -> kospi_yn
                                    if (detOutLine.containsKey("isKospi")) {
                                        if ((boolean) detOutLine.get("isKospi")) {
                                            staterDTO.setKospi_yn("Y");
                                        } else {
                                            staterDTO.setKospi_yn("N");
                                        }
                                    }
                                    //코스닥상장 여부 isKosdaq -> kosdaq_yn
                                    if (detOutLine.containsKey("isKosdaq")) {
                                        if ((boolean) detOutLine.get("isKosdaq")) {
                                            staterDTO.setKosdaq_yn("Y");
                                        } else {
                                            staterDTO.setKosdaq_yn("N");
                                        }
                                    }
                                    //코넥스상장 여부 isKonex -> konex_yn
                                    if (detOutLine.containsKey("isKonex")) {
                                        if ((boolean) detOutLine.get("isKonex")) {
                                            staterDTO.setKonex_yn("Y");
                                        } else {
                                            staterDTO.setKonex_yn("N");
                                        }
                                    }
                                    //KOTC 여부 isKOTC -> kotc_yn
                                    if (detOutLine.containsKey("isKOTC")) {
                                        if ((boolean) detOutLine.get("isKOTC")) {
                                            staterDTO.setKotc_yn("Y");
                                        } else {
                                            staterDTO.setKotc_yn("N");
                                        }
                                    }
                                    //외감기업 여부 isExternalAudit -> external_audit_yn
                                    if (detOutLine.containsKey("isExternalAudit")) {
                                        if ((boolean) detOutLine.get("isExternalAudit")) {
                                            staterDTO.setExternal_audit_yn("Y");
                                        } else {
                                            staterDTO.setExternal_audit_yn("N");
                                        }
                                    }
                                    //일반기업 여부 isGeneral -> general_ent_yn
                                    if (detOutLine.containsKey("isGeneral")) {
                                        if ((boolean) detOutLine.get("isGeneral")) {
                                            staterDTO.setGeneral_ent_yn("Y");
                                        } else {
                                            staterDTO.setGeneral_ent_yn("N");
                                        }
                                    }
                                    //비영리단체 여부 isNonprofit -> non_profit_yn
                                    if (detOutLine.containsKey("isNonprofit")) {
                                        if ((boolean) detOutLine.get("isNonprofit")) {
                                            staterDTO.setNon_profit_yn("Y");
                                        } else {
                                            staterDTO.setNon_profit_yn("N");
                                        }
                                    }
                                    //피흡수합병 여부 isAbsorptionMerged -> absorption_merged_yn
                                    if (detOutLine.containsKey("isAbsorptionMerged")) {
                                        if ((boolean) detOutLine.get("isAbsorptionMerged")) {
                                            staterDTO.setAbsorption_merged_yn("Y");
                                        } else {
                                            staterDTO.setAbsorption_merged_yn("N");
                                        }
                                    }
                                    //폐업 여부 isShutdown -> clsbiz_yn
                                    if (detOutLine.containsKey("isShutdown")) {
                                        if ((boolean) detOutLine.get("isShutdown")) {
                                            staterDTO.setClsbiz_yn("Y");
                                        } else {
                                            staterDTO.setClsbiz_yn("N");
                                        }
                                    }
                                    //휴업 여부 isOnBreak -> TCBIZ_YN
                                    if (detOutLine.containsKey("isOnBreak")) {
                                        if ((boolean) detOutLine.get("isOnBreak")) {
                                            staterDTO.setTcbiz_yn("Y");
                                        } else {
                                            staterDTO.setTcbiz_yn("N");
                                        }
                                    }
                                    //파산 여부 isBankrupt -> bankruptcy_yn
                                    if (detOutLine.containsKey("isBankrupt")) {
                                        if ((boolean) detOutLine.get("isBankrupt")) {
                                            staterDTO.setBankruptcy_yn("Y");
                                        } else {
                                            staterDTO.setBankruptcy_yn("N");
                                        }
                                    }
                                    /* 기본정보 종료 */

                                    /* 사업 서비스 정보 시작 */
                                    /*
                                     * mainBusiness, product, homePage 는 기본정보에 세팅
                                     * service 는 리스트 이므로 리스트에 세팅 후 for 문
                                     */
                                    Map<String, Object> detBusiness = new HashMap<>();
                                    detBusiness = (Map) detailData.get("business");
                                    //주요 사업 mainBusiness -> main_biz
                                    if (detBusiness.containsKey("mainBusiness")) {
                                        staterDTO.setMain_biz((String) detBusiness.get("mainBusiness"));
                                    }
                                    //제품 product -> prdct
                                    if (detBusiness.containsKey("product")) {
                                        staterDTO.setPrdct((String) detBusiness.get("product"));
                                    }
                                    //홈페이지 homePage -> hmpg
                                    if (detBusiness.containsKey("homePage")) {
                                        staterDTO.setHmpg((String) detBusiness.get("homePage"));
                                    }
                                    //service 는 리스트에 담아서 DB 저장
                                    ArrayList<Map<String, Object>> detBizSvc = new ArrayList<>();
                                    detBizSvc = (ArrayList) detBusiness.get("service");
                                    if (detBizSvc.size() > 0) {
                                        //리스트이므로 사이즈 만큼 for 문으로 DB 저장
                                        //저장전 PK 가 없으므로 기업 코드로 먼저 삭제 후 저장
                                        schedulerDAO.bizSrvcDel(staterDTO.getEnt_cd());
                                        for (int biz = 0; biz < detBizSvc.size(); biz++) {
                                            BizSrvcDTO bizSrvcDTO = new BizSrvcDTO();
                                            //기업 코드
                                            bizSrvcDTO.setEnt_cd(staterDTO.getEnt_cd());
                                            //서비스 명 name -> srvc_nm
                                            if (detBizSvc.get(biz).containsKey("name")) {
                                                bizSrvcDTO.setSrvc_nm((String) detBizSvc.get(biz).get("name"));
                                            } else {
                                                bizSrvcDTO.setSrvc_nm("");
                                            }
                                            //구글 플레이스토어 링크 aosLink -> google_app_link
                                            if (detBizSvc.get(biz).containsKey("aosLink")) {
                                                bizSrvcDTO.setGoogle_app_link((String) detBizSvc.get(biz).get("aosLink"));
                                            } else {
                                                bizSrvcDTO.setGoogle_app_link("");
                                            }
                                            //구글 플레이스토어 아이콘 링크 aosIconLink -> google_icon_link
                                            if (detBizSvc.get(biz).containsKey("aosIconLink")) {
                                                bizSrvcDTO.setGoogle_icon_link((String) detBizSvc.get(biz).get("aosIconLink"));
                                            } else {
                                                bizSrvcDTO.setGoogle_icon_link("");
                                            }
                                            //애플 앱스토어 링크 iosLink -> apple_app_link
                                            if (detBizSvc.get(biz).containsKey("iosLink")) {
                                                bizSrvcDTO.setApple_app_link((String) detBizSvc.get(biz).get("iosLink"));
                                            } else {
                                                bizSrvcDTO.setApple_app_link("");
                                            }
                                            //애플 앱스토어 아이콘 링크 iosIconLink -> apple_icon_link
                                            if (detBizSvc.get(biz).containsKey("iosIconLink")) {
                                                bizSrvcDTO.setApple_icon_link((String) detBizSvc.get(biz).get("iosIconLink"));
                                            } else {
                                                bizSrvcDTO.setApple_icon_link("");
                                            }
                                            //웹서비스 링크 webLink -> web_srvc_link
                                            if (detBizSvc.get(biz).containsKey("webLink")) {
                                                bizSrvcDTO.setWeb_srvc_link((String) detBizSvc.get(biz).get("webLink"));
                                            } else {
                                                bizSrvcDTO.setWeb_srvc_link("");
                                            }

                                            //테이블 KB_API_BIZ_SRVC_INFO 저장
                                            schedulerDAO.bizSrvcIns(bizSrvcDTO);
                                        }
                                        System.out.println("====================사업서비스 저장 종료");
                                    }
                                    /* 사업 서비스 정보 종료 */


                                    /* 고용정보 시작 */
                                    /*
                                     * yearmonth, recent12MonthsNewHires, recent12MonthsDepartures, recent12MonthsTurnoverRate, currentEmployees 는 기본정보에 세팅
                                     * dataByMonth 는 리스트 이므로 리스트에 세팅 후 for 문
                                     */
                                    Map<String, Object> detEmplo = new HashMap<>();
                                    detEmplo = (Map) detailData.get("employment");
                                    //기준 년월 yearmonth -> crtr_ym
                                    if (detEmplo.containsKey("yearmonth")) {
                                        staterDTO.setCrtr_ym((String) detEmplo.get("yearmonth"));
                                    }
                                    //최근 12개월간 신규 입사자 수 recent12MonthsNewHires -> mm12_jncmp_nocs
                                    if (detEmplo.containsKey("recent12MonthsNewHires")) {
                                        staterDTO.setMm12_jncmp_nocs((long) detEmplo.get("recent12MonthsNewHires"));
                                    }
                                    //최근 12개월간 신규 퇴사자 수 recent12MonthsDepartures -> mm12_rsgntn_nocs
                                    if (detEmplo.containsKey("recent12MonthsDepartures")) {
                                        staterDTO.setMm12_rsgntn_nocs((long) detEmplo.get("recent12MonthsDepartures"));
                                    }
                                    //최근 12개월간 퇴사율 recent12MonthsTurnoverRate -> mm12_rsgntn_rt
                                    if (detEmplo.containsKey("recent12MonthsTurnoverRate")) {
                                        String Mm12_rsgntn_rt = detEmplo.get("recent12MonthsTurnoverRate").toString();
                                        double rt = Double.parseDouble(Mm12_rsgntn_rt);
                                        //long Mm12_rsgntn_rt = ((long) detEmplo.get("recent12MonthsTurnoverRate"));
                                        //staterDTO.setMm12_rsgntn_rt((double) detEmplo.get("recent12MonthsTurnoverRate"));
                                        staterDTO.setMm12_rsgntn_rt(rt);
                                    }
                                    //현재 근무자 수 currentEmployees -> now_wrkr_nocs
                                    if (detEmplo.containsKey("currentEmployees")) {
                                        staterDTO.setNow_wrkr_nocs((long) detEmplo.get("currentEmployees"));
                                    }
                                    //dataByMonth 는 리스트에 담아 DB 저장
                                    ArrayList<Map<String, Object>> detDataByMon = new ArrayList<>();
                                    detDataByMon = (ArrayList) detEmplo.get("dataByMonth");
                                    if (detDataByMon.size() > 0) {
                                        //리스트이므로 사이즈 만큼 for 문으로 DB 저장
                                        for (int mon = 0; mon < detDataByMon.size(); mon++) {
                                            EmploDTO emploDTO = new EmploDTO();
                                            //기업 코드
                                            emploDTO.setEnt_cd(staterDTO.getEnt_cd());
                                            //기준 년월 dateYm -> crtr_ym
                                            if (detDataByMon.get(mon).containsKey("dateYm")) {
                                                emploDTO.setCrtr_ym((String) detDataByMon.get(mon).get("dateYm"));
                                            }
                                            //입사자 수 in -> jncmp_nocs
                                            if (detDataByMon.get(mon).containsKey("in")) {
                                                emploDTO.setJncmp_nocs((long) detDataByMon.get(mon).get("in"));
                                            }
                                            //퇴사자 수 out -> rsgntn_nocs
                                            if (detDataByMon.get(mon).containsKey("out")) {
                                                emploDTO.setRsgntn_nocs((long) detDataByMon.get(mon).get("out"));
                                            }
                                            //재직자 수 result -> hdof_nocs
                                            if (detDataByMon.get(mon).containsKey("result")) {
                                                emploDTO.setHdof_nocs((long) detDataByMon.get(mon).get("result"));
                                            }

                                            //테이블 KB_API_EMPLO_INFO 저장
                                            schedulerDAO.emploIns(emploDTO);
                                        }
                                        System.out.println("====================고용정보 저장 종료");
                                    }
                                    /* 고용정보 종료 */

                                    /* 투자정보 시작 */
                                    //invest 는 리스트에 담아 DB 저장
                                    ArrayList<Map<String, Object>> detInvest = new ArrayList<>();
                                    detInvest = (ArrayList) detailData.get("invest");
                                    if (detInvest.size() > 0) {
                                        //리스트이므로 사이즈 만큼 for 문으로 DB 저장
                                        //저장전 PK 가 없으므로 기업 코드로 먼저 삭제 후 저장
                                        schedulerDAO.investDel(staterDTO.getEnt_cd());
                                        for (int inv = 0; inv < detInvest.size(); inv++) {
                                            InvestDTO investDTO = new InvestDTO();
                                            //기업 코드
                                            investDTO.setEnt_cd(staterDTO.getEnt_cd());
                                            //투자자 investor -> investor
                                            if (detInvest.get(inv).containsKey("investor")) {
                                                investDTO.setInvestor((String) detInvest.get(inv).get("investor"));
                                            }
                                            //투자 시리즈명 series -> series_nm
                                            if (detInvest.get(inv).containsKey("series")) {
                                                investDTO.setSeries_nm((String) detInvest.get(inv).get("series"));
                                            }
                                            //투자 금액(단위 : 억) amount -> invest_amt
                                            if (detInvest.get(inv).containsKey("amount")) {
                                                if (detInvest.get(inv).get("amount") == null) {
                                                    investDTO.setInvest_amt(0);
                                                } else {
                                                    String amout = detInvest.get(inv).get("amount").toString();
                                                    double amt = Double.parseDouble(amout);
                                                    long amt1 = (long) (amt * 100000000);
                                                    investDTO.setInvest_amt(amt1);
                                                    //investDTO.setInvest_amt((long) detInvest.get(inv).get("amount"));
                                                }
                                            }
                                            //투자일자 investDate -> invest_ymd
                                            if (detInvest.get(inv).containsKey("investDate")) {
                                                investDTO.setInvest_ymd((String) detInvest.get(inv).get("investDate"));
                                            }
                                            //기사 링크 link -> news_link
                                            if (detInvest.get(inv).containsKey("link")) {
                                                investDTO.setNews_link((String) detInvest.get(inv).get("link"));
                                            }

                                            //테이블 KB_API_INVEST_INFO 에 저장
                                            schedulerDAO.investIns(investDTO);
                                        }
                                        System.out.println("====================투자정보 저장 종료");
                                    }
                                    /* 투자정보 종료 */

                                    /* 금융정보 시작 */
                                    Map<String, Object> detFinance = new HashMap<>();
                                    detFinance = (Map) detailData.get("finance");
                                    //자본잉여금 자본잉여금 -> cptl_amt
                                    if (detFinance.containsKey("자본잉여금")) {
                                        staterDTO.setCptl_amt((long) detFinance.get("자본잉여금"));
                                    }
                                    //이익잉여금 이익잉여금 -> profit_amt
                                    if (detFinance.containsKey("이익잉여금")) {
                                        staterDTO.setProfit_amt((long) detFinance.get("이익잉여금"));
                                    }
                                    //majorIncomeStatement(손익계산서) 는 리스트에 담아 DB 저장
                                    ArrayList<Map<String, Object>> detIncome = new ArrayList<>();
                                    detIncome = (ArrayList) detFinance.get("majorIncomeStatement");
                                    if (detIncome.size() > 0) {
                                        //리스트이므로 사이즈 만큼 for 문으로 DB 저장
                                        for (int incom = 0; incom < detIncome.size(); incom++) {
                                            SlsDTO slsDTO = new SlsDTO();
                                            //기업 코드
                                            slsDTO.setEnt_cd(staterDTO.getEnt_cd());
                                            //년도 year -> yr
                                            if (detIncome.get(incom).containsKey("year")) {
                                                slsDTO.setYr((String) detIncome.get(incom).get("year"));
                                            }
                                            //매출액(단위 : 천원) 매출액 -> sls_amt
                                            if (detIncome.get(incom).containsKey("매출액")) {
                                                slsDTO.setSls_amt((long) detIncome.get(incom).get("매출액"));
                                            }
                                            //영업수익(단위 : 천원) 영업수익 -> operating_revenue
                                            if (detIncome.get(incom).containsKey("영업수익")) {
                                                slsDTO.setOperating_revenue((long) detIncome.get(incom).get("영업수익"));
                                            }
                                            //매출원가(단위 : 천원) 매출원가 -> sls_cost_amt
                                            if (detIncome.get(incom).containsKey("매출원가")) {
                                                slsDTO.setSls_cost_amt((long) detIncome.get(incom).get("매출원가"));
                                            }
                                            //매출총이익(단위 : 천원) 매출총이익 -> sls_gramt
                                            if (detIncome.get(incom).containsKey("매출총이익")) {
                                                slsDTO.setSls_gramt((long) detIncome.get(incom).get("매출총이익"));
                                            }
                                            //판관비(단위 : 천원) 판관비 -> sga_amt
                                            if (detIncome.get(incom).containsKey("판관비")) {
                                                slsDTO.setSga_amt((long) detIncome.get(incom).get("판관비"));
                                            }
                                            //영업이익(단위 : 천원) 영업이익 -> operating_profit
                                            if (detIncome.get(incom).containsKey("영업이익")) {
                                                slsDTO.setOperating_profit((long) detIncome.get(incom).get("영업이익"));
                                            }
                                            //당기순이익(단위 : 천원) 당기순이익 -> net_profit
                                            if (detIncome.get(incom).containsKey("당기순이익")) {
                                                slsDTO.setNet_profit((long) detIncome.get(incom).get("당기순이익"));
                                            }

                                            //테이블 KB_API_SLS_INFO 에 저장
                                            schedulerDAO.slsIns(slsDTO);
                                        }
                                        System.out.println("====================손익계산서 저장 종료");
                                    }

                                    //majorBalanceSheet(재무상태) 는 리스트에 담아 DB 저장
                                    ArrayList<Map<String, Object>> detBalance = new ArrayList<>();
                                    detBalance = (ArrayList) detFinance.get("majorBalanceSheet");
                                    if (detBalance.size() > 0) {
                                        //리스트이므로 사이즈 만큼 for 문으로 DB 저장
                                        for (int bal = 0; bal < detBalance.size(); bal++) {
                                            AstDTO astDTO = new AstDTO();
                                            //기업 코드
                                            astDTO.setEnt_cd(staterDTO.getEnt_cd());
                                            //년도 year -> yr
                                            if (detBalance.get(bal).containsKey("year")) {
                                                astDTO.setYr((String) detBalance.get(bal).get("year"));
                                            }
                                            //유동자산(단위 : 천원) 유동자산 -> current_assets
                                            if (detBalance.get(bal).containsKey("유동자산")) {
                                                astDTO.setCurrent_assets((long) detBalance.get(bal).get("유동자산"));
                                            }
                                            //비유동자산(단위 : 천원) 비유동자산 -> non_current_assets
                                            if (detBalance.get(bal).containsKey("비유동자산")) {
                                                astDTO.setNon_current_assets((long) detBalance.get(bal).get("비유동자산"));
                                            }
                                            //자산총계(단위 : 천원) 자산총계 -> ast_gramt
                                            if (detBalance.get(bal).containsKey("자산총계")) {
                                                astDTO.setAst_gramt((long) detBalance.get(bal).get("자산총계"));
                                            }
                                            //부채총계(단위 : 천원) 부채총계 -> debt_gramt
                                            if (detBalance.get(bal).containsKey("부채총계")) {
                                                astDTO.setAst_gramt((long) detBalance.get(bal).get("부채총계"));
                                            }
                                            //자본금(단위 : 천원) 자본금 -> CPTL
                                            if (detBalance.get(bal).containsKey("자본금")) {
                                                astDTO.setCptl((long) detBalance.get(bal).get("자본금"));
                                            }
                                            //자본총계(단위 : 천원) 자본총계 -> cptl_gramt
                                            if (detBalance.get(bal).containsKey("자본총계")) {
                                                astDTO.setCptl_gramt((long) detBalance.get(bal).get("자본총계"));
                                            }

                                            //테이블 KB_API_AST_INFO 에 저장
                                            schedulerDAO.astIns(astDTO);
                                        }
                                        System.out.println("====================재무상태 저장 종료");
                                    }
                                    /* 금융정보 종료 */

                                    /* 뉴스정보 시작 */
                                    Map<String, Object> detNews = new HashMap<>();
                                    detNews = (Map) detailData.get("news");
                                    //newsList 는 리스트에 담아 DB 저장
                                    ArrayList<Map<String, Object>> detNewsList = new ArrayList<>();
                                    detNewsList = (ArrayList) detNews.get("newsList");
                                    if (detNewsList.size() > 0) {
                                        //리스트이므로 사이즈 만큼 for 문으로 DB 저장
                                        for (int news = 0; news < detNewsList.size(); news++) {
                                            NewsDTO newsDTO = new NewsDTO();
                                            //기업 코드
                                            newsDTO.setEnt_cd(staterDTO.getEnt_cd());
                                            //뉴스 아이디 id -> news_id
                                            if (detNewsList.get(news).containsKey("id")) {
                                                newsDTO.setNews_id((String) detNewsList.get(news).get("id"));
                                            }
                                            //제공자 provider -> provider
                                            if (detNewsList.get(news).containsKey("provider")) {
                                                newsDTO.setProvider((String) detNewsList.get(news).get("provider"));
                                            }
                                            //기사 제목 title -> news_ttl
                                            if (detNewsList.get(news).containsKey("title")) {
                                                newsDTO.setNews_ttl((String) detNewsList.get(news).get("title"));
                                            }
                                            //기사 링크 link -> news_link
                                            if (detNewsList.get(news).containsKey("link")) {
                                                newsDTO.setNews_link((String) detNewsList.get(news).get("link"));
                                            }
                                            //썸네일 이미지 URL thumbUrl -> thumb_url
                                            if (detNewsList.get(news).containsKey("thumbUrl")) {
                                                newsDTO.setThumb_url((String) detNewsList.get(news).get("thumbUrl"));
                                            }

                                            //테이블 KB_API_NEWS_INFO 에 저장
                                            schedulerDAO.newsIns(newsDTO);
                                        }
                                        System.out.println("====================뉴스정보 저장 종료");
                                    }

                                    //newsKeywordList 는 리스트에 담아 DB 저장
                                    ArrayList<Map<String, Object>> detKeyword = new ArrayList<>();
                                    detKeyword = (ArrayList) detNews.get("newsKeywordList");
                                    if (detKeyword.size() > 0) {
                                        //리스트이므로 사이즈 만큼 for 문으로 DB 저장
                                        //저장전 PK 가 없으므로 기업 코드로 먼저 삭제 후 저장
                                        schedulerDAO.keywdDel(staterDTO.getEnt_cd());
                                        for (int key = 0; key < detKeyword.size(); key++) {
                                            KeywdDTO keywdDTO = new KeywdDTO();
                                            //기업 코드
                                            keywdDTO.setEnt_cd(staterDTO.getEnt_cd());
                                            //키워드 keyword -> keywd
                                            if (detKeyword.get(key).containsKey("keyword")) {
                                                keywdDTO.setKeywd((String) detKeyword.get(key).get("keyword"));
                                            }
                                            //건수 count -> nocs
                                            if (detKeyword.get(key).containsKey("count")) {
                                                keywdDTO.setNocs((long) detKeyword.get(key).get("count"));
                                            }

                                            //테이블 KB_API_KEYWD_INFO 에 저장
                                            schedulerDAO.keywdIns(keywdDTO);
                                        }
                                        System.out.println("====================키워드정보 저장 종료");
                                    }
                                    /* 뉴스정보 종료 */

                                    //테이블 KB_API_STARTER_INFO 에 저장
                                    schedulerDAO.staterIns(staterDTO);

                                    /* 값이 없으면 key 자체가 없으므로 key 존재 유무 체크 필요 종료 */
                                } else {  //144 Line if 문의 else
                                    System.out.println("Detail Error : " + resDetail.getStatusLine().getStatusCode());
                                    result = resDetail.getStatusLine().getStatusCode();
                                    break;
                                }

                            }  //134 Line for 문 닫기
                            System.out.println("====================API 모든 저장 종료");
                        } else {  //132 Line if 문의 else
                            //size 가 없으면 for 문 break;
                            break;
                        }
                    } else {  //114 Line if 문의 else
                        System.out.println("List 500 Error : " + responseApi.getStatusLine().getStatusCode());
                        result = responseApi.getStatusLine().getStatusCode();

                        break;
                    }

                    forPage = forPage + i;
                }  //102 Line for 문 닫기

                result = response.getStatusLine().getStatusCode();
            } else {  //81 Line if 문의 else
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());

                return response.getStatusLine().getStatusCode();
            }
        //} catch (Exception e) {
        //    result = 999;
        //    return result;
        //}

        //정상이면 200리턴
        return result;
    }
}
