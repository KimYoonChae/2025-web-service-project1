import controller.MenuController;
import view.MenuView;

public class Main {

    public static void main(String[] args) {
        Main m=new Main();
        m.start();
    }

    void start(){
        MenuView mv=new MenuView();
        MenuController mc=new MenuController();
        while(true){
            int s=mv.select();
            if(s==6) break;
            if(s==1) {
                mc.postmenu();
            }
            if(s==2){
                mc.search();
            }
            if(s==3){
                mc.addmenu();
            }
            if(s==4){
                mc.updatemenu();
            }
            if(s==5){
                mc.deletemenu();
            }
        }

    }
}