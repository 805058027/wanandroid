package com.example.administrator.wanandroid.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/7.
 */

public class QueryDataBean {

    public int curPage;
    public int offset;
    public boolean over;
    public int pageCount;
    public int size;
    public int total;
    public List<DatasBean> datas;

    public static class DatasBean {
        /**
         * apkLink :
         * author : code小生
         * chapterId : 414
         * chapterName : code小生
         * collect : true
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 7995
         * link : https://mp.weixin.qq.com/s/hzCBLwMY04aPWrcTlJ2uPQ
         * niceDate : 2019-03-01
         * origin :
         * projectLink :
         * publishTime : 1551369600000
         * superChapterId : 408
         * superChapterName : 公众号
         * tags : [{"name":"公众号","url":"/wxarticle/list/414/1"}]
         * title : 20<em class='highlight'>1</em>9 Android 高级面试题总结
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */

        public String apkLink;
        public String author;
        public int chapterId;
        public String chapterName;
        public boolean collect;
        public int courseId;
        public String desc;
        public String envelopePic;
        public boolean fresh;
        public int id;
        public String link;
        public String niceDate;
        public String origin;
        public String projectLink;
        public long publishTime;
        public int superChapterId;
        public String superChapterName;
        public String title;
        public int type;
        public int userId;
        public int visible;
        public int zan;
        public List<TagsBean> tags;

        public static class TagsBean {
            /**
             * name : 公众号
             * url : /wxarticle/list/414/1
             */

            public String name;
            public String url;
        }
    }
}
