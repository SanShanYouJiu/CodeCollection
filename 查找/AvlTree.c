#include <stdio.h>//AVL树
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define  MAX 100
#define LH +1 //左高
#define EH 0//等高
#define RH -1 //右高
typedef int QElemType;
typedef int Status;
typedef  int  Boolean;/*Boolean是布尔类型 其值是True或False*/
Boolean visited[MAX]; /*访问标识的数组*/

/*二叉树的二叉链表结点结构定义*/
typedef struct BiTNode { /*结点结构*/
	int data; /*结点数据*/
	int bf; /*结点的平衡数据*/
	struct BiTNode *lchild,*rchild; /*左右孩子指针*/
} BiTNode,*BiTree;


/*对以p为根的二叉排序树作右旋处理*/
/*处理之后p指向新的树根结点 即旋转处理之前的左子树的根节点*/
void R_Rotate(BiTree *P) {
	BiTree L;
	L=(*P)->lchild; //L指向P的左子树节点
	(*P)->lchild=L->rchild;//L的右子树挂接为P的左子树
	L->rchild=(*P);
	*P=L; //P指向新的根节点
}

/*对以P为根的二叉排序树作左旋处理*/
/*处理之后P指向新的树根结点 即旋转处理之前的右子树的根节点0*/
void L_Rotate(BiTree *P) {
	BiTree R;
	R=(*P)->rchild;//R指向P的右子树根节点
	(*P)->rchild=R->lchild;//R的左子树挂接为P的右子树
	R->lchild=(*P);
	*P=R;//P指向新的根节点
}

//对以指针T所指结点为根的二叉树作为左平衡旋转处理
//本算法结束时 指针T指向新的根节点
void LeftBalance(BiTree *T) {
	BiTree L,Lr;
	L=(*T)->lchild;//L指向T的左子树节点
	switch(L->bf) {
			//检查T的左子树的平衡度 并作相关平衡处理
		case LH://新节点插入在T的左孩子的左子树上 要作单右旋处理
			(*T)->bf=L->bf=EH;
			R_Rotate(T);
			break;
		case RH://新节点插入在T的左孩子的右子树上 要做双螺旋处理
			Lr=L->rchild;//Lr指向T的左孩子的右子树根
			switch(Lr->bf) { //修改T及其左孩子的平衡因子
				case LH:
					(*T)->bf=RH;
					L->bf=EH;
					break;
				case EH:
					(*T)->bf=L->bf=EH;
					break;
				case RH:
					(*T)->bf=EH;
					L->bf=LH;
					break;
			}
			Lr->bf=EH;
			L_Rotate(&(*T)->lchild);//对T的左子树作左平衡处理
			R_Rotate(T);//对T作右旋平衡处理

	}
}

//右平衡旋转处理
void RightBalance(BiTree *T) {
	BiTree R,RL;
	R=(*T)->rchild;
	switch(R->bf) {
		case RH:
			(*T)->bf=R->bf=EH;
			L_Rotate(T);
			break;
		case LH:
			RL=R->lchild;
			switch(RL->bf) {
				case LH:
					(*T)->bf=LH;
					RL->bf=EH;
					break;
				case EH:
					(*T)->bf=RL->bf=EH;
					break;
				case RH:
					(*T)->bf=EH;
					RL->bf=RH;
					break;
			}
			RL->bf=EH;
			R_Rotate(&(*T)->rchild);
			L_Rotate(T);
	}
}

//若在平衡的二叉排序树T中不存在和e有相同关键字的结点 则插入一个
//数据元素为e的新节点并返回1 否则返回0 若因插入而使二叉排序树
//失去平衡 则作平衡旋转处理 布尔变量taller反映T长高与否
Status InsertAVL(BiTree *T,int e,Status *taller) {
	if(!*T) {
		//插入新节点 树"长高",置taller为TRUE
		(*T)=(BiTree)malloc(sizeof(BiTNode));
		(*T)->data=e;
		(*T)->lchild=(*T)->rchild=NULL;
		(*T)->bf=EH;
		*taller=TRUE;
	} else {
		if(e==(*T)->data) {
			//树中已存在e有相同关键字的结点不再插入
			*taller=FALSE;
			return FALSE;
		}
		if(e<(*T)->data) {
			//应继续在T的左子树中进行搜索
			if(!InsertAVL(&(*T)->lchild,e,taller)) //未插入
				return FALSE;
			if(*taller) { //已插入到T的左子树中且左子树"长高"
				switch((*T)->bf) { //检测T的平衡树
					case LH://原本左子树比右子树高 需要作左平衡处理
						LeftBalance(T);
						*taller=FALSE;
						break;
					case EH://原本左右子树等高 现因左子树增高而树增高
						(*T)->bf=LH;
						*taller=TRUE;
						break;
					case RH://原本右子树比左子树高 现左右子树等高
						(*T)->bf=EH;
						*taller=FALSE;
						break;
				}
			}
		} else {
			//应继续在T的右子树中进行搜索
			if(!InsertABL(&(*T)->rchild,e,taller))//未插入
				return FALSE;
			if(*taller) { //已插入到T的右子树且右子树“长高"
				switch((*T)->bf) { //检测T的平衡度
					case LH://原本左子树比右子树高 现在 右子树等高
						(*T)->bf=EH;
						*taller=FALSE;
						break;
					case EH: //原本左右子树等高 现因右子树增高而树增高
						(*T)->bf=RH;
						*taller=TRUE;
						break;
					case RH://原本右子树比左子树高  需要作右平衡处理
						RightBalance(T);
						*taller=FALSE;
						break;
				}
			}

		}
	}
	return TRUE;
}


int main() {
	int i;
	int a[10]= {3,2,1,4,5,6,7,10,9,8};
	BiTree T=NULL;
	Status taller;
	for(i=0; i<10; i++) {
		InsertAVL(&T,a[i],&taller);
	}
}
