package graphe;

public class Arc {
    private final String source;
    private final String destination;
    private final int valuation;

    public Arc(String source, String destination, int valuation) {
        if (valuation < 0) {
            throw new IllegalArgumentException("La valuation ne peut pas être négative.");
        }
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
    
    public boolean equals(Arc a) {
    	return this.source == a.source && this.destination == a.destination;
    }
    
    @Override
    public String toString() {
        return source + "-" + destination + "(" + valuation + ")";
    }
}