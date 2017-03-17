#include <stdio.h>/*顺序栈与俩栈共用*/
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAXSIZE 20
typedef int SElemType;
typedef int Status;

 typedef struct
 {
 SElemType data[MAXSIZE];
 int top;/*用于栈顶指针*/
 }SqStack;


 /*插入元素e为新的栈顶元素*/
 Status Push(SqStack *S,SElemType e)
 {
 if(S->top == MAXSIZE -1)/*栈满*/
  {
    return ERROR;
  }
  S->top++; /*栈顶指针增加1*/
  S->data[S->top]=e;/*将新插入元素复制给栈顶空间*/
  return OK;
}

/*若栈不空 则删除S的栈顶元素 用e返回其值 并返回OK 否则返回ERROR*/
Status Pop(SqStack *S,SElemType *e)
{

   if(S->top==-1)return ERROR;/*栈空*/
   *e=S->data[S->top];/*将要删除的栈顶元素赋值给e*/
   S->top--;/*栈顶指针减一*/
   return OK;
 }
 /*两栈共享空间结构 俩个栈共用一个数组 使用这种数据结构一般是俩个栈的空间
需求有相反关系时  就是一个栈增长时另一个栈在减少的情况
 */
  typedef struct
 {
  SElemType data[MAXSIZE];
  int top1;/*栈1栈顶指针*/
  int top2;/*栈2栈顶指针*/
  }SqDoubleStack;

  /*插入元素E为新的栈顶元素*/
  Status Push2(SqDoubleStack *S,SElemType e,int StackNumber)
  {
   if(S->top1+1 == S->top2) /*栈已满 不能再push新元素*/
   return ERROR;
   if(StackNumber ==1)/*栈1有元素进栈*/
   S->data[++S->top1]=e;/*若栈1则先top1+1后给数组元素赋值*/
   else if(StackNumber ==2)/*栈2有元素进栈*/
   S->data[--S->top2]=e;/*若栈2则先top2-1后给数组元素赋值*/
              return OK;
}
  /*若栈不空，则删除S的栈顶元素，用E返回其值，并返回ok，否则返回ERROR*/
 Status Pop2(SqDoubleStack *S,SElemType *e,int stackNumber)
 {
 	 if(stackNumber ==1){
 	 	if(S->top1 == -1)
 	 	return ERROR;/*说明栈1已经是空栈 溢出*/
 	 	*e=S->data[S->top1 --];/*将栈1的栈顶元素出栈*/

	  }
	  else if(stackNumber ==2)
	  {
	  	if(S->top2==MAXSIZE)
	  	return ERROR;/*说明栈2已经是空栈了  溢出*/
	  	*e=S->data[S->top2++];/*将栈2的栈顶元素出栈*/
	  }
	  return OK;
  }
