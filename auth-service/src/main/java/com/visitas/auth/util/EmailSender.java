package com.visitas.auth.util;

import com.visitas.auth.model.AppSetting;
import com.visitas.auth.service.AppSettingService;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class EmailSender {

    @Autowired
    private AppSettingService appSettingService;

    public void sendEmail(String to, String subject, String body, byte[] pdfBytes) throws Exception {

        String host = appSettingService.getSettingByKey(AppSettingKeys.EMAIL_HOST)
                .map(AppSetting::getSettingValue)
                .orElseThrow(() -> new IllegalStateException("Configuración de EMAIL_HOST no encontrada."));

        int port = Integer.parseInt(appSettingService.getSettingByKey(AppSettingKeys.EMAIL_PORT)
                .map(AppSetting::getSettingValue)
                .orElseThrow(() -> new IllegalStateException("Configuración de EMAIL_PORT no encontrada.")));

        String username = appSettingService.getSettingByKey(AppSettingKeys.EMAIL_USERNAME)
                .map(AppSetting::getSettingValue)
                .orElseThrow(() -> new IllegalStateException("Configuración de EMAIL_USERNAME no encontrada."));

        String password = appSettingService.getSettingByKey(AppSettingKeys.EMAIL_PASSWORD)
                .map(AppSetting::getSettingValue)
                .orElseThrow(() -> new IllegalStateException("Configuración de EMAIL_PASSWORD no encontrada."));

        boolean useTls = Boolean.parseBoolean(appSettingService.getSettingByKey(AppSettingKeys.EMAIL_USE_TLS)
                .map(AppSetting::getSettingValue)
                .orElse("true"));

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.auth", "true");
        if (useTls) {
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        }

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        // Contenido del mensaje
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(body);

        // Adjuntar PDF
        MimeBodyPart pdfPart = new MimeBodyPart();
        DataSource source = new ByteArrayDataSource(pdfBytes, "application/pdf");
        pdfPart.setDataHandler(new DataHandler(source));
        pdfPart.setFileName("InformeVisita.pdf");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(pdfPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
