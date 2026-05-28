package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Menu.MainMenu;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Menu.SubMenu;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Menu.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO extends BaseDao {
    public List<MainMenu> getAllMenus() {
        return get().withHandle(h->{
            return h.createQuery("select id, name, link from main_menus").map((rs,ctx)->{
                int mainMenuId=rs.getInt("id");
                String name=rs.getString("name");
                String link=rs.getString("link");
                List<SubMenu> subMenus=h.createQuery("select id, name from sub_menus where main_menu_id=:mainMenuId")
                        .bind("mainMenuId",mainMenuId)
                        .map((rsSub,ctxSub)->{
                            int subMenuId=rsSub.getInt("id");
                            String subName=rsSub.getString("name");
                            List<MenuItem> items=h.createQuery("select name, link from menu_items where sub_menu_id=:subMenuId")
                                    .bind("subMenuId",subMenuId)
                                    .map((rsItem,ctxItem)->{
                                        return new  MenuItem(rsItem.getString("name"), rsItem.getString("link"));
                                    }).list();
                            return new SubMenu(subName, items);
                        }).list();
                return new MainMenu(name, link, subMenus);
            }).list();
        });
    }

}