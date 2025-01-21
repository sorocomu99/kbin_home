package com.kbin.inno.Starters.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersApplyRequestWrapper {
    int survey_no;
    String email;
    String company_name;
    List<KbStartersAnswerRequest> answers;
}
