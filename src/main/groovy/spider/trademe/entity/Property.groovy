package spider.trademe.entity

import org.apache.http.annotation.ThreadSafe
import us.codecraft.webmagic.model.annotation.ExtractBy
import us.codecraft.webmagic.model.annotation.HelpUrl
import us.codecraft.webmagic.model.annotation.TargetUrl

/**
 * Created by kris on 1/12/16.
 */
@TargetUrl(value = "http://www.trademe.co.nz/property/residential-property-for-sale/auction-*")
@HelpUrl(value = "http://www.trademe.co.nz/browse/property/regionlistings.aspx*", sourceRegion = "//*[@id=\"SearchResults\"]/table/tbody/tr/td[1]")
@ThreadSafe
class Property {

    @ExtractBy("//*[@id=\"ListingTitle_title\"]/tidyText()")
    public String title

    @ExtractBy("//*[@id=\"ListingTitle_noStatusListingNumberContainer\"]/tidyText()")
    public String tradeMeID

    @ExtractBy("//*[@id=\"ListingTitle_titleTime\"]/tidyText()")
    public String listedDate

    @ExtractBy("//*[@id=\"ListingAttributes\"]/tbody/tidyText()")
    public String information

    @ExtractBy("//*[@id=\"ListingAttributes\"]/tbody/tr/th/tidyText()")
    public ArrayList<String> headers

    @ExtractBy("//*[@id=\"ListingAttributes\"]/tbody/tr/td/tidyText()")
    public ArrayList<String> values

    @ExtractBy("//*[@id=\"lbThumbs\"]/*/*/img/@src")
    public ArrayList<String> photos

//    @ExtractBy("//*[@id=\"SearchResults\"]/table/tbody/tr/td[1]/a/@href")
//    public ArrayList<String> nextPages
}
