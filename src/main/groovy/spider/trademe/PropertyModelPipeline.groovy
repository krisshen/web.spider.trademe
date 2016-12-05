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

        (0..property.headers.size()-1).each { index ->
            println property.headers.get(index) + ' ' + property.values.get(index)
        }

    }
}
