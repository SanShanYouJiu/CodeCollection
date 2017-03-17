package DesignMode.PrincipleDesign.OCP.Test;

import  DesignMode.PrincipleDesign.OCP.IBook;
import   DesignMode.PrincipleDesign.OCP.NovelBook;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by han on 2017/1/18.
 */
public class NovelBookTest extends TestCase {

    private String name = "平凡的世界";
    private int price=6000;
    private String author = "路遥";
    private IBook novelBook = new NovelBook(name, price, author);


    @Test
    public void testGetPrice(){
        super.assertEquals(this.price,this.novelBook.getPrice());
    }
}
