package spider.trademe.pipelines

import groovy.transform.Synchronized
import org.apache.http.annotation.ThreadSafe
import spider.trademe.utils.GoogleSheets
import spider.trademe.entities.Property
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.PageModelPipeline

/**
 * Created by shenk on 12/5/2016.
 */
@ThreadSafe
class PropertyModelPipeline implements PageModelPipeline {

    Property property

    @Override
    @Synchronized
    void process(Object o, Task task) {
        property = (Property) o
        println '=================================================='
        println property.title

        def mapData = [:]
        mapData['title'] = property.title.replace('\n', '')
        mapData['trademe id'] = property.tradeMeID?.replace('\n', '').replace('Listing #: ','')
        mapData['listed date'] = property.listedDate?.replace('\n', '').replace(' * Listed: ','')

        def key, value
        (0..property.headers.size() - 1).each { index ->

            key = property.headers.get(index).trim().toLowerCase()
            value = property.values.get(index)

            if (key.contains('location')) {
                def locationList = value.split('\n')
                mapData['street'] = locationList[0]
                mapData['suburb'] = locationList[1]
                mapData['district'] = locationList[2]
                //some addresses only have 3 lines, because suburb is same with district
                if (locationList.length > 3) {
                    mapData['region'] = locationList[3]
                }
                else {
                    mapData['region'] = mapData['district']
                    mapData['district'] = mapData['suburb']
                }
            } else {
                mapData[key] = value
            }
        }

        def row = parseResultInRowData(mapData)

        GoogleSheets.prepareDataToGoogleSheet(mapData['district'], row)
    }

    def parseResultInRowData(Map mapData) {
        def result = []

        result.add(0, mapData['trademe id'])
        result.add(1, mapData['listed date'])
        result.add(2, mapData['title'])
        result.add(3, mapData['street'])
        result.add(4, mapData['suburb'])
        result.add(5, mapData['district'])
        result.add(6, mapData['region'])
        result.add(7, mapData['rooms:'])
        result.add(8, mapData['property type:'])
        result.add(9, mapData['floor area:'])
        result.add(10, mapData['land area:'])
        result.add(11, mapData['rateable value (rv):']?.toString()?.replace('$', '')?.replace(',', '')?.toInteger())
        result.add(12, mapData['price:'])
        result.add(13, mapData['property id#:'])
        result.add(14, mapData['parking:'])
        result.add(15, mapData['in the area:'])
        result.add(16, mapData['smoke alarm:'])
        //result.add(17, mapData['open home times:'])
        //result.add(18, '=IMAGE("https://developers.google.com/_static/c78d15bae0/images/silhouette36.png?sz=64")')

        result
    }
}
