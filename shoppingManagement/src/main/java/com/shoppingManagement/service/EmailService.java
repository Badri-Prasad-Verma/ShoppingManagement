package com.shoppingManagement.service;

import javax.mail.MessagingException;

public interface EmailService {

    public void sendEmail(String emailTo, String emailSubject, String emailText) throws MessagingException ;
}
