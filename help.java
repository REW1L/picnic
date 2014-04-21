/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package picnic;

/**
 *
 * @author Rew1L
 */
public class help 
{

    /**
     *
     * @param start
     * @param finish
     * @return
     */
    public static String getLineOfItems(int start, int finish)
    {
        String str = "";
        for(int i = start; i<finish; i++)
        {
            str = str+Picnic.whatNeedToFind[i]+" ";
        }
        return str;
    }

    /**
     *
     * @param line what we want to check
     * @param page depth of scaning
     */
    public static void checkLink(String line, int page)
    {
        if(line.contains("<a href=\"") && !line.contains("video") && line.length()<150)
        {
            if(line.contains(Picnic.urls.get(0)))
            {
                line = line.replaceAll(".*<a href=\"", ""); //select only link
                line = line.replaceAll("\".*","");
                if((!Picnic.urls.contains(line)) && (page < 3)) //depth of scaning
                {
                    Picnic.urls.add(line); //adding new link
                    Picnic.numUrls++;
                    Thread newThread = new Thread(new findOnPage(page)); //new thread for finding on other page
                    if(page==1)
                        newThread.setPriority(Thread.NORM_PRIORITY);
                    else
                        newThread.setPriority(Thread.MIN_PRIORITY);
                    try
                    {
                        newThread.start();
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Can't create new thread.");
                    }
                }
            }
        }
    }
}
