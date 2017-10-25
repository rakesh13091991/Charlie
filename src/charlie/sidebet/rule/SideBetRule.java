package charlie.sidebet.rule;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.ISideBetRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the following class gets the sidebet amount and checks whether any of the 
 * side bets match and gives the highest pay out for the sidebet
 * 
 * @author Rakesh
 */
public class SideBetRule implements ISideBetRule {

    private final Logger LOG = LoggerFactory.getLogger(SideBetRule.class);

    /**the following variables tell us how much the bet amount
     * should be multiplied with for a particular side bet
     */
    private final Double SUPER7 = 3.0;
    private final Double ROYALMATCH = 25.0;
    private final Double EXACTLY13 = 1.0;
    
    private Double PAYOFF = 0.0;
    private Double PAYOFF_SUPER7 = 0.0;
    private Double PAYOFF_ROYALMATCH = 0.0;
    private Double PAYOFF_EXACTLY13 = 0.0;

    /**
     * Apply rule to the hand and return the pay out if the rule matches and the
     * negative bet if the rule does not match.
     *
     * @param hand Hand to analyze.
     * @return
     */
    @Override
    public double apply(Hand hand) {

        //the sidebet amount is taken from hand through hid.
        Double bet = hand.getHid().getSideAmt();
        LOG.info("side bet amount = " + bet);

        //if no sidebet is placed then we return with no outcome.
        if (bet == 0) {
            return 0.0;
        }

        LOG.info("side bet rule applying hand = " + hand);

        Card card = hand.getCard(0);
        Card card2 = hand.getCard(1);

        //checking for Super 7 side bet.
        if (card.getRank() == 7) {
            LOG.info("side bet SUPER 7 matches");
            PAYOFF_SUPER7 = bet * SUPER7;
        }

        //checking for Royal match side bet.
        if (card.isFace()) {
            if (card.getRank() == Card.KING && card2.getRank() == Card.QUEEN) {
                if (card.getSuit() == card2.getSuit()) {
                    LOG.info("side bet Royal Match ");
                    PAYOFF_ROYALMATCH = bet * ROYALMATCH;
                }
            }

            if (card.getRank() == Card.QUEEN && card2.getRank() == Card.KING) {
                if (card.getSuit() == card2.getSuit()) {
                    LOG.info("side bet Royal Match ");
                    PAYOFF_ROYALMATCH = bet * ROYALMATCH;
                }
            }

        }

        //Checking for Exactly 13 side bet.
        if (card.value() + card2.value() == 13) {
            LOG.info("side bet Exactly 13 ");
            PAYOFF_EXACTLY13 = bet * EXACTLY13;
        }

        //the maximum payoff amount won
        PAYOFF = max(PAYOFF_SUPER7, PAYOFF_ROYALMATCH, PAYOFF_EXACTLY13);

        PAYOFF_SUPER7 = 0.0;
        PAYOFF_ROYALMATCH = 0.0;
        PAYOFF_EXACTLY13 = 0.0;

        if (PAYOFF != 0.0) {
            return PAYOFF;
        }

        LOG.info("side bet rule no match");

        return -(bet);
    }

    /** 
     * this method gives back the highest payoff amount .
     * 
     * @param SUPER7
     * @param ROYALMATCH
     * @param EXACTLY13
     * @return 
     */
    private Double max(Double SUPER7, Double ROYALMATCH, Double EXACTLY13) {
        if (SUPER7 > ROYALMATCH) {
            if (SUPER7 > EXACTLY13) {
                return SUPER7;
            } else {
                return EXACTLY13;
            }
        } else if (ROYALMATCH > EXACTLY13) {
            return ROYALMATCH;
        } else {
            return EXACTLY13;
        }

    }

}
