/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjzkczfinalf20;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Kevin
 */
public class TopStoryManager extends TopStoryModel {
    private String urlString = "";
    ////https://api.nytimes.com/svc/topstories/v2/arts.json?api-key=wzDvcKfrMrW9S5ZAuJLU2CMG1jrWHgaZ
    private final String topStoryTopic;
    public final String topStoryUrlString;
    private final String apiKey2="wzDvcKfrMrW9S5ZAuJLU2CMG1jrWHgaZ";
    private URL url;
    private ArrayList<TopStory> newsStories;
    //private PrintWriter out= new PrintWriter("articleData.txt");
    public TopStoryManager(){
        topStoryTopic="arts";
        topStoryUrlString="https://api.nytimes.com/svc/topstories/v2/"+topStoryTopic+".json?api-key=";
        newsStories = new ArrayList<>();
        
    }
    public TopStoryManager(String section){
        topStoryTopic=section;
        topStoryUrlString="https://api.nytimes.com/svc/topstories/v2/"+topStoryTopic+".json?api-key=";
        newsStories = new ArrayList<>();
        
    }
    
    //public void load(String searchString) throws Exception {
    @Override
    public void load() throws Exception {

        urlString=topStoryUrlString+apiKey2;
        System.out.println("urlString: " + urlString);
        
        try {
            url = new URL(urlString); //creates url for opening, combination of base url and the query
        } catch(MalformedURLException muex){
            throw muex; 
        }
        
        String jsonString = ""; 
        
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream())); //line where the code tries to open the url
            
            String inputLine; 
            
            while((inputLine = in.readLine()) != null){
                jsonString += inputLine; 
            }
            
            in.close();
            
        } catch(IOException ioex){
            throw ioex; 
        }
        
        System.out.print("jsonString: " + jsonString);
        
        try {
            parseJsonNewsFeed2(jsonString); 
        } catch (Exception ex){
            throw ex; 
        }
    }
    
    @Override
    public void parseJsonNewsFeed2(String jsonString) throws Exception {
        //changed to work with the top stories api and not the seach api shown in class, and writes the json string into a data file
        newsStories.clear();
        
        if (jsonString == null || jsonString == "") return;
        
        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject)JSONValue.parse(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
        
        if (jsonObj == null) return;
        
        String status = "";
        try {
            status = (String)jsonObj.get("status");
        } catch (Exception ex) {
            throw ex;
        }
        
        if (status == null || !status.equals("OK")) {
            throw new Exception("Status returned from API was not OK.");
        }
        
        //the top stories api only has a json object that contains a jsonarray, 
        //i pulled the array directly from the jsonobject
        JSONArray results;
        try {
            results=(JSONArray)jsonObj.get("results");
            } catch (Exception ex) {
            throw ex;
        }
      
        for (Object doc : results) {
            try {
                JSONObject story = (JSONObject)doc;
                String section = (String)story.getOrDefault("section", "");
                String title = (String)story.getOrDefault("title", "");
                String summary = (String)story.getOrDefault("abstract", "");//abstract
                String url = (String)story.getOrDefault("url", "");                
                String byline = (String)story.getOrDefault("byline", "");
                String item_type = (String)story.getOrDefault("item_type", "");

                
                System.out.println("section: " + section + "\n");
                System.out.println("title: " + title + "\n");
                System.out.println("abstract: " + summary + "\n");
                System.out.println("url: " + url + "\n");
                System.out.println("byline: " + byline + "\n");
                System.out.println("item_type: " + item_type + "\n");
                System.out.println("------------------------------------------------------\n");
                
                writeToFile(jsonString);
                
                
                TopStory newsStory = new TopStory(section,title,summary,url,byline,item_type);
                newsStories.add(newsStory);
                
            } catch (Exception ex) {
                throw ex;
            }
            
        }
        
    }
    @Override
    public void writeToFile(String filedata) throws IOException{
        FileWriter writer = new FileWriter("articleData.txt");
       try{
           
           writer.write(filedata);
           writer.close();
           System.out.println("Written to file");
       }catch (IOException e){
           throw e;
       }
       
    }
    
        
        
        
    
    @Override
    public ArrayList<TopStory> getTopStories() {
        return newsStories;
    }
    
    
    @Override
    public int getNumNewsStories() {        
        return newsStories.size();
    }


    
    
    
}
