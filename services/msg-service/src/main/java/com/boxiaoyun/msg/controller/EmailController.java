package com.boxiaoyun.msg.controller;

import com.alibaba.fastjson.JSONObject;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.msg.client.model.EmailMessage;
import com.boxiaoyun.msg.client.model.EmailTplMessage;
import com.boxiaoyun.msg.client.service.IEmailServiceClient;
import com.boxiaoyun.msg.dispatcher.MessageDispatcher;
import com.boxiaoyun.msg.utils.MultipartUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author woodev
 */
@RestController
@Api(value = "邮件", tags = "邮件")
public class EmailController implements IEmailServiceClient {

    @Autowired
    private MessageDispatcher dispatcher;

    /**
     * 发送邮件
     *
     * @param to          接收人 多个用;号隔开
     * @param cc          抄送人 多个用;号隔开
     * @param subject     主题
     * @param content     内容
     * @param attachments 附件
     * @return
     */
    @PostMapping(value = "/email/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResultBody send(@RequestParam(value = "to") String to,
                           @RequestParam(value = "cc", required = false) String cc,
                           @RequestParam(value = "subject") String subject,
                           @RequestParam(value = "content") String content,
                           @RequestPart(value = "attachments", required = false) MultipartFile[] attachments){
        EmailMessage message = new EmailMessage();
        message.setTo(StringUtils.delimitedListToStringArray(to, ";"));
        message.setCc(StringUtils.delimitedListToStringArray(cc, ";"));
        message.setSubject(subject);
        message.setAttachments(MultipartUtil.getMultipartFilePaths(attachments));
        message.setContent(content);
        this.dispatcher.dispatch(message);
        return ResultBody.ok();
    }

    @PostMapping(value = "/email/test/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResultBody send2(@RequestPart(value = "file", required = false) MultipartFile file){
        return ResultBody.ok();
    }

    /**
     * 发送模板邮件
     *
     * @param to          接收人 多个用;号隔开
     * @param cc          抄送人 多个用;号隔开
     * @param subject     主题
     * @param tplCode     内容
     * @param tplCode     模板编号
     * @param tplParams   模板参数 json字符串
     * @param attachments 附件
     * @return
     */
    @Override
    @PostMapping(value = "/email/send/tpl", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultBody sendByTpl(
            @RequestParam(value = "to") String to,
            @RequestParam(value = "cc", required = false) String cc,
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "tplCode") String tplCode,
            @RequestParam(value = "tplParams") String tplParams,
            @RequestPart(value = "attachments", required = false) MultipartFile[] attachments
    ) {
        EmailTplMessage message = new EmailTplMessage();
        message.setTo(StringUtils.delimitedListToStringArray(to, ";"));
        message.setCc(StringUtils.delimitedListToStringArray(cc, ";"));
        message.setSubject(subject);
        message.setAttachments(MultipartUtil.getMultipartFilePaths(attachments));
        message.setTplCode(tplCode);
        message.setTplParams(JSONObject.parseObject(tplParams));
        this.dispatcher.dispatch(message);
        return ResultBody.ok();
    }




}
