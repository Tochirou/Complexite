package main;

import java.util.ArrayList;
import java.util.List;

import model.*;

public class Solution {

    private List<Rectangle> listRect;
    private List<Boite> listBoite;
    private int boiteActuelle;
    private int largeur;
    private int hauteur;

    public Solution(int boiteLargeur,int boiteHauteur) {
        listBoite = new ArrayList<Boite>();
        Boite boite = new Boite(boiteLargeur, boiteHauteur);
        listBoite.add(boite);
        boiteActuelle=0;
        largeur=boiteLargeur;
        hauteur=boiteHauteur;
        
    }

    public void setRectangles(List<Rectangle> l) {
        listRect=l;
    }

    public void placer(int x1,int y1) {
        while (!listRect.isEmpty()) {
            
            try {
                listBoite.get(boiteActuelle).addRect(listRect.get(0), x1, y1,listRect.get(0).getLargeur(),listRect.get(0).getHauteur());
                listRect.remove(0);
            } catch (Exception e) {
                if (x1+1+listRect.get(0).getLargeur()<=listBoite.get(boiteActuelle).getLargeur()) {
                    placer(x1+1,y1);
                } else if (y1+1+listRect.get(0).getHauteur()<=listBoite.get(boiteActuelle).getHauteur()) {
                    placer(x1,y1+1);
                } else {
                    Boite boite = new Boite(largeur, hauteur);
                    listBoite.add(boite);
                    boiteActuelle++;
                    placer(0, 0);
                }
            }
        }

    }

    public Boite getBoiteActu() {
        return listBoite.get(boiteActuelle);
    }
    
    public List<Boite> getListeBoite() {
        return listBoite;
    }

    public static void main(String[] args) {
        Solution sol = new Solution(3, 3);
        List<Rectangle> listRect = new ArrayList<Rectangle>();
        listRect.add(new Rectangle(1, 2));
        listRect.add(new Rectangle(2, 1));
        listRect.add(new Rectangle(2, 1));
        listRect.add(new Rectangle(2, 1));
        listRect.add(new Rectangle(2, 3));
        listRect.add(new Rectangle(1, 3));
        sol.setRectangles(listRect);
        sol.placer(0, 0);
        
        System.out.println("Solution : "+sol.getListeBoite().size()+" boite"+ (sol.getListeBoite().size()==1? "":"s"));
        for (int i=0; i<sol.getListeBoite().size();i++) {
            System.out.println(sol.getListeBoite().get(i).toString());
        }

    }

}
