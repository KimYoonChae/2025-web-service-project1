package controller;

import dao.MenuItemdao;

public class MenuController {
    MenuItemdao menu = new MenuItemdao();
    public void postmenu(){
        menu.listAll();
    }
    public void search(){
        menu.searchItem();
    }
    public void addmenu(){
        menu.addItem();
    }
    public void updatemenu(){
        menu.updateItem();
    }
    public void deletemenu(){
        menu.deleteItem();
    }
}
