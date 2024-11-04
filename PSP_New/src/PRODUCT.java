public class PRODUCT {
    String number;
    String name ;
    Double price ;
    int qua ;
    String type ;

    public PRODUCT(String number , String name , Double price ,int qua , String type){
        this.number = number;
        this.name = name;
        this.price = price;
        this.qua = qua;
        this.type = type;

    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public int getQua() {
        return qua;
    }

    public String getType() {
        return type;
    }


}
