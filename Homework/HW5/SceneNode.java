/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #5
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 * <p>
 * SceneNode class refers to the node of the scene
 */

public class SceneNode {
    private String title, sceneDescription;
    private int sceneID;
    private SceneNode left, middle, right, parent;
    private static int numScenes = 0;

    public SceneNode() {
    }

    /**
     * Argument constructor for creating a SceneNode object
     *
     * @param newTitle            sets the title for the SceneNode
     * @param newSceneDescription sets the sceneDescription of the SceneNode
     */
    public SceneNode(String newTitle, String newSceneDescription) {
        this.title = newTitle;
        this.sceneDescription = newSceneDescription;
        numScenes++;
        this.sceneID = numScenes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSceneDescription() {
        return sceneDescription;
    }

    public void setSceneDescription(String sceneDescription) {
        this.sceneDescription = sceneDescription;
    }

    public int getSceneID() {
        return sceneID;
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public SceneNode getLeft() {
        return left;
    }

    public void setLeft(SceneNode left) {
        this.left = left;
    }

    public SceneNode getMiddle() {
        return middle;
    }

    public void setMiddle(SceneNode middle) {
        this.middle = middle;
    }

    public SceneNode getRight() {
        return right;
    }

    public void setRight(SceneNode right) {
        this.right = right;
    }

    public static int getNumScenes() {
        return numScenes;
    }

    public static void setNumScenes(int numScenes) {
        SceneNode.numScenes = numScenes;
    }

    public SceneNode getParent() {
        return parent;
    }

    public void setParent(SceneNode parent) {
        this.parent = parent;
    }

    /**
     * Adds a SceneNode to the current SceneNode object
     *
     * @param scene the sceneNode to be added as a child
     * @throws FullSceneException if there is no space to fit another child
     */
    public void addScene(SceneNode scene) throws FullSceneException {
        if (left == null)
            left = scene;

        else if (middle == null)
            middle = scene;

        else if (right == null)
            right = scene;
        else {
            numScenes--;
            throw new FullSceneException("You cannot add another scene.");
        }
    }

    /**
     * Checks if the SceneNode is a leaf
     *
     * @return true if the SceneNode is a leaf, false otherwise
     */
    public boolean isEnding() {
        return left == null;
    }

    /**
     * Prints the title and scene description of the SceneNode
     */
    public void displayScene() {
        System.out.println(title);
        System.out.println(sceneDescription);
    }

    /**
     * Displays all the information about the scene node and it's children
     */
    public void displayFullScene() {
        System.out.println("Scene ID: " + sceneID);
        System.out.println("Title: " + title);
        System.out.println("Scene: " + sceneDescription);
        System.out.print("Leads to: ");
        if (left == null)
            System.out.print("NONE\n");
        else if (middle == null)
            System.out.print("'" + left.getTitle() + "' (#" + left.getSceneID() + ")\n");
        else if (right == null) {
            System.out.print("'" + left.getTitle() + "' (#" + left.getSceneID() + "), ");
            System.out.print("'" + middle.getTitle() + "' (#" + middle.getSceneID() + ")\n");
        } else {
            System.out.print("'" + left.getTitle() + "' (#" + left.getSceneID() + "), ");
            System.out.print("'" + middle.getTitle() + "' (#" + middle.getSceneID() + "), ");
            System.out.print("'" + right.getTitle() + "' (#" + right.getSceneID() + ")\n");
        }
    }

    /**
     * Puts the title and the ID of the SceneNode into String format
     *
     * @return the title and the ID of the SceneNode
     */
    public String toString() {
        return title + " (#" + sceneID + ")";
    }
}

