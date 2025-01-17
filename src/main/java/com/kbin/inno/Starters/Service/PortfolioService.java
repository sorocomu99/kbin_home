package com.kbin.inno.Starters.Service;

import com.kbin.inno.Starters.DAO.PortfolioDAO;
import com.kbin.inno.Starters.DTO.PortfolioDTO;
import com.kbin.inno.Community.DTO.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    @Autowired
    private PortfolioDAO portfolioDAO;

    public Map<String, Object> getPortYearList(Model model) {
        Map<String, Object> result = new HashMap<>();
        List<PortfolioDTO> portYearList = portfolioDAO.selectPortYearList(model);
        result.put("portYearList", portYearList);
        return result;
    }

    public Map<String, Object> selectList(Model model, String keyword) {
        Map<String, Object> result = new HashMap<>();
        SearchDTO search = new SearchDTO();
        search.setKeyword(keyword); //년도 (port_yr)
        List<PortfolioDTO> selectList = portfolioDAO.selectList(search);
        result.put("selectList", selectList);
        return result;
    }
}
