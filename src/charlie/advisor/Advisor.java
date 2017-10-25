/* 
Team : Rakesh Nandigama & Vishnu Meduri
*/

package charlie.advisor;

import charlie.plugin.IAdvisor;
import charlie.util.Play;
import charlie.card.Card;
import charlie.card.Hand;

/**
 *
 * @author Rakesh
 */

public class Advisor implements IAdvisor  {
    @Override
    public Play advise (Hand myHand,Card upCard)
    {
        //object created for BasicStrategy 
        BasicStrategy bs = new BasicStrategy();  
        
       //getPlay method is called with Hand value and Dealer's upcard from BasicStrategy class
        
        return bs.getPlay(myHand, upCard);  
    }
}