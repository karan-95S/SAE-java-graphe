package graphe;


import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class IGrapheTest {
	private IGraphe[] graphes = { 
			new GrapheLArcs(), new GrapheLArcs(),
			new GrapheMAdj(), new GrapheHHAdj()
	};
	
	// graphe de l'exercice 3.1 du poly de maths
	// avec en plus un noeud isole : J
	private String g31 = 
			"A-C(2), A-D(1), "
			+ "B-G(3), "
			+ "C-H(2), "
			+ "D-B(3), D-C(5), D-E(3), "
			+ "E-C(1), E-G(3), E-H(7), "
			+ "F:, "
			+ "G-B(2), G-F(1), "
			+ "H-F(4), H-G(2), "
			+ "I-H(10), "
			+ "J:";
	
	private String g31a = ""       // melangee
			+ "D-C(5), D-E(3), D-B(3), "
			+ "E-G(3), E-C(1), E-H(7), "
			+ "I-H(10), "
			+ "J:,"
			+ "G-B(2), G-F(1), "
			+ "F:, "
			+ "H-G(2), H-F(4), "
			+ "A-C(2), A-D(1), "
			+ "B-G(3), "
			+ "C-H(2) "
;
	
	@Test
	void exo3_1Maths() {
		for (IGraphe g : graphes) {
			g.peupler(g31a); 
			tester3_1(g);			
		}
	}
	
	void tester3_1(IGraphe g) {
		List<String> sommets_exp = List.of("A","B","C","D","E","F","G","H","I", "J");
		List<String> sommets = new ArrayList<String>(g.getSommets()); // pas forcement triee
		Collections.sort(sommets);
		assertEquals(sommets_exp, sommets);
		assertTrue(g.contientSommet("C"));
		assertFalse(g.contientSommet("c"));
		assertTrue(g.contientArc("C","H"));
		assertFalse(g.contientArc("H","C"));
		assertEquals(7,g.getValuation("E", "H"));
		List<String> successeurs = new ArrayList<String>(g.getSucc("D")); // pas forcement triee
		Collections.sort(successeurs);
		assertEquals(List.of("B","C", "E"), successeurs);
		assertEquals(g31, g.toString());
		
		g.ajouterSommet("A"); // ne fait rien car A est deja present
		assertEquals(g31, g.toString());
		assertThrows(IllegalArgumentException.class,  
				() -> g.ajouterArc("G", "B", 1));		// deja present
		g.oterSommet("X"); // ne fait rien si le sommet n'est pas present
		assertEquals(g31, g.toString());
		assertThrows(IllegalArgumentException.class,
				() -> g.oterArc("X", "Y"));  // n'existe pas
		
		assertThrows(IllegalArgumentException.class,
				() -> g.ajouterArc("A", "B", -1)); // valuation negative
		
		// Test de Loic
		g.oterSommet("D");
		assertEquals("A-C(2), B-G(3), C-H(2), E-C(1), E-G(3), "
				+ "E-H(7), F:, G-B(2), G-F(1), H-F(4), H-G(2), "
				+ "I-H(10), J:", g.toString());
		g.oterArc("I","H");
		assertEquals("A-C(2), B-G(3), C-H(2), E-C(1), E-G(3), "
				+ "E-H(7), F:, G-B(2), G-F(1), H-F(4), H-G(2), "
				+ "I:, J:", g.toString());
		assertEquals(g.getValuation("A", "B"), -1);
	}
	
	void testImportation(IGraphe g) throws NumberFormatException, FileNotFoundException {
		Arc a = GraphImporter.importer("graphes/ac/g-10-1.txt", g);
		assertEquals(g.toString(), "1-3(5), "
				+ "10-3(3), 2-1(5), 2-3(5), 2-5(4), "
				+ "3-4(4), 3-5(4), 4-10(1), 4-2(1), 4-7(3), "
				+ "5-9(4), 6-2(3), 6-3(4), 7-3(2),"
				+ " 8-2(4), 8-6(1), 9-2(4)");
		assertEquals("5", a.getSource());
		assertEquals("7", a.getDestination());
	}
	
	@Test
	void importer() throws NumberFormatException, FileNotFoundException {
		for (IGraphe g : graphes)
			testImportation(g);
	}
	
	// Test de Loic
	@Test
	void dijkstra() throws FileNotFoundException {
		IGraphe g = new GrapheLAdj();
		Arc a = GraphImporter.importer("graphes/ac/g-100-1.txt", g);
		Map<String, Integer> dist = new HashMap<String, Integer>();
		Map<String, String> pred = new HashMap<String, String>();
		IGrapheConst.dijkstra(g, a.getSource(), dist, pred);
		
		List<Integer> expectedList = new ArrayList<Integer>();
		int distance = GraphImporter.importerReponse("reponses/ac/r-100-1.txt", expectedList);
		
		List<Integer> list = new ArrayList<Integer>();
		String dest = a.getDestination();
		assertEquals(distance, dist.get(dest));
		if (pred.get(dest) != null) {
			while (Integer.parseInt(dest) - 1 != expectedList.get(0)){
				list.add(Integer.parseInt(dest) - 1);
				dest = pred.get(dest);
			}
			list.add(Integer.parseInt(dest) - 1);
			Collections.reverse(list);
			assertEquals(expectedList, list);
		}
	}
	
	// Test de chatGPT (un peut bof)
	@Test
    void testDijkstra() {
        IGraphe graphe = new GrapheLAdj();
        graphe.ajouterSommet("A");
        graphe.ajouterSommet("B");
        graphe.ajouterSommet("C");
        graphe.ajouterSommet("D");
        graphe.ajouterSommet("E");
        graphe.ajouterArc("A", "B", 4);
        graphe.ajouterArc("A", "C", 2);
        graphe.ajouterArc("B", "C", 1);
        graphe.ajouterArc("B", "D", 5);
        graphe.ajouterArc("C", "D", 8);
        graphe.ajouterArc("C", "E", 10);
        graphe.ajouterArc("D", "E", 2);
        
        Map<String, Integer> dist = new HashMap<String, Integer>();
        Map<String, String> pred = new HashMap<String, String>();
        IGrapheConst.dijkstra(graphe, "A", dist, pred);
        
        Map<String, Integer> expectedDist = new HashMap<String, Integer>();
        expectedDist.put("A", 0);
        expectedDist.put("B", 4);
        expectedDist.put("C", 2);
        expectedDist.put("D", 9);
        expectedDist.put("E", 11);
        assertEquals(expectedDist, dist);
        
        Map<String, String> expectedPred = new HashMap<String, String>();
        expectedPred.put("A", null);
        expectedPred.put("B", "A");
        expectedPred.put("C", "A");
        expectedPred.put("D", "B");
        expectedPred.put("E", "D");
        assertEquals(expectedPred, pred);
    }
}
