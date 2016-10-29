#include<stdio.h>/*线性表-顺序存储*/
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAXSIZE 20 /*存储空间初始化分配量*/
typedef int ElemType;/*ElemType根据实际情况而定 这里假设为int*/

typedef struct{
    ElemType data[MAXSIZE];  /*数据存储数据元素 最大值是MAXSIZE*/
	int length;/*线性表当前长度*/
}SqList;

typedef int Status;

/* Status是函数的基本类型 其值是函数状态代码 如OK等*/
/*初始条件: 顺序线性表L已存在 i<=i<=ListLength(L)*/
/*操作结果:用e返回L中第i个数据元素的值*/
/*获得元素操作*/
Status GetElem(SqList L,int i,ElemType *e)
{
       if(L.length==0 || i<1 || i>L.length)
		   return ERROR;
	   *e=L.data[i-1];
	   return OK;
}


 /*插入*/
/*初始条件:顺序线性表L已存在,1<=i<=ListLength(L)*/
/*操作结果:在L中第i个位置之前插入新的数据类型e,L的长度加1*/
Status ListInsert (SqList *L,int i,ElemType e){

int k;
if(L->length==MAXSIZE)/*顺序线性表已满*/
 return ERROR;

if(i<1 || i>L->length+1)/*当i不在范围内时*/
return ERROR;

if(i<=L->length)
{
	for(k=L->length-1;k>=i-1;k--){/*将要插入位置后数据元素向后移动一位*/
	 L->data[k+1]=L->data[k];
	}

}

L->data[i-1]=e; /*将新元素插入*/
L->length++;
return OK;
}


/*初始条件:顺序线性表L已存在 1<=i<=ListLength(L)*/
/*操作结果:删除的第i个数据元素,并用e返回其值 L的长度减1*/
Status ListDelete(SqList *L,int i,ElemType *e)
{
 int k;
 if(L->length==0) /*线性表为空*/
	 return ERROR;

 if(i<1 || i>L->length) /*删除位置不正确*/
	 return ERROR;
    *e=L->data[i-1];


 if(i<L->length) /* 如果删除不是最后位置*/

 {
 for(k=i;k<L->length;k++)/*将删除位置后继元素前移*/
	 L->data[k-1]=L->data[i];

}
 L->length--;
 return OK;

}



void  main()
{
	ElemType e=1;

	SqList L;



}
