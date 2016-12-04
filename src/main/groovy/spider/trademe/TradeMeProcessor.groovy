package spider.trademe

import spider.trademe.entity.Property
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.model.ConsolePageModelPipeline
import us.codecraft.webmagic.model.OOSpider
import us.codecraft.webmagic.pipeline.FilePipeline

/**
 * Created by kris on 1/12/16.
 */
class TradeMeProcessor {

    static String url = "http://www.trademe.co.nz/property/residential-property-for-sale/wellington/lower-hutt/price-range-200000-400000"

    private static Site site = Site.me().setRetryTimes(5).setSleepTime(10)

    public static void main(String[] args) {
        OOSpider tradeMeProcessor = OOSpider.create(site, new PropertyModelPipeline(), Property.class)

        tradeMeProcessor.addUrl(url).thread(5).run()

    }

}
