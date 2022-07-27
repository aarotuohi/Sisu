
package fi.tuni.prog3.sisu;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Courses {

    static Map<String,Sisu.course> courses = new TreeMap<>();
    
    public static void populate() throws IOException, JSONException{
        
        

        File path = new File("./src/main/resources/json/courseunits/");

        File [] files = path.listFiles();
        for (int i = 1; i < files.length; i++){
            if (files[i].isFile()){
                File localfile = new File(files[i].toString());
                if (localfile.exists()){
                    InputStream stream = new FileInputStream(localfile);
                    String jsonTxt = IOUtils.toString(stream, "UTF-8");


                    JSONObject json = new JSONObject(jsonTxt);

                    Sisu.course uusi = new Sisu.course();


                    uusi.setId(json.get("id").toString());
                    uusi.setName(json.getJSONObject("name").get("fi").toString());
                    uusi.setCredits(json.getJSONObject("credits").get("min").toString());


                    courses.put(json.get("id").toString(),uusi);

                }
            }
        }

        
        
        
        
        
        
        
        
    }
    
}
