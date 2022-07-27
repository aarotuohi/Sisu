package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class populate {

    static Map<String,Sisu.Item> Items = new TreeMap<String,Sisu.Item>();
    
    public static void populate() throws IOException, JSONException{
        URL url = new URL("https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000");
        JSONObject jsonfile = new JSONObject(IOUtils.toString(url,Charset.forName("UTF-8")));
        
        JSONArray jsonitems = jsonfile.getJSONArray("searchResults");
        
        for(int i = 0 ; i < jsonitems.length() ; i++){
            Sisu.Item newitem = new Sisu.Item();
            
            newitem.setId(jsonitems.getJSONObject(i).get("id").toString());
            newitem.setCode(jsonitems.getJSONObject(i).get("code").toString());
            newitem.setLang(jsonitems.getJSONObject(i).get("lang").toString());
            newitem.setGroupId(jsonitems.getJSONObject(i).get("groupId").toString());
            newitem.setName(jsonitems.getJSONObject(i).get("name").toString());
            newitem.setNameMatch(jsonitems.getJSONObject(i).get("nameMatch").toString());
            newitem.setSearchTagsMatch(jsonitems.getJSONObject(i).get("searchTagsMatch").toString());
            
            JSONArray jsonarray = (JSONArray) jsonitems.getJSONObject(i).get("curriculumPeriodIds");
            
            ArrayList<String> array = new ArrayList<>();
            
            for(int j = 0; j < jsonarray.length(); j++){
                array.add(jsonarray.get(j).toString());
            }
            newitem.setCurriculumPeriodIds(array);
            
            JSONObject creditslist = (JSONObject) jsonitems.getJSONObject(i).get("credits");
            Sisu.credits crediitit = new Sisu.credits();
            
            crediitit.setmin(creditslist.get("min").toString());
            crediitit.setmax(creditslist.get("max").toString());
            newitem.setCredits(crediitit);
            
            Items.put(jsonitems.getJSONObject(i).getString("name"),newitem);
            
        }
        
    }
    
}
