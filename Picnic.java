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
    static String whatNeedToFind = "";
    public static void main(String[] args)
    {
        whatNeedToFind = "Ukraine"; //What need find
        urls.add("http://www.nytimes.com/"); //site that we use
        if(args.length>0)
            urls.set(0, args[0]);
        if(args.length>1)
            whatNeedToFind = args[1];
        Thread newThread = new Thread(new findOnPage()); //first thread that implements finding
        newThread.start(); //start finding on first page
    }
}