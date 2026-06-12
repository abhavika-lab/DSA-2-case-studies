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

    static int getBalance(Node node) {
        return (node == null) ? 0 :
               getHeight(node.left) - getHeight(node.right);
    }

    // Right Rotation
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        update(y);
        update(x);

        return x;
    }

    // Left Rotation
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        update(x);
        update(y);

        return y;
    }

    static Node insert(Node root, double rating) {

        if (root == null)
            return new Node(rating);

        if (rating < root.rating)
            root.left = insert(root.left, rating);
        else
            root.right = insert(root.right, rating);

        update(root);

        int balance = getBalance(root);

        // LL Rotation
        if (balance > 1 && rating < root.left.rating)
            return rightRotate(root);

        // RR Rotation
        if (balance < -1 && rating > root.right.rating)
            return leftRotate(root);

        // LR Rotation
        if (balance > 1 && rating > root.left.rating) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RL Rotation
        if (balance < -1 && rating < root.right.rating) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    static double findMedian(Node root, int n) {

        int rank = (n + 1) / 2;

        while (root != null) {

            int leftSize = getSize(root.left);

            if (rank == leftSize + 1)
                return root.rating;

            else if (rank <= leftSize)
                root = root.left;

            else {
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

        System.out.println("The augmented AVL Tree successfully supports efficient median-rating queries on Goodreads book ratings.");
        System.out.println("Example Query:");
        System.out.println("Find the median rating among the Goodreads ratings.");
        System.out.println("Median Rating: " + median + " stars");
        System.out.println("Using subtree-size information, the AVL Tree directly navigates to the median element without performing a full traversal.");
    }
}
