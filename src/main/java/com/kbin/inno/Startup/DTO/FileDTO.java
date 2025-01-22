package com.kbin.inno.Startup.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    private String filename;
    private String filePath;
    private String originalFilename;

}
