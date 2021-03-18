package common_webview.web.js;

/**
 * created by houyl
 * on  6:26 PM
 */
public final class H5Event {
    public H5Event(String mothodName, String jsonSource) {
        this.jsonSource = jsonSource;
        this.mothodName = mothodName;
    }

    public String getJsonSource() {
        return jsonSource;
    }

    public void setJsonSource(String jsonSource) {
        this.jsonSource = jsonSource;
    }

    private String jsonSource;

    public String getMothodName() {
        return mothodName;
    }

    public void setMothodName(String mothodName) {
        this.mothodName = mothodName;
    }

    private String mothodName;

    @Override
    public String toString() {
        return "H5Event{" +
                "jsonSource='" + jsonSource + '\'' +
                ", mothodName='" + mothodName + '\'' +
                '}';
    }
}
