package spider.trademe

import spider.trademe.entities.Car
import spider.trademe.pipelines.CarModelPipeline
import spider.trademe.utils.GoogleSheets
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.model.OOSpider

/**
 * Created by shenk on 12/8/2016.
 */
class TradeMeCarsSpider {

    static String url = 'http://www.trademe.co.nz/Browse/CategoryAttributeSearchResults.aspx?search=1&cid=268&sidebar=1&5=&14=&18=0&18=7500&24=2006&24=0&54=&searchRegion=100&sidebarSearch_keypresses=0&sidebarSearch_suggested=0'

    private static Site site = Site.me().setRetryTimes(5).setSleepTime(20)

    static void main(String[] args) {

        OOSpider tradeMeSpider = OOSpider.create(site, new CarModelPipeline(), Car.class)
        tradeMeSpider.addUrl(url).thread(5).run()
        GoogleSheets.upload()
    }
}
