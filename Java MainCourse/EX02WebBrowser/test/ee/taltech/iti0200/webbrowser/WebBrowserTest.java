package ee.taltech.iti0200.webbrowser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


class WebBrowserTest {
    WebBrowser webBrowser;

    private final int fifteenTimes = 15;

    @BeforeEach
    void setUp() {
        webBrowser = new WebBrowser();
    }

    @Test
    void homePage() {
        webBrowser.goTo("twitter.com");
        webBrowser.homePage();
        assertEquals("google.com", webBrowser.getCurrentUrl());
    }

    @Nested
    @DisplayName("BackTests")
    class Back {

        @Test
        void backSimple() {
            webBrowser.goTo("twitter.com");
            webBrowser.goTo("terminal.com");
            webBrowser.goTo("tide.com");
            webBrowser.back();
            webBrowser.back();
            assertEquals("twitter.com", webBrowser.getCurrentUrl());
        }

        @Test
        void backComplex() {
            ArrayList<String> history = new ArrayList<>(Arrays.asList("google.com"));
            for (int i = 0; i < fifteenTimes; i++) {
                history.add(i + ".com");
                webBrowser.goTo(i + ".com");
            }
            for (int i = 0; i < 5; i++) {
                webBrowser.back();
            }
            webBrowser.homePage();
            for (int i = 0; i < 5; i++) {
                webBrowser.back();
            }
            assertEquals("5.com", webBrowser.getCurrentUrl());
        }
    }

    @Nested
    @DisplayName("Forward")
    class Forward {

        @Test
        void forward1() {
            webBrowser.goTo("twitter.com");
            webBrowser.homePage();
            webBrowser.forward();
            assertEquals("google.com", webBrowser.getCurrentUrl());
        }

        @Test
        void forward2() {
            webBrowser.goTo("twitter.com");
            webBrowser.goTo("twitter1.com");
            webBrowser.goTo("twitter2.com");
            webBrowser.goTo("twitter3.com");
            webBrowser.back();
            webBrowser.back();
            webBrowser.back();
            webBrowser.forward();
            webBrowser.forward();
            assertEquals("twitter2.com", webBrowser.getCurrentUrl());
        }
    }

    @Test
    void goTo() {
        webBrowser.goTo("twitter.com");
        assertEquals("twitter.com", webBrowser.getCurrentUrl());
        ArrayList<String> history = new ArrayList<>(Arrays.asList("google.com", "twitter.com"));
        assertEquals(history, webBrowser.getHistory());
    }

    @Test
    void addAsBookmark() {
        webBrowser.goTo("twitter.com");
        webBrowser.addAsBookmark();
        ArrayList<String> bookMarks = new ArrayList<>(Collections.singletonList("twitter.com"));
        assertEquals(bookMarks, webBrowser.getBookmarks());
    }

    @Test
    void removeBookmark() {
        webBrowser.goTo("twitter.com");
        webBrowser.addAsBookmark();
        webBrowser.removeBookmark("twitter.com");
        ArrayList<String> bookMarks = new ArrayList<>();
        assertEquals(bookMarks, webBrowser.getBookmarks());
    }

    @Test
    void getBookmarks() {
        webBrowser.goTo("youtube.com");
        webBrowser.addAsBookmark();
        webBrowser.goTo("twitter.com");
        webBrowser.addAsBookmark();
        ArrayList<String> bookMarks = new ArrayList<>(Arrays.asList("youtube.com", "twitter.com"));
        assertEquals(bookMarks, webBrowser.getBookmarks());
    }

    @Test
    void setHomePage() {
        webBrowser.setHomePage("taltech.ee");
        webBrowser.goTo("twitter.com");
        webBrowser.homePage();
        assertEquals("taltech.ee", webBrowser.getCurrentUrl());
    }

    @Nested
    @DisplayName("Top3")
    class GetTop3VisitedPages {

        @Test
        void getTop3VisitedPages2To1() {
            webBrowser.goTo("twitter.com");
            webBrowser.goTo("tweet.com");
            webBrowser.goTo("neti.com");
            webBrowser.homePage();
            assertEquals("google.com - 2 visits\ntwitter.com - 1 visit\ntweet.com - 1 visit\n",
                    webBrowser.getTop3VisitedPages());
        }

        @Test
        void getTop3VisitedPages1Visit() {
            webBrowser.goTo("twitter.com");
            assertEquals("google.com - 1 visit\ntwitter.com - 1 visit\n", webBrowser.getTop3VisitedPages());
        }
    }

    @Test
    void getHistory() {
        webBrowser.goTo("twitter.com");
        webBrowser.back();
        webBrowser.goTo("youtube.com");
        webBrowser.homePage();
        ArrayList<String> history = new ArrayList<>(Arrays.asList("google.com", "twitter.com", "google.com",
                "youtube.com", "google.com"));
        assertEquals(history, webBrowser.getHistory());
    }

    @Nested
    @DisplayName("Url tests")
    class GetCurrentUrl {

        @Test
        void getCurrentUrlSimple() {
            webBrowser.goTo("twitter.com");
            assertEquals("twitter.com", webBrowser.getCurrentUrl());
        }

        @Test
        void getCurrentUrlComplex() {
            webBrowser.goTo("twitter.com");
            webBrowser.back();
            webBrowser.goTo("neti.ee");
            webBrowser.goTo("youtube.com");
            webBrowser.homePage();
            webBrowser.back();
            webBrowser.back();
            assertEquals("neti.ee", webBrowser.getCurrentUrl());
        }
    }
}
