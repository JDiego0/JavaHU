package com.riwi.legacy;

public final class Manager extends Person implements Promotional {
    private double monthlyBudget;

    public Manager(String id, String name, double monthlyBudget){
        setId(id);
        setName(name);
        this.monthlyBudget = monthlyBudget;
    }

    public double getMonthlyBudget(){
        return  monthlyBudget;
    }
    
    @Override
    public double calculatePromotionBonus() {
        // Managers get a 5% bonus based on their budget responsibility
        return monthlyBudget * 0.05;
    }
}
