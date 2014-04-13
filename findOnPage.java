package picnic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rew1L
 */
public class findOnPage implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            URL newUrl; 
            BufferedReader rd;
            try
            {
                newUrl = new URL(Picnic.urls.get(Picnic.numUrls)); //select last link that we found
                InputStream is = newUrl.openConnection().getInputStream();
                rd = new BufferedReader(new InputStreamReader(is, "UTF-8")); //read page
            }
            catch(Exception ex)
            {
                return;
            }
            int lineNum = 0; //count lines
            String line;
            while((line = rd.readLine())!=null)
            {
                line = line.toLowerCase();//for better compare
                lineNum++; //number of this line
                if(line.contains("<a href=\""))
                {
                    line = line.replaceAll(".*<a href=\"", ""); //select only link
                    line = line.replaceAll("\".*","");
                    if((!Picnic.urls.contains(line)) && (Picnic.pages < 4)) //depth of scaning
                    {
                        Picnic.urls.add(line); //adding new link
                        Picnic.numUrls++;
                        Thread newThread = new Thread(new findOnPage()); //new thread for finding on other page
                        newThread.start();
                    }
                }
                if(line.contains("<p>") || line.contains("<h")) //finding in content and headers
                {
                    while(true)
                    {
                        for(int i = 0; i< Picnic.whatNeedToFind.length; i++)
                        {
                            if(line.contains(Picnic.whatNeedToFind[0])) //print line if it contains our word
                            {
                                System.out.println("Ссылка: "+newUrl.toString());
                                System.out.println("Строка: \""+lineNum+"\"\r\n");
                                //line = line.replaceAll(".*>", "");
                                //System.out.println(line+"\"\r\n");
                            }
                            if(line.contains("</p>") || line.contains("</h")) //exit if tag finished
                            {
                                break;
                            }
                            lineNum++;
                            line = rd.readLine();
                        }
                    }
                }
            }
            rd.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
        finally
        {
            Picnic.pages++; //increment depth
        }
    }
    
}
