package spider.trademe

import spider.trademe.entities.Car
import spider.trademe.pipelines.CarModelPipeline
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.model.OOSpider

/**
 * Created by shenk on 12/8/2016.
 */
class TradeMeCarsSpider {

    private static Site site = Site.me().setRetryTimes(5).setSleepTime(20)

    static void main(String[] args) {

        OOSpider tradeMeSpider = OOSpider.create(site, new CarModelPipeline(), Car.class)
    }
}
