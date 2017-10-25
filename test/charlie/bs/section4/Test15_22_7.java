/* 
Team : Rakesh Nandigama & Vishnu Meduri
*/

package charlie.bs.section4;

import charlie.advisor.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.util.Play;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rakesh
 */
public class Test15_22_7 {
    
    public Test15_22_7() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
     public void test() {
        Hid hid = new Hid(Seat.YOU);
        Hand myHand = new Hand(hid);
        
        Card card1 = new Card(2,Card.Suit.CLUBS);
        Card card2 = new Card(2,Card.Suit.DIAMONDS); //Hand is a pair of 2's.
        
        myHand.hit(card1);
        myHand.hit(card2);
        
        Card upCard = new Card(Card.ACE,Card.Suit.HEARTS); //Dealer's upcard is Ace.
        
        Advisor advisor = new Advisor();
        
        Play advice = advisor.advise(myHand, upCard);
        
        assertEquals(advice, Play.HIT);                 //Hit is adviced.
     
}
}
