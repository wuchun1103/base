package org.example.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class JsoupUtils {

    public static String earnings(int type) {
        String earning =null;
        try {
            Document doc = Jsoup.connect("http://f2pool.com/").timeout(5000).get();
            if (type == 0) { //BTC
                Elements elements = doc.select(".row-collapse");
                for (Element e : elements) {
                    if (e.attr("data-code").equals("btc")) {
                        earning= e.select(".pl-1.profit-val").text();
                    }
                }
            } else if (type == 1) {//ETH
                Elements elements = doc.select(".row-collapse");
                for (Element e : elements) {
                    if (e.attr("data-code").equals("eth")) {
                        earning = e.select(".pl-1.profit-val").text();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return earning;
    }
}
