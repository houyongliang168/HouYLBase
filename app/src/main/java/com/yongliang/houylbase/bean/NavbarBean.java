package com.yongliang.houylbase.bean;

/**
 * created by houyl
 * on  5:36 PM
 */
public class NavbarBean {

    /**
     * navbarName : 首页
     * navbarType : native
     * preImage : https://www.baidu.com/a.png
     * nextImage : https://www.baidu.com/b.png
     * h5Url :
     * iosData : sdfe
     * androidData : cev
     */

    private String title;
    private String nextC;
    private String type;
    private String url;
    private String prevC;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNextC() {
        return nextC;
    }

    public void setNextC(String nextC) {
        this.nextC = nextC;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrevC() {
        return prevC;
    }

    public void setPrevC(String prevC) {
        this.prevC = prevC;
    }

    @Override
    public String toString() {
        return "NavbarBean{" +
                "title='" + title + '\'' +
                ", nextC='" + nextC + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", prevC='" + prevC + '\'' +
                '}';
    }
}
