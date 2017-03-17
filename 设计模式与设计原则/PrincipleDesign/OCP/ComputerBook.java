package DesignMode.PrincipleDesign.OCP;

/**
 * Created by han on 2017/1/18.
 */
public class ComputerBook implements IComputerBook {
    private String name;

    private String scope;

    private String author;

    private  int price;

    public ComputerBook(String name,int price, String author, String scope) {
        this.name = name;
        this.scope = scope;
        this.author = author;
        this.price = price;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }
}
