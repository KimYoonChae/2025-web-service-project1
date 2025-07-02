package controller;

import dao.MenuItemdao;

public class MenuController {
    MenuItemdao menu = new MenuItemdao();
    public void postmenu(){
        menu.listAll();
    }
}
