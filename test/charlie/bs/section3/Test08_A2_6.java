/* 
Team : Rakesh Nandigama & Vishnu Meduri
*/

package charlie.bs.section3;

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
public class Test08_A2_6 {
    
    public Test08_A2_6() {
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
        
        Card card1 = new Card(Card.ACE,Card.Suit.CLUBS);
        Card card2 = new Card(5,Card.Suit.DIAMONDS);        //Hand value is Ace,5
             
        myHand.hit(card1);
        myHand.hit(card2);
        
        Card upCard = new Card(4,Card.Suit.HEARTS);         //Dealer's upcard is 4
        
        Advisor advisor = new Advisor();
        
        Play advice = advisor.advise(myHand, upCard);
        
        assertEquals(advice, Play.DOUBLE_DOWN);             //Double Down is adviced
     
     
     }
}
