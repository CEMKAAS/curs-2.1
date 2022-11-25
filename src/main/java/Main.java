import com.google.gson.Gson;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private MaxCategory maxCategory = new MaxCategory();

    public void server() {

        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    out.println("Start!" + " Введите покупку и сумму в таком формате " + "пример: булка 03.10.2022 300");
                    File binFile = new File("basket.bin");
                    String dataEntry = in.readLine();
                    Gson gson = new Gson();
                    Category category = gson.fromJson(dataEntry, Category.class);

                    System.out.println(category.getTitle());//можно удалить

                    //парсим
                    CSVParser parser = new CSVParserBuilder()
                            .withSeparator('\t')
                            .build();
                    CSVReader reader = new CSVReaderBuilder(new FileReader("categories.tsv"))
                            .withCSVParser(parser)
                            .build();

                    Map<String, String> categoryInTSV = new HashMap<>();
                    List<String[]> listCategoryInTSV = reader.readAll();
                    for (String[] line : listCategoryInTSV
                    ) {
                        String title = line[0];
                        String titleCategory = line[1];
                        categoryInTSV.put(title, titleCategory);
                    }

//                    if (binFile.exists()) {
//                        maxCategory.loadBinFile(binFile);
//                    }

                    if (categoryInTSV.get(category.getTitle()) != null) {
                        out.println(
                                output(category, categoryInTSV.get(category.getTitle()))
                        );
                    } else {
                        out.println(
                                output(category, "другое")
                        );
                    }
//                    maxCategory.saveBin(binFile);

                }
            }
        } catch (
                IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }

    public String output(Category category, String titleCategory) {
        Gson gson = new Gson();
        String[] date = category.getDate().split("\\.");
        String mount = date[1];
        String year = date[2];
        String maxCategoryJson = gson.toJson(maxCategory.maxCategory(titleCategory, category.getSum()));
        String maxCategoryDayJson = gson.toJson(maxCategory.maxDayCategory(titleCategory, category.getDate(), category.getSum()));
        String maxCategoryMountJson = gson.toJson(maxCategory.maxMountCategory(titleCategory, mount + "." + year, category.getSum()));
        String maxCategoryYearJson = gson.toJson(maxCategory.maxYearCategory(titleCategory, year, category.getSum()));
        return maxCategoryJson + maxCategoryDayJson + maxCategoryMountJson + maxCategoryYearJson;
    }
}
