package graphe.implems;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GrapheHHAdj extends Graphe {
	private Map<String, Map<String, Integer>> hhadj;

	public GrapheHHAdj() {
		this.hhadj = new HashMap<>();
	}

	public GrapheHHAdj(String str) {
		this();
		peupler(str);
	}

	@Override
	public void ajouterSommet(String noeud) {
		hhadj.putIfAbsent(noeud, new HashMap<>());
	}

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		if (valeur < 0)
			throw new IllegalArgumentException("valuation negative");
		if (contientArc(source, destination))
			throw new IllegalArgumentException("L'arc est deja present");
		ajouterSommet(source);
		ajouterSommet(destination);
		hhadj.get(source).put(destination, valeur);
	}

	@Override
	public void oterSommet(String sommet) {
		if (contientSommet(sommet)) {
			hhadj.values().forEach(S -> S.remove(sommet));
			hhadj.remove(sommet);
		}
	}

	@Override
	public void oterArc(String source, String destination) {
		if (!contientArc(source, destination))
			throw new IllegalArgumentException("n'existe pas");
		hhadj.get(source).remove(destination);
	}

	@Override
	public List<String> getSommets() {
		return new ArrayList<>(hhadj.keySet());
	}

	@Override
	public List<String> getSucc(String sommet) {
		return contientSommet(sommet) ? new ArrayList<>(hhadj.get(sommet).keySet()) : null;
	}

	@Override
	public int getValuation(String src, String dest) {
		if (!contientSommet(src) || !contientSommet(dest))
			throw new IllegalArgumentException("n'existe pas");
		return contientArc(src, dest) ? hhadj.get(src).get(dest) : -1;
	}

	@Override
	public boolean contientSommet(String sommet) {
		return hhadj.containsKey(sommet);
	}

	@Override
	public boolean contientArc(String src, String dest) {
		return contientSommet(src) && hhadj.get(src).containsKey(dest);
	}
}