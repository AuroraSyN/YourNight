package apackage.com.yournight.model.event.eventAssist;

import java.io.Serializable;

/**
 * Created by Aleksandr Soloninov on 24.11.2017.
 * Hochschule Worms, inf3032
 */

public class EventVenue implements Serializable {
    private String id;
    private String name;
    private String category;
    private String[] categoryList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(String[] categoryList) {
        this.categoryList = categoryList;
    }

    // to string

    @Override
    public String toString() {
        return "--- Event Venue ---\n"
                + name + " (ID:" + id + ")\n"
                + "Category: " + category + " : " + categoryList[1];
    }
}
