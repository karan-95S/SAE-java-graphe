package graphe.core;

public class Arc {
	private final String source;
	private final String destination;
	private final int valuation;

	public Arc(String source, String destination, int valuation) {
		this.source = source;
		this.destination = destination;
		this.valuation = valuation;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public int getValuation() {
		return valuation;
	}

	public boolean equals(String src, String dest) {
		return this.source.equals(src) && this.destination.equals(dest);
	}

	@Override
	public String toString() {
		return source + "-" + destination + "(" + valuation + ")";
	}
}