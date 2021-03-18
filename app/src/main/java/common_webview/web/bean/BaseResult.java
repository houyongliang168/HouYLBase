package common_webview.web.bean;

public class BaseResult {
    private String code;
    private String desc;

    public BaseResult(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}