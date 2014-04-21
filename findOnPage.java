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
public class findOnPage extends Thread
{
    int page;

    /**
     *
     * @param prevPage
     */
    public findOnPage(int prevPage)
    {
        Picnic.indexOfNextPage++;
        this.page = prevPage+1;
        Picnic.konec++;
    }
    @Override
    public void run()
    {
        try
        {
            URL newUrl; 
            BufferedReader rd;
            InputStream is;
            try
            {
                newUrl = new URL(Picnic.urls.get(Picnic.numUrls)); //select last link that we found
                is = newUrl.openConnection().getInputStream();
                rd = new BufferedReader(new InputStreamReader(is, "UTF-8")); //read page
            }
            catch(Exception ex)
            {
                Picnic.konec--;
                return;
            }
            int lineNum = 0; //count lines
            String line;
            while((line = rd.readLine())!=null)
            {
                line = line.toLowerCase();//for better compare
                lineNum++; //number of this line
                help.checkLink(line,this.page);
                if(line.matches(".*(<p .*>|<h.? .*>)")) //finding in content and headers
                {
                    while(line!=null)
                    {
                        help.checkLink(line,this.page);
                        for(int i = 0; i< Picnic.whatNeedToFind.length; i++)
                        {
                            for(int g = i+1; g<Picnic.whatNeedToFind.length;g++)
                            {
                                if(line.contains(help.getLineOfItems(i, g))) //print line if it contains our word
                                {
                                    if(Picnic.urlsFound.contains(newUrl.toString()))
                                    {
                                        int temp = Picnic.urlsFound.indexOf(newUrl.toString());
                                        Picnic.urlIndex.set(temp, Picnic.urlIndex.get(temp)+(g-i));
                                    }
                                    else
                                    {
                                        Picnic.urlsFound.add(newUrl.toString());
                                        Picnic.lines.add(Picnic.urlsFound.indexOf(newUrl.toString()), lineNum);
                                        Picnic.urlIndex.add(Picnic.urlsFound.indexOf(newUrl.toString()), 1);
                                        System.out.println("\r\nСсылка: "+newUrl.toString());
                                        System.out.println("Строка: "+lineNum);
                                    }
                                }
                            }
                        }
                        if(line.contains("</p>") || line.contains("</h")) //exit if tag finished
                            break;
                        lineNum++;
                        line = rd.readLine();
                        line = line.toLowerCase();
                    }
                }
            }
            is.close();
            rd.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString()+" in findOnPage.java");
            
        }
        finally
        {
            Picnic.konec--;
        }
    }
    
}
