package adapter;

public class MainAdapter {

    public static void main(String[] args) {

        XmlService xmlService = new XmlService();

        JsonService adapter = new JsonAdapter(xmlService);

        String json = "{name: 'Truong', age: 22}";

        adapter.processJson(json);
    }
}