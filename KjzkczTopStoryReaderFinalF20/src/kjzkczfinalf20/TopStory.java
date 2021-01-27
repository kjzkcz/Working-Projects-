/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjzkczfinalf20;

/**
 *
 * @author Kevin
 */
public class TopStory {
    
    protected String section;
    protected String title;   
    //abstract
    protected String summary;
    protected String url;
    protected String byline;
    protected String item_type;

    TopStory(String section, String title, String summary, String url, String byline, String item_type) {
        this.section=section;
        this.title= title;
        this.summary=summary;
        this.url=url;
        this.byline=byline;
        this.item_type=item_type;
    }
    
}
