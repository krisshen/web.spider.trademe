package webmagic.test

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.annotation.ThreadSafe
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import us.codecraft.webmagic.utils.FilePersistentBase

/**
 * Created by kris on 30/11/16.
 */
@ThreadSafe
class ImagePipeline extends FilePersistentBase implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    void process(ResultItems resultItems, Task task) {

        setPath('data/webmagic/' + resultItems.get('id').toString() + '/')

        saveTextInfo(resultItems)

        downloadImage(path, resultItems.get('img'))
    }

    def saveTextInfo(ResultItems resultItems) {
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + "textInfo.txt")), "UTF-8"))
            printWriter.println("url:\t" + resultItems.getRequest().getUrl())
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                if (entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable) entry.getValue()
                    printWriter.println(entry.getKey() + ":")
                    for (Object o : value) {
                        printWriter.println(o)
                    }
                } else {
                    printWriter.println(entry.getKey() + ":\t" + entry.getValue())
                }
            }
            printWriter.close()
        } catch (IOException e) {
            logger.warn("write file error", e)
        }

    }

    def downloadImage(String path, List imgLinks) {
        imgLinks.forEach() { link ->
            println 'saving image url: ' + link.toString()
            def filename = getFileName(link)
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(link);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                long len = entity.getContentLength();
                InputStream is = entity.getContent();
                FileOutputStream fos = new FileOutputStream(new File(path + filename))
                int inByte
                while((inByte = is.read()) != -1)
                    fos.write(inByte)
                is.close()
                fos.close()
            }
        }
    }

    def getFileName(String link) {
        return link.split('/').last().toString()
    }
}
