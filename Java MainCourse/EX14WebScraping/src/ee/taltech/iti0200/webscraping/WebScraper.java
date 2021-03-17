package ee.taltech.iti0200.webscraping;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileWriter;
import java.io.IOException;

public class WebScraper {

    private JSONArray array;
    private static FileWriter file;
    private static final int HREF_START_INDEX = 6;

    public WebScraper() {
        this.array = new JSONArray();
    }

    public void getJson(String url) {
        int pages = 0;
        try {
            final Document document = Jsoup.connect(url).get();
            pages = Integer.parseInt(document.select("span.page-count").text());
            handleData(document);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int pageNumber = 1;
        while (pageNumber <= pages) {
            try {
                pageNumber++;
                Document document = Jsoup.connect(url + "/page-" + pageNumber).get();
                handleData(document);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        try {
            file = new FileWriter("osta_arvutid.txt");
            file.write(array.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleData(Document document) {
        for (Element element : document.select(
                "ul.row.offers-list.js-main-offers-list.thumbs li")) {
            if (element.select(".offer-thumb__title").text().equals("")) {
                continue;
            } else {
                JSONObject object = new JSONObject();
                final String title =
                        element.select(".offer-thumb__title").text();
                object.put("Title", title);
                String price =
                        element.select(".price-cp.price-bn").text();
                if (price.equals("")) {
                    price = element.select(".price-cp").text();
                }
                object.put("Price", price.replace("€", ""));
                String helper =
                        element.select("figure.offer-thumb__image").html();
                String imageHref = "";
                for (String x : helper.split(" ")) {
                    if (x.contains("href")) {
                        imageHref = x;
                        break;
                    }
                }
                object.put("Href", imageHref.substring(HREF_START_INDEX, imageHref.length() - 1));
                array.add(object);
            }
        }
    }

    public static void main(String[] args) {
        WebScraper webScraper = new WebScraper();
        webScraper.getJson("https://www.osta.ee/kategooria/arvutid");
        System.out.println();
    }
}
