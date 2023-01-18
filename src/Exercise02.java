import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Base64;


public class Exercise02 {

    public static void main(String[] args){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("data.json")){
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray devices = (JSONArray) jsonObject.get("Devices");
            long current_time = Instant.now().getEpochSecond();
            JSONObject device_json;
            long timestamp;
            JSONArray valid_devices = new JSONArray();
            JSONArray uuids = new JSONArray();
            int total_value = 0;


            for (Object device: devices){
                device_json = (JSONObject) device;

                timestamp = Long.parseLong((String) device_json.get("timestamp"));

                if (Long.compare(timestamp, current_time) >= 0){ // timestamp is after or equal to the current time
                    valid_devices.add(device_json);
                }
            }

            for (Object device: valid_devices){
                device_json = (JSONObject) device;
                String value_string = (String) device_json.get("value");
                byte[] byte_arr = Base64.getDecoder().decode(value_string);
                int val = Integer.parseInt(new String(byte_arr));
                String info = (String) device_json.get("Info");
                int start = info.indexOf(':')+1;
                int end = info.indexOf(",");
                String uuid = info.substring(start, end);
                uuids.add(uuid);
                total_value += val;
            }

            JSONObject output = new JSONObject();
            output.put("ValueTotal", total_value);
            output.put("UUIDS", uuids);

            try (FileWriter file = new FileWriter("output.json")) {
                file.write(output.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
