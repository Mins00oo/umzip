package com.ssafy.umzip.global.util;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class S3UploadDto {

    private String originName;
    private String saveName;
    private String imgUrl;

}