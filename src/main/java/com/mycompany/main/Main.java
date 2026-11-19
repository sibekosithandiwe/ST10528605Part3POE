package com.mycompany.main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static Random random = new Random();

    static String registeredUsername;
    static String registeredPassword;
    static String registeredCellPhone;

    static ArrayList<Message> sentMessagesList = new ArrayList<>();
    static ArrayList<Message> disregardedMessagesList = new ArrayList<>();
    static ArrayList<Message> storedMessagesList = new ArrayList<>();

    // Inner Message class so everything runs inside Main.java without Message.java
    static class Message {

        private String messageID;
        private String recipient;
        private String messageText;
        private int messageNumber;
        private String messageHash;
        private String messageStatus;

        public Message(String messageID, String recipient,
                       String messageText, int messageNumber) {
            this.messageID = messageID;
            this.recipient = recipient;
            this.messageText = messageText;
            this.messageNumber = messageNumber;
            this.messageHash = createMessageHash(
                    messageID, messageNumber, messageText);
            this.messageStatus = "Pending";
        }

        // Checks that the message ID contains exactly 10 digits.
        public boolean checkMessageID(String id) {
            return id != null && id.matches("[0-9]{10}");
        }

        // Validates a South African cellphone number.
        public String checkRecipientCell(String number) {
            if (number != null && number.matches("\\+27[0-9]{9}")) {
                return "Cell phone number successfully captured.";
            }
            return "Cell phone number is incorrectly formatted "
                    + "or does not contain the international code.";
        }

        // Validates that a message contains fewer than 250 characters.
        public String checkMessageLength(String text) {
            if (text == null) {
                return "Message cannot be null.";
            }

            if (text.length() < 250) {
                return "Message ready to send.";
            }

            return "Message exceeds the 250 character limit.";
        }

        // Creates the message hash using the first two ID digits,
        // message number, first word and last word.
        public String createMessageHash(String id, int number, String text) {
            if (id == null || id.length() < 2) {
                return "";
            }

            if (text == null || text.trim().isEmpty()) {
                return id.substring(0, 2) + ":" + number + ":";
            }

            String[] words = text.trim().split("\\s+");
            String firstWord = words[0];
            String lastWord = words[words.length - 1];

            return (id.substring(0, 2) + ":" + number + ":"
                    + firstWord + lastWord).toUpperCase();
        }

        // Processes the user's selected message action.
        public String sentMessage(int choice) {
            switch (choice) {
                case 1:
                    messageStatus = "Sent";
                    return "Message successfully sent.";

                case 2:
                    messageStatus = "Disregarded";
                    return "Message disregarded.";

                case 3:
                    messageStatus = "Stored";
                    return "Message stored to send later.";

                default:
                    return "Invalid action. Choose 1, 2, or 3.";
            }
        }

        // Returns all message details.
        public String printMessageDetails() {
            return "Message ID: " + messageID
                    + "\nMessage Hash: " + messageHash
                    + "\nRecipient: " + recipient
                    + "\nMessage: " + messageText
                    + "\nMessage Status: " + messageStatus;
        }

        // Getters
        public String getMessageID() {
            return messageID;
        }

        public String getRecipient() {
            return recipient;
        }

        public String getMessageText() {
            return messageText;
        }

        public String getMessageHash() {
            return messageHash;
        }

        public int getMessageNumber() {
            return messageNumber;
        }

        public String getMessageStatus() {
            return messageStatus;
        }
    }

    // Validate username: underscore and maximum 5 characters.
    public static boolean checkUserName(String username) {
        return username != null
                && username.length() <= 5
                && username.contains("_");
    }

    // Validate password complexity.
    public static boolean checkPasswordComplexity(String password) {
        return password != null
                && password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    // Validate South African cellphone number.
    public static boolean checkCellPhoneNumber(String number) {
        return number != null && number.matches("\\+27[0-9]{9}");
    }

    // Register a user.
    public static void registerUser() {
        String username;
        String password;
        String cellPhone;

        do {
            System.out.print("Enter your username: ");
            username = input.nextLine();

            if (!checkUserName(username)) {
                System.out.println(
                    "Username must contain an underscore "
                    + "and be no more than 5 characters."
                );
            } else {
                System.out.println("Username successfully captured.");
            }
        } while (!checkUserName(username));

        do {
            System.out.print("Enter your password: ");
            password = input.nextLine();

            if (!checkPasswordComplexity(password)) {
                System.out.println(
                    "Password must contain at least 8 characters, "
                    + "an uppercase letter, a lowercase letter, "
                    + "a number, and a special character."
                );
            } else {
                System.out.println("Password successfully captured.");
            }
        } while (!checkPasswordComplexity(password));

        do {
            System.out.print("Enter cellphone number (+27...): ");
            cellPhone = input.nextLine();

            if (!checkCellPhoneNumber(cellPhone)) {
                System.out.println(
                    "Invalid cellphone number. Use +27 "
                    + "followed by 9 digits."
                );
            } else {
                System.out.println(
                    "Cellphone number successfully added."
                );
            }
        } while (!checkCellPhoneNumber(cellPhone));

        registeredUsername = username;
        registeredPassword = password;
        registeredCellPhone = cellPhone;

        System.out.println("User registered successfully.");
    }

    // Check login credentials.
    public static boolean loginUser(String username, String password) {
        return username != null
                && password != null
                && username.equals(registeredUsername)
                && password.equals(registeredPassword);
    }

    // Return login status.
    public static String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome, it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }

    // Read a valid integer from the user.
    private static int readInteger(String prompt) {
        while (true) {
            System.out.print(prompt);

            if (input.hasNextInt()) {
                int value = input.nextInt();
                input.nextLine();
                return value;
            }

            input.nextLine();
            System.out.println("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {

        // PART 1: REGISTRATION
        registerUser();

        // PART 1: LOGIN
        boolean loginSuccessful = false;

        while (!loginSuccessful) {
            System.out.print("\nEnter your username to login: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter your password to login: ");
            String loginPassword = input.nextLine();

            loginSuccessful = loginUser(loginUsername, loginPassword);
            System.out.println(returnLoginStatus(loginSuccessful));
        }

        // PART 2: QUICKCHAT
        System.out.println("\nWelcome to QuickChat.");

        ArrayList<String> messageIDs = new ArrayList<>();
        ArrayList<String> recipients = new ArrayList<>();
        ArrayList<String> messages = new ArrayList<>();
        ArrayList<String> messageHashes = new ArrayList<>();
        ArrayList<String> messageStatuses = new ArrayList<>();

        int totalSentCount = 0;
        int messageNumber = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");

            int choice = readInteger("Choose an option (1-4): ");

            switch (choice) {

                case 1:
                    int numMessages = readInteger(
                            "How many messages do you wish to enter? ");

                    if (numMessages < 1) {
                        System.out.println(
                                "Please enter at least one message.");
                        break;
                    }

                    for (int i = 0; i < numMessages; i++) {
                        System.out.println(
                                "\n--- Entering Message "
                                + (i + 1) + " of " + numMessages + " ---");

                        String msgID = String.format(
                                "%010d",
                                random.nextLong(10_000_000_000L));

                        System.out.print(
                                "Enter recipient cell number (+27718693002): ");
                        String recipient = input.nextLine();

                        Message tempMsg = new Message(
                                msgID, recipient, "", messageNumber);

                        String cellResult =
                                tempMsg.checkRecipientCell(recipient);
                        System.out.println(cellResult);

                        if (!cellResult.contains("successfully")) {
                            System.out.println(
                                    "Message cancelled. Please retry.");
                            i--;
                            continue;
                        }

                        System.out.print(
                                "Enter message text (less than 250 characters): ");
                        String text = input.nextLine();

                        String lengthResult =
                                tempMsg.checkMessageLength(text);
                        System.out.println(lengthResult);

                        if (!lengthResult.contains("ready to send")) {
                            System.out.println(
                                    "Please enter a shorter message.");
                            i--;
                            continue;
                        }

                        Message msg = new Message(
                                msgID, recipient, text, messageNumber);
                        messageHashes.add(msg.getMessageHash());
                        messageIDs.add(msg.getMessageID());
                        recipients.add(msg.getRecipient());
                        messages.add(msg.getMessageText());

                        System.out.println("\nChoose action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");

                        int actionChoice;

                        do {
                            actionChoice = readInteger(
                                    "Enter choice (1-3): ");

                            if (actionChoice < 1 || actionChoice > 3) {
                                System.out.println(
                                        "Please choose 1, 2, or 3.");
                            }
                        } while (actionChoice < 1 || actionChoice > 3);

                        System.out.println(
                                msg.sentMessage(actionChoice));
                        messageStatuses.add(msg.getMessageStatus());

                        if (actionChoice == 1) {
                            sentMessagesList.add(msg);
                            totalSentCount++;
                        } else if (actionChoice == 2) {
                            disregardedMessagesList.add(msg);
                        } else if (actionChoice == 3) {
                            storedMessagesList.add(msg);
                        }

                        System.out.println("\n--- Message Summary ---");
                        System.out.println(msg.printMessageDetails());

                        messageNumber++;
                    }

                    System.out.println(
                            "\nTotal messages successfully sent: "
                            + totalSentCount);
                    break;

                case 2:
                    System.out.println("Sent messages count: "
                            + sentMessagesList.size());

                    if (sentMessagesList.isEmpty()) {
                        System.out.println("No messages have been sent yet.");
                    } else {
                        System.out.println("\n--- Sent Messages ---");

                        for (Message msg : sentMessagesList) {
                            System.out.println(
                                    msg.printMessageDetails());
                            System.out.println("--------------------");
                        }
                    }

                    break;

                case 3:
                    System.out.println("Stored messages count: "
                            + storedMessagesList.size());
                    System.out.println("\n--- Stored Messages ---");
                    System.out.println("1. Display all stored messages");
                    System.out.println("2. Display longest stored message");
                    System.out.println("3. Search by Message ID");
                    System.out.println("4. Search by recipient");
                    System.out.println("5. Delete by message hash");
                    System.out.println("6. Display report");

                    int storedOption =
                            readInteger("Choose option: ");

                    if (storedOption == 1) {
                        for (Message msg : storedMessagesList) {
                            System.out.println(
                                    "Recipient: "
                                    + msg.getRecipient());

                            System.out.println(
                                    "Message: "
                                    + msg.getMessageText());

                            System.out.println();
                        }
                    } else if (storedOption == 2) {
                        String longest = "";

                        for (Message msg : storedMessagesList) {
                            if (msg.getMessageText().length()
                                    > longest.length()) {
                                longest = msg.getMessageText();
                            }
                        }

                        System.out.println(
                                "Longest stored message:");
                        System.out.println(longest);
                    } else if (storedOption == 3) {
                        System.out.print(
                                "Enter Message ID: ");
                        String searchID =
                                input.nextLine();

                        boolean found = false;

                        for (Message msg : storedMessagesList) {
                            if (msg.getMessageID()
                                    .equals(searchID)) {
                                System.out.println(
                                        "Recipient: "
                                        + msg.getRecipient());

                                System.out.println(
                                        "Message: "
                                        + msg.getMessageText());

                                found = true;
                            }
                        }

                        if (!found) {
                            System.out.println(
                                    "Message ID not found.");
                        }
                    } else if (storedOption == 4) {
                        System.out.print(
                                "Enter recipient: ");
                        String searchRecipient =
                                input.nextLine();

                        boolean found = false;

                        for (Message msg : storedMessagesList) {
                            if (msg.getRecipient()
                                    .equals(searchRecipient)) {
                                System.out.println(
                                        msg.getMessageText());
                                found = true;
                            }
                        }

                        if (!found) {
                            System.out.println(
                                    "No messages found.");
                        }
                    } else if (storedOption == 5) {
                        System.out.print(
                                "Enter message hash: ");
                        String hash =
                                input.nextLine();

                        boolean deleted = false;

                        for (int i = 0;
                             i < storedMessagesList.size();
                             i++) {
                            if (storedMessagesList.get(i)
                                    .getMessageHash()
                                    .equals(hash)) {
                                System.out.println(
                                        "Message: \""
                                        + storedMessagesList
                                        .get(i)
                                        .getMessageText()
                                        + "\" successfully deleted.");

                                storedMessagesList.remove(i);
                                deleted = true;
                                break;
                            }
                        }

                        if (!deleted) {
                            System.out.println(
                                    "Hash not found.");
                        }
                    } else if (storedOption == 6) {
                        System.out.println(
                                "\n--- Message Report ---");

                        for (Message msg : sentMessagesList) {
                            System.out.println(
                                    "Message Hash: "
                                    + msg.getMessageHash());

                            System.out.println(
                                    "Recipient: "
                                    + msg.getRecipient());

                            System.out.println(
                                    "Message: "
                                    + msg.getMessageText());

                            System.out.println(
                                    "--------------------");
                        }
                    }

                    break;

                case 4:
                    System.out.println(
                            "Exiting QuickChat. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid selection. Choose 1, 2, or 3.");
            }
        }

        input.close();
    }
}