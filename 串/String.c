#include<stdio.h>/*串*/

/*T为非空串 若主串S中的第pos个字符之后存在与T相等的子串*/
/*则返回第一个这样的子串在S中的位置 否则返回0*/
int Index(String S,String T,int pos)
{
  int  n,m,i;
  String sub;
  if(pos >0)
  {
  	n=StrLength(S);/*得到主串S的长度*/
  	m=StrLength(T);/*得到子串T的长度*/
  	i=pos;
  	while(i <= n-m+1){
  		SubString(sub,S,i,m);/*取主串第i个位置*/
  		                       /*长度与T相等子串给sub*/
  		if(StrCompare(sub,T)!=0)/*如果俩串不相等*/
  	        	++i;
  		else                      /*如果俩串相等*/
  	     	return i;                /*则返回i值*/
	  }
   }
   return 0;/*若无子串与T相等 返回0*/
}





/*返回子串T在主串S中的第pos个字符之后的位置 若不存在 则函数返回值0*/
/*T非空 1<=pos<=Strlength()*/
int Index2(String S,String T,int pos)
{
	int i=pos;/*i用于主串S中当前位置下标，若pos不为1*/
	             /*则从pos位置开始匹配*/
	int j=1; /*j用于子串T中当前位置下标值*/
	while(i<=S[0] && j<=T[0] )/*若i小于S长度且j小于T的长度时循环*/
	{
		if(S[i]==T[j]) /*俩字母相等则继续*/
		{
			++i;
			++j;
		}
		else /*指针后退重新开始匹配*/
		{
		i=i-j+2; /*i返回到上次匹配首位的下一位*/
		j=1; /*j返回到子串T的首位*/
	 	}
	 }
	 if(j>T[0])
	 return i-T[0];
	 else
	 return 0;
 }



 void get_next(String T,int *next)
 {
 	int i,j;
 	i=1;
 	j=0;
 	next[1]=0;
 	while(i<T[0]){
 		 if( j==0 || T[i] ==T[j])/*T[i]表示后缀的单个字符 T[j]表示前缀的单个字符*/
 		 {
 		 	++i;
 		 	++j;
 		 	next[i]=j;
		  }else
		  j=next[j];/*若字符不相同 则j值回溯*/
	 }
  }


/*KMP方式
返回子串T在主串S中第pos个字符之后的位置 若不存在 则函数返回值为0*/
/*T 非空*/
 void  index3(String S,String T,int pos){
 int i=pos; /*i用于主串S当前位置下标值 若pos不为1*/
          /*则从pos位置开始匹配*/
 int j=1; /*j用于子串T中当前位置下标值*/
 int next[255];/*定义next数组*/
 get_next(T,next);/*对串T作分析 得到next数组*/
 while(i<=S[0] && j<=T[0]){/*若i小于s的长度且j小于T的长度时*/
                        /*循环继续*/
 	if(j==0 ||S[i] ==T[j]){/*俩字母相等则继续 相对于朴素算法增加了*/
	                        /*j=0判断*/
 		++i;
 		++j;
	 }
	 else /*指针后退重新开始匹配*/
	 {
	 	j=next[j];/*j退后合适的位置 i值不变*/
	 }
 }
   if(j>T[0])
   return i-T[0];
   else
   return 0;
 }

 
