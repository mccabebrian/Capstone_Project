package brianmccabe.coffeenow.models;

/**
 * Created by brian on 11/02/2017.
 */

public class Coffee {
    private String name;
    private byte[] coffeeImage;
    private String price;

    public Coffee() {

    }

    public Coffee(String name, byte[] coffeeImage, String price) {
        this.name = name;
        this.coffeeImage = coffeeImage;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getCoffeeImage() {
        return coffeeImage;
    }

    public void setCoffeeImage(byte[] coffeeImage) {
        this.coffeeImage = coffeeImage;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
