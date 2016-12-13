package spider.trademe.utils

import java.text.SimpleDateFormat

/**
 * Created by shenk on 12/13/2016.
 */
class DateProcessor {

    static process(String listedDate) {
        //'Wed 7 Dec, 7:15 pm' -> 'Wed 7 Dec'
        def listedDateWithoutTime =listedDate.replace('\n', '').replace(' * Listed: ', '').split(',')[0]
        //'Wed 7 Dec' -> '7-Dec'
        def listedDateWithoutTimeSplit = listedDateWithoutTime.split(' ')
        def listedDateWithoutWeekday = listedDateWithoutTimeSplit[1] + listedDateWithoutTimeSplit[2]
        SimpleDateFormat dt = new SimpleDateFormat("ddMMM")
        Date date = dt.parse(listedDateWithoutWeekday)
        SimpleDateFormat dtNew = new SimpleDateFormat("MMdd")
        def formattedDate = '2016' + dtNew.format(date)
    }

}
