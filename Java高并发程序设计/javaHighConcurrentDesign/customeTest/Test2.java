package javaHighConcurrentDesign.customeTest;

/**
 * Created by www85 on 2017/7/27.
 */
public class Test2 {

    static class A{
        static {
            System.out.println("1");
        }
        public  A(){
            System.out.println("2");
        }

    }

    static class B extends A{
         static {
              System.out.println("a");
         }
         public B(){
             System.out.println("b");
         }
    }

    public static void main(String[] args) {
        A ab = new A();
        ab =new B();

    }
}
