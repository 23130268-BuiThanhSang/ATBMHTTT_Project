package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.MenuDAO;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Menu.MainMenu;

import java.util.List;

public class MenuService {
    MenuDAO menuDao=new MenuDAO();
    public List<MainMenu> getAllMenus() {
      return   menuDao.getAllMenus();
    }
}
