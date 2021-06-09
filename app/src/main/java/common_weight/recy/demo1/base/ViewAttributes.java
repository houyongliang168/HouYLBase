package common_weight.recy.demo1.base;

/**
 * created by houyl
 * on  10:09 AM
 *  View 宽高等基本属性 的获取
 *  具体使用时候定义
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * created by houyl
 * on  10:09 AM
 *  View 宽高等基本属性 的获取
 *  具体使用时候定义
 */
public class ViewAttributes implements Parcelable {
    public int padding;

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
    //

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.padding);
    }

    public ViewAttributes() {
    }

    protected ViewAttributes(Parcel in) {
        this.padding = in.readInt();
    }

    public static final Creator<ViewAttributes> CREATOR = new Creator<ViewAttributes>() {
        @Override
        public ViewAttributes createFromParcel(Parcel source) {
            return new ViewAttributes(source);
        }

        @Override
        public ViewAttributes[] newArray(int size) {
            return new ViewAttributes[size];
        }
    };
}
