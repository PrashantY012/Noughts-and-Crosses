public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    public int findSmallestValueInTree(TreeNode root){
        if(root==null)return (int) 1e9;
        int a1 = root.val;
        int a2 = findSmallestValueInTree(root.right);
        int a3 = findSmallestValueInTree(root.left);
        return Math.min(a1, Math.min(a2, a3));
    }

    public TreeNode deleteSmallestNode(TreeNode root){
        if(root == null)return null;
        if(root.left == null){
            return root.right;
        }
        root.left = deleteSmallestNode(root.left);
        return root;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        //base case
        if(root==null)return null;

        //cal
        if(root.val == key){
            if(root.right == null) return root.left;
            else{ //swap values and delete node
                int sml = findSmallestValueInTree(root.right);
                root.val = sml;
                root.right = deleteSmallestNode(root.right);
            }
        }
        else if(root.val > key){
            root.left = deleteNode(root.left, key);
        }else{
            root.right = deleteNode(root.right, key);
        }
        return root;

        
    }
}