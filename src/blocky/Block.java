package blocky;
import static blocky.Game.createRandomChildren;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

// A square block in the Blocky game.
public class Block
{
    public final static Color PACIFIC_POINT = new Color(1, 128, 181); //blue
    public final static Color OLD_OLIVE = new Color(138, 151, 71); //green
    public final static Color REAL_RED = new Color(199, 44, 58); //red
    public final static Color DAFFODIL_DELIGHT = new Color(255, 211, 92); //yellow
    public final static Color[] COLORS = {DAFFODIL_DELIGHT, OLD_OLIVE, REAL_RED, PACIFIC_POINT};
    
    public final static Color MELON_MAMBO = new Color(234, 62, 112);
    public final static Color HIGHLIGHT_COLOR = new Color(75, 196, 213); //TEMPTING_TURQUOISE
    
    //=== Public Attributes ===
    //position:
    //    The (x, y) coordinates of the upper left corner of this Block.
    //    Note that (0, 0) is the top left corner of the window.
    private int xCoordinate;
    private int yCoordinate;
    
    //size:
    //    The height and width of this Block.  Since all blocks are square,
    //    we needn't represent height and width separately.
    private int size;
    
    //color:
    //    If this block is not subdivided, color stores its color.
    //    Otherwise, color is null and this block's sublocks store their
    //    individual colours.
    private Color color;
    
    //level:
    //    The level of this block within the overall block structure.
    //    The outermost block, corresponding to the root of the tree,
    //    is at level zero.  If a block is at level i, its children are at
    //    level i+1.
    private int level;
    
    //max_depth:
    //    The deepest level allowed in the overall block structure.
    public static int MAX_DEPTH = 5;
    
    // max_size:
    //    The size of the biggest Block, and thus, the entire game board.
    public final static int MAX_SIZE = 640;
    
    //highlighted:
    //    True if the user has selected this block for action.
    private boolean highlighted;
    
    //children:
    //    The blocks into which this block is subdivided.  The children are
    //    stored in this order: upper-left child, upper-right child,
    //    lower-left child, lower-right child.
    List<Block> children;
    
    //parent:
    //    The block that this block is directly within.
    Block parent;

    /*
    === Notes ===
    - children.size() == 0 or children.size() == 4
    - If this Block has children,
        - their max_depth is the same as that of this Block,
        - their size is half that of this Block,
        - their level is one greater than that of this Block,
        - their position is determined by the position and size of this Block
    - level <= max_depth
    */

    public Block()
    {
        this(Color.WHITE, 0, MAX_SIZE, null);
    }
    
    public Block(Color inColor, int inLevel, int inSize, Block inParent)
    {
        xCoordinate = 0;
        yCoordinate = 0;
        size = inSize;
        color = inColor;
        level = inLevel;
        highlighted = false;
        children = new ArrayList<>();
        parent = inParent;
    }

    public int getXCoordinate()
    {
        return xCoordinate;
    }

    public int getYCoordinate()
    {
        return yCoordinate;
    }

    public int getSize()
    {
        return size;
    }

    public Color getColor()
    {
        return color;
    }

    public int getLevel()
    {
        return level;
    }

    public boolean isHighlighted()
    {
        return highlighted;
    }

    public List<Block> getChildren()
    {
        return children;
    }

    public Block getParent()
    {
        return parent;
    }

    public void setXCoordinate(int xCoordinate)
    {
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(int yCoordinate)
    {
        this.yCoordinate = yCoordinate;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setHighlighted(boolean highlighted)
    {
        this.highlighted = highlighted;
    }

    public void setChildren(List<Block> children)
    {
        this.children = children;
    }

    public void setParent(Block parent)
    {
        this.parent = parent;
    }
    
    //CUSTOM METHODS FOR BLOCKS
    
    public void swapBlocks(boolean swapDirection){

        if(!this.getChildren().isEmpty()){
            
            Block blockOneChild = children.get(0);
            Block blockTwoChild = children.get(1);
            Block blockThreeChild = children.get(2);
            Block blockFourChild = children.get(3);
        
            //Horizontal swap
            if(swapDirection == true){

                //Block 1 switches with block 2
                children.set(0, blockTwoChild);
                children.set(1, blockOneChild);

                //Block 3 switches with block 4
                children.set(2, blockFourChild);
                children.set(3, blockThreeChild);

            }
            //Vertical Swap
            else{
                //Block 1 switches with block 3
                children.set(0, blockThreeChild);
                children.set(2, blockOneChild);

                //Block 2 switches with block 4
                children.set(1, blockFourChild);
                children.set(3, blockTwoChild);
            }
        }
    }
    
    public void smashBlock(Block selectedBlock){

        if(selectedBlock.getLevel() != 0 && selectedBlock.getLevel() != Block.MAX_DEPTH){
            createRandomChildren(selectedBlock);
        }
    }
    
    //Recursive method
    public void rotateBlocks(boolean rotationDirection){

        if(!this.getChildren().isEmpty()){
            
            Block blockOneChild = children.get(0);
            Block blockTwoChild = children.get(1);
            Block blockThreeChild = children.get(2);
            Block blockFourChild = children.get(3);
        
            //Counter Clockwise rotation
            if(rotationDirection == false){

                //Block 2 switches to block 1
                children.set(0, blockTwoChild);

                //Block 1 switches to block 3
                children.set(2, blockOneChild);
                
                //Block 3 switches to block 4
                children.set(3, blockThreeChild);
                
                //Block 4 switches to block 2
                children.set(1, blockFourChild);
            }
            
            //Clockwise rotation
            else{
                
                //Block 2 switches to block 4
                children.set(3, blockTwoChild);
                
                //Block 4 switches to block 3
                children.set(2, blockFourChild);
                
                //Block 3 switches to block 1
                children.set(0, blockThreeChild);

                //Block 1 switches to block 2
                children.set(1, blockOneChild);
            }
            
            for(Block childBlock: children){
                childBlock.rotateBlocks(rotationDirection);
            }
        } 
    }
}