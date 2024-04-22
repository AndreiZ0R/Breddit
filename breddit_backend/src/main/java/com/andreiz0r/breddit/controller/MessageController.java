package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.response.Response;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


//TODO:
@Controller
public class MessageController extends AbstractController {

    //TODO: save an incoming message + get all messages
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Response hello(@Payload final String name) {
        return this.successResponse("Hello " + HtmlUtils.htmlEscape(name));
    }

    @SubscribeMapping("/topic/messages")
    public String onSubscribe() {
        return "Successfully subscribed to topic -MESSAGES-";
    }
}
