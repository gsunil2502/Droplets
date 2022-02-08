package com.lti.droplets.notifications.helper;


import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class MailContentCreatorHelper {

    @Autowired
    private Configuration configuration;

    public String getEmailContent(Map<String, Object> model, String ftlFileName) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(ftlFileName).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}

