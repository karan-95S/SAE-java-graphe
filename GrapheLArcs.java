package graphe;

import java.util.List;
import java.util.ArrayList;

public class GrapheLArcs extends Graphe{
    private List<Arc> arcs;
    
    public GrapheLArcs() {
        arcs = new ArrayList<Arc>();
    }

    public GrapheLArcs(String str) {
    	this();
    	peupler(str);
    }

    @Override
    public void ajouterSommet(String noeud) {
    	if (!contientSommet(noeud))
    	    arcs.add(new Arc(noeud, "", 0));
    }
    
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0)
            throw new IllegalArgumentException("valuation negative");
        if (contientArc(source, destination))
            throw new IllegalArgumentException("L'arc est deja present");
        if (!contientSommet(source))
            ajouterSommet(source);
        if (!contientSommet(destination))
            ajouterSommet(destination);
        arcs.add(new Arc(source, destination, valeur));
    }

    @Override
    public void oterSommet(String noeud) {
        if (contientSommet(noeud))
        	arcs.removeIf(arc -> arc.getSource().equals(noeud) || arc.getDestination().equals(noeud));
    }
    
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException("n'existe pas");
        arcs.removeIf(arc -> arc.equals(source, destination));
    }

    @Override
    public List<String> getSommets() {
        List<String> S = new ArrayList<String>();
        for (Arc arc : arcs)
            if (arc.getDestination().equals(""))
                S.add(arc.getSource());
        return S;
    }
    
    @Override
    public List<String> getSucc(String sommet) {
    	List<String> succ = new ArrayList<String>();
    	if (contientSommet(sommet)) {
    	    for (Arc arc : arcs)
    		if (arc.getSource().equals(sommet))
    		    succ.add(arc.getDestination());
    	    succ.remove(succ.indexOf(""));
    	}
    	return succ;
    }
	
    @Override
    public int getValuation(String src, String dest) {
    	if (!contientSommet(src) || !contientSommet(dest))
            throw new IllegalArgumentException("n'existe pas");
        for (Arc arc : arcs)
            if (arc.equals(src, dest))
                return arc.getValuation();
        return -1;
    }
    
    @Override
    public boolean contientSommet(String sommet) {
    	return getSommets().contains(sommet);
    }
    
    @Override
    public boolean contientArc(String src, String dest) {
        if (!contientSommet(src) && !contientSommet(dest))
            return false;
    	for (Arc arc : arcs)
            if (arc.equals(src, dest))
                return true;
        return false;
    }
}
