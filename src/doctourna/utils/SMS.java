/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author mouhe
 */
public class SMS {

    public static final String ACCOUNT_SID = "AC43d024adc7d15159f52ce1c9d000c0c6";
    public static final String AUTH_TOKEN = "37e9889e39373d05aaa82597e5fa092b";
    public static final String FROM_NUMBER = "+12819378200";

    public static void send(String sms, String number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(number),
                new PhoneNumber(FROM_NUMBER),
                sms).create();

        System.out.println(message.getSid());
    }
}
