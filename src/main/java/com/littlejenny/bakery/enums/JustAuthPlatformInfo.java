package com.littlejenny.bakery.enums;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2020/6/24 13:02
 * @since 1.0.0
 */
public enum JustAuthPlatformInfo {

    /**
     * 平台
     */

    GOOGLE("Google", "", "", "v1.3.0", false);

    // 平台名
    private final String name;
    // 帮助文档
    private final String readme;
    // 官网api文档
    private final String apiDoc;
    // 集成该平台的 版本
    private final String since;
    private final boolean latest;

    JustAuthPlatformInfo(String name, String readme, String apiDoc, String since, boolean latest) {
        this.name = name;
        this.readme = readme;
        this.apiDoc = apiDoc;
        this.since = since;
        this.latest = latest;
    }

    public static List<Map<String, Object>> getPlatformInfos() {
        List<Map<String, Object>> list = new LinkedList<>();
        Map<String, Object> map = null;
        JustAuthPlatformInfo[] justAuthPlatformInfos = JustAuthPlatformInfo.values();
        for (JustAuthPlatformInfo justAuthPlatformInfo : justAuthPlatformInfos) {
            map = new HashMap<>();
            map.put("name", justAuthPlatformInfo.getName());
            map.put("readme", justAuthPlatformInfo.getReadme());
            map.put("apiDoc", justAuthPlatformInfo.getApiDoc());
            map.put("since", justAuthPlatformInfo.getSince());
            map.put("enname", justAuthPlatformInfo.name().toLowerCase());
            map.put("isLatest", justAuthPlatformInfo.isLatest());
            list.add(map);
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public String getReadme() {
        return readme;
    }

    public String getApiDoc() {
        return apiDoc;
    }

    public String getSince() {
        return since;
    }

    public boolean isLatest() {
        return latest;
    }
}
