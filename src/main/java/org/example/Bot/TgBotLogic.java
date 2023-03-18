package org.example.Bot;

import org.example.Db.PhonesManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TgBotLogic {

    private final PhonesManager phonesManager;
    private final CommandsHistoryControl commandsHistory;

    public TgBotLogic() throws IOException {
        phonesManager = new PhonesManager();
        commandsHistory = new CommandsHistoryControl();
        phonesManager.LoadPhonesFromFile("phones.txt");
    }

    private void addMarkup(InlineKeyboardMarkup markup,
                           List<List<InlineKeyboardButton>> rowList,
                           String text,
                           String callBackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callBackData);

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton);

        rowList.add(keyboardButtonsRow1);
        markup.setKeyboard(rowList);
    }

    private void sendTextToUser(MyBot myBot, String chatId, String toUserText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(toUserText);

        try {
            myBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTextToUser(MyBot myBot, String chatId, String toUserText, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(toUserText);
        message.setReplyMarkup(markup);

        try {
            myBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendStartMessage(MyBot myBot, String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        addMarkup(inlineKeyboardMarkup, rowList, "Open menu", "/menu");

        String toUserText = "Program for display phones list! press /menu";
        sendTextToUser(myBot, chatId, toUserText, inlineKeyboardMarkup);
        commandsHistory.addCommandToHistory("/start");
    }

    private void sendMenuMessage(MyBot myBot, String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        addMarkup(inlineKeyboardMarkup, rowList,"1. print all phones", "/list");
        addMarkup(inlineKeyboardMarkup, rowList, "2. Go back", "/back");

        String toUserText = "It's menu\n1. List of all phones - displays all phones";
        sendTextToUser(myBot, chatId, toUserText, inlineKeyboardMarkup);
        commandsHistory.addCommandToHistory("/menu");
    }

    private void sendListMessage(MyBot myBot, String chatId) {
        String responseText = phonesManager.getListPhones();
        sendTextToUser(myBot, chatId, responseText);
        commandsHistory.addCommandToHistory("/list");
    }

    private void sendDefaultMessage(MyBot myBot, String chatId) {
        String toUserText = "Command not found!";
        sendTextToUser(myBot, chatId, toUserText);
    }

    private void sendPreviousMessage(MyBot myBot, String chatId) {
        routeMessage(myBot, chatId, commandsHistory.goToPreviousCommand());
    }

    public void routeMessage(MyBot myBot, String chatId, String fromUserText) {

        switch (fromUserText) {
            case "/start" -> sendStartMessage(myBot, chatId);
            case "/menu" -> sendMenuMessage(myBot, chatId);
            case "/list" -> sendListMessage(myBot, chatId);
            case "/back" -> sendPreviousMessage(myBot, chatId);
            default -> sendDefaultMessage(myBot, chatId);
        }
    }
}
