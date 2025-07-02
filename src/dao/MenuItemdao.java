package dao;
//DB에 실제로 접근하여 insert, select, update, delete 등 수행

import model.MenuItem;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuItemdao implements ICRUD{
    final String selectAll = "select * from menu_item";
    final String MENU_INSERT = "insert into menu_item (name,description,price,regdate)"+" values(?,?,?,?)";
    List<MenuItem> list = new ArrayList<>();

    Scanner s;
    Connection conn;
    public MenuItemdao() {
        conn = DbUtil.getConnection();
    }
    public void loadData(){
        list.clear();
        String selectall = "select * from menu_item";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectall);
            while(true){
                if(!rs.next()) break;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                list.add(new MenuItem(id,name,description,price));
            }
            rs.close();
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void listAll(){
        loadData();
        System.out.println("-------------------------");
        for(int i=0;i<list.size();i++){
            System.out.print((i+1)+" ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("-------------------------");
    }
    public String getCurrentDate(){
        LocalDate now =LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(now);
    }
    @Override
    public int add(MenuItem item) {
        int retval=0;
        PreparedStatement pstmt;
        try{
            pstmt=conn.prepareStatement(MENU_INSERT);
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.setInt(3,item.getPrice());
            retval=pstmt.executeUpdate();
            pstmt.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return retval;
    }
    public void addItem() {
        System.out.print("=> 새 메뉴 이름을 입력하세요 : ");
        String name = s.nextLine();

        System.out.print("=> 새 메뉴의 가격을 입력하세요 : ");
        int price = s.nextInt();

        System.out.print("=> 새 메뉴의 설명문을 작성하세요 : ");
        String description = s.nextLine();

        MenuItem one = new MenuItem(0,name,description,price);
        int retval =add(one);
        if(retval>0) System.out.println("새 메뉴가 메뉴판에 추가되었습니다.");
        else System.out.println("새 메뉴 추가중 에러가 발생했습니다.");

    }
}


