/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package picnic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
/**
 *
 * @author Rew1L
 */
public class Picnic {

    /**
     * @param args the command line arguments
     */
    static ArrayList<String> urls = new ArrayList<String>();
    static int numUrls = 0, pages = 0;
    static String whatNeedToFind[];
    public static void main(String[] args)
    {
        if(args.length>0)
        {
            if(!args[0].matches("http://.*"))
                args[0] = "http://"+args[0];
            urls.add(args[0]);
            if(args.length>1)
            {
                whatNeedToFind = new String[args.length-1];
                for(int i = 1; i<args.length; i++)
                    whatNeedToFind[i-1] = args[i];
                Thread newThread = new Thread(new findOnPage()); //first thread that implements finding
                newThread.start(); //start finding on first page
            }
            else
                System.out.println("You should use -jar <way_to_/picnic.jar> <url> <word> <word2>...<wordN>");
        }
        else
            System.out.println("You should use -jar <way_to_/picnic.jar> <url> <word> <word2>...<wordN>");
    }
}