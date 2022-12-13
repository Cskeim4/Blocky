package blocky;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game
{
    //=== Public Attributes ===
    //root:
    //    The Blocky board on which this game will be played.
    private Block root;
    
    //highlightedBlock:
    //    The block the user has selected.  This will be colored specially when rendered.
    private Block highlightedBlock;
    
    public Game()
    {
        //root = createTestBoard();
        //Creates one block, the root of the tree
        root = new Block();
        //Call to this method creates the rest of the tree
        createRandomChildren(root);
        highlightedBlock = null;
    }
    
    public static Color getRandomColor() {
        int colorIndex = new Random().nextInt(Block.COLORS.length);
        return Block.COLORS[colorIndex];
    }
    
    public static void createRandomChildren(Block parent)
    {
        // TODO - complete this method.
        
        int childLevel;
        int childSize;
        
        childLevel = parent.getLevel() + 1;
        childSize = parent.getSize() / 2;
        
        List<Block> rootChildren = new ArrayList<>();

        rootChildren.add( new Block(getRandomColor(), childLevel, childSize, parent));
        rootChildren.add( new Block(getRandomColor(), childLevel, childSize, parent));
        rootChildren.add( new Block(getRandomColor(), childLevel, childSize, parent));
        rootChildren.add( new Block(getRandomColor(), childLevel, childSize, parent));

        parent.setChildren(rootChildren);
        
        if(childLevel != Block.MAX_DEPTH){
            for (Block childBlock: rootChildren){
                if(Math.random() < Math.exp(-0.25 * childLevel)){
                    createRandomChildren(childBlock);
                }
            }
        }
    }
    
    public Block createTestBoard()
    {
        Block root = new Block();
        root.setSize(Block.MAX_SIZE);
        
        List<Block> rootChildren = new ArrayList<>();
        rootChildren.add(new Block(Block.DAFFODIL_DELIGHT, 1, 320, root));
        rootChildren.add(new Block(Block.OLD_OLIVE, 1, 320, root));
        rootChildren.add(new Block(Block.PACIFIC_POINT, 1, 320, root));
        rootChildren.add(new Block(Block.REAL_RED, 1, 320, root));
        
        root.setChildren(rootChildren);
        Block child = root.getChildren().get(0);
        
        rootChildren = new ArrayList<>();
        rootChildren.add(new Block(Block.DAFFODIL_DELIGHT, 2, 160, child));
        rootChildren.add(new Block(Block.OLD_OLIVE, 2, 160, child));
        rootChildren.add(new Block(Block.PACIFIC_POINT, 2, 160, child));
        rootChildren.add(new Block(Block.REAL_RED, 2, 160, child));
        
        child.setChildren(rootChildren);
        
        return root;
    }

    public Block getRoot()
    {
        return root;
    }

    public Block getHighlightedBlock()
    {
        return highlightedBlock;
    }

    public void setRoot(Block root)
    {
        this.root = root;
    }

    public void setHighlightedBlock(Block highlightedBlock)
    {
        this.highlightedBlock = highlightedBlock;
    }
}