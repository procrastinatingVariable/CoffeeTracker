package ro.fmi.ip.trei.coffeetracker.data.model;

public class BeverageEntity {

    private String imageUrl;
    private String name;
    private int concentration;

    public BeverageEntity() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConcentration() {
        return concentration;
    }

    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }
}
