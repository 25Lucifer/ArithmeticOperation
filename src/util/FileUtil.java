package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author lucifer
 */
public class FileUtil {
    public void fileWriter(List<String> strings, String filepath) {
        String str = "";
        File file = new File(filepath);
        boolean res = true;
        try {
            if (!file.exists()) {
                res = file.createNewFile();
            }
            if (res){
                FileWriter fw = new FileWriter(file);
                int i = 0;
                while(i<strings.size()){
                    str = strings.get(i);
                    fw.write(str+"\n");
                    fw.flush();
                    i++;
                }
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
