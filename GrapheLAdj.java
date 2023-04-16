package graphe;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GrapheLAdj extends Graphe {
	private Map<String, List<Arc>> ladj;
	
    public GrapheLAdj() {
        this.ladj = new HashMap<String, List<Arc>>();
    }

    public GrapheLAdj(String str) {
    	this();
    	peupler(str);
    }

    @Override
    public void ajouterSommet(String noeud) {
    	if (!contientSommet(noeud))
    	    ladj.put(noeud, new ArrayList<Arc>());
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
        ladj.get(source).add(new Arc(source, destination, valeur));
    }

    @Override
    public void oterSommet(String noeud) {
        if (contientSommet(noeud)) {
            for (String S : getSommets())
            	ladj.get(S).removeIf(arc -> arc.getDestination().equals(noeud));
            ladj.remove(noeud);
        }
    }
    
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException("n'existe pas");
        ladj.get(source).removeIf(arc -> arc.equals(source, destination));
    }

    @Override
    public List<String> getSommets() {
        return new ArrayList<String>(ladj.keySet());
    }
    
    @Override
    public List<String> getSucc(String sommet) {
    	List<String> succ = new ArrayList<String>();
    	if (contientSommet(sommet))
    	    for (Arc arc : ladj.get(sommet))
    		succ.add(arc.getDestination());
    	return succ;
    }
	
    @Override
    public int getValuation(String src, String dest) {
    	if (!contientSommet(src) || !contientSommet(dest))
            throw new IllegalArgumentException("n'existe pas");
    	for (Arc arc : ladj.get(src))
            if (arc.getDestination().equals(dest))
                return arc.getValuation();
        return -1;
    }
    
    @Override
    public boolean contientSommet(String sommet) {
    	return ladj.containsKey(sommet);
    }
    
    @Override
    public boolean contientArc(String src, String dest) {
        if (!contientSommet(src) || !contientSommet(dest))
            return false;
    	for (Arc arc : ladj.get(src))
            if (arc.getDestination().equals(dest))
                return true;
        return false;
    }
}
