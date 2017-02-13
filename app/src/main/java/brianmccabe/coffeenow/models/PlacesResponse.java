package brianmccabe.coffeenow.models;

import java.io.Serializable;

/**
 * Created by brian on 29/01/2017.
 */

public class PlacesResponse implements Serializable {
    private String next_page_token;

    private Results[] results;

    private String[] html_attributions;

    private String status;

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    public String[] getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
