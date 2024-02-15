package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class MyBot extends TelegramLongPollingBot {

    public MyBot() {
        super("6882336805:AAHtkGqGRtfbmR-4EJCHN_M1Xccz05qb-TI");
    }

    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();

        try {
            var message = new SendMessage();
            message.setChatId(chatId);
            if (text.equals("/start")) {
                message.setText("Hello");
            } else {
                String[] commands = text.split(",");
                StringBuilder response = new StringBuilder();
                for (String command : commands) {
                    command = command.trim();
                    if (command.equals("btc")) {
                        var price = CryptoPrice.spotPrice("BTC");
                        response.append("BTC price: ").append(price.getAmount().doubleValue()).append("\n");
                    } else if (command.equals("eth")) {
                        var price = CryptoPrice.spotPrice("ETH");
                        response.append("ETH price: ").append(price.getAmount().doubleValue()).append("\n");
                    } else if (command.equals("doge")) {
                        var price = CryptoPrice.spotPrice("DOGE");
                        response.append("DOGE price: ").append(price.getAmount().doubleValue()).append("\n");
                    } else if (text.equals("all")) {
                        var price = CryptoPrice.spotPrice("BTC");
                        var price1 = CryptoPrice.spotPrice("ETH");
                        var price2 = CryptoPrice.spotPrice("DOGE");
                        message.setText("BTC price: " + price.getAmount().doubleValue() + "\n"
                                + "ETH price: " + price1.getAmount().doubleValue() + "\n"
                                + "DOGE price: " + price2.getAmount().doubleValue());
                    } else {
                        response.append("Unknown command: ").append(command).append("\n");
                    }
                }
                message.setText(response.toString());
            }
            execute(message);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    @Override
    public String getBotUsername() {
        return "it_practice_super_bot";
    }
}
