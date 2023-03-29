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
    	assert this.getSommets().isEmpty();
	    String[] arcs = str.split(",\\s*");
	    for (String arc : arcs) {
	        String[] elements = arc.trim().split("-");
	        // extrait le noeud source et ote ":" si nécessaire dans le nom
	        String src = elements[0].replaceAll(":", "");
	        ajouterSommet(src);
	        if (elements.length == 1)
	        	continue; // pas de destination
	        String target = elements[1];
	        if (!target.isEmpty()) {
	             String dest = target.substring(0, target.indexOf('('));
	             int val = Integer.parseInt(target
	                	.substring(target.indexOf('(') + 1,
	                				   target.indexOf(')')));
	             ajouterSommet(dest);
	             ajouterArc(src, dest, val);
	        }
	    }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Les noeuds source et destination doivent être non nuls");
        }
        if (valeur < 0) {
            throw new IllegalArgumentException("La valuation d'un arc doit être non négative");
        }
        if (contientArc(source, destination)) {
            throw new IllegalArgumentException("L'arc est déjà présent dans le graphe");
        }
        ajouterSommet(source);
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
        // Ne fait rien si le sommet est déjà présent
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
        for (String S : sommets) {
            if (S.equals(sommet)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void oterArc(String source, String destination) {
        for (Arc arc : arcs) {
        	if (arc.getSource() == source && arc.getDestination() == destination)
        		arcs.remove(arc);
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
        for (Arc arc : arcs) {
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest)) {
                return arc.getValuation();
            }
        }
        throw new IllegalArgumentException("L'arc de " + src + " à " + dest + " n'est pas présent dans le graphe");
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
        Collections.sort(S);
        return S;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	List<String> Succ;
    	int nbSucc;
        for (String s : sommets) {
        	nbSucc = 0;
        	Succ = getSucc(s);
        	Collections.sort(Succ);
        	if (Succ.size() != 0) {
        		for (int i = 0; nbSucc < Succ.size(); i++) {
            		if (arcs.get(i).getSource().equals(s) && arcs.get(i).getDestination().equals(Succ.get(nbSucc))) {
            			nbSucc++;
            			if (!sb.isEmpty()) {
            				sb.append(", ");
            			}
            			sb.append(arcs.get(i).toString());
            		}
            		if (i == arcs.size() - 1) {
            			i = 0;
            		}
            	}
        	}
        	else {
    			if (!sb.isEmpty()) {
    				sb.append(", ");
    			}
    			sb.append(s + ":");
    		}
        }
        return sb.toString();
    }
}
