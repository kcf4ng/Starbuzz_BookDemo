package com.hfad.starbuzz;

public class Drink {

    String name, description;
    int imageResourceIdId;

    public static final Drink[] drinks ={
            new Drink("Latte", "espresso + milk",R.drawable.coffee),
            new Drink("Cappuccino","espresso + milk + milk foam", R.drawable.beer),
            new Drink("Filter","filter water",R.drawable.water)
    };

    public Drink(String name, String description, int imageResourceIdId) {
        this.name = name;
        this.description = description;
        this.imageResourceIdId = imageResourceIdId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceIdId() {
        return imageResourceIdId;
    }

    public String toString(){
        return this.name;
    }
}