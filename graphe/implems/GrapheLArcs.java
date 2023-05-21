package graphe.implems;

import java.util.List;

import graphe.core.Arc;

//import java.util.stream.Collectors;
import java.util.ArrayList;

public class GrapheLArcs extends Graphe {
	private List<Arc> arcs;

	public GrapheLArcs() {
		arcs = new ArrayList<>();
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
		ajouterSommet(source);
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
		List<String> S = new ArrayList<>();
		for (Arc arc : arcs) {
			if (arc.getDestination().equals(""))
				S.add(arc.getSource());
		}
		return S;
		// return arcs.stream().filter(arc ->
		// arc.getDestination().equals("")).map(Arc::getSource).collect(Collectors.toList());

	}

	@Override
	public List<String> getSucc(String sommet) {
		List<String> Succ = new ArrayList<>();
		for (Arc arc : arcs)
			if (arc.getSource().equals(sommet) && !arc.getDestination().equals(""))
				Succ.add(arc.getDestination());
		return Succ;
		// return arcs.stream().filter(arc -> arc.getSource().equals(sommet) &&
		// !arc.getDestination().equals("")).map(Arc::getDestination).collect(Collectors.toList());

	}

	@Override
	public int getValuation(String src, String dest) {
		if (!contientSommet(src) || !contientSommet(dest))
			throw new IllegalArgumentException("n'existe pas");
		return arcs.stream().filter(arc -> arc.equals(src, dest)).findFirst().map(Arc::getValuation).orElse(-1);
	}

	@Override
	public boolean contientSommet(String sommet) {
		return getSommets().contains(sommet);
	}

	@Override
	public boolean contientArc(String src, String dest) {
		for (Arc arc : arcs)
			if (arc.equals(src, dest))
				return true;
		return false;
		// return arcs.stream().anyMatch(arc -> arc.equals(src, dest));
	}
}
