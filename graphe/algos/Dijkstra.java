package graphe.algos;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

import graphe.core.IGrapheConst;

public class Dijkstra {
	private static int INFINITY = Integer.MAX_VALUE;

	public static void dijkstra(IGrapheConst graphe, String source, Map<String, Integer> dist,
			Map<String, String> pred) {
		if (!graphe.contientSommet(source))
			throw new IllegalArgumentException("Le sommet source n'existe pas dans le graphe.");
		graphe.getSommets().forEach(S -> {
			dist.put(S, INFINITY);
			pred.put(S, null);
		});
		Queue<Map.Entry<String, Integer>> nonVisites = new PriorityQueue<>(
				Comparator.comparingInt(Map.Entry::getValue));
		dist.put(source, 0);
		nonVisites.offer(new AbstractMap.SimpleEntry<>(source, 0));
		while (!nonVisites.isEmpty()) {
			Map.Entry<String, Integer> min = nonVisites.poll();
			String S = min.getKey();
			int D = min.getValue();
			if (D > dist.get(S))
				continue;
			graphe.getSucc(S).forEach(Succ -> {
				int longueur = D + graphe.getValuation(S, Succ);
				if (longueur < dist.get(Succ)) {
					dist.put(Succ, longueur);
					pred.put(Succ, S);
					nonVisites.offer(new AbstractMap.SimpleEntry<>(Succ, longueur));
				}
			});
		}
		dist.entrySet().removeIf(entry -> entry.getValue() == INFINITY);
		pred.values().removeIf(Objects::isNull);
	}
}
