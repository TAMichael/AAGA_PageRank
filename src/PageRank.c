#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <strings.h>

#define ALPHA 0.9
#define NBITE 20
#define ESTIMATION 10000000

typedef struct {
  long src;
  long tgt;
} edge;

typedef struct {
  long n;
  long nbEdges;
  edge *edgeList;
  long *degree;
} graph;

long max(long a,long b,long c){
  int m = a;
  (m < b) && (m = b); //these are not conditional statements.
  (m < c) && (m = c); //these are just boolean expressions.
  return m;   
}

graph* extractInfoFichier(char* fichier){
  graph *g=malloc(sizeof(graph));
  long esti;
  FILE *ficInput;
  long src;
  long tgt;
  long i;
  
  
  g->n=0;
  g->nbEdges=0;
  
  esti=ESTIMATION;
  g->edgeList=malloc(esti*sizeof(edge));
  
  ficInput=fopen(fichier,"r");
  
  if(ficInput == NULL){
    return NULL;
  }

  while (fscanf(ficInput,"%ld %ld\n", &src, &tgt)==2) {
    g->edgeList[g->nbEdges].src = src;
    g->edgeList[g->nbEdges].tgt = tgt;
    g->n=max(g->n,src,tgt);
    g->nbEdges++;
    
    if (esti <= g->nbEdges) {
      esti+=ESTIMATION;
      g->edgeList=realloc(g->edgeList, esti*sizeof(edge));
    }
  }
  g->n++;

  fclose(ficInput);
  
  //Pour éviter d'avoir de la mémoire non utilisée
  g->edgeList=realloc(g->edgeList, g->nbEdges*sizeof(edge)); 
  g->degree=malloc(g->n*sizeof(long));
  memset(g->degree, 0, sizeof(long)*g->n);

  for (i=0;i<g->nbEdges;i++){
    g->degree[g->edgeList[i].src]++;
  }
  return g;
}

void matVectProd(double *p1, double *p2, graph *g, long nbNodes){
  edge currEdge;
  long i;
  memset(p2,0.0, sizeof(double)*nbNodes);
  for (i=0; i<g->nbEdges; i++){
    currEdge = g->edgeList[i];
    if(g->degree[currEdge.src]!=0){
      p2[currEdge.tgt]+=p1[currEdge.src]/((double)(g->degree[currEdge.src]));
    }
  
  }

}

void normalisation(double* p2, long nbNodes){
  double s= 0.0;
  double toAdd=0.0;
  int i;

  for (i=0; i<nbNodes; i++){
    p2[i]= (p2[i]*(1.0-ALPHA)) + ALPHA/((double)nbNodes);
    s+=p2[i];
  }

  toAdd=(1.0-s)/((double)nbNodes);

  for (i=0; i<nbNodes; i++){
    p2[i]+=toAdd;
  }
}

double* powerIte(graph* g){
  long nbNodes = g->n;
  double *p1,*p2,*p3;
  double s;
  long i, k;
  
  p1=malloc(nbNodes*sizeof(double));
  p2=malloc(nbNodes*sizeof(double));
    
  for (i=0; i<nbNodes; i++){
    p1[i]=1.0/nbNodes;
  }


  for (k=0; k<NBITE; k++){

    matVectProd(p1, p2, g, nbNodes);
    normalisation(p2, nbNodes);

    p3=p1;
    p1=p2;
    p2=p3;

  }
  free(p2);
  return p1;
}

int main(int argc,char** argv){
  graph* g;
  double* res;
  FILE* ficRes;
  FILE* fileDIn;
  FILE* fileDOut;

  time_t t1,t2;
  long i;

  t1=time(NULL);

  g=extractInfoFichier(argv[1]);

  printf("N: %ld, nbEdges: %ld\n",g->n, g->nbEdges);

  res=powerIte(g);

  t2=time(NULL);

  printf("Temps = %lds\n",(t2-t1));

  printf("Debut stockage du résultat\n");
  ficRes =fopen(argv[2],"w");

  for (i=0; i<g->n; i++){
    //On considere que cela vaut 0 dès que les 7 premiers decimaux sont des 0
    if(res[i] > 0.00000009){ 
      fprintf(ficRes,"%ld %.8lf\n",i,res[i]);
    } 
  }

  /*
  fileDIn=fopen(argv[3],"w");
  for (i=0;i<g->n;i++){
    fprintf(fileDIn,"%ld %ld\n",i,g->degree[i]);
  }
  fclose(fileDIn);

  long *din=calloc(g->n,sizeof(long));
  long j;
  for (j=0;j<g->nbEdges;j++){
    din[g->edgeList[j].tgt]++;
  }
	
  fileDOut=fopen(argv[4],"w");
  for (j=0;j<g->n;j++){
    fprintf(fileDOut,"%ld %ld\n",j,din[j]);
  }
  fclose(fileDOut);
  free(din);*/

  free(g->edgeList);
  free(g->degree);
  free(g);
  fclose(ficRes);

  free(res);

  return 0;
}
