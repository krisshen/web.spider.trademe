package spider.trademe

import spider.trademe.entity.Property
import us.codecraft.webmagic.Request
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.model.ConsolePageModelPipeline
import us.codecraft.webmagic.model.OOSpider
import us.codecraft.webmagic.pipeline.FilePipeline

/**
 * Created by kris on 1/12/16.
 */
class TradeMeProcessor {

    //static String url = "http://www.trademe.co.nz/property/residential-property-for-sale/wellington/lower-hutt/price-range-200000-400000"
    static String url = "http://www.trademe.co.nz/property/residential-property-for-sale/wellington/lower-hutt"
//    static String url = "http://www.trademe.co.nz/browse/property/regionlistings.aspx?cid=3399&134=15&135=46&rptpath=350-5748-3399-&key=299854932&page=1&sort_order=prop_default"
    static String url2 = "http://www.trademe.co.nz/browse/property/regionlistings.aspx?cid=3399&134=15&135=46&rptpath=350-5748-3399-&key=*&page=*&sort_order=prop_default"
//    static String url3 = "http://www.trademe.co.nz/browse/property/regionlistings.aspx?cid=3399&134=15&135=46&rptpath=350-5748-3399-&key=*&page=3&sort_order=prop_default"

    private static Site site = Site.me().setRetryTimes(5).setSleepTime(20)

    static void main(String[] args) {

        OOSpider tradeMeProcessor = OOSpider.create(site, new PropertyModelPipeline(), Property.class)

        tradeMeProcessor.addUrl(url).addRequest(new Request(url2)).thread(5).run()

        GoogleSheets.upload()


    }

}
