package common_network;

import android.os.Parcel;
import android.os.Parcelable;

public class FunctionMoudleBean implements Cloneable, Parcelable {

    /**
     * logoImgUrl : https:///static/itemimg/2019012213382346250793.png
     * resourcePath : /1060
     * resourceName : 职业HWP专区
     * targetType : native
     * type : more
     * url :
     * parentId : 127
     * blocked : false
     * tip :
     * id : 128
     * newFunction : true
     * androidUpperLimit :
     * iosUpperLimit :
     * trackEvent :
     * trackLabel :
     */

    private String logoImgUrl;
    private String resourcePath;
    private String resourceName;
    private String targetType;
    private String type;
    private String url;
    private int parentId;
    private boolean blocked;
    private String tip;
    private int id;
    private boolean newFunction;
    private String androidUpperLimit;
    private String iosUpperLimit;
    private String trackEvent;
    private String trackLabel;

    public FunctionMoudleBean() {
    }

    protected FunctionMoudleBean(Parcel in) {
        logoImgUrl = in.readString();
        resourcePath = in.readString();
        resourceName = in.readString();
        targetType = in.readString();
        type = in.readString();
        url = in.readString();
        parentId = in.readInt();
        blocked = in.readByte() != 0;
        tip = in.readString();
        id = in.readInt();
        newFunction = in.readByte() != 0;
        androidUpperLimit = in.readString();
        iosUpperLimit = in.readString();
        trackEvent = in.readString();
        trackLabel = in.readString();
    }

    public static final Creator<FunctionMoudleBean> CREATOR = new Creator<FunctionMoudleBean>() {
        @Override
        public FunctionMoudleBean createFromParcel(Parcel in) {
            return new FunctionMoudleBean(in);
        }

        @Override
        public FunctionMoudleBean[] newArray(int size) {
            return new FunctionMoudleBean[size];
        }
    };

    public String getLogoImgUrl() {
        return logoImgUrl;
    }

    public void setLogoImgUrl(String logoImgUrl) {
        this.logoImgUrl = logoImgUrl;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isNewFunction() {
        return newFunction;
    }

    public void setNewFunction(boolean newFunction) {
        this.newFunction = newFunction;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAndroidUpperLimit() {
        return androidUpperLimit;
    }

    public void setAndroidUpperLimit(String androidUpperLimit) {
        this.androidUpperLimit = androidUpperLimit;
    }

    public String getIosUpperLimit() {
        return iosUpperLimit;
    }

    public void setIosUpperLimit(String iosUpperLimit) {
        this.iosUpperLimit = iosUpperLimit;
    }

    public String getTrackEvent() {
        return trackEvent;
    }

    public void setTrackEvent(String trackEvent) {
        this.trackEvent = trackEvent;
    }

    public String getTrackLabel() {
        return trackLabel;
    }

    public void setTrackLabel(String trackLabel) {
        this.trackLabel = trackLabel;
    }

    @Override
    public Object clone() {
        FunctionMoudleBean moduleBean = null;
        try {
            moduleBean = (FunctionMoudleBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return moduleBean;
    }

    @Override
    public String toString() {
        return "FunctionMoudleBean{" +
                "logoImgUrl='" + logoImgUrl + '\'' +
                ", resourcePath='" + resourcePath + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", targetType='" + targetType + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", parentId=" + parentId +
                ", blocked=" + blocked +
                ", tip='" + tip + '\'' +
                ", id=" + id +
                ", newFunction=" + newFunction +
                ", androidUpperLimit='" + androidUpperLimit + '\'' +
                ", iosUpperLimit='" + iosUpperLimit + '\'' +
                ", trackEvent='" + trackEvent + '\'' +
                ", trackLabel='" + trackLabel + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(logoImgUrl);
        dest.writeString(resourcePath);
        dest.writeString(resourceName);
        dest.writeString(targetType);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeInt(parentId);
        dest.writeByte((byte) (blocked == true ? 1 : 0));
        dest.writeString(tip);
        dest.writeInt(id);
        dest.writeByte((byte) (newFunction == true ? 1 : 0));
        dest.writeString(androidUpperLimit);
        dest.writeString(iosUpperLimit);
        dest.writeString(trackEvent);
        dest.writeString(trackLabel);
    }
}
