import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class SE_STORE {
    ArrayList<PRODUCT> product = new ArrayList<>();
    ArrayList<CATEGORY> category = new ArrayList<>();
    ArrayList<MEMBER> member = new ArrayList<>();
    ArrayList<Cart> cart = new ArrayList<>();
    Scanner keyboard = new Scanner(System.in);
    int count = 1 ;


    //ตัว Add ArrayList ของ product
    public void addProduct (PRODUCT products){
        product.add(products);

    }

    //ตัว Add ArrayList ของ category
    public void addCategory (CATEGORY categories){
        category.add(categories);
    }

    //ตัว Add ArrayList ของ member
    public void addMember(MEMBER members){
        member.add(members);
    }

    //ตัว Add ArrayList ของ cart
    public void addCart(Cart carts){
        cart.add(carts);
    }


    // ตัวอ่านไฟล์ category และนำค่าไปใส่ ArrayList category
    public void Read_Add_category() throws FileNotFoundException {
        Scanner File_CATEGORY = new Scanner(new File("C:\\SE Year 2\\PSP_New\\src\\CATEGORY.txt"));

        while (File_CATEGORY.hasNextLine()) {
            String[] part = File_CATEGORY.nextLine().split("\t");
            String id = part[0];
            String name = part[1];

            addCategory(new CATEGORY(id , name));


        }

    }

    // ตัวอ่านไฟล์ product และนำค่าไปใส่ ArrayList product
    public void Read_Add_product() throws FileNotFoundException {
        Scanner File_PRODUCT = new Scanner(new File("C:\\SE Year 2\\PSP_New\\src\\PRODUCT (1).txt"));

        while (File_PRODUCT.hasNextLine()){
            String[] part = File_PRODUCT.nextLine().split("\t");
            String number = part[0];
            String name = part[1];

            //String string_price =  part[2].replaceAll("[^0-9.]", "");
            String string_price = part[2].replace("$" , "");


            //แยกตัวอักษรกับตัวเลข
            double double_price = (Double.parseDouble(string_price) * 34 );
            String e = String.format("%.2f" , double_price);
            //เปลี่ยนหน่วยเป็นบาท
            double price = Double.parseDouble(e);
            int quantity = Integer.parseInt(part[3]);
            String type = part[4];

            addProduct(new PRODUCT(number , name , price  , quantity , type));

        }

    }

    // ตัวอ่านไฟล์ member และนำค่าไปใส่ ArrayList member
    public void Read_Add_member() throws FileNotFoundException{
        Scanner File_MEMBER = new Scanner(new File("C:\\SE Year 2\\PSP_New\\src\\MEMBER.txt"));

        while (File_MEMBER.hasNextLine()){
            String[] part = File_MEMBER.nextLine().split("\t");

            String id = part[0];
            String first_name = part[1] ;
            String last_name = part[2];
            String email = part[3] ;
            String password = part[4] ;
            String phone = part[5] ;
            int point = (int)Double.parseDouble(part[6]);

            addMember(new MEMBER(id , first_name , last_name , email , password , phone , point));

        }

    }

    //เป็นอย่างแรกที่จะแสดง
    public void Start_STORE() throws IOException {
        System.out.println("===== SE Store =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("====================");
        System.out.print("Select (1-2) : ");

        Menu_Display();
    }



    //ต่อจาก Start_STORE
    public void Menu_Display() throws IOException {
        String input = keyboard.nextLine();
        if (input.equals("1")){
            Login_Display();
            //กด 1 ไปหน้า Login

        } else if (input.equals("2")) {
            System.out.println("===== SE STORE =====\t\t\t\n" +
                    "\tThank you for using our service :3");
            //กด 2 ออกจากโปรแกรม

        }else {
            Menu_Display();
            //นอกเหนือจากนี้ วนซ้ำ

        }

    }

    public void Login_Display() throws IOException {
        System.out.println("===== LOGIN =====");

        System.out.print("Email : ");
        String Email = keyboard.nextLine();
        System.out.print("Password : ");
        String Password = keyboard.nextLine();

        //รับค่า Email และ Password


        if (checkMember_Expired(Email , Password) == 1){
            //เช็คว่า Email หมดอายุไหม

            if (check_information(Email , Password) != -1) {
                //เช็คว่ากรอก Email , Password ถุกไหม

                if (member.get(check_information(Email , Password)).password.charAt(6) == '0'){
                    //กรณีเป็น Role Staff
                    Staff_memberInfo(check_information(Email , Password));

                }else {
                    //กรณีเป็น Role ไม่ใช่ Stff
                    Normal_memberInfo(check_information(Email , Password));

                }


            }else {
                //กรอก Email , Password ผิด
                if (count < 3) {
                    //นับจำนวนครั้งที่ผิด

                    System.out.println("Error! - Email or Password is Incorrect " + "(" + count + ")");
                    System.out.println();
                    count++;
                    Login_Display();

                } else {
                    //ผิดครั้งที่ 3 กลับไปทำงาน Start_STORE

                    System.out.println("Error! - Email or Password is Incorrect " + "(" + count + ")");
                    System.out.println();
                    count = 1 ;
                    Start_STORE();
                }

            }



        }else if (checkMember_Expired(Email , Password) == 2) {
            //พบว่า Email หมดอายุ

            Account_Expired_Display();
            Start_STORE();

        }else {
            //กรณีกรอกอย่างอื่นทำงานเหมือน ตัวกรอก Email , Password ผิด
            if (count < 3) {

                System.out.println("Error! - Email or Password is Incorrect " + "(" + count + ")");
                System.out.println();
                count++;
                Login_Display();

            } else {

                System.out.println("Error! - Email or Password is Incorrect " + "(" + count + ")");
                System.out.println();
                count = 1 ;
                Start_STORE();
            }
        }




    }
    /*******************************************************************************************
                                                NORMAL
     *******************************************************************************************/
    //กรณีผู้ใช้ Login เข้ามาไม่ใช่ Staff
    public void Normal_memberInfo(int index) throws IOException {
        System.out.println("===== SE STORE =====");
        System.out.println("Hello, " + member.get(index).last_name.charAt(0) + ". " + member.get(index).first_name + " (" + member.get(index).check_Role() + ")");
        System.out.println("Email : " + member.get(index).CensorEmail());
        System.out.println("Phone : " + member.get(index).FullPhone());
        System.out.println("You have " + member.get(index).point + " Point");

        System.out.println("====================");
        System.out.println("1. Show Category");
        System.out.println("2. Order Product");
        System.out.println("3. Logout");
        System.out.println("====================");
        System.out.print("Select (1-3) :");

        showMember(index);

    }
    public void showMember(int index) throws IOException {

        String input = keyboard.nextLine();

        if (input.equals("1")){
            //กด 1 เลือก Show Category
            show_Category_Display1(index);

        }else if(input.equals("2")){
            //กด 2 เลือก Order Product
            show_select_order(index);

        } else if (input.equals("3")) {
            //กด 2 กลับไป Login
            Login_Display();
            count = 0 ;

        }else {
            //กดอย่างอื่นวนซ้ำ
            showMember(index);

        }

    }
    public void show_select_order(int index) throws IOException {
        //กด 2 เลือก Order Product


        Show_All_products();
        //Show product ทั้งหมด

        System.out.println("Enter the product number followed by the quantity.");
        System.out.println("1. How to Order");
        System.out.println("2. List Products");
        System.out.println("Q. Exit");

        Order_product_select(index);


    }

    public void Order_product_select(int index) throws IOException {

        String Order_select;
        boolean exit = false;

        while (!exit) {

            System.out.print("Enter :");
            Order_select = keyboard.nextLine();
            String[] Order = Order_select.split("\\s+");

            if (Order_select.equalsIgnoreCase("1")) {
                // แสดงวิธีการสั่งสินค้า
                How_to_Order(index);


            } else if (Order_select.equalsIgnoreCase("2")) {
                // แสดงรายการสินค้า
                Show_All_products();

            } else if (Order_select.equalsIgnoreCase("q")) {
                //ออกไปหน้า Normal_memberInfo
                exit = true;
                write_Carts();
                Normal_memberInfo(index);


            } else if (Order_select.matches("\\d+\\s+[+-]?\\d+") && Order.length == 2) {

                // ตรวจสอบ input สำหรับการเลือกสินค้าและจำนวน
                int productNumber = Integer.parseInt(Order[0]);

                if (productNumber >= 1 && productNumber <= product.size()) {
                    // เพิ่มสินค้าไปยังตะกร้า
                    addProduct_toCart(Order[0], Order[1], index);

                    exit = true ;
                } else {
                    System.out.println("Your input is invalid!");
                }

            } else {
                System.out.println("Your input is invalid!");
            }
        }

    }
    public void addProduct_toCart(String product_Type , String amount , int member_index) throws IOException {

        int ID_Product = Integer.parseInt(product_Type) - 1  ;
        //รับค่าตำแหน่งของ product

        int quantity_Cart ;
        //ตัวช่วยคำนวณ

        Cart cart1 = null ;
        //ตัวคืนค่าใน cart


        for (Cart i : cart){
            if (i.getProduct_ID().equals(product.get(ID_Product).number) && i.getMember_ID().equals(member.get(member_index).id)) {
                cart1 = i;
                //เมื่อพบ ID ของ product และ ID ของ member ที่จะเพิ่มเข้าไปมีอยู่แล้ว ไปคืนค่าตำแหน่งนั้นมา
            }

        }
        try {

            if (cart1 == null && amount.charAt(0) != '+' && amount.charAt(0) != '-') {
                //เมื่อ cart ยังว่างอยู่

                if (Integer.parseInt(amount) > product.get(ID_Product).qua){
                    //เช็ตของจำนวนของที่เพิ่มเข้าไปมากกว่าจำนวนในคลังป่าว
                    System.out.println("Your products is not Enough..!");

                }else {
                    //add ลง cart
                    System.out.println("Product added to cart.");
                    addCart(new Cart(member.get(member_index).id, product.get(ID_Product).number, Integer.parseInt(amount)));
                }

            } else {

                if (amount.contains("+")) {
                    //กรณีมีของอยู่แล้วและต้องการบวกเพิ่ม

                    quantity_Cart = Integer.parseInt(amount.substring(1));
                    //เก็บค่าที่ต้องการเพิ่มและตัดตัว + ออก

                    if (cart.get(cart.indexOf(cart1)).getProduct_Quantity() + quantity_Cart > product.get(ID_Product).qua){
                        //เช็ตของจำนวนของที่เพิ่มเข้าไปมากกว่าจำนวนในคลังป่าว
                        System.out.println("Your products is not Enough..!");

                    }else {
                        //add ลง cart
                        cart.get(cart.indexOf(cart1)).setProduct_Quantity(cart.get(cart.indexOf(cart1)).getProduct_Quantity() + quantity_Cart);

                    }

                } else if (amount.contains("-")) {
                    //กรณีมีของอยู่แล้วและต้องการบวกเพิ่ม

                    quantity_Cart = Integer.parseInt(amount.substring(1));
                    //เก็บค่าที่ต้องการเพิ่มและตัดตัว + ออก

                    cart.get(cart.indexOf(cart1)).setProduct_Quantity(cart.get(cart.indexOf(cart1)).getProduct_Quantity() - quantity_Cart);
                    //add ลง cart

                } else {
                    //กรณีมีของอยู่แล้วและต้องการแก้ไขจำนวน

                    if (Integer.parseInt(amount) > product.get(ID_Product).qua){
                        //เช็ตของจำนวนของที่เพิ่มเข้าไปมากกว่าจำนวนในคลังป่าว

                        System.out.println("Your products is not Enough..!");
                    }else {
                        //add ลง cart

                        cart.get(cart.indexOf(cart1)).setProduct_Quantity(Integer.parseInt(amount));
                    }
                }

            }
        }catch (Exception e){
            //เมื่อกรอกอย่างอื่น วนซ้ำ
            System.out.println("Your input is invalid!");
        }

        for (int i = 0; i < cart.size(); i++) {
            //loop เช็คถ้าค่า quantity ใน cart น้อยกว่าหรือเท่ากับ 0 ให้ลบออก
            if (cart.get(i).product_Quantity <= 0){
                cart.remove(i);
            }

        }

        /*
        for (int i = 0; i < cart.size(); i++) {
            System.out.println(cart.get(i).member_ID + "\t" + cart.get(i).product_ID + "\t" + cart.get(i).product_Quantity);

        }

         */

        //วนซ้ำ
        Order_product_select(member_index);

    }
    public void write_Carts() throws IOException {
        //เขียนไฟล์ที่อยู่ใน ArrayList cart

        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\SE Year 2\\PSP_New\\src\\Cart.txt"));
        for (int i = 0; i < cart.size(); i++) {
            writer.write(cart.get(i).member_ID + "\t" + cart.get(i).product_ID + "\t" + cart.get(i).product_Quantity);
            writer.newLine();
        }
        writer.close();

        System.out.println("Your cart has been saved!");

    }


    public void How_to_Order(int index) throws IOException {
        System.out.println(
                "How to Order:\n" +
                        "To Add Product: \n" +
                        "\tEnter the product number followed by the quantity.\t\n" +
                        "\tExample: 1 50 (Adds 50 chips)\n" +
                        "\tTo Adjust Quantity:\n" +
                        "\t+ to add more items: 1 +50 (Adds 50 more chips)\n" +
                        "\t- to reduce items: 1 -50 (Removes 50 chips)");

        Order_product_select(index);

    }
    /*******************************************************************************************
                                        END NORMAL
     *******************************************************************************************/






    /*******************************************************************************************
                                        STAFF
     *******************************************************************************************/
    public void Staff_memberInfo(int index) throws IOException {
        //login เข้ามาเป็น Role Staff
        System.out.println("===== SE STORE =====");
        System.out.println("Hello, " + member.get(index).last_name.charAt(0) + ". " + member.get(index).first_name + " (" + member.get(index).check_Role() + ")");
        System.out.println("Email : " + member.get(index).CensorEmail());
        System.out.println("Phone : " + member.get(index).FullPhone());
        System.out.println("You have " + member.get(index).point + " Point");

        System.out.println("====================");
        System.out.println("1. Show Category");
        System.out.println("2. Add Member");
        System.out.println("3. Edit Member");
        System.out.println("4. Edit Products");
        System.out.println("5. Logout");
        System.out.println("====================");
        System.out.print("Select (1-5) :");

        showMember_Staff(index);

    }

    public void showMember_Staff(int index) throws IOException {
        String input = keyboard.nextLine();

        if (input.equals("1")){
            show_Category_Display1(index);
            //กด 1 Show_category

        } else if (input.equals("2")) {
            Add_New_Member(index);
            //กด 2 Add_New_Member


        } else if (input.equals("3")){
            show_All_Member(index);
            //กด 3 Edit Member


        } else if (input.equals("4")) {
            select_product1(index);
            //กด 4 Edit Products

        } else if (input.equals("5")){
            //กด 5 กลับไปหน้า login
            Login_Display();


        }else {
            //กรอกอย่างอื่นวนซ้ำ
            Staff_memberInfo(index);
        }

    }
    public void Add_New_Member(int index) throws IOException {
        //กรณี Add_New_Member

        int[] check = new int[4];
        //สร้างมาเช็คว่า ผู้ใช้กรอกรูปแบบแต่ละตัวถูกต้องไหม

        String new_Firstname = "";
        String new_Lastname = "";
        String new_Email = "";
        String new_Phone = "";

        System.out.print("Firstname :");
        new_Firstname = keyboard.next();

        if (new_Firstname.length() > 2) {
            check[0] = 1;
        } else {
            check[0] = 0;
        }
        //กรอกถูก check ตำแหน่งแรกเป็น 1 ไม่ถูกเป็น 0

        System.out.print("Lastname :");
        new_Lastname = keyboard.next();
        if (new_Lastname.length() > 2) {
            check[1] = 1;
        } else {
            check[1] = 0;
        }
        //กรอกถูก check ตำแหน่งสองเป็น 1 ไม่ถูกเป็น 0

        System.out.print("Email :");
        new_Email = keyboard.next();
        if (new_Email.length() > 2) {
            for (int j = 0; j < new_Email.length(); j++) {
                if (new_Email.charAt(j) == '@') {
                    check[2] = 1;
                    break;
                } else {
                    check[2] = 0;
                }

            }
        } else {
            check[2] = 0;

        }
        //กรอกถูก check ตำแหน่งสามเป็น 1 ไม่ถูกเป็น 0

        System.out.print("Phone :");
        new_Phone = keyboard.next();
        if (new_Phone.length() == 10) {
            check[3] = 1;

        } else {
            check[3] = 0;

        }
        //กรอกถูก check ตำแหน่งสี่เป็น 1 ไม่ถูกเป็น 0

        boolean success = true;
        //เช็คว่าค่าที่กรอกเข้ามาถูกหมดไหม


        for (int j = 0; j < check.length; j++) {
            if (check[j] == 1) {
                success = true;
                //ทุกตำแหน่งเป็น 1 เป็น ture
            } else {
                success = false;
                //เจอตำแหน่งเป็น 0 เป็น false
                break;
            }

        }



        if (success){
            //เป็น ture


            System.out.println("Success - New Member has been created!");
            System.out.print(new_Firstname + "'s Password is ");
            for (int j = 0; j < get_New_Password().length(); j++) {
                if (j == 9 || j == 10 || j == 13 || j == 14 || j == 15 || j == 16) {
                    System.out.print(get_New_Password().charAt(j));
                }

            }
            System.out.println();
            //แสดงชื่อและ Password ที่ random มา

            PrintWriter Output = new PrintWriter(new FileWriter("C:\\SE Year 2\\PSP_New\\src\\MEMBER.txt", true));
            int IntId = Integer.parseInt(member.get(member.size() - 1).id);
            IntId++;
            String StrTd = String.valueOf(IntId);


            Output.println(StrTd + "\t" + new_Firstname + "\t" + new_Lastname + "\t" + new_Email + "\t" + get_New_Password() + "\t" + new_Phone + "\t" + "0.00");
            addMember(new MEMBER(StrTd, new_Firstname, new_Lastname, new_Email, get_New_Password(), new_Phone, 0));
            Output.close();
            //เขียนข้อมูลลงใน MEMBER.txt


            Staff_memberInfo(index);


        }else {
            System.out.println("Error! - Your Information are Incorrect!");
            System.out.println();
            Staff_memberInfo(index);
            //เมื่อข้อมูลไม่ถูกต้องกลับไป Staff_memberInfo
        }


    }
    public void show_All_Member(int index) throws IOException {
        //กด 3 Edit Member

        System.out.println("===== SE STORE's Member =====");
        System.out.printf("%-8s %-30s %-20s" , "#" , "Name" , "Email");
        System.out.println();
        int count = 1 ;

        for (int i = 0; i < member.size(); i++) {
            System.out.printf("%-8s %-30s %-20s" , count++ , (member.get(i).first_name + " " + member.get(i).last_name) , member.get(i).email );
            System.out.println();


        }
        System.out.println("================================");

        System.out.println("Type Member Number, You want to edit or Press Q to Exit");
        System.out.print("Select (1-" + member.size() + ") : ");
        select_Member(index);




    }
    public void select_Member(int index) throws IOException {

        String input_Member = keyboard.nextLine();


        if (input_Member.equalsIgnoreCase("q")) {
            Staff_memberInfo(index);
            //กลับไปหน้า Staff_memberInfo

        }else if (Integer.parseInt(input_Member) > 0 && Integer.parseInt(input_Member) <= member.size()) {
            //เช็คว่าค่าที่รับมาเกินขอบเขตใน ArrayList member หรือป่าว
            show_Edit_info(input_Member, index);

        }else {
            //กรอกไม่ถูกวนซ้ำ
            select_Member(index);
        }




    }
    public void show_Edit_info(String input_Member , int index) throws IOException {
        //แสดงหน้าแก้ไข member

        System.out.println();
        System.out.println("==== Edit info of " + member.get(Integer.parseInt(input_Member)-1).first_name + " " + member.get(Integer.parseInt(input_Member)-1).last_name + " ====");
        System.out.println("Type new info or Hyphen (-) for none edit.");


        int[] check = new int[4];

        ArrayList<String> edit = new ArrayList<>();
        //edit เก็บข้อมูลที่แก้ไข้

        ArrayList<String> lines = new ArrayList<>();
        //lines เก็บข้อมูลเก่าทั้งหมด



        System.out.print("Firstname : ");
        String F_name = keyboard.nextLine();

        if (F_name.equalsIgnoreCase("-")){
            edit.add(member.get(Integer.parseInt(input_Member)-1).first_name) ;
            check[0] = 1 ;
            //ถ้าเป็น - นำข้อมูลเดิมลง edit

        }else {
            if (F_name.length() > 2) {
                check[0] = 1;
                edit.add(F_name) ;
                //ข้อมูลถูกต้องนำข้อมูลใหม่ลง edit
            } else {
                check[0] = 0;
            }


        }
        //กรอกถูก check ตำแหน่งแรกเป็น 1 ไม่ถูกเป็น 0


        System.out.print("Lastname : ");
        String L_name = keyboard.nextLine();

        if (L_name.equalsIgnoreCase("-")){
            edit.add(member.get(Integer.parseInt(input_Member)- 1).last_name) ;
            check[1] = 1 ;
            //ถ้าเป็น - นำข้อมูลเดิมลง edit

        }else {
            if (L_name.length() > 2) {
                check[1] = 1;
                edit.add(L_name);
                //ข้อมูลถูกต้องนำข้อมูลใหม่ลง edit

            } else {
                check[1] = 0;
            }
        }
        //กรอกถูก check ตำแหน่งสองเป็น 1 ไม่ถูกเป็น 0


        System.out.print("Email : ");
        String E_mail = keyboard.nextLine();

        if (E_mail.equalsIgnoreCase("-")){
            edit.add(member.get(Integer.parseInt(input_Member)- 1).email);
            check[2] = 1 ;
            //ถ้าเป็น - นำข้อมูลเดิมลง edit

        }else {
            if (E_mail.length() > 2) {
                check[2] = 1;
                edit.add(E_mail);
                //ข้อมูลถูกต้องนำข้อมูลใหม่ลง edit
            } else {
                check[2] = 0;
            }

        }
        //กรอกถูก check ตำแหน่งสามเป็น 1 ไม่ถูกเป็น 0


        System.out.print("Phone : ");
        String P_hone = keyboard.nextLine();

        if (P_hone.equalsIgnoreCase("-")){
            edit.add(member.get(Integer.parseInt(input_Member)- 1).phone);
            check[3] = 1 ;
            //ถ้าเป็น - นำข้อมูลเดิมลง edit

        }else {
            if (P_hone.length() == 10) {
                edit.add(P_hone);
                check[3] = 1;
                //ข้อมูลถูกต้องนำข้อมูลใหม่ลง edit



            } else {
                check[3] = 0;

            }
        }


        boolean success = true;


        for (int j = 0; j < check.length; j++) {
            if (check[j] == 1) {
                success = true;
                //ทุกตำแหน่งเป็น 1 เป็น ture

            } else {
                success = false;
                //เจอตำแหน่งเป็น 0 เป็น ture
                break;
            }

        }



        if (success) {
            //เป็น ture

            String print = member.get(Integer.parseInt(input_Member) - 1).id + "\t" + edit.get(0) + "\t" + edit.get(1) + "\t" + edit.get(2) + "\t" + member.get(Integer.parseInt(input_Member) - 1).password + "\t" + edit.get(3) + "\t" + member.get(Integer.parseInt(input_Member) - 1).point;
            //print เก็บข้อมูลใหม่ทั้งหมด

            BufferedReader reader = new BufferedReader(new FileReader("C:\\SE Year 2\\PSP_New\\src\\MEMBER.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                //นำข้อมูลเก่าลง ArrayList lines เป็นแบบบรรทัด
            }
            if ((Integer.parseInt(input_Member) - 1) >= 0 && (Integer.parseInt(input_Member) - 1) < lines.size()) {
                lines.set((Integer.parseInt(input_Member) - 1), print);
                //set ข้อมูลตำแหน่งที่ต้องการแก้ไข แทนด้วยตำแหน่งใหม่
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\SE Year 2\\PSP_New\\src\\MEMBER.txt"));
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                writer.newLine();
                //นำข้อมูลใน ArrayList lines เขียนทับใน MEMBER.txt
            }
            writer.close();
            member.removeAll(member);
            //นำข้อมูลเก่าใน ArrayList member ออก

            Read_Add_member();
            //อ่านไฟล์ใหม่

            System.out.println("Success - Member has been updated!");
            Staff_memberInfo(index);


        } else {
            //กรอกข้อมูลไม่ถูก
            System.out.println("Error! - Your Information are Incorrect!");
            System.out.println();
            Staff_memberInfo(index);

        }





    }
    public void select_product1(int index) throws IOException {
        //กด 4 Edit product

        Show_All_products();
        //Show product ทั้งหมด

        System.out.println("Type Product Number, You want to edit or Press Q to Exit");
        System.out.print("Select (1-"+(count-1)+ ") : ");

        select_product2(index);


    }
    public void select_product2(int index) throws IOException {
        //เลือก product
        String select_product = keyboard.nextLine();

        if (select_product.equalsIgnoreCase("q")){
            Staff_memberInfo(index);
            //กด q ไปหน้า Staff_memberInfo
        }
        if (Integer.parseInt(select_product) > 0 && Integer.parseInt(select_product) <= product.size()){
            show_edit_product(select_product , index);
            //เช็คว่าค่าที่รับมาเกินขอบเขตใน ArrayList member หรือป่าว

        }else {
            //วนซ้ำ
            select_product1(index);
        }

    }
    public void show_edit_product(String select_product , int index) throws IOException {
        //แสดงหน้า edit product

        System.out.println("==== Edit info of "+ product.get(Integer.parseInt(select_product)-1).name+" ====");
        System.out.println("Type new info or Hyphen (-) for none edit.");

        int[] check = new  int[2];

        ArrayList<String> edit = new ArrayList<>();
        //edit เก็บข้อมูลที่แก้ไข้

        ArrayList<String> lines = new ArrayList<>();
        //lines เก็บข้อมูลทเก่าทั้งหมด

        System.out.print("Name : ");
        String N_ame = keyboard.nextLine();

        if (N_ame.equalsIgnoreCase("-")){
            edit.add(product.get(Integer.parseInt(select_product)-1).name);
            check[0] = 1;
            // - นำข้อมูลเก่าลง edit

        }else {
            edit.add(N_ame);
            // นำข้อมูลใหม่ลง edit
            check[0] = 1 ;
        }

        System.out.print("Quantity (+ or -) : ");
        String Q_uantity = keyboard.nextLine();

        if (Q_uantity.equalsIgnoreCase("-")){
            edit.add(String.valueOf(product.get(Integer.parseInt(select_product)-1).qua));
            check[1] = 1 ;
            // เมื่อเป็น - ตัวเดียว นำข้อมูลเก่าลง edit

        }else if (Q_uantity.charAt(0) == '-' || Q_uantity.charAt(0) == '+' ){
            String check_Qua = Q_uantity.substring(1);
            //เมื่อตัวแรกเป็น - หรือ + ตัวตัว + - ออกและนำไปใส่ check_Qua

            if (isInteger(check_Qua)){
                //เช็คว่า check_Qua เป็น int ไหม

                if (Q_uantity.charAt(0) == '-'){
                    //เป็นลบ

                    int edit_Quantity = ( product.get(Integer.parseInt(select_product)-1).qua ) - Integer.parseInt(check_Qua) ;
                    //ลบค่า Quantity ใน product ด้วยค่าที่กรอกเข้ามา

                    if (edit_Quantity < 0){
                        edit.add(String.valueOf(0));
                        //เมื่อเป็นค่าติดลบ set เป็น 0
                    }else {
                        edit.add(String.valueOf(edit_Quantity));
                        //เมื่อไม่เป็นค่าติดลบ นำลง edit
                    }
                    check[1] = 1 ;


                }
                if (Q_uantity.charAt(0) == '+'){
                    //เป็นบวก
                    int edit_Quantity = ( product.get(Integer.parseInt(select_product)-1).qua ) + Integer.parseInt(check_Qua) ;
                    //บวกค่า Quantity ใน product ด้วยค่าที่กรอกเข้ามา

                    edit.add(String.valueOf(edit_Quantity)) ;
                    // นำลง edit
                    check[1] = 1 ;
                }

            }else {
                check[1] = 0 ;
            }

        }else {
            check[1] = 0 ;
        }

        boolean success = true;


        for (int j = 0; j < check.length; j++) {
            if (check[j] == 1) {
                success = true;
                //เป็น 1 ทั้งหมด

            } else {
                success = false;
                //เจอ 0

                break;
            }

        }

        if (success){

            String double_price = String.format("%.2f" , (product.get(Integer.parseInt(select_product) - 1).price) / 34 ) ;
            //double_price เก็บค่าที่แปลงเป็น $

            String print = product.get(Integer.parseInt(select_product) - 1).number +"\t"+ edit.get(0) + "\t"+"$" +double_price  + "\t" + edit.get(1) + "\t" + product.get(Integer.parseInt(select_product) - 1).type ;
            //print เก็บค่าใหม่ทั้งหมด

            BufferedReader reader = new BufferedReader(new FileReader("C:\\SE Year 2\\PSP_New\\src\\PRODUCT (1).txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                //lines เก็บเก่าทั้งหมด
            }
            if ((Integer.parseInt(select_product) - 1) >= 0 && (Integer.parseInt(select_product) - 1) < lines.size()) {
                lines.set((Integer.parseInt(select_product) - 1), print);
                //set ข้อมูลตำแหน่งที่ต้องการแก้ไข แทนด้วยตำแหน่งใหม่
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\SE Year 2\\PSP_New\\src\\PRODUCT (1).txt"));
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                writer.newLine();
                //นำค่า lines เขียนลงใน PRODUCT (1).txt
            }
            writer.close();

            product.removeAll(product);
            //นำค่าเก่าออก

            Read_Add_product();
            //อ่านใหม่

            System.out.println("Success - Products has been updated!");
            Staff_memberInfo(index);



        }else {
            System.out.println("Error! - Your Information are Incorrect!");
            Staff_memberInfo(index);

        }






    }
    /*******************************************************************************************
                                        END STAFF
     *******************************************************************************************/




    /*******************************************************************************************
     * ******************************************************************************************

                                Select 1 Show category Normal,Staff

     *******************************************************************************************
     *******************************************************************************************/
    public void show_Category_Display1(int index) throws IOException {
        //เลือก Show category ทั้ง Normal,Staff
        System.out.println("===== SE Store's Product Categories =====\t");
        System.out.println("#\tCategory\t");
        for (int i = 0; i < category.size(); i++) {
            System.out.println(i + 1 + "\t" + category.get(i).name);

        }
        System.out.println("=========================================");
        System.out.println("Select Category to Show Product (1-"+category.size() + ") or Q for exit");
        System.out.print("Select : ");

        show_Category_Display2(index);




    }
    public void show_Category_Display2(int index) throws IOException {
        try {
            String input = keyboard.nextLine(); // รับค่าจากผู้ใช้

            if (input.equalsIgnoreCase("q")) {
                // ตรวจสอบว่าเป็นพนักงานหรือสมาชิกปกติ
                if (member.get(index).password.charAt(6) == '0') {

                    Staff_memberInfo(index); // แสดงข้อมูลพนักงาน
                } else {
                    Normal_memberInfo(index); // แสดงข้อมูลสมาชิกปกติ
                }

            } else {
                int categoryIndex = Integer.parseInt(input); // แปลง input เป็นตัวเลข
                if (categoryIndex >= 1 && categoryIndex <= category.size()) {
                    // แสดงสินค้าตามหมวดหมู่
                    show_Product(input, index);
                } else {
                    show_Category_Display2(index); // เรียกตัวเองใหม่เพื่อรับข้อมูลใหม่
                }
            }
        } catch (NumberFormatException e) {
            // จัดการกรณีที่ input ไม่ใช่ตัวเลข
            show_Category_Display2(index); // เรียกตัวเองใหม่เพื่อรับข้อมูลใหม่
        }


    }
    public void show_Product(String input , int index) throws IOException {
        System.out.println("============ " + category.get(Integer.parseInt(input) - 1).name + " ============");
        System.out.printf("%-8s %-15s %-20s %-10s", "#", "Name", "Price(฿)", "Quantity");
        System.out.println();

        if (member.get(index).password.charAt(6) == '0' || member.get(index).password.charAt(6) == '1'){
            //เมื่อ Role เป็น Staff , Regular

            int count = 1 ;
            int num = Integer.parseInt(input);

            for (int i = 0; i < product.size(); i++) {
                if (product.get(i).type.equals(category.get(num-1).id)){
                    System.out.printf("%-8s %-15s %-20s %-10s", count++, product.get(i).name, product.get(i).price, product.get(i).qua);
                    System.out.println();

                }

            }
            System.out.println("======================================================");
            //Show product ใน category ที่เลือก ไม่ลดราคา




        }else if (member.get(index).password.charAt(6) == '2'){
            //เมื่อ Role เป็น Silver


            int count = 1 ;
            int num = Integer.parseInt(input);
            double double_price ;

            for (int i = 0; i < product.size(); i++) {
                if (product.get(i).type.equals(category.get(num - 1).id)) {

                    double sale = (product.get(i).price) * 5 / 100;
                    double Final_price = (product.get(i).price) - sale;
                    double_price = Double.parseDouble(String.format("%.2f" ,Final_price ));
                    double Old_price = Double.parseDouble(String.format("%.2f" , (product.get(i).price)));

                    System.out.printf("%-8s %-15s %-20s %-10s", count++, product.get(i).name, double_price + " (" + Old_price + ")", product.get(i).qua);
                    System.out.println();

                }

            }

            System.out.println("======================================================");
            //Show product ใน category ที่เลือก ลด 5%






        } else if (member.get(index).password.charAt(6) == '3') {
            //เมื่อ Role เป็น Gold



            int count = 1 ;
            int num = Integer.parseInt(input);
            double double_price ;

            for (int i = 0; i < product.size(); i++) {
                if (product.get(i).type.equals(category.get(num-1).id)){
                    double sale = ((product.get(i).price) * 10) / 100;
                    double Final_price = (product.get(i).price) - sale;
                    double_price = Double.parseDouble(String.format("%.2f" ,Final_price ));
                    double Old_price = Double.parseDouble(String.format("%.2f" , (product.get(i).price)));

                    System.out.printf("%-8s %-15s %-20s %-10s", count++, product.get(i).name, double_price + " (" + Old_price + ")", product.get(i).qua);
                    System.out.println();


                }

            }
            System.out.println("======================================================");
            //Show product ใน category ที่เลือก ลด 10%



        }


        Show_select_DESC_ASC(input ,index);


    }

    public void Show_select_DESC_ASC(String input ,int index) throws IOException {
        System.out.print("1. Show Name By DESC\n" +
                "2. Show Quantity By ASC\n" +
                "or Press Q to Exit : ");

        String select = keyboard.next();

        if (select.equalsIgnoreCase("q")){
            show_Category_Display1(index);
            //กลับไปหน้า show_Category

        }else if (select.equalsIgnoreCase("1")){
            Show_Name_By_DESC(input , index);
            //เรียงสินค้าตามตัวอักษร

        } else if (select.equalsIgnoreCase("2")) {
            Show_Quantity_By_ASC(input , index);
            //เรียงสินค้าตามราคา

        }else {
            Show_select_DESC_ASC(input , index);
            //วนซ้ำ





        }


    }
    public void Show_Name_By_DESC(String input , int index) throws IOException {
        //เรียงตัวอักษร

        ArrayList<PRODUCT> new_product = new ArrayList<>();
        new_product.addAll(product);

        new_product.sort(Comparator.comparing(PRODUCT::getName).reversed());
        //ใช้ sort เรียงให้

        System.out.println("============ " + category.get(Integer.parseInt(input) - 1).name + " ============");
        System.out.printf("%-8s %-15s %-20s %-10s", "#", "Name", "Price(฿)", "Quantity");
        System.out.println();

        int count = 1 ;


        for (int i = 0; i < new_product.size(); i++) {

            if (new_product.get(i).type.equals(category.get(Integer.parseInt(input) - 1).id)){
                //เมื่อ product ตรงกับ category ที่เลือก

                if (member.get(index).password.charAt(6) == '0' || member.get(index).password.charAt(6) == '1') {
                    //เมื่อ Role เป็น Staff , Regular

                    System.out.printf("%-8s %-15s %-20s %-10s", (count++), new_product.get(i).name, new_product.get(i).price, new_product.get(i).qua);
                    System.out.println();



                }else if (member.get(index).password.charAt(6) == '2'){
                    //เมื่อ Role เป็น Silver


                    double sale = (new_product.get(i).price * 5) / 100;
                    double Final_price = new_product.get(i).price - sale;
                    double double_price = Double.parseDouble(String.format("%.2f" ,Final_price ));
                    double Old_price = Double.parseDouble(String.format("%.2f" ,(new_product.get(i).price)));

                    System.out.printf("%-8s %-15s %-20s %-10s", (count++), new_product.get(i).name, double_price + " (" + Old_price + ")", new_product.get(i).qua);
                    System.out.println();





                } else if (member.get(index).password.charAt(6) == '3') {
                    //เมื่อ Role เป็น Gold

                    double sale = ((new_product.get(i).price) * 10) / 100;
                    double Final_price = (new_product.get(i).price) - sale;
                    double double_price = Double.parseDouble(String.format("%.2f" ,Final_price ));
                    double Old_price = Double.parseDouble(String.format("%.2f" , new_product.get(i).price));

                    System.out.printf("%-8s %-15s %-20s %-10s", (count++), new_product.get(i).name, double_price + " (" + Old_price + ")", new_product.get(i).qua);
                    System.out.println();



                }


            }

        }
        System.out.println("======================================================");
        Show_select_DESC_ASC(input,index);




    }
    public void Show_Quantity_By_ASC(String input , int index) throws IOException {
        //เรียงราคา

        ArrayList<PRODUCT> new_Quantity = new ArrayList<>();
        new_Quantity.addAll(product);

        new_Quantity.sort(Comparator.comparing(PRODUCT::getQua));


        System.out.println("============ " + category.get(Integer.parseInt(input) - 1).name + " ============");
        System.out.printf("%-8s %-15s %-20s %-10s", "#", "Name", "Price(฿)", "Quantity");
        System.out.println();

        int count = 1 ;


        for (int i = 0; i < new_Quantity.size(); i++) {

            if (new_Quantity.get(i).type.equals(category.get(Integer.parseInt(input) - 1).id)){

                if (member.get(index).password.charAt(6) == '0' || member.get(index).password.charAt(6) == '1') {
                    //เมื่อ Role เป็น Staff , Regular

                    System.out.printf("%-8s %-15s %-20s %-10s", (count++), new_Quantity.get(i).name, new_Quantity.get(i).price, new_Quantity.get(i).qua);
                    System.out.println();



                }else if (member.get(index).password.charAt(6) == '2'){
                    //เมื่อ Role เป็น Silver


                    double sale = (new_Quantity.get(i).price * 5) / 100;
                    double Final_price = new_Quantity.get(i).price - sale;
                    double double_price = Double.parseDouble(String.format("%.2f" ,Final_price ));
                    double Old_price = Double.parseDouble(String.format("%.2f" , new_Quantity.get(i).price));

                    System.out.printf("%-8s %-15s %-20s %-10s", (count++), new_Quantity.get(i).name, double_price + " (" + Old_price + ")", new_Quantity.get(i).qua);
                    System.out.println();





                } else if (member.get(index).password.charAt(6) == '3') {
                    //เมื่อ Role เป็น Gold

                    double sale = (new_Quantity.get(i).price * 10) / 100;
                    double Final_price = new_Quantity.get(i).price - sale;
                    double double_price = Double.parseDouble(String.format("%.2f" ,Final_price ));
                    double Old_price = Double.parseDouble(String.format("%.2f" , new_Quantity.get(i).price));

                    System.out.printf("%-8s %-15s %-20s %-10s", (count++), new_Quantity.get(i).name, double_price + " (" + Old_price + ")", new_Quantity.get(i).qua);
                    System.out.println();



                }


            }

        }
        System.out.println("======================================================");
        Show_select_DESC_ASC(input,index);

    }
    /*******************************************************************************************
     * ******************************************************************************************

                                ***END*** Select 1 Show category Normal,Staff

     *******************************************************************************************
     *******************************************************************************************/







    public void Show_All_products(){
        //แสดง product ทั้งหมด

        System.out.println("=========== SE STORE's Products ===========");
        System.out.printf("%-8s %-15s %-20s %-10s", "#", "Name", "Price(฿)", "Quantity");
        System.out.println();

        count = 1 ;


        for (int i = 0; i < product.size(); i++) {
            System.out.printf("%-8s %-15s %-20s %-10s", count++, product.get(i).name, product.get(i).price, product.get(i).qua);
            System.out.println();

        }
        System.out.println("======================================================");

    }
    public int checkMember_Expired(String Email, String Password) {
        //check Email หมดอายุ

        int num_check = 0;

        for (int i = 0; i < member.size(); i++) {
            if (member.get(i).email.equals(Email) ) {
                num_check = 2;
                if (member.get(i).password.charAt(2) == '1') {
                    num_check = 1;
                }
            }
        }

        return num_check;
    }
    public int check_information(String Email , String Password){
        //check ความถูกต้องของ Email , Password
        for (int i = 0; i < member.size(); i++) {
            if (member.get(i).email.equals(Email) && member.get(i).new_Pass().equals(Password)) {
                return i;
            }
        }
        return -1;

    }
    public void Account_Expired_Display(){
        System.out.println("Error! - Your Account are Expired! ");

    }
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);  // ลองแปลงค่า input เป็น int
            return true;  // ถ้าแปลงได้สำเร็จ แสดงว่าเป็น int
        } catch (NumberFormatException e) {
            return false; // ถ้าเกิดข้อผิดพลาด แสดงว่าไม่ใช่ int
        }
    }
    public String get_New_Password(){
        //Password ใหม่ที่ Random มา
        String new_pass = RANDOM.generateCustomPassword();
        return new_pass;
    }






}
