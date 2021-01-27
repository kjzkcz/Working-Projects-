/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjzkczfinalf20;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public abstract class TopStoryModel {
    
    private ArrayList<TopStory> newsStories;
    public abstract ArrayList<TopStory> getTopStories();
    public abstract void load() throws Exception;
    public abstract void parseJsonNewsFeed2(String jsonString) throws Exception;
    public abstract void writeToFile(String filedata)throws IOException;
    public abstract int getNumNewsStories();
    
}
