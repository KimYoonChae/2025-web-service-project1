package view;

import java.util.Scanner;

public class MenuView {
    Scanner sc=new Scanner(System.in);
    public int select(){
        System.out.print("*** cafe ***\n"
                + "**************************\n"
                + "1. 모든 카페 메뉴 보기\n"
                + "2. 메뉴 검색\n"
                +"3. 메뉴 추가\n"
                +"4. 메뉴 수정\n"
                +"5. 메뉴 삭제\n"
                +"6. 나가기\n"
                + "**************************\n"
                + "원하는 번호를 고르세요 => ");
        return sc.nextInt();
    }
}
