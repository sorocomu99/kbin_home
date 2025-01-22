package com.kbin.inno.Starters.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersApplyAnswerDTO {
    private int apply_answer_no;
    private int apply_no;
    private int question_no;
    private int question_choice_no;
    private String answer_content;
    private String answer_file_path;
    private String answer_original_filename;
    private String answer_filename;
    private int frst_rgtr;
    private Date frst_reg_dt;
    private int last_mdfr;
    private Date last_mdfcn_dt;
}
