package com.kbin.inno.Starters.DAO;

import com.kbin.inno.Starters.DTO.PortfolioDTO;
import com.kbin.inno.Community.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
@Repository
public interface PortfolioDAO {
    List<PortfolioDTO> selectPortYearList(Model model);

    List<PortfolioDTO> selectList(SearchDTO search);
}
