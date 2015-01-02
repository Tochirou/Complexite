package model;


public class Boite {

    private int largeur;
    private int hauteur;
    private String[][] position;
    private int compteur = 1;

    public Boite(int largeur, int hauteur) {
        this.largeur=largeur;
        this.hauteur=hauteur;
        position = new String[largeur][hauteur];
    }

    public boolean pleine() {



        return false;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public String[][] getPositions() {
        return position;
    }

    public String[] getLigne(int y) {
        return position[y];
    }

    public String[] getColonne(int x) {
        String[] colonne = new String[hauteur];
        for (int i = 0; i<hauteur; i++) {
            colonne[i]=position[x][i];
        }
        return colonne;
    }

    public String toString() {
        String s = "";
        for (int i =0; i<largeur; i++) {
            s+="|";
            for (int j=0; j<hauteur; j++) {
                if (position[i][j]==null) s+=" . ";
                else s+=" "+position[i][j]+" ";
            }
            s+="|\n";
        }
        return s;
    }

    public void addRect(Rectangle rect,int x1,int y1,int largeur,int hauteur) throws Exception {
        
        verifier(x1, y1, largeur, hauteur);

        for (int i=x1; i<=x1+largeur-1; i++) {
            for (int j=y1; j<=y1+hauteur-1;j++) {
                position[i][j]=""+compteur;
            }
        }
        compteur++;

    }

    public void verifier(int x1, int y1, int largeur,int hauteur) throws Exception{

        for (int i=x1;i<=x1+largeur-1;i++) {
            for (int j=y1;j<=y1+hauteur-1;j++) {
                if (position[i][j]!=null) throw new Exception();
            }
        }
    }
}
