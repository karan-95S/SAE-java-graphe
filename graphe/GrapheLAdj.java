package graphe;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
//import java.util.stream.Collectors;
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
		ladj.putIfAbsent(noeud, new ArrayList<Arc>());
	}

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		if (valeur < 0)
			throw new IllegalArgumentException("valuation negative");
		if (contientArc(source, destination))
			throw new IllegalArgumentException("L'arc est deja present");
		ajouterSommet(source);
		ajouterSommet(destination);
		ladj.get(source).add(new Arc(source, destination, valeur));
	}

	@Override
	public void oterSommet(String noeud) {
		if (contientSommet(noeud)) {
			ladj.values().forEach(arcs -> arcs.removeIf(arc -> arc.getDestination().equals(noeud)));
			ladj.remove(noeud);
		}
	}

	@Override
	public void oterArc(String source, String destination) {
		if (!contientArc(source, destination))
			throw new IllegalArgumentException("n'existe pas");
		ladj.get(source).removeIf(arc -> arc.getDestination().equals(destination));
	}

	@Override
	public List<String> getSommets() {
		return new ArrayList<String>(ladj.keySet());
	}

	@Override
	public List<String> getSucc(String sommet) {
		List<String> Succ = new ArrayList<String>();
		if (contientSommet(sommet))
			for (Arc arc : ladj.get(sommet))
				Succ.add(arc.getDestination());
		return Succ;
		// return ladj.getOrDefault(sommet,
		// null).stream().map(Arc::getDestination).collect(Collectors.toList());
	}

	@Override
	public int getValuation(String src, String dest) {
		if (!contientSommet(src) || !contientSommet(dest))
			throw new IllegalArgumentException("n'existe pas");
		if (contientArc(src, dest))
			for (Arc arc : ladj.get(src))
				if (arc.getDestination().equals(dest))
					return arc.getValuation();
		return -1;
		// return ladj.get(src).stream().filter(arc ->
		// arc.getDestination().equals(dest)).findFirst().map(Arc::getValuation).orElse(-1);

	}

	@Override
	public boolean contientSommet(String sommet) {
		return ladj.containsKey(sommet);
	}

	@Override
	public boolean contientArc(String source, String destination) {
		if (contientSommet(source))
			for (Arc arc : ladj.get(source))
				if (arc.getDestination().equals(destination))
					return true;
		return false;
		// return contientSommet(source) && ladj.get(source).stream().anyMatch(arc ->
		// arc.getDestination().equals(destination));
	}
}
