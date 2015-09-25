/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.microtripit.mandrillapp.lutung.view.MandrillUserInfo;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alessio.finamore
 */
public class TestMandrill {

    public TestMandrill() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() throws MandrillApiError, IOException {

        Config conf = ConfigFactory.load();
        System.out.println("The KEY is: " + conf.getString("dev.mandrillkey"));
        
        MandrillApi mandrillApi = new MandrillApi(conf.getString("dev.mandrillkey"));
        MandrillUserInfo user = mandrillApi.users().info();
// pretty-print w/ gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(user));

        // SEnd Message
        // create your message
        MandrillMessage message = new MandrillMessage();
        message.setSubject("Hello Foobar & Wheel!");
        message.setHtml("<h1>Hi fobar!</h1><br />Really, I'm just saying f**k!");
        message.setAutoText(true);
        message.setFromEmail("foobarswheels@gmail.com");
        message.setFromName("Foobar & Wheels");
// add recipients
        ArrayList<Recipient> recipients = new ArrayList<Recipient>();
        Recipient recipient = new Recipient();
        recipient.setEmail("bayois@gmail.com");
        recipient.setName("Bayo Is F****d");
        recipients.add(recipient);
        recipient = new Recipient();
        recipient.setEmail("foobarswheels@gmail.com");
        recipients.add(recipient);
        message.setTo(recipients);
        message.setPreserveRecipients(true);
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("test");
        tags.add("helloworld");
        message.setTags(tags);
// ... add more message details if you want to!
// then ... send
        MandrillMessageStatus[] messageStatusReports = mandrillApi
                .messages().send(message, false);
    }

}
