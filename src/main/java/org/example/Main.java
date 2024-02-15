package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws Exception {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new MyBot());

        // it_practice_super_bot
        // 6882336805:AAHtkGqGRtfbmR-4EJCHN_M1Xccz05qb-TI
    }
}