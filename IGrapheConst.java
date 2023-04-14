package graphe;


import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public interface IGrapheConst {
	List<String> getSommets(); // pas forcement triee
	List<String> getSucc(String sommet); // pas forcement triee
	int getValuation(String src, String dest); // les sommets doivent exister, -1 si pas d'arc
	boolean contientSommet(String sommet);
	boolean contientArc(String src, String dest);
	
	default String toAString() {
		List<String> descriptionsArcs = new ArrayList<String>();
		List<String> Sommets = new ArrayList<String>(getSommets());
		Collections.sort(Sommets);
        for (String S : Sommets) {
        	List<String> Succ = getSucc(S);
        	if (Succ.isEmpty())
        		descriptionsArcs.add(S + ":");
        	else {
        		Collections.sort(Succ);
    			for (String succ : Succ)
    				descriptionsArcs.add(S + "-" + succ + "(" + getValuation(S, succ) + ")");
    			}
        }
        return String.join(", ", descriptionsArcs);
	}
	
    /* ma méthode toString
    public String toString() {
    	StringBuilder sb = new StringBuilder();
        for (String S : getSommets()) {
        	List<String> Succ = getSucc(S);
        	if (Succ.isEmpty())
    			sb.append(!sb.isEmpty()? ", " + S + ":" : S + ":");
        	else
    			for (String succ : Succ)
        			sb.append(!sb.isEmpty()? ", " + S + "-" + succ + "(" + getValuation(S, succ) + ")": S + "-" + succ + "(" + getValuation(S, succ) + ")");
        }
        return sb.toString();
    }*/
	
	public static void dijkstra(IGrapheConst graphe, String source, Map<String, Integer> dist, Map<String, String> pred) {
		if (!graphe.contientSommet(source))
			throw new IllegalArgumentException("Le sommet source n'existe pas dans le graphe.");
		Map<String, Integer> nonVisites = new HashMap<String, Integer>();
		for (String S : graphe.getSommets()) {
            dist.put(S, Integer.MAX_VALUE);
            pred.put(S, null);
        }
		dist.put(source, 0);
		nonVisites.put(source, 0);
		String keyDistMin = source;
		while (!nonVisites.isEmpty()) {
			for (String succ : graphe.getSucc(keyDistMin)) {
				int distance = dist.get(keyDistMin) + graphe.getValuation(keyDistMin, succ);
				if (dist.get(succ) > distance) {
					dist.put(succ, distance);
					pred.put(succ, keyDistMin);
					nonVisites.put(succ, distance);
				}
			}
			String keyDistMinAvant = keyDistMin;
			nonVisites.remove(keyDistMin);
			for (String nonV : nonVisites.keySet())
				if (keyDistMin.equals(keyDistMinAvant) || dist.get(keyDistMin) > dist.get(nonV))
					keyDistMin = nonV;
		}
	}
}