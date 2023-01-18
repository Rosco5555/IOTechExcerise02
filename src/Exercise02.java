import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Exercise02 {

    public static void main(String[] args){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("./data.json")){
            Object obj = jsonParser.parse(reader);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
