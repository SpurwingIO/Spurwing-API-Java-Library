package spurwing.util.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Credentials {
    
    private String PID = null;
    private String KEY = null;
    public Credentials() {
      try (InputStream input = new FileInputStream(".properties")) {
            Properties prop = new Properties();
            prop.load(input);
            PID = prop.getProperty("PID");
            KEY = prop.getProperty("KEY");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getPID() {
        return PID;
    }

    public String getKEY() {
        return KEY;
    }
}
