package org.example.Bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyBot extends TelegramLongPollingBot {
    private final TgBotLogic tgBotLogic;

    public MyBot() throws Exception {
        tgBotLogic = new TgBotLogic();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String fromUserText = update.getMessage().getText();

            tgBotLogic.routeMessage(this, chatId, fromUserText);
        } else if (update.hasCallbackQuery()) {
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            String fromUserText = update.getCallbackQuery().getData();

            tgBotLogic.routeMessage(this, chatId, fromUserText);
        }
    }

    @Override
    public String getBotUsername() {
        return BotConfig.getName();
    }

    @Override
    public String getBotToken() {
        return BotConfig.getToken();
    }
}
