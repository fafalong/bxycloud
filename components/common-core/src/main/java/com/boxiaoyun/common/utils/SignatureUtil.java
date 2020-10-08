package com.boxiaoyun.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.alibaba.fastjson.JSONObject;
import com.boxiaoyun.common.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 */
@Slf4j
public class SignatureUtil {

    /**
     * 5分钟有效期
     */
    private final static long MAX_EXPIRE = 5 * 60;

    public static void main(String[] args) throws Exception {
        String secretKey = "0osTIhce7uPvDKHz6aa67bhCukaKoYl4";
        //参数签名算法测试例子
        HashMap<String, String> signMap = new HashMap<String, String>();
        signMap.put("ApiKey", "7gBZcbsC7kLIWCdELIl8nxcs");
        signMap.put("SignType", SignType.SHA256.name());
        signMap.put("Timestamp", DateUtil.format(new Date(),"yyyyMMddHHmmss"));
        signMap.put("Nonce", "m6yuosdq6b");
        String sign = SignatureUtil.getSign(signMap, secretKey);
        System.out.println("签名结果:" + sign);
        signMap.put("Sign", sign);
        System.out.println("签名参数:" + JSONObject.toJSONString(signMap));
        System.out.println(SignatureUtil.validateSign(signMap, secretKey));
    }

    /**
     * 验证参数
     *
     * @param paramsMap
     * @throws Exception
     */
    public static void validateParams(Map<String, String> paramsMap) throws Exception {
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_API_KEY), "签名验证失败:ApiKey不能为空");
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_NONCE_KEY), "签名验证失败:Nonce不能为空");
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_TIMESTAMP_KEY), "签名验证失败:Timestamp不能为空");
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_SIGN_TYPE_KEY), "签名验证失败:SignType不能为空");
        Assert.hasText(paramsMap.get(CommonConstants.SIGN_SIGN_KEY), "签名验证失败:Sign不能为空");
        if (!SignatureUtil.SignType.contains(paramsMap.get(CommonConstants.SIGN_SIGN_TYPE_KEY))) {
            throw new IllegalArgumentException(String.format("签名验证失败:SignTyp必须为:%s,%s", SignatureUtil.SignType.MD5, SignatureUtil.SignType.SHA256));
        }
        DateUtil.parse(paramsMap.get(CommonConstants.SIGN_TIMESTAMP_KEY), "yyyyMMddHHmmss");
        Long clientTimestamp = Long.parseLong(paramsMap.get(CommonConstants.SIGN_TIMESTAMP_KEY));
        Long currenttimestamp = Long.parseLong(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        //判断时间戳 timestamp=201808091113    liujian
        //if ((currenttimestamp - clientTimestamp) > MAX_EXPIRE) {
        //    throw new IllegalArgumentException("签名验证失败:Timestamp已过期");
        //}
    }

    /**
     * @param paramsMap 必须包含
     * @param secretKey
     * @return
     */
    public static boolean validateSign(Map<String, String> paramsMap, String secretKey) {
        try {
            validateParams(paramsMap);
            String sign = paramsMap.get(CommonConstants.SIGN_SIGN_KEY);
            //重新生成签名
            paramsMap.remove(CommonConstants.SIGN_SIGN_KEY);
            String signNew = getSign(paramsMap, secretKey);
            //判断当前签名是否正确
            if (signNew.equals(sign)) {
                return true;
            }
        } catch (Exception e) {
            log.error("validateSign error:{}", e.getMessage());
            return false;
        }
        return false;
    }


    /**
     * 得到签名
     *
     * @param paramMap  参数集合不含appSecret
     *                  必须包含appId=客户端ID
     *                  signType = SHA256|MD5 签名方式
     *                  timestamp=时间戳
     *                  nonce=随机字符串
     * @param secretKey 验证接口的clientSecret
     * @return
     */
    public static String getSign(Map<String, String> paramMap, String secretKey) {
        paramMap.remove(CommonConstants.SIGN_SECRET_KEY);
        String signStr = null;
        String signType = paramMap.get(CommonConstants.SIGN_SIGN_TYPE_KEY);
        SignType type = null;
        if (StrUtil.isNotBlank(signType)) {
            type = SignType.valueOf(signType);
        }
        if (type == null) {
            type = SignType.MD5;
        }
        // 排序map
        paramMap = MapUtil.sort(paramMap);
        // 参数拼接方式 参数名按顺序 排序
        String paramsStr = MapUtil.join(paramMap, "&", "=", true);
        // 拼接API密钥
        String temp = paramsStr + "&" + CommonConstants.SIGN_SECRET_KEY + "=" + secretKey;
        //加密
        switch (type) {
            case MD5:
                signStr = new Digester(DigestAlgorithm.MD5).digestHex(temp);
                break;
            case SHA256:
                signStr = new Digester(DigestAlgorithm.SHA256).digestHex(temp);
                break;
            default:
                break;
        }
        return signStr;
    }




    public enum SignType {
        MD5,
        SHA256;

        public static boolean contains(String type) {
            for (SignType typeEnum : SignType.values()) {
                if (typeEnum.name().equals(type)) {
                    return true;
                }
            }
            return false;
        }
    }

}
