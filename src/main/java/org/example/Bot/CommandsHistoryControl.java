package org.example.Bot;

import java.util.Stack;

public class CommandsHistoryControl {

    private final Stack<String> commandHistory;

    public CommandsHistoryControl() {
        commandHistory = new Stack<>();
        commandHistory.push("/start");
    }

    protected void addCommandToHistory(String command) {
        if (!commandHistory.empty()) {
            if (!command.equals(commandHistory.peek())) {
                commandHistory.push(command);
            }
        }
    }

    protected String goToPreviousCommand() {
        if (commandHistory.empty()) {
            commandHistory.push("/start");
            return "/start";
        } else {
            commandHistory.pop();
            return !commandHistory.empty() ? commandHistory.peek() : "/start";
        }
    }
}
