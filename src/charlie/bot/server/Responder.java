package charlie.bot.server;

import charlie.advisor.BasicStrategy;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.util.Play;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Rakesh
 */
class Responder implements Runnable {

    private final Logger LOG = LoggerFactory.getLogger(Responder.class);
    Hand dealerHand;
    Hand myHand;
    Card upCard;
    Dealer dealer;
    B9 me;

    public Responder(B9 aThis, Hand myHand, Dealer dealer, Hand DHand) {

        this.myHand = myHand;
        this.dealerHand = DHand;
        this.dealer = dealer;
        this.me = aThis;

        // LOG.info("upCard with Bot B9 = "+upCard);
    }

    @Override
    public void run() {
        //gets the upCard of dealer
        upCard = dealerHand.getCard(0);

        Hid hid = myHand.getHid();

        //gets the Play from Basic Strategy
        BasicStrategy bs = new BasicStrategy();
        Play play = bs.getPlay(myHand, upCard);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Responder.class.getName()).log(Level.SEVERE, null, ex);
        }
        //bot hits
        if (play == Play.HIT) {
            dealer.hit(me, hid);

        } //bot stays
        else if (play == Play.STAY) {
            dealer.stay(me, hid);
            LOG.info("Bot B9 stays the turn");
            me.myTurn = false;

        } //bot double's down
        else if (play == Play.DOUBLE_DOWN) {

            if (myHand.size() > 2) {
                dealer.hit(me, hid);
            } else {
                myHand.dubble();
                dealer.doubleDown(me, hid);
                me.myTurn = false;
            }
        } else if (play == Play.SPLIT) {
            if (myHand.getValue() < 10) {
                dealer.hit(me, hid);
            } else if (myHand.getValue() == 12 || myHand.getValue() == 14) {
                dealer.hit(me, hid);
            } else if (myHand.getValue() == 16 && upCard.value() < 7) {
                dealer.stay(me, hid);
                me.myTurn = false;
            } else if (myHand.getValue() == 16 && upCard.value() > 7) {
                dealer.hit(me, hid);
            } else if (myHand.getValue() > 18) {
                dealer.stay(me, hid);
                me.myTurn = false;
            }
        }

    }
}
