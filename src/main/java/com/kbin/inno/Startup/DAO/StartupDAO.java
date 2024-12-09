package com.kbin.inno.Startup.DAO;

import com.kbin.inno.Startup.DTO.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface StartupDAO {
    //전체 스타트업 갯수
    int selectTotalCount(HashMap<String, Object> map);
    //리스트 조회
    List<StartupDTO> selectPageList(HashMap<String, Object> map);
    //상세조회
    //StaterDTO select(@Param("ent_cd") String ent_cd);
    StaterDTO select(String ent_cd);
    //사업서비스 정보 조회 (리스트)
    List<BizSrvcDTO> selectBizList(String ent_cd);
    //고용정보 조회 (리스트)
    List<EmploDTO> selectEmploList(String ent_cd);
    //투자정보 조회 (리스트)
    List<InvestDTO> selectInvestList(String ent_cd);
    //매출정보 조회 (리스트)
    List<SlsDTO> selectSlsList(String ent_cd);
    //SlsDTO selectSlsChgList(String ent_cd, String yr);
    SlsDTO selectSlsChgList(SlsDTO slsDTO);
    //재무상태 조회 (리스트)
    List<AstDTO> selectAstList(String ent_cd);
    //AstDTO selectAstChgList(String ent_cd, String yr);
    AstDTO selectAstChgList(AstDTO astDTO);
    //뉴스 메인 조회 (단건)
    NewsDTO selectNewsMain(String ent_cd);
    //뉴스정보 조회 (리스트)
    List<NewsDTO> selectNewsList(String ent_cd);
    //키워드정보 조회 (리스트)
    List<KeywdDTO> selectKeywdList(String ent_cd);
}
