package ee.taltech.iti0200.webbrowser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;
import static java.util.stream.Collectors.toMap;

public class WebBrowser {
    private String homePage = "google.com";
    private ArrayList<String> history = new ArrayList<>(Collections.singletonList("google.com"));
    private ArrayList<String> activeHistory = new ArrayList<>(Collections.singletonList("google.com"));
    private Map<String, Integer> visits = new LinkedHashMap<>();
    private ArrayList<String> bookmarks = new ArrayList<>();
    private int historyPos = 0;
    private int activePos = 0;
    private int stepsBack = 0;
    private final int nextPos = 1;

    /**
     * Goes to homepage.
     */
    public void homePage() {
        history.add(homePage);
        historyPos = history.size() - nextPos;
        int size = activeHistory.size();
        if (size > activePos + nextPos) {
            activeHistory.subList(activePos + nextPos, size).clear();
        }
        activePos += nextPos;
        activeHistory.add(homePage);
    }

    /**
     * Goes back to previous page.
     */
    public void back() {
        int higherThanNegative = 0;
        int posBefore = 1;
        if (activePos - posBefore >= higherThanNegative) {
            activePos -= posBefore;
            historyPos -= posBefore;
            history.add(history.get(historyPos));
            activeHistory.add(activeHistory.get(activePos));
            int addStep = 1;
            stepsBack += addStep;
        }
    }

    /**
     * Goes forward to next page.
     */
    public void forward() {
        if (activePos < activeHistory.size() - 1 && stepsBack > 0) {
            historyPos += nextPos;
            activePos += nextPos;
            history.add(history.get(historyPos));
            int removeStep = 1;
            stepsBack -= removeStep;
        }
    }

    /**
     * Go to a webpage.
     *
     * @param url url to go to
     */
    public void goTo(String url) {
        history.add(url);
        historyPos = history.size() - nextPos;
        int size = activeHistory.size();
        if (size > activePos + nextPos) {
            activeHistory.subList(activePos + nextPos, size).clear();
        }
        activePos += nextPos;
        activeHistory.add(url);
    }

    /**
     * Add a webpage as a bookmark.
     */
    public void addAsBookmark() {
        if (!bookmarks.contains(getCurrentUrl())) {
            bookmarks.add(getCurrentUrl());
        }
    }

    /**
     * Remove a bookmark.
     *
     * @param bookmark to remove
     */
    public void removeBookmark(String bookmark) {
        bookmarks.remove(bookmark);
    }

    public List<String> getBookmarks() {
        return bookmarks;
    }

    public void setHomePage(String newHomePage) {
        homePage = newHomePage;
    }


    /**
     * Get top 3 visited pages.
     *
     * @return a String that contains top three visited pages separated with a newline "\n"
     */
    public String getTop3VisitedPages() {
        if (history.isEmpty()) {
            return "";
        }
        for (String webpage : history) {
            int addOne = 1;
            if (!visits.containsKey(webpage)) {
                visits.put(webpage, addOne);
            } else {
                int count = visits.get(webpage) + addOne;
                visits.replace(webpage, count);
            }
        }
        Map<String, Integer> sortedVisits = visits
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        StringBuilder top3 = new StringBuilder();
        int counter = 1;
        for (Map.Entry<String, Integer> entry : sortedVisits.entrySet()) {
            if (counter <= 3) {
                if (entry.getValue() > 1) {
                    top3.append(entry.getKey()).append(" - ").append(entry.getValue()).append(" visits\n");
                } else if (entry.getValue() == 1) {
                    top3.append(entry.getKey()).append(" - ").append(entry.getValue()).append(" visit\n");
                }
                counter += 1;
            }
        }
        return top3.toString();
    }

    /**
     * Returns a list of all visited pages.
     *
     * Not to be confused with pages in your back-history.
     *
     * For example, if you visit "facebook.com" and hit back(),
     * then the whole history would be: ["google.com", "facebook.com", "google.com"]
     * @return list of all visited pages
     */
    public List<String> getHistory() {
        return history;
    }


    /**
     * Returns the active web page (string).
     *
     * @return active web page
     */
    public String getCurrentUrl() {
        return activeHistory.get(activePos);
    }
}
