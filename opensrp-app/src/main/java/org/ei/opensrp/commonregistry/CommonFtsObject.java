package org.ei.opensrp.commonregistry;

import android.util.Pair;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by keyman on 30/08/16.
 */
public class CommonFtsObject {

    private String[] tables;
    private String[] alertFilterVisitCodes;
    private Map<String, String[]> searchMap;
    private Map<String, String[]> sortMap;
    private Map<String, String[]> mainConditionMap;
    private Map<String, String> customRelationalIdMap;
    private Map<String, Pair<String, Boolean>> alertsScheduleMap;
    public static final String idColumn = "object_id";
    public static final String relationalIdColumn = "object_relational_id";
    public static final String phraseColumnName = "phrase";

    public CommonFtsObject(String[] tables) {
        this.tables = tables;
        this.searchMap = new HashMap<String, String[]>();
        this.sortMap = new HashMap<String, String[]>();
        this.mainConditionMap = new HashMap<String, String[]>();
        this.customRelationalIdMap = new HashMap<String, String>();
        this.alertsScheduleMap = new HashMap<String, Pair<String, Boolean>>();
    }

    public void updateSearchFields(String table, String[] searchFields) {
        if (containsTable(table) && searchFields != null) {
            searchMap.put(table, searchFields);
        }
    }

    public void updateSortFields(String table, String[] sortFields) {
        if (containsTable(table) && sortFields != null) {
            sortMap.put(table, sortFields);
        }
    }

    public void updateMainConditions(String table, String[] mainConditions) {
        if (containsTable(table) && mainConditions != null) {
            mainConditionMap.put(table, mainConditions);
        }
    }

    public void updateCustomRelationalId(String table, String  customRelationalId) {
        if (containsTable(table) && StringUtils.isNotBlank(customRelationalId)) {
            customRelationalIdMap.put(table, customRelationalId);
        }
    }

    public void updateAlertScheduleMap(Map<String, Pair<String, Boolean>> alertsScheduleMap) {
        this.alertsScheduleMap = alertsScheduleMap;
    }

    public void updateAlertFilterVisitCodes(String[] alertFilterVisitCodes) {
        this.alertFilterVisitCodes = alertFilterVisitCodes;
    }

    public String[] getTables() {
        if(tables == null){
            tables = ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return tables;
    }

    public String[] getSearchFields(String table) {
        return searchMap.get(table);
    }

    public String[] getSortFields(String table) {
        return sortMap.get(table);
    }

    public String[] getMainConditions(String table) { return mainConditionMap.get(table); }

    public String getCustomRelationalId(String table) {
        return customRelationalIdMap.get(table);
    }

    public String getAlertBindType(String schedule) {
        if(StringUtils.isBlank(schedule)){
            return null;
        }
        Pair<String, Boolean> pair =  alertsScheduleMap.get(schedule);
        if(pair == null){
            return null;
        }

        return pair.first;
    }

    public Boolean alertUpdateVisitCode(String schedule) {
        if(StringUtils.isBlank(schedule)){
            return null;
        }
        Pair<String, Boolean> pair =  alertsScheduleMap.get(schedule);
        if(pair == null){
            return null;
        }

        return pair.second;
    }

    public String[] getAlertFilterVisitCodes(){
        return alertFilterVisitCodes;
    }

    public boolean containsTable(String table) {
        if (tables == null || StringUtils.isBlank(table)) {
            return false;
        }

        List<String> tableList = Arrays.asList(tables);
        return tableList.contains(table);
    }

    public static String searchTableName(String table) {
        return table + "_search";
    }

}
