public class Cart {
    String member_ID ;
    String product_ID ;
    int product_Quantity ;

    public Cart(String member_ID , String product_ID , int product_Quantity  ){
        this.member_ID = member_ID ;
        this.product_ID = product_ID ;
        this.product_Quantity = product_Quantity ;

    }

    public String getMember_ID() {
        return member_ID;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public int getProduct_Quantity() {
        return product_Quantity;
    }

    public void setMember_ID(String member_ID) {
        this.member_ID = member_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public void setProduct_Quantity(int product_Quantity) {
        this.product_Quantity = product_Quantity;
    }
}
