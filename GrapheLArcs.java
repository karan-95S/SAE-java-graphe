package graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrapheLArcs implements IGraphe {
    private List<Arc> arcs;
    private List<String> sommets;
    
    public GrapheLArcs() {
        arcs = new ArrayList<Arc>();
        sommets = new ArrayList<String>();
    }

    public GrapheLArcs(String str) {
    	this();
    	peupler(str);
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0) {
            throw new IllegalArgumentException("La valuation d'un arc doit être non négative");
        }
        if (contientArc(source, destination)) {
            throw new IllegalArgumentException("L'arc est déjà présent dans le graphe");
        }
        if (!contientSommet(source))
        	ajouterSommet(source);
        if (!contientSommet(destination))
        	ajouterSommet(destination);
        Arc arc = new Arc(source, destination, valeur);
        arcs.add(arc);
    }

    @Override
    public void ajouterSommet(String noeud) {
    	if (contientSommet(noeud)) {
    		return;
    	}
    	sommets.add(noeud);
    	Collections.sort(sommets);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Arc arc : arcs) {
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return sommets.contains(sommet);
    }

    @Override
    public void oterArc(String source, String destination) {
        for (Arc arc : arcs) {
        	if (arc.getSource() == source && arc.getDestination() == destination) {
        		arcs.remove(arc);
        		return;
        	}
        }
        throw new IllegalArgumentException("L'arc de " + source + " à " + destination + " n'est pas présent dans le graphe");
    }

    @Override
    public void oterSommet(String noeud) {
        List<Arc> toRemove = new ArrayList<>();
        for (Arc arc : arcs) {
            if (arc.getSource().equals(noeud) || arc.getDestination().equals(noeud)) {
                toRemove.add(arc);
            }
        }
        arcs.removeAll(toRemove);
    }

    @Override
    public int getValuation(String src, String dest) {
    	if (!contientSommet(src) || !contientSommet(dest))
        	throw new IllegalArgumentException("L'arc de " + src + " à " + dest + " n'est pas présent dans le graphe");
        for (Arc arc : arcs) {
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest)) {
                return arc.getValuation();
            }
        }
        return -1;
    }
    
	@Override
    public List<String> getSucc(String sommet) {
    	ArrayList<String> succ = new ArrayList<String>();
    	for (Arc arc : arcs) {
    		if (arc.getSource().equals(sommet))
            	succ.add(arc.getDestination());
        }
    	return succ;
    }
    
    @Override
    public List<String> getSommets() {
        ArrayList<String> S = new ArrayList<String>();
        for (String sommet : sommets) {
        	if (!S.contains(sommet)){
                S.add(sommet);
            }
        }
        return S;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	List<String> Succ;
        for (String s : sommets) {
        	Succ = getSucc(s);
        	Collections.sort(Succ);
        	if (Succ.size() == 0)
    			sb.append(!sb.isEmpty()? ", " + s + ":" : s + ":");
        	else
    			for (String succ : Succ)
        			sb.append(!sb.isEmpty()? ", " + s + "-" + succ + "(" + getValuation(s,succ) + ")": s + "-" + succ + "(" + getValuation(s,succ) + ")");
        }
        return sb.toString();
    }
}
