package vn.edu.hcmuaf.fit.laptrinhweb2.model.Menu;

import java.util.List;

public class MainMenu {
    private String name;
    private String link;
    private List<SubMenu> subMenus;

    public MainMenu(String name, String link, List<SubMenu> subMenus) {
        this.name = name;
        this.link = link;
        this.subMenus = subMenus;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubMenu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SubMenu> subMenus) {
        this.subMenus = subMenus;
    }
}

