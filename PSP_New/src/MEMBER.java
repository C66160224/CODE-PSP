public class MEMBER {
    String id;
    String first_name ;
    String last_name;
    String email ;
    String password ;
    String phone ;
    int point ;


    public MEMBER(String id, String first_name, String last_name, String email, String password, String phone, int point) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.point = point;
    }

    public String check_Role(){
        //เช็ค Role
        String role = "";

        if (password.charAt(6) == '0'){
            role = "Staff";

        }if (password.charAt(6) == '1'){
            role = "Regular";

        }if (password.charAt(6) == '2'){
            role = "Silver";

        }if (password.charAt(6) == '3') {
            role = "Gold";

        }
        return role ;

    }

    public String CensorEmail (){
        //Censor อีเมล์ที่ login เข้ามา

        String part[] ;
        String new_Email = "";
        part = email.split("@");

        new_Email += part[0].substring(0 , 2);
        new_Email += "***@" ;
        new_Email += part[1].substring(0,2);
        new_Email += "***";

        return  new_Email ;

    }
    public String new_Pass(){
        //Password ที่มีแค่ตัวเลข

        String new_Password = "";

        for (int i = 0; i < getPassword().length(); i++) {
            if (i==9||i==10||i==13||i==14||i==15||i==16){
                new_Password += getPassword().charAt(i);
            }

        }
        return new_Password;
    }

    public String FullPhone(){
        //Phone ที่เติม -
        return phone.substring(0,3)+"-"+phone.substring(3,6)+"-"+phone.substring(6,10);

    }

    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public int getPoint() {
        return point;
    }


}
