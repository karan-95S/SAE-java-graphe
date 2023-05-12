package graphe;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {
	public static void dijkstra(IGrapheConst graphe, String source, Map<String, Integer> dist,
			Map<String, String> pred) {
		if (!graphe.contientSommet(source))
			throw new IllegalArgumentException("Le sommet source n'existe pas dans le graphe.");
		graphe.getSommets().forEach(s -> {
			dist.put(s, Integer.MAX_VALUE);
			pred.put(s, null);
		});
		Queue<Map.Entry<String, Integer>> nonVisites = new PriorityQueue<Map.Entry<String, Integer>>(
				Comparator.comparingInt(Map.Entry::getValue));
		dist.put(source, 0);
		nonVisites.offer(new AbstractMap.SimpleEntry<String, Integer>(source, 0));
		while (!nonVisites.isEmpty()) {
			Map.Entry<String, Integer> min = nonVisites.poll();
			String u = min.getKey();
			int d = min.getValue();
			if (d > dist.get(u))
				continue;
			for (String v : graphe.getSucc(u)) {
				int alt = d + graphe.getValuation(u, v);
				if (alt < dist.get(v)) {
					dist.put(v, alt);
					pred.put(v, u);
					nonVisites.offer(new AbstractMap.SimpleEntry<String, Integer>(v, alt));
				}
			}
		}
		graphe.getSommets().forEach(s -> {
			if (dist.get(s) == Integer.MAX_VALUE)
				dist.remove(s);
			if (pred.get(s) == null)
				pred.remove(s);
		});
	}
}
