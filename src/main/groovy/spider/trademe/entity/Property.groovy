package spider.trademe.entity

import groovyjarjarantlr.collections.List
import us.codecraft.webmagic.model.annotation.ExtractBy
import us.codecraft.webmagic.model.annotation.HelpUrl
import us.codecraft.webmagic.model.annotation.TargetUrl

/**
 * Created by kris on 1/12/16.
 */
@TargetUrl(value = "http://www.trademe.co.nz/property/residential-property-for-sale/auction-*")
@HelpUrl("http://www.trademe.co.nz/property/residential-property-for-sale/wellington/lower-hutt/price-range-200000-400000")
class Property {

    @ExtractBy("//*[@id=\"ListingTitle_title\"]/tidyText()")
    public String title

    @ExtractBy("//*[@id=\"ListingAttributes\"]/tbody/tidyText()")
    public String information

    @ExtractBy("//*[@id=\"lbThumbs\"]/*/*/img/@src")
    public ArrayList<String> photos

}
