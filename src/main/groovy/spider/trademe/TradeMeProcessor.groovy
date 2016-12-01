package spider.trademe

import spider.trademe.entity.Properties
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.model.ConsolePageModelPipeline
import us.codecraft.webmagic.model.OOSpider
import us.codecraft.webmagic.pipeline.FilePipeline

/**
 * Created by kris on 1/12/16.
 */
class TradeMeProcessor {

    static String url = "http://www.trademe.co.nz/property/residential-property-for-sale/wellington/lower-hutt/price-range-200000-400000"

    public static void main(String[] args) {
        OOSpider.create(Site.me().setRetryTimes(5).setSleepTime(10)
                , new ConsolePageModelPipeline(), Properties.class)
                .addUrl(url).addPipeline(new FilePipeline("test")).thread(5).run()
    }

}
