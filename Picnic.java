/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package picnic;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Rew1L
 */
public class Picnic {

    /**
     * @param args the command line arguments
     */
    static LinkedList<String> urls = new LinkedList<String>();
    static LinkedList<String> urlsFound = new LinkedList<String>();
    static LinkedList<Integer> urlIndex = new LinkedList<Integer>();
    static LinkedList<Integer> lines = new LinkedList<Integer>();
    static int indexOfNextPage = 0;
    static int numUrls = 0, pages = 0, konec = 1, depth = 2;
    static String whatNeedToFind[];
    public static void main(String[] args)
    {
        if(args.length>0 && args[0].charAt(0)>47 && args[0].charAt(0)<59)
            depth = Integer.parseInt(args[0]);
        else
        {
            System.out.println("You should use java -jar <way_to_/picnic.jar> <number> <url> <word> <word2>...<wordN>");
            System.exit(1);
        }
        if(args.length>1)
        {
            if(!args[1].matches("http://.*"))
                args[1] = "http://"+args[1];
            urls.add(args[1]);
            if(args.length>2)
            {
                whatNeedToFind = new String[args.length-1];
                for(int i = 2; i<args.length; i++)
                    whatNeedToFind[i-1] = args[i];
                Thread newThread = new Thread(new findOnPage(0)); //first thread that implements finding
                newThread.setPriority(Thread.MAX_PRIORITY);
                newThread.start(); //start finding on first page
                System.out.println(args[1]+" "+help.getLineOfItems(1, args.length-1));
            }
            else
            {
                System.out.println("You should use java -jar <way_to_/picnic.jar> <number> <url> <word> <word2>...<wordN>");
                System.exit(3);
            }
        }
        else
        {
            System.out.println("You should use java -jar <way_to_/picnic.jar> <number> <url> <word> <word2>...<wordN>");
            System.exit(2);
        }
        int a = 0;
        konec--;
        while(true)
        {
            if(konec<=0)
            {
                System.out.println("Checked "+Picnic.urls.size()+" links.");
                System.out.println("Found "+urlsFound.size()+" matches.");
                System.exit(0);
            }
            try {
                //System.out.println("Konec = "+konec);
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Picnic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}