package main.java;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Trim {
    private static final String COMMA_DELIMITER = ",";

    // simple method to concatenate arrays from stack overflow
    // https://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static void main(String[] argument) throws IOException {
//        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/enteilegend/Downloads/movies_metadata_trimed.csv"));
//
//        try (BufferedReader br = new BufferedReader(new FileReader("/Users/enteilegend/Downloads/movies_metadata.csv"))) {
//            BufferedReader br_2 = new BufferedReader(new FileReader("/Users/enteilegend/Downloads/keywords.csv"));
//            String line;
//            String line_2;
//            while ((line = br.readLine()) != null && (line_2 = br_2.readLine()) != null) {
//                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//                String[] values_2 = line_2.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//
//                if (values.length < 24) {
//                    line = br.readLine();
//                    if (line == null) {
//                        break;
//                    }
//                    String[] more_values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//                    values = concat(values, more_values);
//                }
//
//                if (values_2.length < 24) {
//                    line = br.readLine();
//                    if (line == null) {
//                        break;
//                    }
//                    String[] more_values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//                    values_2 = concat(values_2, more_values);
//                }
//
////                for (int i = 0; i < values.length; i++) {
////                    System.out.println(values[i]);
////
////                }
//
//                // id, title, keywords
//                // System.out.println("line1: " + values.length);
////                System.out.println("line_2: " + values_2.length);
//                // System.out.println(line);
//                if (values[5].equals("0")) {
//                    System.out.println("error");
//                }
//                writer.write(values[5] + "," + values[8] + "," + values_2[1] + "\n");
//                System.out.println(values[5] + "," + values[8] + "," + values_2[1]);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        writer.close();

        csv_parser();

    }


    public static void csv_parser( ) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/enteilegend/Downloads/movies_metadata_title.csv"));
        File csv_data = new File("/Users/enteilegend/Downloads/movies_metadata.csv");
        File csv_data2 = new File("/Users/enteilegend/Downloads/keywords.csv");

        BufferedWriter writer_2 = new BufferedWriter(new FileWriter("/Users/enteilegend/Downloads/movies_metadata_tagline.csv"));
        BufferedWriter writer_3 = new BufferedWriter(new FileWriter("/Users/enteilegend/Downloads/movies_metadata_overview.csv"));
        BufferedWriter writer_4 = new BufferedWriter(new FileWriter("/Users/enteilegend/Downloads/movies_metadata_vote_average.csv"));
        BufferedWriter writer_5 = new BufferedWriter(new FileWriter("/Users/enteilegend/Downloads/movies_metadata_cover.csv"));

        Reader reader = Files.newBufferedReader(Paths.get("/Users/enteilegend/Downloads/movies_metadata.csv"));

        CSVParser parser = CSVParser.parse(csv_data, Charset.defaultCharset(), CSVFormat.DEFAULT);
        Set<Integer> set = new HashSet<Integer>();
        for (CSVRecord csv_record : parser) {
            // System.out.println(csv_record);
            System.out.println(csv_record.get(5));
            System.out.println(csv_record.get(8));
            if (csv_record.get(5).equals("id")) {
                continue;
            }
            int id = Integer.parseInt(csv_record.get(5));
            if (set.contains(id)) {
                System.out.println("ERROR");
                continue;
            }
            set.add(id);
            // id, title, tagline, overview, vote_average
            writer.write(csv_record.get(5) + " ^^^ " + csv_record.get(8) + "\n");
            writer_2.write(csv_record.get(5) + " ^^^ " + csv_record.get(19) + "\n");
            writer_3.write(csv_record.get(5) + " ^^^ " + csv_record.get(9) + "\n");
            writer_4.write(csv_record.get(5) + " ^^^ " + csv_record.get(22) + "\n");
            writer_5.write(csv_record.get(5) + " ^^^ " + "https://image.tmdb.org/t/p/original" + csv_record.get(11) + "\n");
        }

        writer.close();


        CSVParser parser2 = CSVParser.parse(csv_data2, Charset.defaultCharset(), CSVFormat.DEFAULT);
        writer = new BufferedWriter(new FileWriter("/Users/enteilegend/Downloads/keywords_trimed.csv"));

        Set<Integer> set2 = new HashSet<Integer>();
        for (CSVRecord csv_record : parser2) {
            // System.out.println(csv_record);
            System.out.println(csv_record.get(0));
            System.out.println(csv_record.get(1));
            if (csv_record.get(0).equals("id")) {
                continue;
            }
            int id = Integer.parseInt(csv_record.get(0));
            if (set2.contains(id)) {
                // System.out.println("ERROR");
                continue;
            }
            set2.add(id);

            ObjectMapper mapper = new ObjectMapper();
            String line = csv_record.get(1).replace("\\", "");
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            List<Map<String, Object>> data_list = mapper.readValue(line, new TypeReference<List<Map<String, Object>>>(){});
            String final_line = "";
            for (Map<String, Object> map : data_list) {

                System.out.println(map.get("name"));
                final_line += map.get("name") + " ";
            }

            writer.write(csv_record.get(0) + "^" + final_line + "\n");
        }

        writer.close();
    }
}

