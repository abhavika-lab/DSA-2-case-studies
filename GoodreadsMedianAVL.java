class Node {
    double rating;
    Node left, right;
    int height;
    int size;

    Node(double rating) {
        this.rating = rating;
        this.height = 1;
        this.size = 1;
    }
}

public class GoodreadsMedianAVL {

    static int getHeight(Node node) {
        return (node == null) ? 0 : node.height;
    }

    static int getSize(Node node) {
        return (node == null) ? 0 : node.size;
    }

    static void update(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left),
                                        getHeight(node.right));

            node.size = 1 + getSize(node.left)
                           + getSize(node.right);
        }
    }

    static Node insert(Node root, double rating) {

        if (root == null) {
            return new Node(rating);
        }

        if (rating < root.rating) {
            root.left = insert(root.left, rating);
        } else {
            root.right = insert(root.right, rating);
        }

        update(root);

        return root;
    }

    static double findMedian(Node root, int n) {

        int rank = (n + 1) / 2;

        while (root != null) {

            int leftSize = getSize(root.left);

            if (rank == leftSize + 1) {
                return root.rating;
            } else if (rank <= leftSize) {
                root = root.left;
            } else {
                rank = rank - leftSize - 1;
                root = root.right;
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        double[] ratings = {
            4.0, 5.0, 3.5, 4.5, 4.2,
            4.8, 3.8, 4.1, 4.6
        };

        Node root = null;

        for (double rating : ratings) {
            root = insert(root, rating);
        }

        double median = findMedian(root, ratings.length);

        System.out.println("Median Rating: " + median + " stars");
    }
}
