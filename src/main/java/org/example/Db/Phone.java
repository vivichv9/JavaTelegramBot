package org.example.Db;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Phone {
    private String model;
    private int price;
    private int quantity;

    @Override
    public String toString() {
        return model + ", " + price + ", " + quantity;
    }
}
