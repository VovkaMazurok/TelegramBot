package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
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

            String[] commands = text.split(" ");
            StringBuilder response = new StringBuilder();

            if (commands.length == 2 && isNumeric(commands[1])) {
                calculateCryptoForDollars(commands[0], Double.parseDouble(commands[1]), chatId);
            } else  if (text.equals("/start")) {
                message.setText("Hello");
            } else if (text.equals("all")) {
                var btcPrice = CryptoPrice.spotPrice("BTC");
                sendPicture(chatId, "bitcoin.png");
                var ethPrice = CryptoPrice.spotPrice("ETH");
                sendPicture(chatId, "ethereum.png");
                var dogePrice = CryptoPrice.spotPrice("DOGE");
                sendPicture(chatId, "dogecoin.png");
                message.setText("BTC price: " + btcPrice.getAmount().doubleValue() + "\n"
                        + "ETH price: " + ethPrice.getAmount().doubleValue() + "\n"
                        + "DOGE price: " + dogePrice.getAmount().doubleValue());
            } else {

                for (String command : commands) {
                    command = command.trim();
                    if (command.equals("btc")) {
                        var price = CryptoPrice.spotPrice("BTC");
                        sendPicture(chatId, "bitcoin.png");
                        response.append("BTC price: ").append(price.getAmount().doubleValue()).append("\n");
                    } else if (command.equals("eth")) {
                        var price = CryptoPrice.spotPrice("ETH");
                        sendPicture(chatId, "ethereum.png");
                        response.append("ETH price: ").append(price.getAmount().doubleValue()).append("\n");
                    } else if (command.equals("doge")) {
                        var price = CryptoPrice.spotPrice("DOGE");
                        sendPicture(chatId, "dogecoin.png");
                        response.append("DOGE price: ").append(price.getAmount().doubleValue()).append("\n");
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

    public void calculateCryptoForDollars(String command, double dollars, long chatId) throws Exception {
        var price = CryptoPrice.spotPrice(command.toUpperCase());
        double amount = dollars / price.getAmount().doubleValue();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("You can buy " + amount + " " + command.toUpperCase() + " for "
                + dollars + " dollars.");
        execute(message);
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double number = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    void sendPicture(long chatId, String image) throws Exception {
        var photo = getClass().getClassLoader().getResourceAsStream(image);
        var message = new SendPhoto();
        message.setChatId(chatId);
        message.setPhoto(new InputFile(photo, image));
        execute(message);
    }

    @Override
    public String getBotUsername() {
        return "it_practice_super_bot";
    }
}

