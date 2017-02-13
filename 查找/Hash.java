
/**散列表
 * Created by han on 2017/2/13.
 */
public class Hash {
    static final int max = 100;
    static final int HASHSIZE = 12;
    static final int NULLKEY = -327768;

    int m=0;


    //初始化散列表
    boolean InitHashTable(HashTable H){
      int i;
      this.m=HASHSIZE;
      H.count=m;
      for (i=0;i<m;i++)
          H.elem[i]=NULLKEY;
        return true;
    }


    int Hash(int key) {
        return key % m;
    }

    void InsertHash(HashTable H,int key) {
        int addr = Hash(key);
        while (H.elem[addr]!=NULLKEY)
            addr=(addr+1)%m;
        H.elem[addr] = key;
    }


     Boolean SearchHash(HashTable H,int key) {
         int  addr = Hash(key);
         while (H.elem[addr]!=key){
             addr=(addr+1)%m;
             if (H.elem[addr] == NULLKEY || addr == Hash(key)) {
                 //如果循环回到原点
                 return false;
             }
         }
         return true;
     }

    class HashTable {
        int[] elem = new int[max];
        int count;
    }


    public static void main(String[] args) {
        Hash hash = new Hash();
        HashTable hashTable = new Hash().new HashTable();
        hash.InitHashTable(hashTable);
        for (int i=0;i<10;i++) {
            hash.InsertHash(hashTable, i);
        }
        Integer integer=1;
        System.out.println(hash.SearchHash(hashTable,3));
    }

}
