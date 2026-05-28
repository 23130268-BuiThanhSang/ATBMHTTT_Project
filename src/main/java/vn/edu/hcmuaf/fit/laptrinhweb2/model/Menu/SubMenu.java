package vn.edu.hcmuaf.fit.laptrinhweb2.model.Menu;

import java.util.List;

public class SubMenu {
    private String name;
    private List<MenuItem> items;

    public SubMenu(String name, List<MenuItem> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}