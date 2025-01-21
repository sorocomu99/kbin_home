package com.kbin.inno.Starters.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersSurveyRequestDTO {

    KbStartersSurveyDTO survey;

    KbStartersSurveyDTO surveyInfo;

    List<KbStartersQuestionRequestDTO> data = new ArrayList<>();

}
