/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #5
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * SceneTree represents all the SceneNode objects arranged as a ternary tree
 */

public class SceneTree extends SceneNode {
    private SceneNode root, cursor;
    private String tree;
    private SceneNode traverse;

    public SceneTree() {
    }

    public SceneNode getRoot() {
        return root;
    }

    public void setRoot(SceneNode root) {
        this.root = root;
        this.cursor = root;
    }

    public SceneNode getCursor() {
        return cursor;
    }

    public void setCursor(SceneNode cursor) {
        this.cursor = cursor;
    }

    /**
     * Moves the cursor to the previous scene
     *
     * @throws NoSuchNodeException there is no node to move backwards to
     */
    public void moveCursorBackwards() throws NoSuchNodeException {
        if (cursor != root) {
            cursor = cursor.getParent();
            System.out.println("Successfully moved back to " + cursor.getTitle() + ".");
        } else
            throw new NoSuchNodeException("That option does not exist.");
    }

    /**
     * Move cursor to one of it's child nodes
     *
     * @param option which child will it move to
     * @throws NoSuchNodeException if the cursor doesn't have any child nodes
     */
    public void moveCursorForwards(String option) throws NoSuchNodeException {
        if (cursor.isEnding())
            throw new NoSuchNodeException("Cursor is at an ending scene.");
        switch (option.toLowerCase()) {
            case "a":
                cursor = cursor.getLeft();
                break;
            case "b":
                if (cursor.getMiddle() == null)
                    throw new NoSuchNodeException("That option does not exist.");
                else
                    cursor = cursor.getMiddle();
                break;
            case "c":
                if (cursor.getRight() == null)
                    throw new NoSuchNodeException("That option does not exist.");
                else
                    cursor = cursor.getRight();
                break;
            default:
                System.out.println("Please enter either 'A', 'B', or 'C'");
                break;
        }
        System.out.println("Successfully moved to " + cursor.getTitle() + ".");
    }

    /**
     * Adds a scene node to be the child of the node at cursor
     * @param title the title of the new scene node
     * @param sceneDescription the description of the new scene node
     * @throws FullSceneException if the current node already has 3 childs
     */
    public void addNewNode(String title, String sceneDescription) throws FullSceneException {
        SceneNode newSceneNode = new SceneNode(title, sceneDescription);
        newSceneNode.setParent(cursor);
        cursor.addScene(newSceneNode);
    }

    /**
     * Removes a child from the cursor and shifts the other children to the left
     * @param option which child is to be removed
     * @throws NoSuchNodeException there is no child to remove
     */
    public void removeScene(String option) throws NoSuchNodeException {
        if (cursor.isEnding())
            throw new NoSuchNodeException("Cursor is at an ending scene.");
        SceneNode temp = cursor;
        switch (option.toLowerCase()) {
            case "a":
                temp = cursor.getLeft();
                cursor.getLeft().setParent(null);
                cursor.setLeft(cursor.getMiddle());
                cursor.setMiddle(cursor.getRight());
                cursor.setRight(null);
                System.out.println(temp.getTitle() + " removed.");
                break;
            case "b":
                if (cursor.getMiddle() == null)
                    throw new NoSuchNodeException("There is no scene to remove.");
                temp = temp.getMiddle();
                cursor.getMiddle().setParent(null);
                cursor.setMiddle(cursor.getRight());
                cursor.setRight(null);
                System.out.println(temp.getTitle() + " removed.");
                break;
            case "c":
                if (cursor.getRight() == null)
                    throw new NoSuchNodeException("There is no scene to remove.");
                else {
                    temp = temp.getRight();
                    cursor.getRight().setParent(null);
                }
                cursor.setRight(null);
                System.out.println(temp.getTitle() + " removed.");
                break;
            default:
                System.out.println("That option does not exist");
                break;
        }
    }

    /**
     * moves the node at cursor to be a child with the ID specified
     * @param sceneIDToMoveTo the ID which node of this sceneNode to be moved under
     * @throws NoSuchNodeException there is no node with the same ID
     * @throws FullSceneException the sceneNode with the ID doesn't have space for any child
     */
    public void moveScene(int sceneIDToMoveTo) throws NoSuchNodeException, FullSceneException {
        traverse = null;
        traverse(root, sceneIDToMoveTo);
        SceneNode temp = traverse;
        if (temp == null)
            throw new NoSuchNodeException("There is no scenes with that ID");
        else if (temp.getRight() != null)
            throw new FullSceneException("There is no available child " +
              "positions for the scene with that ID");
        else if (cursor == root)
            System.out.println("Cannot move the root node");
        else if (cursor.getParent().getLeft() == cursor) {
            SceneNode tempCursor = cursor;
            cursor = cursor.getParent();
            removeScene("A");
            temp.addScene(tempCursor);
            cursor = tempCursor;
        } else if (cursor.getParent().getMiddle() == cursor) {
            SceneNode tempCursor = cursor;
            cursor = cursor.getParent();
            removeScene("B");
            temp.addScene(tempCursor);
            cursor = tempCursor;
        } else if (cursor.getParent().getRight() == cursor) {
            SceneNode tempCursor = cursor;
            cursor = cursor.getParent();
            removeScene("C");
            temp.addScene(tempCursor);
            cursor = tempCursor;
        }
    }

    /**
     * traverses through the tree to find the SceneNode with the specified ID
     * @param parent checks if the SceneNode is the one with the matching ID
     * @param IDToMoveTo the ID of the specific SceneNode to be located
     */
    private void traverse(SceneNode parent, int IDToMoveTo) {
        if (parent.getSceneID() == IDToMoveTo)
            traverse = parent;
        if (parent.getLeft() != null)
            traverse(parent.getLeft(), IDToMoveTo);
        if (parent.getMiddle() != null)
            traverse(parent.getMiddle(), IDToMoveTo);
        if (parent.getRight() != null)
            traverse(parent.getRight(), IDToMoveTo);
    }

    /**
     * Shows how to get from the root to the cursor
     * @return string of the titles to go from root to cursor
     */
    public String getPathFromRoot() {
        String path = cursor.getTitle();
        SceneNode tempCursor = cursor.getParent();
        while (tempCursor.getParent() != null) {
            path = tempCursor.getTitle() + ", " + path;
            tempCursor = tempCursor.getParent();
        }
        path = root.getTitle() + ", " + path;
        return path;
    }

    /**
     * String representation of the tree
     * @return the string of all the SceneNodes
     */
    public String toString() {
        tree = "";
        toStringHelper(root, 1);
        return tree;
    }

    /**
     * Helps create the String for the toString method
     * @param node the node to be added to the string
     * @param indent how many indents to be made according to the depth of the node
     */
    private void toStringHelper(SceneNode node, int indent) {
        String temp = "";
        if (node == cursor)
            tree += node.toString() + " *";
        else
            tree += node.toString();
        tree += "\n";
        for (int i = 0; i < indent; i++)
            temp += "\t";
        if (node.getLeft() != null) {
            tree += temp;
            tree += "A)";
            toStringHelper(node.getLeft(), indent + 1);
        }
        if (node.getMiddle() != null) {
            tree += temp;
            tree += "B)";
            toStringHelper(node.getMiddle(), indent + 1);
        }
        if (node.getRight() != null) {
            tree += temp;
            tree += "C)";
            toStringHelper(node.getRight(), indent + 1);
        }
    }


}
