package fi.abo.date.datepiikkiapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 12/3/2017.
 */

public class CSVFile {
    InputStream inputStream;
    OutputStream outputStream;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    public CSVFile(OutputStream outputStream) { this.outputStream = outputStream; }

    public List read(){
        List<String[]> logList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine()) != null){
                String[] row = csvLine.split(",");
                logList.add(0,row);
            }
        }catch (IOException ex){
            throw new RuntimeException("Error in reading CSV file");
        }
        finally {
            try{
                inputStream.close();
            }catch (IOException ex){
                throw new RuntimeException("Error while closing CSV file");
            }
        }
        System.out.println("CSV file load successful");
        System.out.println("CSV file contained: " + logList.size() + " logs.");
        System.out.println("-----------------------------------------------------");
        return logList;
    }
    public void write(String path,String[] text){
        try{
        PrintWriter pw = new PrintWriter(new File(path));
        StringBuilder sb = new StringBuilder();
        for(String s:text){
           sb.append(s);
           sb.append(",");
        }
            sb.append("\n");
            pw.write(sb.toString());
            pw.close();
            System.out.println("Done writing to CSV file, save successful");
        }catch (IOException ex){
            throw new RuntimeException("Error, didn't find CSV file path");
        }
    }
}
