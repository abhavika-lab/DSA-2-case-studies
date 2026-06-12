import java.util.BitSet;

public class RocksDBBloomFilter {

    static final int m = 10000000;   // Size of bit array
    static final int k = 7;          // Number of hash functions

    BitSet bloomFilter = new BitSet(m);

    // Insert a key into the Bloom Filter
    void add(String key) {
        for (int i = 0; i < k; i++) {
            int hash = hash(key, i);
            bloomFilter.set(hash);
        }
    }

    // Check if a key may be present
    boolean mightContain(String key) {
        for (int i = 0; i < k; i++) {
            int hash = hash(key, i);

            if (!bloomFilter.get(hash)) {
                return false;    // Definitely not present
            }
        }

        return true;             // Maybe present
    }

    // Simple hash function
    int hash(String key, int seed) {
        return Math.abs((key + seed).hashCode()) % m;
    }

    public static void main(String[] args) {

        RocksDBBloomFilter filter = new RocksDBBloomFilter();

        // Sample SSTable keys
        String[] keys = {
            "book101",
            "book205",
            "book310",
            "book450",
            "book501"
        };

        // Insert keys
        for (String key : keys) {
            filter.add(key);
        }

        // Query 1: Existing key
        String query1 = "book310";

        if (filter.mightContain(query1)) {
            System.out.println(query1 +
                    " : Maybe Present (Check SSTable)");
        } else {
            System.out.println(query1 +
                    " : Definitely Not Present");
        }

        // Query 2: Non-existing key
        String query2 = "book999";

        if (filter.mightContain(query2)) {
            System.out.println(query2 +
                    " : Maybe Present (Check SSTable)");
        } else {
            System.out.println(query2 +
                    " : Definitely Not Present");
            System.out.println(
                "Disk page read avoided."
            );
        }

        // False Positive Probability
        double n = 1000000;
        double fpr = Math.pow(
                1 - Math.exp((-k * n) / m),
                k);

        double optimalK = (m / n) * Math.log(2);

        System.out.printf(
            "\nFalse Positive Rate: %.3f%%\n",
            fpr * 100
        );

        System.out.printf(
            "Optimal k: %.2f hash functions\n",
            optimalK
        );
    }
}
