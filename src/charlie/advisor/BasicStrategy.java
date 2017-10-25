/* 
Team : Rakesh Nandigama & Vishnu Meduri
*/

package charlie.advisor;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;
import static charlie.util.Play.DOUBLE_DOWN;
import static charlie.util.Play.HIT;
import static charlie.util.Play.SPLIT;
import static charlie.util.Play.STAY;

/**
 *
 * @author Rakesh
 */
public class BasicStrategy {

    public final static Play H = HIT;
    public final static Play S = STAY;
    public final static Play D = DOUBLE_DOWN;
    public final static Play P = SPLIT;
    
   public Play getPlay(Hand myHand, Card upCard) {
        //Checking if Hand has pair
    if(myHand.isPair())                                                    
            return getSection4(myHand,upCard);
        
    //checking if Hand has an Ace.
        else if(myHand.getCard(0).isAce() || myHand.getCard(1).isAce() ) 
            return getSection3(myHand,upCard);
     
    // if Hand value between 5 & 11
        if (myHand.getValue() >= 5 && myHand.getValue() <= 11)          
        {
            return getSection2(myHand, upCard);
        }
        //Hand Value >= 12
        return getSection1(myHand,upCard);                              
    }

   //Section1 for Hand value >= 12
    protected Play getSection1(Hand myHand, Card upCard)                
    {
        Play[] plays17Plus = { S, S, S, S, S, S, S, S, S, S };          
        Play[] plays16 =     { S, S, S, S, S, H, H, H, H, H };
        Play[] plays15 =     { S, S, S, S, S, H, H, H, H, H };
        Play[] plays14 =     { S, S, S, S, S, H, H, H, H, H };
        Play[] plays13 =     { S, S, S, S, S, H, H, H, H, H };        
        Play[] plays12 =     { H, H, S, S, S, H, H, H, H, H };  
        
        Play[][] composite =
            { plays17Plus,
              plays16,
              plays15,
              plays14,
              plays13,
              plays12
            };
        
        int column;
        column = upCard.value() - 2;
        
        if(upCard.isAce())
            column = 9;
        
        int row = 0;
        
        if (myHand.getValue() >= 17) {
            row = 0;
        }
        else
            row = 17 - myHand.getValue(); 
        
        Play play = composite[row][column];

        return play;
    }

    //Section2 for Hand value between 5 & 11
    protected Play getSection2(Hand myHand, Card upCard)                
    {
           
        Play[] plays11     =     { D, D, D, D, D, D, D, D, D, H };
        Play[] plays10     =     { D, D, D, D, D, D, D, D, H, H };
        Play[] plays9      =     { H, D, D, D, D, H, H, H, H, H };
        Play[] plays5to8   =     { H, H, H, H, H, H, H, H, H, H };
        
        Play[][] composite2 =
            { plays11,
              plays10,
              plays9,
              plays5to8,
              
            };
        
          int column = upCard.value() - 2;
        
        if(upCard.isAce())
            column = 9;
        
          int row =0;
          
        if (myHand.getValue() >= 5 && myHand.getValue() <= 8 )          
        {
            row = 3;
        }
        else
            row = 11 - myHand.getValue(); 
        
        Play play = composite2[row][column];

        if(myHand.size()>2 && play==Play.DOUBLE_DOWN){
            return Play.HIT;
        }
       else return play;
       
    }

    //Section3 if Hand has an Ace
    protected Play getSection3(Hand myHand, Card upCard) {
        
        Play[] playsA8to10 = { S, S, S, S, S, S, S, S, S, S };
        Play[] playsA7     = { S, D, D, D, D, S, S, H, H, H };
        Play[] playsA6     = { H, D, D, D, D, H, H, H, H, H };
        Play[] playsA5     = { H, H, D, D, D, H, H, H, H, H };
        Play[] playsA4     = { H, H, D, D, D, H, H, H, H, H };
        Play[] playsA3     = { H, H, H, D, D, H, H, H, H, H};
        Play[] playsA2     = { H, H, H, D, D, H, H, H, H, H }; 
        
         Play[][] composite3 =
            { playsA8to10,
              playsA7,  
              playsA6,
              playsA5,
              playsA4,
              playsA3,
              playsA2,
                  
            };
        
           int column = upCard.value() - 2;
        
        if(upCard.isAce())
            column = 9;
        
           int row =0;
           
         
             if (myHand.getCard(0).isAce() )  {
        if(myHand.getCard(1).value()>=8 && myHand.getCard(1).value()<=10 )
            row=0;
        else
            row = 8 - myHand.getCard(1).value();
                
             }
                
             if (myHand.getCard(1).isAce() )  {
           if(myHand.getCard(0).value()>=8 && myHand.getCard(0).value()<=10 )
            row=0;
        else
            row = 8 - myHand.getCard(0).value();
                
            
        }
           
       Play play = composite3[row][column];

        return play;
        
        }

     
//Section4 if hand has pair
    protected Play getSection4(Hand myHand, Card upCard) {
        
        Play[] playsAA88   = { P, P, P, P, P, P, P, P, P, P };
        Play[] plays1010   = { S, S, S, S, S, S, S, S, S, S };
        Play[] plays99     = { P, P, P, P, P, S, P, P, S, S };
        Play[] plays77     = { P, P, P, P, P, P, H, H, H, H };
        Play[] plays66     = { P, P, P, P, P, H, H, H, H, H };
        Play[] plays55     = { D, D, D, D, D, D, D, D, H, H };
        Play[] plays44     = { H, H, H, P, P, H, H, H, H, H };
        Play[] plays33     = { P, P, P, P, P, P, H, H, H, H };        
        Play[] plays22     = { P, P, P, P, P, P, H, H, H, H };  
        
        
           Play[][] composite4 =
            { playsAA88,
              plays1010,  
              plays99,
              plays77,
              plays66,
              plays55,
              plays44,
              plays33,
              plays22      
            };
        
        
            int column = upCard.value() - 2;
        
        if(upCard.isAce())
            column = 9;
        
           int row =0;
           
           
             if (myHand.getCard(0).isAce()|| myHand.getValue() == 16 )  {
            row = 0;
            
        }
             else if( myHand.getValue() == 20 ||  myHand.getValue() == 18)
            row = ((20 - myHand.getValue())/2) + 1; 
             
              else 
            row = ((20 - myHand.getValue())/2); 
        
           Play play = composite4[row][column];

        return play;
        }
        
    }