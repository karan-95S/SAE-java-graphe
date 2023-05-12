package graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheMAdj extends Graphe {
	private int[][] matrice;
	private Map<String, Integer> indices;
	private final static int VALEUR_INIT = -1;

	public GrapheMAdj() {
		indices = new HashMap<String, Integer>();
		matrice = new int[0][0];
	}

	public GrapheMAdj(String str) {
		this();
		peupler(str);
	}

	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			indices.put(noeud, indices.size());
			int taille = indices.size();
			int[][] nouvelleMatrice = new int[taille][taille];
			for (int i = 0; i < taille; i++)
				for (int j = 0; j < taille; j++) {
					if (j < taille - 1 && i < taille - 1)
						nouvelleMatrice[i][j] = matrice[i][j];
					else
						nouvelleMatrice[i][j] = VALEUR_INIT;
				}
			matrice = nouvelleMatrice;
		}
	}

	public void ajouterArc(String source, String destination, Integer valeur) {
		if (valeur < 0)
			throw new IllegalArgumentException("valuation negative");
		if (contientArc(source, destination))
			throw new IllegalArgumentException("L'arc est deja present");
		ajouterSommet(source);
		ajouterSommet(destination);
		matrice[indices.get(source)][indices.get(destination)] = valeur;
	}

	public void oterSommet(String sommet) {
		if (contientSommet(sommet)) {
			int indiceOterSommet = indices.get(sommet), taille = indices.size() - 1, ligne = 0, colonne = 0;
			int[][] nouvelleMatrice = new int[taille][taille];
			for (int i = 0; i <= taille; i++) {
				if (i == indiceOterSommet)
					continue;
				for (int j = 0; j <= taille; j++) {
					if (j == indiceOterSommet)
						continue;
					nouvelleMatrice[ligne][colonne++] = matrice[i][j];
				}
				ligne++;
				colonne = 0;
			}
			matrice = nouvelleMatrice;
			for (String S : getSommets())
				if (indices.get(S) > indiceOterSommet)
					indices.replace(S, indices.get(S) - 1);
			indices.remove(sommet);
		}
	}

	public void oterArc(String source, String destination) {
		if (!contientArc(source, destination))
			throw new IllegalArgumentException("n'existe pas");
		matrice[indices.get(source)][indices.get(destination)] = VALEUR_INIT;
	}

	@Override
	public List<String> getSommets() {
		return new ArrayList<String>(indices.keySet());
	}

	@Override
	public List<String> getSucc(String sommet) {
		List<String> succ = new ArrayList<String>();
		if (contientSommet(sommet)) {
			int indice = indices.get(sommet);
			for (String S : getSommets())
				if (matrice[indice][indices.get(S)] != VALEUR_INIT)
					succ.add(S);
		}
		return succ;
	}

	@Override
	public int getValuation(String src, String dest) {
		if (!contientSommet(src) || !contientSommet(dest))
			throw new IllegalArgumentException("n'existe pas");
		return matrice[indices.get(src)][indices.get(dest)];
	}

	@Override
	public boolean contientSommet(String sommet) {
		return indices.containsKey(sommet);
	}

	@Override
	public boolean contientArc(String src, String dest) {
		return contientSommet(src) && contientSommet(dest)
				&& matrice[indices.get(src)][indices.get(dest)] != VALEUR_INIT;
	}
}
