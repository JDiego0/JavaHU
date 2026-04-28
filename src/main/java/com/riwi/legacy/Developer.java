package com.riwi.legacy;

public final class Developer extends Person implements Promotional {
    private String mainlanguage;


    public Developer(String id, String name, String mainlanguage) {
        setId(id);
        setName(name);
        this.mainlanguage = mainlanguage;
    }

    public String getMainlanguage(){
        return mainlanguage;
    }
    
    @Override
    public double calculatePromotionBonus() {
        // Developers get a 15% bonus based on their expertise
        return 5000.0 * 1.15;
    }
}
