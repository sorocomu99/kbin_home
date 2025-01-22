package com.kbin.inno.Starters.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersAnswerRequest {

    int apply_no;

    int question_no;

    Integer question_choice_no;

    String answer_content;

    MultipartFile answer_file;


}
