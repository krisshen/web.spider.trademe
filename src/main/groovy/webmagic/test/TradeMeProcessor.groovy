package webmagic.test

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.processor.PageProcessor
import us.codecraft.webmagic.selector.Selectable

/**
 * Created by kris on 30/11/16.
 */
public class TradeMeProcessor implements PageProcessor {

    static String url = "http://www.trademe.co.nz/Browse/CategoryAttributeSearchResults.aspx?search=1&cid=5748&sidebar=1&132=PROPERTY&selected135=46&134=15&135=46&216=0&216=0&217=0&217=0&153=&29=House&122=0&122=0&123=0&123=0&49=0&49=450000&178=0&178=0&sidebarSearch_keypresses=0&sidebarSearch_suggested=0"

    private Site site = Site.me().setRetryTimes(5).setSleepTime(10)

    @Override
    public void process(Page page) {

        //main result page
        List<Selectable> resultLinks = page.getHtml().xpath("//*[@class=\"dotted\"]").nodes()
        for (Selectable s : resultLinks) {
            page.addTargetRequest(s.links().toString())
        }

        //individual property page
        if (page.getUrl().toString().contains('residential')) {
            //save address and RV as result
//            page.putField(
//                    page.getHtml().xpath("//*[@id=\"ListingAttributes\"]/tbody/tr[1]/td/tidyText()").toString(),
//                    page.getHtml().xpath("//*[@id=\"ListingAttributes\"]/tbody/tr[6]/td/tidyText()").toString()
//            )

            //save title and all information as result
            page.putField(
                    page.getHtml().xpath("//*[@id=\"ListingTitle_title\"]/tidyText()").toString(),
                    page.getHtml().xpath("//*[@id=\"ListingAttributes\"]/tbody/allText()").toString()
            )

            //save listing id
            def id = page.getHtml().xpath("//*[@id=\"ListingTitle_noStatusListingNumberContainer\"]/tidyText()").toString().replace("Listing #: ", '')
            def idValue = id==null ? 'main' : id
            page.putField('id', idValue)

            //'description' xpath: //*[@id="ListingDescription_ListingDescription"]

            //save photo links
            List<Selectable> photoNodes = page.getHtml().xpath("//*[@id=\"lbThumbs\"]/*/*/img/@src").nodes()
            def photoLinks = []
            for (Selectable s : photoNodes) {
                String photoLink = s.nodes().get(0).toString().replace('thumb', 'full')
                photoLinks << photoLink
            }
            page.putField('img', photoLinks)
        }
    }

    @Override
    public Site getSite() {
        return site
    }

    public static void main(String[] args) {

        TradeMeProcessor tradeMeProcessor = new TradeMeProcessor()

        Spider tradeMeSpider = Spider.create(tradeMeProcessor)
        tradeMeSpider.addUrl(url)
        tradeMeSpider.addPipeline(new ImagePipeline())
        tradeMeSpider.thread(5)
        tradeMeSpider.run()
    }
}
