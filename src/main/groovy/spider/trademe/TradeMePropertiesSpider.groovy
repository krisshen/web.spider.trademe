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
class TradeMePropertiesSpider {

    static String url_LH = "http://www.trademe.co.nz/property/residential-property-for-sale/wellington/lower-hutt"
    static String url_WL = "http://www.trademe.co.nz/property/residential-property-for-sale/wellington/wellington"
    static String url_PR = "http://www.trademe.co.nz/property/residential-property-for-sale/wellington/porirua"

    static urls = [url_LH, url_WL, url_PR]

    private static Site site = Site.me().setRetryTimes(5).setSleepTime(20)

    static void main(String[] args) {

        OOSpider tradeMeSpider = OOSpider.create(site, new PropertyModelPipeline(), Property.class)

        urls.each { url ->
            tradeMeSpider.addUrl(url).thread(5).run()
            GoogleSheets.upload()
        }
    }

}
