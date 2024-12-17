package com.lokapos.services.impl;

import com.lokapos.exception.SystemErrorException;
import com.lokapos.services.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final Configuration config;


    @Override
    public void testingSendMail() {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Map<String, Object> model = new HashMap<>();
            model.put("Name", "RIVO NAME");
            model.put("location", "Jakarta,indonesia");
            Template template = config.getTemplate("testing-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setFrom("noreply@rivopelu.com");
            helper.setTo("contactrivopelu@gmail.com");
            helper.setSubject("HELLO");
            helper.setText(html, true);
            emailSender.send(message);
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
