package com.ssafy.umzip.global.util.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3Client amazonS3Client;

    public S3UploadDto upload(MultipartFile multipartFile, String bucket, String dirName){
        File uploadFile = convert(multipartFile);
        String originalFilename = multipartFile.getOriginalFilename();
        return upload(uploadFile, bucket, dirName, originalFilename);
    }

    // S3로 파일 업로드하기
    private S3UploadDto upload(File uploadFile, String bucket, String dirName, String originalFilename) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, bucket, fileName); // s3로 업로드
        removeNewFile(uploadFile);

        return S3UploadDto.builder()
                .imgUrl(uploadImageUrl)
                .saveName(fileName)
                .originName(originalFilename)
                .build();
    }

    public String putS3(File uploadFile, String bucket, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("File delete success");
            return;
        }
        System.out.println("File delete fail");
    }

    // 로컬에 파일 업로드 하기

    private File convert(MultipartFile multipartFile) {

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        //파일 업로드
        File file = new File(System.getProperty("user.dir") + storeFileName);

        try {
            multipartFile.transferTo(file);
        } catch (IOException exception) {
            throw new BaseException(StatusCode.TRANSLATE_FILE_FAILED);
        }

        return file;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}