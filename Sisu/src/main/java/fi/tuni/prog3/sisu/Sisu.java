package fi.tuni.prog3.sisu;
import org.apache.commons.io.*;
import org.json.*;
import java.io.FileNotFoundException;
import java.net.URL;
import javafx.application.Application;
import java.util.ArrayList;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



public class Sisu extends Application {
    
    
    
    
    
    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException , JSONException{
        
        populate.populate();
        Map<String,Item> setti = populate.Items;
        Courses.populate();
        Map<String,course> setti2 = Courses.courses;
        
        
        stage.setTitle("Sisu");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        
        
        GridPane grid2 = new GridPane();

        Scene kirjatuminen = new Scene(grid, 300, 310);
        stage.setScene(kirjatuminen);
        
        Scene toinen = new Scene(grid2,390,370);
        

        Text scenetitle = new Text("Kirjautuminen");
        scenetitle.setFont(Font.font ("Dialog", 23));
        scenetitle.setFill(Color.web("#343a40"));
        grid.add(scenetitle, 0, 0, 2, 1);


        TextField inputNimi = new TextField();
        inputNimi.setPromptText("Name");
        inputNimi.setFocusTraversable(false); 
        grid.add(inputNimi, 1, 1);

        TextField inputNro = new TextField();
        inputNro.setPromptText("Student number");
        inputNro.setFocusTraversable(false); 
        grid.add(inputNro, 1, 2);
        
        TextField inputAV = new TextField();
        inputAV.setPromptText("Start year");
        inputAV.setFocusTraversable(false); 
        grid.add(inputAV, 1, 3);
        
        TextField inputVV = new TextField();
        inputVV.setPromptText("End year");
        inputVV.setFocusTraversable(false); 
        grid.add(inputVV, 1, 4);
        


        HBox hbBtn = new HBox(10);
        
        HBox hbChange = new HBox(10);

        
        grid.add(hbBtn, 1, 6);
        
        grid.setVgap(10);
        
        grid.add(hbChange, 1, 7);
        
        grid2.setVgap(10);
        
        grid2.setHgap(10);
        
        
        
        
        
        //Ikkuna2
        HBox hbChange2 = new HBox(10);
        HBox hbSearch = new HBox(10);
        
        hbChange2.setPadding(new Insets(0, 0, 10, 0));
        
        Label syote = new Label("Syötä tutkinto");
        grid2.add(syote,1,1);
        
        grid2.add(hbChange2, 1, 4);
        grid2.add(hbSearch, 2, 2);
        
        
        TextField search = new TextField();
        search.setPromptText("Syötä tutkinto");
        search.setFocusTraversable(false);
        grid2.add(search, 1, 2);
        
        ListView<String> list = new ListView<>();
        ObservableList<String> items =FXCollections.observableArrayList ();
        list.setItems(items);
        list.setPrefWidth(320);
        list.setPrefHeight(300);
        
        
        grid2.add(list,1,3);
        

        //Nappulat
        Button btn1 = new Button("Kirjaudu sisään");
        btn1.setPrefWidth(200);
        hbBtn.getChildren().add(btn1);

        
        Button change2 = new Button("Takaisin kirjautumiseen");
        hbChange2.getChildren().add(change2);
        change2.setPrefWidth(200);
        
        Button search1 = new Button("Hae");
        hbSearch.getChildren().add(search1);
        
        
        //Nappulat
        
        change2.setOnAction(e -> stage.setScene(kirjatuminen));

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                stage.setScene(toinen);

            }
        });
         search1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                      
                String searchResults = search.getText();

               
                try {
                    URL searchurl = new URL("https://sis-tuni.funidata.fi/kori/api/modules/" + setti.get(searchResults).getId());
                    JSONObject jsonfile = new JSONObject(IOUtils.toString(searchurl,Charset.forName("UTF-8")));
                    
                    
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                ArrayList<String> lista = new ArrayList();
                
                for (Map.Entry<String, course> entry : setti2.entrySet()) {
                    lista.add(setti2.get(entry.getKey()).getName() + " (" +setti2.get(entry.getKey()).getCredits() +" op)" );
                }
                
                Collections.sort(lista);
                lista.add(0,"Nimi: "+setti.get(searchResults).getName());
                lista.add(1,"Opintopisteet: "+setti.get(searchResults).getCredits().getmin());
                lista.add(2,"Kurssit:");
                
                ObservableList<String> courses = FXCollections.observableArrayList(lista);
                
                list.setItems(courses);
                
                
                

            }
        });
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static class Item{
        private String id;
        private String code;
        private String lang;
        private String groupId;
        private String name;
        private String nameMatch;
        private String searchTagsMatch;
        private ArrayList<String> curriculumPeriodIds;
        private credits credits;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameMatch() {
            return nameMatch;
        }

        public void setNameMatch(String nameMatch) {
            this.nameMatch = nameMatch;
        }

        public String getSearchTagsMatch() {
            return searchTagsMatch;
        }

        public void setSearchTagsMatch(String searchTagsMatch) {
            this.searchTagsMatch = searchTagsMatch;
        }

        public ArrayList<String> getCurriculumPeriodIds() {
            return curriculumPeriodIds;
        }

        public void setCurriculumPeriodIds(ArrayList<String> curriculumPeriodIds) {
            this.curriculumPeriodIds = curriculumPeriodIds;
        }

        public credits getCredits() {
            return credits;
        }

        public void setCredits(credits credits) {
            this.credits = credits;
        }
        
        
        
    }
    
    public static class credits{
        private String min;
        private String max;
        
        public String getmin(){
            return this.min;
        }
        public void setmin(String min){
            this.min = min;
        }
        public String getmax(){
            return this.max;
        }
        public void setmax(String max){
            this.max = max;
        }
    }

    public static class course{
        private String id;
        private String name;
        private String credits;


        public String getId() {
            return id;
        }


        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }


        public String getCredits() {
            return credits;
        }

        public void setCredits(String credits) {
            this.credits = credits;
        }
        

    
    }
}
 