package spider.trademe.entities

import org.apache.http.annotation.ThreadSafe
import us.codecraft.webmagic.model.annotation.ExtractBy
import us.codecraft.webmagic.model.annotation.HelpUrl
import us.codecraft.webmagic.model.annotation.TargetUrl

/**
 * Created by shenk on 12/8/2016.
 */
@TargetUrl(value = "http://www.trademe.co.nz/motors/used-cars/*/auction-*.htm")
@HelpUrl(value = "http://www.trademe.co.nz/browse/categoryattributesearchresults.aspx?*", sourceRegion = "//*[@id=\"PagingFooter\"]/tbody/tr/td[1]")
@ThreadSafe
class Car {

    //Car information page
    @ExtractBy("//*[@id=\"ListingTitle_title\"]/tidyText()")
    public String title

    @ExtractBy("//*[@id=\"ListingTitle_classifiedTitlePrice\"]/tidyText()")
    public String askingPrice

    //save with start price
    @ExtractBy("//*[@id=\"ListingTitle_auctionTitleBids\"]/tidyText()")
    public String currentBid

    @ExtractBy("//*[@id=\"QuickBid_buyNowText\"]/tidyText()")
    public String buyNowPrice

    @ExtractBy("//*[@id=\"ListingTitle_noStatusListingNumberContainer\"]/tidyText()")
    public String tradeMeID

    @ExtractBy("//*[@id=\"ListingTitle_titleTime\"]/tidyText()")
    public String listedDate

    @ExtractBy("//*[@id=\"ListingAttributes\"]/tbody/tr/th/tidyText()")
    public ArrayList<String> headers

    @ExtractBy("//*[@id=\"ListingAttributes\"]/tbody/tr/td/tidyText()")
    public ArrayList<String> values
}
