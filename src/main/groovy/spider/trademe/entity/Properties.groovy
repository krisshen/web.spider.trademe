package spider.trademe.entity

import us.codecraft.webmagic.model.annotation.ExtractBy
import us.codecraft.webmagic.model.annotation.HelpUrl
import us.codecraft.webmagic.model.annotation.TargetUrl

/**
 * Created by kris on 1/12/16.
 */
@TargetUrl("http://www.trademe.co.nz/property/residential-property-for-sale/auction-*")
@HelpUrl("http://www.trademe.co.nz/property/residential-property-for-sale/wellington/lower-hutt/price-range-200000-400000")
class Properties {

    @ExtractBy("//*[@id=\"ListingTitle_title\"]/tidyText()")
    private String title

    @ExtractBy("//*[@id=\"ListingAttributes\"]/tbody/allText()")
    private String information

    private photos

}
