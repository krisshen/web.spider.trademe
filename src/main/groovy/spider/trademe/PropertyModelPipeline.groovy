package spider.trademe

import groovy.transform.Synchronized
import org.apache.http.annotation.ThreadSafe
import spider.trademe.entity.Property
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
//        println property.information

//        (0..property.headers.size()-1).each { index ->
//            println property.headers.get(index) + ' ' + property.values.get(index)
//        }

        def mapData = [:]
        mapData['title'] = property.title

        (0..property.headers.size() - 1).each { index ->
            mapData[property.headers.get(index).trim().toLowerCase()] = property.values.get(index)
        }

        println mapData

        def row = parseResultInRowData(mapData)

        GoogleSheets.uploadToGoogleSheet(row)
    }

    def parseResultInRowData(Map mapData) {
        def result = []

        result.add(0, mapData['title'])
        result.add(1, mapData['location:'])
        result.add(2, mapData['rooms:'])
        result.add(3, mapData['property type:'])
        result.add(4, mapData['floor area:'])
        result.add(5, mapData['rateable value (rv):'])
        result.add(6, mapData['price:'])
        result[7] = ''
        result.add(8, mapData['parking:'])
        result.add(9, mapData['in the area:'])
        result.add(10, mapData['smoke alarm:'])
        result.add(11, mapData['open home times:'])


        result
    }
}
