package com.boxiaoyun.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.boxiaoyun.common.exception.BaseFeginException;
import com.boxiaoyun.common.interceptor.FeignRequestInterceptor;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.utils.StringUtil;
import feign.*;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.ContentType;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringFormEncoder;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

/**
 * Feign OAuth2 request interceptor.
 *
 * @author
 */
@Slf4j
@Configuration
public class FeignAutoConfiguration {
    public static int connectTimeOutMillis = 12000;
    public static int readTimeOutMillis = 12000;

    @Bean
    public Encoder feignSpringFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        Encoder encoder = new FeignSpringFormEncoder(new SpringEncoder(messageConverters));
        log.info("FeignSpringFormEncoder [{}]", encoder);
        return encoder;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        ErrorDecoder errorDecoder = new FeignErrorDecoder();
        log.info("FeignErrorDecoder [{}]", errorDecoder);
        return errorDecoder;
    }

    @Bean
    @ConditionalOnMissingBean(FeignRequestInterceptor.class)
    public RequestInterceptor feignRequestInterceptor() {
        FeignRequestInterceptor interceptor = new FeignRequestInterceptor();
        log.info("FeignRequestInterceptor [{}]", interceptor);
        return interceptor;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }


    @Autowired
    private ObjectMapper objectMapper;

    /**
     * /**
     * 当调用服务时，如果服务返回的状态码不是200，就会进入到Feign的ErrorDecoder中，因此如果我们要解析异常信息，就要重写ErrorDecoder：
     */
    public class FeignErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            try {
                String json = Util.toString(response.body().asReader());
                exception = new RuntimeException(json);
                if (StringUtil.isEmpty(json)) {
                    return null;
                }
                ResultBody resultBody = objectMapper.readValue(json, ResultBody.class);
                // 业务异常包装成自定义异常类SystemFeginException
                if (!resultBody.getIsSuccess()) {
                    exception = new BaseFeginException(resultBody.getCode(), resultBody.getMsg());
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            return exception;
        }
    }

    /**
     * @Description: 处理多个文件上传
     * @author:
     * @date 2019/8/16 下午4:26
     */
    public class FeignSpringFormEncoder extends SpringFormEncoder {
        public FeignSpringFormEncoder(Encoder delegate) {
            super(delegate);
            MultipartFormContentProcessor processor = (MultipartFormContentProcessor) getContentProcessor(ContentType.MULTIPART);
            processor.addWriter(new SpringSingleMultipartFileWriter());
            processor.addWriter(new SpringManyMultipartFilesWriter());
        }

        @Override
        public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
            if (bodyType != null && bodyType.equals(MultipartFile[].class) && object != null) {
                MultipartFile[] file = (MultipartFile[]) object;
                if (file.length == 0) {
                    return;
                }
                Map data = Collections.singletonMap(file.length == 0 ? "" : file[0].getName(), object);
                super.encode(data, MAP_STRING_WILDCARD, template);
                return;
            }
            super.encode(object, bodyType, template);
        }
    }

}
