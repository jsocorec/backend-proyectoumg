package com.visitas.auth.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String to, String subject, String body, byte[] pdfBytes) throws Exception {

        String host = "mail.jesusnazarenodelasalvacion.com";
        String port = "587";
        String username = "inscripciones@jesusnazarenodelasalvacion.com";
        String password = "Inscripciones2023";
        boolean useTls = true;

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        if (useTls) {
            props.put("mail.smtp.starttls.enable", "true");
        }

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
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
