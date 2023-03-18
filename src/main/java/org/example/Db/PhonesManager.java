package org.example.Db;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PhonesManager {
    private List<Phone> phones;

    public PhonesManager() {
        phones = new ArrayList<>();
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    public int getSize() {
        return phones.size();
    }

    public Phone getPhone(int index) {
        return phones.get(index);
    }

    public void LoadPhonesFromFile(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        int listSize = Integer.parseInt(bufferedReader.readLine());

        for (int i = 0; i < listSize; ++i) {
            String[] data = bufferedReader.readLine().split(", ");

            String model = data[0];
            int price = Integer.parseInt(data[1]);
            int quantity = Integer.parseInt(data[2]);

            phones.add(new Phone(model, price, quantity));
        }

        bufferedReader.close();
        fileReader.close();
    }

    public void savePhonesToFile(String fileName) throws Exception {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(Integer.toString(phones.size()));
        bufferedWriter.newLine();

        for (int i = 0; i < phones.size(); ++i) {
            bufferedWriter.write(phones.get(i).toString());
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
        fileWriter.close();
    }

    public String getListPhones() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Phone phone : phones) {
            stringBuilder.append("{").append(phone).append("}").append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}
