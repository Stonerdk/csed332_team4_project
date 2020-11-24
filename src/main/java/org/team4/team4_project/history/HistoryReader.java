package org.team4.team4_project.history;

import java.util.ArrayList;
import java.util.Date;

public class HistoryReader {
    public ArrayList<HistoryData> getHistoryList() {
        //TODO: Erase all and implement real getHistoryList()
        //TODO: If possible, add nullable Filter class to first parameter to filter the histories.
        //TODO: by time interval, by history, by branch, etc...

        ArrayList<HistoryData> ret = new ArrayList<>();
        ret.add(new HistoryData(new Date(20201116), "Commit 1", "master", HistoryData.makeAttrMap(10,14,60)));
        ret.add(new HistoryData(new Date(20201118), "Commit 2", "master", HistoryData.makeAttrMap(13,17,100)));
        ret.add(new HistoryData(new Date(20201119), "Commit 3", "master", HistoryData.makeAttrMap(14, 12, 50)));
        ret.add(new HistoryData(new Date(20201119), "Commit 3", "master", HistoryData.makeAttrMap(16, 20, 111)));
        ret.add(new HistoryData(new Date(20201119), "Commit 3", "master", HistoryData.makeAttrMap(12, 12, 80)));
        ret.add(new HistoryData(new Date(20201119), "Commit 3", "master", HistoryData.makeAttrMap(18, 11, 95)));
        return ret;
    }
}
