package camera_surveillance;


import ilog.concert.*;
import ilog.cplex.*;

public class CameraSurveillance {
	private static boolean mAdj[][];
	private int n ;
	
	
	
	
	public static void main(String[] args) {
		CameraSurveillance G = new CameraSurveillance(49);
		
		


		G.Arete(1, 2);
		G.Arete(1, 3);
		G.Arete(2, 41);
		G.Arete(2, 39);
		G.Arete(3, 11);
		G.Arete(4, 5);
		G.Arete(4, 6);
		G.Arete(4, 9);
		G.Arete(6, 7);
		G.Arete(6, 8);
		G.Arete(9, 10);
		G.Arete(11, 21);
		G.Arete(12, 13);
		G.Arete(12, 15);
		G.Arete(13, 14);
		G.Arete(14, 15);
		G.Arete(14, 18);
		G.Arete(15, 19);
		G.Arete(16, 20);
		G.Arete(17, 18);
		G.Arete(18, 19);
		G.Arete(19, 20);
		G.Arete(20, 21);
		G.Arete(21, 22);
		G.Arete(22, 25);
		G.Arete(22, 23);
		G.Arete(23, 32);
		G.Arete(24, 25);
		G.Arete(25, 26);
		G.Arete(25, 30);
		G.Arete(26, 27);
		G.Arete(26, 28);
		G.Arete(28, 29);
		G.Arete(30, 31);
		G.Arete(31, 32);
		G.Arete(31, 33);
		G.Arete(32, 38);
		G.Arete(33, 34);
		G.Arete(33, 37);
		G.Arete(34, 35);
		G.Arete(35, 36);
		G.Arete(37, 43);
		G.Arete(37, 38);
		G.Arete(38, 40);
		G.Arete(39, 40);
		G.Arete(40, 41);
		G.Arete(41, 42);
		G.Arete(43, 44);
		G.Arete(44, 49);
		G.Arete(44, 45);
		G.Arete(45, 47);
		G.Arete(47, 48);
		G.Arete(46, 46);
	
		calcul(G);
		
	}
	
	public CameraSurveillance(int noeud) {
		this.n  = noeud;
		mAdj = new boolean[noeud][noeud];
	}
	public static void Arete(int i, int j) {
		// TODO Auto-generated method stub
		mAdj[i-1][j-1] = true;
	}

	public static boolean adjacent(int i, int j) {
		return mAdj[i][j];
	}

	public static void calcul (CameraSurveillance adj) {
		try {
			int n = 49;
			IloCplex simplexe = new IloCplex ();
			
			IloNumVar[] X = new IloNumVar[n];
			
			for (int i=0;i<n;i++){
				X[i]= simplexe.boolVar();
			}
			
			IloLinearNumExpr obj = simplexe.linearNumExpr();
		
			for (int i=0;i<n;i++){
				obj.addTerm(1, X[i]);
			}
			
			simplexe.addMinimize(obj);
			
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					if(adj.adjacent(i, j)) {
						IloLinearNumExpr c = simplexe.linearNumExpr();
						c.addTerm(1, X[i]);
						c.addTerm(1, X[j]);
						simplexe.addGe(c, 1);						
					}
				}
			}
			
			simplexe.solve(); 
			
			
			System.out.println("\n le nombre minimale des cameras qu'on va placé est: "+ simplexe.getObjValue() + "\n");
			System.out.println("les " + simplexe.getObjValue() + " emplacements doivent etre surveillé sont: ") ;
			
			for (int i=0;i<n;i++) {
				if(simplexe.getValue(X[i]) != 0.0) {
					int j = i + 1;
					System.out.println( "Emplacement "+j);
				}				
			}
		} catch (IloException e){
			System.out.print("Exception levée " + e);
		}
	}
}
