import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Cocinelle {
    static List<String> chemin= new ArrayList<>();

    static int maxi(int[] List){
        int max=List[0];
        for(int i : List){
            if(i>max){
                max=i;
            }
        }
        return max;
    }

    static int valCase(int pL, int pC, int[][] M){
        int L= M.length, C=M[0].length;
        if (pL>L-1|| pL<0) return 0;
        if(pC>C-1 || pC<0) return 0;
        else return M[pL][pC];
    }

    static int trouverIndice(int[] M, int val){
        for(int i=0; i<M.length; i++){
            if(M[i]==val){
                return i;
            }
        }
        return -1;
    }

    static int[][] calculerM(int[][] M){
        int L= M.length, C=M[0].length;
        int mNE=0, mN=0, mNO=0;
        int[][] mCalc= new int[M.length][M[0].length];
        for(int pL=1; pL<L; pL++){
            for(int pC=0; pC<C; pC++){
                mN= M[pL][pC] + valCase(pL-1, pC, M);
                mNE= M[pL][pC] + valCase(pL-1, pC-1,M);
                mNO= M[pL][pC] + valCase(pL-1, pC+1,M);
                //calcul max
                M[pL][pC]= Math.max(mN, Math.max(mNE, mNO));
            }
        }
        return M;
    }


    static void trouverChemin(int[][] M){
        int valMax= maxi(M[M.length-1]);
        int indiceFin= trouverIndice(M[M.length-1], valMax);
        trouverChemin(M, M.length-1, indiceFin, chemin);
    }

    static void trouverChemin(int[][] M, int ligne, int colonne, List<String> chemin){
        int mN,mNE, mNO;
        int valMax, indiceChemin;

        //Cas de base (Si il n'y a plus de valeurs sous la case)
        if(valCase(ligne, colonne,M)==0) return;
        if(valCase(ligne-1, colonne, M)==0){
            String pos= "("+ligne+","+colonne+")";
            System.out.print(pos+" ");
            chemin.add(pos);
            return;
        };

        //Calcul max
        mN=  valCase(ligne-1, colonne, M);
        mNE= valCase(ligne-1, colonne-1,M);
        mNO= valCase(ligne-1, colonne+1,M);

        valMax= Math.max(mN, Math.max(mNE, mNO));
        indiceChemin= trouverIndice(M[ligne-1], valMax);


        //Recursion
        trouverChemin(M, ligne-1, indiceChemin, chemin);
        String pos= "("+ligne+","+colonne+")";
        System.out.print(pos+" ");
        chemin.add(pos);

        return;
    }

    static void listA(List<String> a){
        for(String aa : a){
            System.out.print(aa);
        }
    }


    static void printM(int[][] M){
        int L= M.length, C=M[0].length;
        for(int l=L-1; l>=0;l--){
            for(int c=0; c<C; c++){
                System.out.print(M[l][c]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void afficherM(int[][][] Ms){
        int[][] M= Ms[0];
        int[][] m= Ms[1];


        int L= m.length, C=m[0].length;
        int valMax= maxi(m[L-1]);


        //Affichage de la grille des pucerons
        System.out.println("Grille des pucerons: ");
        printM(M);

        //Affichage tableau du nombre max de pucerons par case
        System.out.println("Tableau M[L][C] de terme général M[l][c] = m(l,c) :");
        printM(m);

        System.out.print("\nLa coccinelle a mangé "+ valMax +" pucerons !\nElle a suivi le chemin suivant : ");
        trouverChemin(m);


        System.out.println(
                "\nCase d'atterisage = "+chemin.get(0)+
                        "\nCase d'interview = "+chemin.get(M.length-1)
        );


    }

    static int[][] copyOfM(int[][] M){
        int[][] mFin= new int[M.length][M[0].length];
        for(int i=0; i<M.length; i++){
            for(int j=0; j<M[0].length; j++){
                mFin[i][j]= M[i][j];
            }
        }
        return mFin;
    }

    public static void main(String[] args){

        int M[][]={
                {2,4,3,9,6},
                {1,10,15,1,2},
                {2,4,11,26,66},
                {36,34,1,13,30},
                {46,2,8,7,15},
                {89,27,10,12,3},
                {1,72,3,6,6},
                {3,1,2,4,5},
        };


        int Mp[][]= copyOfM(M);

        //M et son calcul sont gardés dans Ms, une liste a 3 dimensions
        int[][][] Ms= new int[2][M.length][M[0].length];
        Ms[0]= Mp;
        Ms[1]=calculerM(M);

        afficherM(Ms);

    }


}
