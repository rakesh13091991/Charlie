package charlie.sidebet.view;

import charlie.audio.Effect;
import charlie.audio.SoundFactory;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.util.Constant;
import charlie.view.AMoneyManager;
import charlie.view.sprite.AtStakeSprite;
import charlie.view.sprite.Chip;
import charlie.view.sprite.ChipButton;
import java.util.Random;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class renders the sidebet stake and coins and also manages the 
 * sound effects for sidebet and displays whether a sidebet is won or lost 
 * 
 * @author Rakesh
 */
public class SideBetView implements ISideBetView {

    private final Logger LOG = LoggerFactory.getLogger(SideBetView.class);

    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;

    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected Font font2 = new Font("Arial", Font.PLAIN, 15);
    protected BasicStroke stroke = new BasicStroke(3);

    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);

    protected final static String[] UP_FILES
            = {"chip-100-1.png", "chip-25-1.png", "chip-5-1.png"};

    protected List<ChipButton> buttons;
    protected int amt = 0;
    protected AMoneyManager moneyManager;
    protected AtStakeSprite AtStake = new AtStakeSprite(X - DIAMETER / 2, Y - DIAMETER / 2, DIAMETER);
    protected List<Chip> chips = new ArrayList<>();
    protected Random randomNum = new Random();
    protected boolean sidebeton;
    private double checkbet;
    private int width = 0;

    ImageIcon icon = new ImageIcon(Constant.DIR_IMGS + UP_FILES[0]);
    Image img = icon.getImage();

    public SideBetView() {
        LOG.info("side bet view constructed");
        this.width = img.getWidth(null);
    }

    /**
     * Sets the money manager.
     *
     * @param moneyManager
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    }

    /**
     * Registers a click for the side bet.
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;

        // Test if any chip button has been pressed.
        for (ChipButton button : buttons) {
            if (button.isPressed(x, y)) {
                amt += button.getAmt();

                int n = chips.size();
                Chip chip = new Chip(button.getImage(), X + n * width / 3 + 30 + randomNum.nextInt(10), Y - 15 + randomNum.nextInt(5) - 5, button.getAmt());

                chips.add(chip);
                LOG.info("A. side bet amount " + button.getAmt() + " updated new amt = " + amt);
                SoundFactory.play(Effect.CHIPS_IN);
            }
        }

        if (AtStake.isPressed(x, y)) {
            amt = 0;
            chips.clear();
            LOG.info("B. side bet amount cleared");
            SoundFactory.play(Effect.CHIPS_OUT);
        }

        /* if(oldAmt == amt) {
         amt = 0;
         LOG.info("B. side bet amount cleared");
         }
         */
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for
     * the hand.
     *
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();

        checkbet = bet;
        sidebeton = false;

        if (bet == 0) {
            return;
        }

        LOG.info("side bet outcome = " + bet);

        // Update the bankroll
        moneyManager.increase(bet);

        LOG.info("new bankroll = " + moneyManager.getBankroll());
    }

    /**
     * Informs view the game is starting
     */
    @Override
    public void starting() {
        sidebeton = true;
    }

    /**
     * Gets the side bet amount.
     *
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view
     */
    @Override
    public void update() {
    }

    /**
     * Renders the view
     *
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        // Draw the at-stake place on the table
        g.setColor(Color.RED);
        g.setStroke(dashed);
        g.drawOval(X - DIAMETER / 2, Y - DIAMETER / 2, DIAMETER, DIAMETER);

        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("" + amt, X - 5, Y + 5);

        //Draw sidebet details
        g.setFont(font2);
        g.setColor(Color.WHITE);
        g.drawString("SUPER 7 pays 3:1", X + 35, Y + 100);
        g.setFont(font2);
        g.setColor(Color.YELLOW);
        g.drawString("ROYAL MATCH pays 25:1", X + 35, Y + 120);
        g.setFont(font2);
        g.setColor(Color.WHITE);
        g.drawString("EXACTLY 13 pays 1:1", X + 35, Y + 140);

        //Draw chips beside sidebet stake   
        for (Chip chip : chips) {
            chip.render(g);
        }

        //Draw win or lose beside sidebet stake.
        if (checkbet < 0 && sidebeton == false) {
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.setBackground(Color.RED);
            g.clearRect(X+25, Y-25, 50, 25);
            g.drawString("Lose!", X + 25, Y - 5);

        } else if (checkbet > 0 && sidebeton == false) {
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.setBackground(Color.GREEN);
            g.clearRect(X+25, Y-25, 50, 25);
            g.drawString("WIN!", X + 25, Y - 5);

        }
    }
}
