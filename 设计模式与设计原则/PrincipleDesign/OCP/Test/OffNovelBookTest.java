package DesignMode.PrincipleDesign.OCP.Test;

import DesignMode.PrincipleDesign.OCP.IBook;
import DesignMode.PrincipleDesign.OCP.OffNoveLBook;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by han on 2017/1/18.
 */
public class OffNovelBookTest extends TestCase {

    private IBook below40NovelBook = new OffNoveLBook("平凡的世界", 3000, "路遥");
    private IBook above40NoveBook = new OffNoveLBook("平凡的世界", 6000, "路遥");

    @Test
    public void testGetPriceBelow40(){
    super.assertEquals(2400,below40NovelBook.getPrice());
    super.assertEquals(5400,above40NoveBook.getPrice());
    }
}
