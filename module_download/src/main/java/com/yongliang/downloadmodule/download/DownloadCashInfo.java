package com.yongliang.downloadmodule.download;

public class DownloadCashInfo {
    //下载存储位置 默认为0    0下载到泰行销目录里面  1 为下载到相册里面
    private String seat;
    //是否支持暂停 默认0     0不暂停，1为可暂停
    private String ispause;
    //文件的网络地址
    private String url;
    //回调名称
    private String callBackName;
    //文件类型  视频为 MP4  其余默认为图片 为jpg
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getIspause() {
        return ispause;
    }

    public void setIspause(String ispause) {
        this.ispause = ispause;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCallBackName() {
        return callBackName;
    }

    public void setCallBackName(String callBackName) {
        this.callBackName = callBackName;
    }

    @Override
    public String toString() {
        return "DownloadCashInfo{" +
                "seat='" + seat + '\'' +
                ", ispause='" + ispause + '\'' +
                ", url='" + url + '\'' +
                ", callBackName='" + callBackName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
