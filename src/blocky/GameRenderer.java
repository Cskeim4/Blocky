package blocky;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameRenderer extends JComponent implements MouseListener, KeyListener
{
    private Image image;
    private JFrame frame;
    private Game game;
    
    public GameRenderer(Game inGame)
    {
        game = inGame;
        setUpRenderer();
    }
    
    public void display()
    {
        paintBlocks(game.getRoot(), 0, 0);
        paintHighlightedBlock();
        repaint();
    }
    
    private void setUpRenderer()
    {
        image = new BufferedImage(Block.MAX_SIZE, Block.MAX_SIZE, BufferedImage.TYPE_INT_RGB);

        frame = new JFrame("Blocky");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        frame.getContentPane().add(topPanel);

        setPreferredSize(new Dimension(Block.MAX_SIZE, Block.MAX_SIZE));
        addMouseListener(this);
        frame.addKeyListener(this);
        topPanel.add(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(buttonPanel);
        
        frame.pack();
        frame.addWindowListener(new WindowAdapter() { 
          @Override
          public void windowClosing(WindowEvent e) {System.exit(0);} 
        });
        frame.setVisible(true);
    }
    
    private void paintHighlightedBlock()
    {
        Block highlightedBlock = game.getHighlightedBlock();
        
        if (highlightedBlock != null)
        {
            // Set up the graphics object and paint the border the highlight color.
            Graphics graphics = image.getGraphics();
            graphics.setColor(Block.HIGHLIGHT_COLOR);
            graphics.drawRect(highlightedBlock.getXCoordinate(), highlightedBlock.getYCoordinate(), 
                    highlightedBlock.getSize(), highlightedBlock.getSize());
            graphics.drawRect(highlightedBlock.getXCoordinate() + 1, highlightedBlock.getYCoordinate() + 1, 
                    highlightedBlock.getSize() - 2, highlightedBlock.getSize() - 2);
        }
    }
    
    private void paintBlocks(Block block, int xCoordinate, int yCoordinate)
    {
        // Set up the graphics object and paint the solid rectangle the appropriate color.
        Graphics graphics = image.getGraphics();
        graphics.setColor(block.getColor());
        graphics.fillRect(xCoordinate, yCoordinate, block.getSize(), block.getSize());
        
        // Set the border color.
        if (block.isHighlighted())
        {
            
            game.getHighlightedBlock().setXCoordinate(xCoordinate);
            game.getHighlightedBlock().setYCoordinate(yCoordinate);
        }
        else
        {
            graphics.setColor(Color.BLACK);
            graphics.drawRect(xCoordinate, yCoordinate, block.getSize(), block.getSize());
            graphics.drawRect(xCoordinate + 1, yCoordinate + 1, block.getSize() - 2, block.getSize() - 2);
        }
        
        List<Block> children = block.getChildren();
        
        if (!children.isEmpty())
        {
            Block childBlock;
            
            // Upper left.
            childBlock = children.get(0);
            paintBlocks(childBlock, xCoordinate, yCoordinate);
            
            // Upper right.
            childBlock = children.get(1);
            paintBlocks(childBlock, xCoordinate + childBlock.getSize(), yCoordinate);
            
            // Lower left.
            childBlock = children.get(2);
            paintBlocks(childBlock, xCoordinate, yCoordinate + childBlock.getSize());
            
            // Lower right.
            childBlock = children.get(3);
            paintBlocks(childBlock, xCoordinate + childBlock.getSize(), yCoordinate + childBlock.getSize());
        }
    }
    
    private void highlightBlock(Block block, int row, int column)
    {
        // TODO - complete this method.
        
        //if the current block has no children...
        if(block.getChildren().size() == 0){
            
            Block currentBlock;
            currentBlock = game.getHighlightedBlock();
            
            //if the current highlighted block is not null, set the highlighted variable to false
            if(currentBlock != null){
                currentBlock.setHighlighted(false);
            }
            
            //Set the new highlighted block to true
            block.setHighlighted(true);
            game.setHighlightedBlock(block);
        }

        else if(row <= block.getSize() / 2){
            
            //Block is in section 1
            if(column <= block.getSize() / 2){

                Block childBlock;

                //if the block has children, make a recursive call to the method with the current block
                if(block.getChildren().size() != 0){
                    childBlock = block.getChildren().get(0);
                    highlightBlock(childBlock, row, column);
                }

                //if the block has no children, make a recursive call to the method with the current block
                else{
                    childBlock = block.getChildren().get(0);
                    highlightBlock(childBlock, row, column);
                }
            }
            //Block is in section 2
            else if(column >= block.getSize() / 2){

                Block childBlock;
                int newColumn;

                if(block.getChildren().size() != 0){
                    childBlock = block.getChildren().get(1);
                    newColumn = column - block.getSize() / 2;
                    highlightBlock(childBlock, row, newColumn);
                }

                else{
                    childBlock = block.getChildren().get(1);
                    highlightBlock(childBlock, row, column);
                }
            }
        }
        
        else{
            //Block is in section 3
            if(column < block.getSize() / 2){

                Block childBlock;
                int newRow;

                if(block.getChildren().size() != 0){
                    childBlock = block.getChildren().get(2);
                    newRow = row - block.getSize() / 2;
                    highlightBlock(childBlock, newRow, column);
                }

                else{
                    childBlock = block.getChildren().get(2);
                    highlightBlock(childBlock, row, column);
                }
            }
            //Block is in section 4
            else if(column > block.getSize() / 2){

                Block childBlock;
                int newRow;
                int newColumn;

                if(block.getChildren().size() != 0){
                    childBlock = block.getChildren().get(3);
                    newRow = row - block.getSize() / 2;
                    newColumn = column - block.getSize() / 2;
                    highlightBlock(childBlock, newRow, newColumn);
                }

                else{
                    childBlock = block.getChildren().get(3);
                    highlightBlock(childBlock, row, column);
                }
            }
        }   
    }
    
    private void highlightBlock(MouseEvent e)
    {
        int row = e.getY();
        int column = e.getX();
        highlightBlock(game.getRoot(), row, column);
    }
  
    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        Block highlightedBlock = game.getHighlightedBlock();
        Block parentBlock = highlightedBlock.getParent();
        
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            if (highlightedBlock != null && parentBlock != null)
            {
                // TODO - complete the rest of this method.
                
                //Set the currently highlighted block to false
                highlightedBlock.setHighlighted(false);
                
                //Get the highlighted block's parent and set it as the new highlighted block
                parentBlock.setHighlighted(true);
                game.setHighlightedBlock(parentBlock);
                
                //System.out.println(game.getHighlightedBlock());
                
                display();
            }
        }
        
        else if(e.getKeyCode() == KeyEvent.VK_H)
        {
            highlightedBlock.swapBlocks(true);
            display();
        }
        else if(e.getKeyCode() == KeyEvent.VK_V)
        {
            highlightedBlock.swapBlocks(false);
            display();
        }
        else if(e.getKeyCode() == KeyEvent.VK_S)
        {
            highlightedBlock.smashBlock(highlightedBlock);
            display();
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            highlightedBlock.rotateBlocks(false);
            display();
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            highlightedBlock.rotateBlocks(true);
            display();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        highlightBlock(e);
        display();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
}