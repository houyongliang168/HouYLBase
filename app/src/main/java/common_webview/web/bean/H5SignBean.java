package common_webview.web.bean;


/**
 *  接受信息的bean
 */

public class H5SignBean {
    private String desc;
    private String code;
    private MsgBean msg;
    private String tag;//标识 getSign 0  checkSign 1

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean{
        public MsgBean(String txxSign) {
            this.txxSign = txxSign;
        }

        private String txxSign;

        public String getTxxSign() {
            return txxSign;
        }

        public void setTxxSign(String txxSign) {
            this.txxSign = txxSign;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "txxSign='" + txxSign + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "H5SignBean{" +
                "desc='" + desc + '\'' +
                ", code='" + code + '\'' +
                ", msg=" + msg +
                ", tag='" + tag + '\'' +
                '}';
    }
}

