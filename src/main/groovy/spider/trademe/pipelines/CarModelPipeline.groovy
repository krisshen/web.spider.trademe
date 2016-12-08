package spider.trademe.pipelines

import groovy.transform.Synchronized
import org.apache.http.annotation.ThreadSafe
import spider.trademe.entities.Car
import spider.trademe.utils.GoogleSheets
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.PageModelPipeline

/**
 * Created by shenk on 12/8/2016.
 */
@ThreadSafe
class CarModelPipeline implements PageModelPipeline {

    Car car

    @Override
    @Synchronized
    void process(Object o, Task task) {

        car = (Car) o
        println '=================================================='
        println car.title

        def mapData = [:]
        mapData['title'] = car.title.trim().replace('\n', '')
        mapData['trademe id'] = car.tradeMeID.replace('\n', '').replace('Listing #: ', '')
        mapData['listed date'] = car.listedDate.replace('\n', '').replace('* Listed ', '')
        mapData['asking price'] = car.askingPrice?.toString()?.replace(' Or Near Offer', '')?.replace('* Asking price: $', '')?.replace(',', '')?.toFloat()
        // current and start price are same
        mapData['current bid'] = car.currentBid?.toString()?.replace('* Start price: $', '')?.replace('* Current bid: $', '')?.replace(',', '')?.toFloat()
        mapData['buy now price'] = car.buyNowPrice?.toString()?.replace('Buy Now:  $', '')?.replace(',', '')?.toFloat()
        mapData['year'] = mapData['title'].split(' ')[-1]

        def key, value
        (0..car.headers.size() - 1).each { index ->

            key = car.headers.get(index).trim().toLowerCase()
            value = car.values.get(index).trim().replace('\n','')
            if (key.contains('kilometres')) {
                mapData[key] = value.trim().replace('km','').replace(',','')
            } else if (key.contains('engine')) {
                mapData['engine size:'] = value
            }
            else {
                mapData[key] = value
            }
        }

        def row = parseResultInRowData(mapData)
        GoogleSheets.prepareDataToGoogleSheet('Cars', row)
    }

    def parseResultInRowData(Map mapData) {
        def result = []

        result.add(0, mapData['trademe id'])
        result.add(1, mapData['listed date'])
        result.add(2, mapData['title'])
        result.add(3, mapData['asking price'])
        result.add(4, mapData['current bid'])
        result.add(5, mapData['buy now price'])
        result.add(6, mapData['year'])
        result.add(7, mapData['kilometres:'])
        result.add(8, mapData['body:'])
        result.add(9, mapData['on road costs:'])
        result.add(10, mapData['fuel type:'])
        result.add(11, mapData['engine size:'])
        result.add(12, mapData['transmission:'])
        result.add(13, mapData['history:'])
        result.add(14, mapData['registration expires:'])
        result.add(15, mapData['wof expires:'])

        result
    }
}
