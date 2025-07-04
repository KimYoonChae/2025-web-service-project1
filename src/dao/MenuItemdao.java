package dao;
//DB에 실제로 접근하여 insert, select, update, delete 등 수행

import model.MenuItem;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.random;

public class MenuItemdao implements ICRUD{
    final String selectAll = "select * from menu_item";
    final String MENU_INSERT = "insert into menu_item (name,price,description,regdate)"+" values(?,?,?,?)";
    final String MENU_UPDATE = "update menu_item set name=?,price=?,description=? where id=?";
    final String MENU_DELETE = "delete from menu_item where id=?";
    List<MenuItem> list = new ArrayList<>();

    Scanner s = new Scanner(System.in);

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
                list.add(new MenuItem(id,name,price,description));
            }
            rs.close();
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int generateId() {
        loadData();

        int id = 1;
        boolean duplicate;

        while (true) {
            duplicate = false;
            for (MenuItem item : list) {
                if (item.getId() == id) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) break;
            id++;
        }

        return id;
    }

    public void listAll(){
        loadData();
        System.out.println("-------------------------");
        for(int i=0;i<list.size();i++){
            MenuItem item = list.get(i);
            System.out.printf("%d. [%s, id: %d] %d원 / %s\n", i + 1, item.getName(), item.getId(),item.getPrice(), item.getDescription());
        }
        System.out.println("-------------------------");
    }
    public String getCurrentDate(){
        LocalDate now =LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(now);
    }
    public void searchItem(){
        System.out.print("메뉴를 검색해보세요 > ");
        String name = s.nextLine();
        boolean flag = false;
        for (MenuItem item : list) {
            if (item.getName().contains(name))
            {
                if(!flag){
                    System.out.println("검색 결과:");
                    flag = true;
                }
                System.out.printf("%s / %d원 / %s\n",item.getName(), item.getPrice(), item.getDescription());
            }
        }
        if(!flag){
            System.out.println("메뉴를 찾을 수 없습니다.");
        }
    }
    @Override
    public int add(MenuItem item) {
        int retval=0;
        PreparedStatement pstmt;
        try{
            pstmt=conn.prepareStatement(MENU_INSERT);
            pstmt.setString(1, item.getName());
            pstmt.setInt(2,item.getPrice());
            pstmt.setString(3, item.getDescription());
            pstmt.setString(4, getCurrentDate());
            retval=pstmt.executeUpdate();
            pstmt.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return retval;
    }
    public void addItem() {
        String name;
        while (true) {
            System.out.print("=> 새 메뉴 이름을 입력하세요 : ");
            name = s.nextLine();
            if (name.isEmpty()) {
                System.out.print("이름은 비워둘 수 없습니다.");
            } else {
                break;
            }
        }

        int price;
        while (true) {
            System.out.print("가격을 입력하세요 > ");
            try {
                price = s.nextInt();
                s.nextLine();
                if (price < 0) {
                    System.out.println("가격은 0 이상이어야 합니다.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력해주세요!");
                s.nextLine();
            }
        }


        System.out.print("=> 새 메뉴의 설명문을 작성하세요 : ");
        String description = s.nextLine();
        int id = generateId();

        MenuItem one = new MenuItem(id, name, price, description);
        int retval = add(one);
        if (retval > 0) System.out.println("새 메뉴가 메뉴판에 추가되었습니다.");
        else System.out.println("새 메뉴 추가중 에러가 발생했습니다.");
    }
    @Override
    public int update(MenuItem item) {
        int retval=0;
        PreparedStatement pstmt;
        try{
            pstmt=conn.prepareStatement(MENU_UPDATE);
            pstmt.setString(1, item.getName());
            pstmt.setInt(2,item.getPrice());
            pstmt.setString(3, item.getDescription());
            pstmt.setInt(4, item.getId());
            retval=pstmt.executeUpdate();
            pstmt.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return retval;
        }
    public void updateItem() {
        listAll();
        System.out.print("수정할 메뉴 아이디를 고르세요 > ");
        int id=s.nextInt();
        s.nextLine();
        System.out.print("새로운 메뉴명을 입력하세요 > ");
        String name = s.nextLine();
        System.out.print("새로운 가격을 입력하세요 > ");
        int price = s.nextInt();
        s.nextLine();
        System.out.print("새로운 설명을 작성하세요 > ");
        String description = s.nextLine();
        MenuItem one = new MenuItem(id,name,price,description);
        int retval =update(one);
        if(retval>0) System.out.println("새 메뉴가 메뉴판에 추가되었습니다.");
        else System.out.println("새 메뉴 추가중 에러가 발생했습니다.");
    }
    @Override
    public int delete(MenuItem item) {
        int retval=0;
        PreparedStatement pstmt;
        try{
            pstmt=conn.prepareStatement(MENU_DELETE);
            pstmt.setInt(1, item.getId());
            retval=pstmt.executeUpdate();
            pstmt.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return retval;
    }
    public void deleteItem() {
        listAll();
        System.out.print("삭제할 메뉴 이름을 입력하세요 > ");
        String name = s.nextLine();
        for (int i = 0; i < list.size(); i++) {
            MenuItem item = list.get(i);
            if (item.getName().equals(name)) {
                delete(item);
                System.out.println("Item deleted.");
            }
        }
    }
}


