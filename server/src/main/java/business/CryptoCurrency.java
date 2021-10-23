package business;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.*;

public class CryptoCurrency {
    private String name;
    private double buyPrice;
    private double sellPrice;
    private boolean recommendBuy = false;
    private boolean recommendSell = false;

    public CryptoCurrency(String name, double buyPrice, double sellPrice) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public boolean isRecommendBuy() {
        return recommendBuy;
    }

    public void setRecommendBuy(boolean recommendBuy) {
        this.recommendBuy = recommendBuy;
    }

    public boolean isRecommendSell() {
        return recommendSell;
    }

    public void setRecommendSell(boolean recommendSell) {
        this.recommendSell = recommendSell;
    }

    public static Map<String, Map<String,CryptoCurrency>> getPrices() {
        Map<String, Map<String,CryptoCurrency>> currencies = new HashMap<>();

        currencies.put("Kraken", getKrakenPrices());
        currencies.put("CEX.IO", getCexPrices());

        for(String currency : new String[]{"Bitcoin", "Ethereum"}) {
            if(currencies.get("Kraken").get(currency).buyPrice > currencies.get("CEX.IO").get(currency).buyPrice) {
                currencies.get("CEX.IO").get(currency).recommendBuy = true;
            } else {
                currencies.get("Kraken").get(currency).recommendBuy = true;
            }

            if(currencies.get("Kraken").get(currency).sellPrice < currencies.get("CEX.IO").get(currency).sellPrice) {
                currencies.get("CEX.IO").get(currency).recommendSell = true;
            } else {
                currencies.get("Kraken").get(currency).recommendSell = true;
            }
        }

        return currencies;
    }

    public static Map<String,CryptoCurrency> getKrakenPrices()  {
        Map<String,CryptoCurrency> cryptos = new HashMap<>();

        JSONObject data = fetch("https://api.kraken.com/0/public/Ticker?pair=BTCUSD,ETHUSD");
        data = (JSONObject) data.get("result");
        JSONObject btc = (JSONObject) data.get("XXBTZUSD");
        JSONArray btc_a = (JSONArray) btc.get("a");
        JSONArray btc_b = (JSONArray) btc.get("b");
        cryptos.put("Bitcoin",new CryptoCurrency("Bitcoin"
                , Double.parseDouble((String) btc_a.get(0))
                , Double.parseDouble((String) btc_b.get(0))));

        JSONObject eth = (JSONObject) data.get("XETHZUSD");
        JSONArray eth_a = (JSONArray) eth.get("a");
        JSONArray eth_b = (JSONArray) eth.get("b");
        cryptos.put("Ethereum",new CryptoCurrency("Ethereum"
                , Float.parseFloat((String) eth_a.get(0))
                , Float.parseFloat((String) eth_b.get(0))));
        return cryptos;
    }

    public static Map<String,CryptoCurrency> getCexPrices()  {
        Map<String,CryptoCurrency> cryptos = new HashMap<>();

        JSONObject btc = fetch("https://cex.io/api/ticker/BTC/USD");
        Double bid = null,ask = null;
        if(btc.get("bid") instanceof Long) {
            bid = ((Long)btc.get("bid")).doubleValue();
            System.out.println("Bid was Long");
        } else bid = (Double) btc.get("bid");
        if(btc.get("ask") instanceof Long) {
            ask = ((Long)btc.get("ask")).doubleValue();
            System.out.println("Ask was Long");
        } else ask = (Double) btc.get("ask");
        cryptos.put("Bitcoin",new CryptoCurrency("Bitcoin"
                , ask, bid));

        JSONObject eth = fetch("https://cex.io/api/ticker/ETH/USD");
        bid = null;ask = null;
        if(eth.get("bid") instanceof Long) {
            bid = ((Long)eth.get("bid")).doubleValue();
            System.out.println("Bid was Long");
        } else bid = (Double) eth.get("bid");
        if(eth.get("ask") instanceof Long) {
            ask = ((Long)eth.get("ask")).doubleValue();
            System.out.println("Ask was Long");
        } else ask = (Double) eth.get("ask");

        cryptos.put("Ethereum",new CryptoCurrency("Ethereum"
                , ask,bid));

        return cryptos;
    }

    public static JSONObject fetch(String strUrl) {
        try {
            URL url = new URL(strUrl);
            URLConnection connection = url.openConnection();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            }
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline);

            System.out.println(data_obj.toString());

            return data_obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
