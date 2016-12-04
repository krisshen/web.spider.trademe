package spider.trademe

import spider.trademe.entity.Property
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.PageModelPipeline

/**
 * Created by shenk on 12/5/2016.
 */
class PropertyModelPipeline implements PageModelPipeline {

    Property property

    @Override
    void process(Object o, Task task) {
        property = (Property)o
        println '=================================================='
        println property.title
        println property.information
        println property.photos.size()
//         println (((Property)property).photos.size())
    }
}
