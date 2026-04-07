package adapter;

public class JsonAdapter implements JsonService {

    private XmlService xmlService;

    public JsonAdapter(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    @Override
    public void processJson(String json) {

        // chuyển JSON → XML (giả lập)
        String xml = convertJsonToXml(json);

        xmlService.processXml(xml);
    }

    private String convertJsonToXml(String json) {
        return "<xml>" + json + "</xml>";
    }
}