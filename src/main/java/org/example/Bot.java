package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String serialNumber = update.getMessage().getText();

            try {
                URL url = new URL("https://raw.githubusercontent.com/randallerasmus/kfmcompetition/main/serial_numbers.txt");
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder data = new StringBuilder();
                while (scanner.hasNext()) {
                    data.append(scanner.nextLine()).append("\n");
                }
                scanner.close();

                List<String> serialNumbers = Arrays.asList(data.toString().split("\n"));
                if (serialNumbers.contains(serialNumber)) {
                    execute(new SendMessage(chatId, "Yipee!!!! A match was found."));
                } else {
                    execute(new SendMessage(chatId, "No match was found.Sorry next time"));
                }
            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "YourBotUsername";
    }

    @Override
    public String getBotToken() {
        return "6778420907:AAHnByc5pAji4fbWEe4alvqsAK7QYzFoEkM";
    }
}
