package homework.HW1.connection;

import java.io.IOException;
public interface HttpClient {

    public String doHttpGet(String url) throws IOException;
}
