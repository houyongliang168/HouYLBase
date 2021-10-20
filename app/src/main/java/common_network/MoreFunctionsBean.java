package common_network;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MoreFunctionsBean {

    /**
     * rspCode : 0
     * rspDesc : 成功
     * info : {"moduleList":[{"functionList":[{"logoImgUrl":"https://isaleuat./static/itemimg/2019012213382346250793.png","resourcePath":"/1060","resourceName":"职业HWP专区","targetType":"native","type":"more","url":"","parentId":127,"blocked":"false","tip":"","id":128,"newFunction":"true","androidUpperLimit":"","iosUpperLimit":"","trackEvent":"","trackLabel":""}],"resourceName":"高客专区","id":127},{"functionList":[{"logoImgUrl":"https://isaleuat./static/itemimg/2019021309485781386792.png","resourcePath":"/1069","resourceName":"活动量管理","targetType":"native","type":"more","url":"","parentId":159,"blocked":"false","tip":"","id":160,"newFunction":"true","androidUpperLimit":"","iosUpperLimit":"","trackEvent":"","trackLabel":""}],"resourceName":"活动专区","id":159}]}
     * url :
     * pageNums : 0
     */

    private String rspCode;
    private String rspDesc;
    private InfoBean info;

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        private List<ModuleListBean> moduleList;

        public List<ModuleListBean> getModuleList() {
            return moduleList;
        }

        public void setModuleList(List<ModuleListBean> moduleList) {
            this.moduleList = moduleList;
        }

        public static class ModuleListBean implements Parcelable {
            /**
             * functionList : [{"logoImgUrl":"https://isaleuat./static/itemimg/2019012213382346250793.png","resourcePath":"/1060","resourceName":"职业HWP专区","targetType":"native","type":"more","url":"","parentId":127,"blocked":"false","tip":"","id":128,"newFunction":"true","androidUpperLimit":"","iosUpperLimit":"","trackEvent":"","trackLabel":""}]
             * resourceName : 高客专区
             * id : 127
             */

            private String resourceName;
            private String resourcePath;
            private int id;
            private List<FunctionMoudleBean> functionList;

            public ModuleListBean() {
            }

            protected ModuleListBean(Parcel in) {
                resourceName = in.readString ();
                resourcePath = in.readString ();
                id = in.readInt ();
                functionList = in.createTypedArrayList (FunctionMoudleBean.CREATOR);
            }

            public String getResourcePath() {
                return resourcePath;
            }

            public void setResourcePath(String resourcePath) {
                this.resourcePath = resourcePath;
            }

            public static final Creator<ModuleListBean> CREATOR = new Creator<ModuleListBean>() {
                @Override
                public ModuleListBean createFromParcel(Parcel in) {
                    return new ModuleListBean (in);
                }

                @Override
                public ModuleListBean[] newArray(int size) {
                    return new ModuleListBean[size];
                }
            };

            public String getResourceName() {
                return resourceName;
            }

            public void setResourceName(String resourceName) {
                this.resourceName = resourceName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<FunctionMoudleBean> getFunctionList() {
                return functionList;
            }

            public void setFunctionList(List<FunctionMoudleBean> functionList) {
                this.functionList = functionList;
            }

            @Override
            public String toString() {
                return "ModuleListBean{" +
                        "resourceName='" + resourceName + '\'' +
                        ", resourcePath='" + resourcePath + '\'' +
                        ", id=" + id +
                        ", functionList=" + functionList +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString (resourceName);
                dest.writeString (resourcePath);
                dest.writeInt (id);
                dest.writeTypedList (functionList);
            }
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "moduleList=" + moduleList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MoreFunctionsBean{" +
                "rspCode='" + rspCode + '\'' +
                ", rspDesc='" + rspDesc + '\'' +
                ", info=" + info +
                '}';
    }
}
