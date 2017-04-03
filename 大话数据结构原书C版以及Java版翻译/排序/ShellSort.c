#include<stdio.h>
#define MAXSIZE 10 //用于要排序数组个数最大值 可根据需要修改
#define OK 1
#define OVERFLOW 0
#define ERROR 0
#define TRUE 1
#define FALSE 0
typedef int Status;
// 对顺序表Ｌ作冒泡处理
typedef struct {
	int r[MAXSIZE +1];//用于存储要排序数组 r[0]用于哨兵或临时变量
	int length;//用于记录顺序表的长度
} SqList;

//交换L中数组r的下标为i和j的值
void swap(SqList *L,int i,int j) {
	int temp=L->r[i];
	L->r[i]=L->r[j];
	L->r[j]=temp;
}

//对顺序表L作希尔排序
void ShellSort(SqList *L) {
	int i,j;
	int increment=L->length;
	do {
		increment=increment/3+1;//增量序列
		for(i=increment+1; i<=L->length; i++) {
			if(L->r[i]<L->r[i-increment]) {
				//需要L->r[i]插入有序增量子表
				L->r[0]=L->r[i];//暂存在L->r[0]
				for(j=i-increment; j>0&&L->r[0]<L->r[j]; j-=increment;)
					L->r[j+increment]=L->r[j];//记录后移 查找插入记录
				L->r[j+increment]=L->r[0];//插入
			}
		}

	} while(increment>1);
}
