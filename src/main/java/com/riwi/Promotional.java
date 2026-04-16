package com.riwi;

public interface Promotional {
    double calculatePromotionBonus();

    default void registerLog(String message){
        System.out.println("[LOG]: "+ message);
    }
}
