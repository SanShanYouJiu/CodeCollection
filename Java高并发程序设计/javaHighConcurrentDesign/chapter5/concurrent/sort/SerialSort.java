package javaHighConcurrentDesign.chapter5.concurrent.sort;

/**
 */
public class SerialSort {

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void oddEventSort(int[] arr) {
        int exchFlag = 1, start = 0;
        while (exchFlag == 1 || start == 1) {
            exchFlag = 0;
            for (int i = start; i < arr.length - 1; i += 2) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    exchFlag = 1;
                }
            }
            if (start == 0) {
                start = 1;
            } else {
                start = 0;
            }

        }
    }

    public static void insertSort(int[] arr) {
        int length = arr.length;
        int j,i,key;
        for (i=1;i<length;i++) {
            key = arr[i];
            j=i-1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            //找到合适的位置 插入key
            arr[j + 1] = key;
        }
    }

    public static void shellSort(int[] arr) {
        //计算出最大的h值
        int h=1;
        while (h <= arr.length / 3) {
            h=h*3+1;
        }
        while (h > 0) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] < arr[i - h]) {
                    int tmp = arr[i];
                    int j=i-h;
                    while (j >= 0 && arr[j] > tmp) {
                        arr[j + h] = arr[j];
                        j-=h;
                        arr[j+h]=tmp;
                    }
                }
                //计算下一个h值
                h=(h-1)/3;
            }
        }
    }
}
