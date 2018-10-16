#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <strings.h>

#define ALPHA 0.15
#define NBITE 30
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
  g->nbEdges++;

  fclose(ficInput);
  
  g->edgeList=realloc(g->edgeList, g->nbEdges*sizeof(edge)); //Pour éviter d'avoir de la mémoire non utilisée
  g->degree=malloc(g->n*sizeof(long));
  memset(g->degree, 0, sizeof(long)*g->n);

  for (i=0;i<g->nbEdges;i++){
    g->degree[g->edgeList[i].src]++;
  }
  return g;
}

double* powerIte(graph* g){
  long nbNodes = g->n;
  double *p1,*p2,*p3;
  double s;
  long i, k;
  edge currEdge;
  double normalisationToAdd;
  
  p1=malloc(nbNodes*sizeof(double));
  p2=malloc(nbNodes*sizeof(double));
  
  for (i=0; i<nbNodes; i++){
    p1[i]=1.0/nbNodes;
  }
  

  for (k=0; k<NBITE; k++){
    
    memset(p2,0.0, sizeof(double)*nbNodes);
    for (i=0; i<g->nbEdges; i++){
      currEdge = g->edgeList[i];

      if(g->degree[currEdge.src]!=0){
	p2[currEdge.tgt]+=p1[currEdge.src]/((double)(g->degree[currEdge.src]));
      }
    }
    
    s=0.;
    for (i=0; i<nbNodes; i++){
      p2[i]= (p2[i]*(1.0-ALPHA)) + ALPHA/((double)nbNodes);
      s+=p2[i];
    }
    
    normalisationToAdd=(1.0-s)/((double)nbNodes);
    
    for (i=0; i<nbNodes; i++){
      p2[i]+=normalisationToAdd;
    }
    
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
  time_t t1,t2;
  long i;

  t1=time(NULL);

  g=extractInfoFichier(argv[1]);

  printf("N: %ld, nbEdges: %ld\n",g->n, g->nbEdges);

  res=powerIte(g);

  ficRes =fopen(argv[2],"w");
  
  for (i=0; i<g->n; i++){
    fprintf(ficRes,"%ld %lf\n",i,res[i]);
  }

  fclose(ficRes);

  /* file=fopen("outdegrees.txt","w"); */
  /* unsigned long i; */
  /* for (i=0;i<g->n;i++){ */
  /*   fprintf(file,"%lu %lu\n",i,g->dout[i]); */
  /* } */
  /* fclose(file); */

  /* //prints the indegrees in text file named indegrees.txt */
  /* unsigned long *din=calloc(g->n,sizeof(unsigned long)); */
  /* unsigned long j; */
  /* for (j=0;j<g->e;j++){ */
  /*   din[g->el[j].t]++; */
  /* } */
  /* file=fopen("indegrees.txt","w"); */
  /* for (j=0;j<g->n;j++){ */
  /*   fprintf(file,"%lu %lu\n",j,din[j]); */
  /* } */
  /* fclose(file); */
  /* free(din); */


  free(g->edgeList);
  free(g->degree);
  free(g);


  t2=time(NULL);

  free(res);

  printf("- Overall time = %ldh%ldm%lds\n",(t2-t1)/3600,((t2-t1)%3600)/60,((t2-t1)%60));

  return 0;
}
