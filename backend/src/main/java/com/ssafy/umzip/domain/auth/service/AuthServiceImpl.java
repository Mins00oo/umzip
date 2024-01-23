package com.ssafy.umzip.domain.auth.service;

import com.ssafy.umzip.domain.auth.dto.AuthCodeRequestDto;
import com.ssafy.umzip.global.util.redis.RedisService;
import com.ssafy.umzip.global.util.sms.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final SmsUtil smsUtil;

    private final RedisService redisService;

    //    @Value("${SMS_SERVICE_ID}")
    private String serviceId;

    //@Value("${SMS_ACCESS_KEY}")
    private String accessKey;

    //@Value("${SMS_SECRET_KEY}")
    private String secretKey;

    //@Value("${SMS_SENDER_PHONE}")
    private String senderPhone;

    public String makeSignature(String time) throws Exception {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + serviceId + "/messages";

        String message = method +
                space +
                url +
                newLine +
                time +
                newLine +
                accessKey;

        SecretKeySpec signingKey;
        Mac mac;
        String encodeBase64String;

        try {
            signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            encodeBase64String = Base64.encodeBase64String(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return encodeBase64String;
    }

    @Override
    public void sendCode(AuthCodeRequestDto codeRequestDto) {
        String code = createCode();
        smsUtil.sendOne(codeRequestDto.getPhone(), code);
    }

    public String createCode() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 4; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }
}
