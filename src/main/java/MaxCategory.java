import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class MaxCategory {
    private Map<String, Integer> maxCategoryAllTime = new HashMap<>();
    private ArrayList<Category> arraysDays = new ArrayList<>();
    private ArrayList<Category> arraysMount = new ArrayList<>();
    private ArrayList<Category> arraysYear = new ArrayList<>();

    public MaxJson maxCategory(String titleCategory, Integer price) {
        if (maxCategoryAllTime.get(titleCategory) == null) {
            maxCategoryAllTime.put(titleCategory, price);
        } else {
            int sum = maxCategoryAllTime.get(titleCategory) + price;
            maxCategoryAllTime.put(titleCategory, sum);
        }
        String titleCategoryMax = maxCategoryAllTime.entrySet().stream()
                .filter(entry -> Collections.max(maxCategoryAllTime.values()).equals(entry.getValue()))
                .findFirst().map(Map.Entry::getKey)
                .orElse(null);
        return new MaxJson("maxCategory", titleCategoryMax, Collections.max(maxCategoryAllTime.values()));
    }

    public MaxJson maxDayCategory(String titleCategory, String date, Integer price) {
        arraysDays.add(new Category(titleCategory, date, price));
        String[] lineMaxDay = calcMaxCategory(date, arraysDays).split(" ");
        String maxDayTitle = lineMaxDay[0];
        Integer maxDayPrice = Integer.valueOf(lineMaxDay[1]);
        return new MaxJson("maxDayCategory", maxDayTitle, maxDayPrice);
    }

    public MaxJson maxMountCategory(String titleCategory, String date, Integer price) {
        arraysMount.add(new Category(titleCategory, date, price));
        String[] lineMaxMount = calcMaxCategory(date, arraysMount).split(" ");
        String maxMountTitle = lineMaxMount[0];
        Integer maxMountPrice = Integer.valueOf(lineMaxMount[1]);
        return new MaxJson("maxMonthCategory", maxMountTitle, maxMountPrice);
    }

    public MaxJson maxYearCategory(String titleCategory, String date, Integer price) {
        arraysYear.add(new Category(titleCategory, date, price));
        String[] lineMaxYear = calcMaxCategory(date, arraysYear).split(" ");
        String maxYearTitle = lineMaxYear[0];
        Integer maxYearPrice = Integer.valueOf(lineMaxYear[1]);
        return new MaxJson("maxYearCategory", maxYearTitle, maxYearPrice);
    }

    public String calcMaxCategory(String date, ArrayList list) {
        Map<String, Integer> mapMaxCategory = new HashMap<>();
        Iterator<Category> maxTimeIterator = list.iterator();

        while (maxTimeIterator.hasNext()) {
            Category max = maxTimeIterator.next();
            if (max.getDate().equals(date)) {
                if (mapMaxCategory.get(max.getTitle()) == null) {
                    mapMaxCategory.put(max.getTitle(), max.getSum());
                } else {
                    int sum = mapMaxCategory.get(max.getTitle()) + max.getSum();
                    mapMaxCategory.put(max.getTitle(), sum);
                }
            }
        }
        String maxTitle = mapMaxCategory.entrySet().stream()
                .filter(entry -> Collections.max(mapMaxCategory.values()).equals(entry.getValue()))
                .findFirst().map(Map.Entry::getKey)
                .orElse(null);

        return maxTitle + " " + Collections.max(mapMaxCategory.values());
    }

//    public void saveBin(File binFile) {
//        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(binFile))) {
//            objectOutputStream.writeObject(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static MaxCategory loadBinFile(File binFile) throws IOException, ClassNotFoundException {
//        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(binFile))) {
//            MaxCategory maxCategory = (MaxCategory) objectInputStream.readObject();
//            maxCategory.setArraysMount(arraysMount);
//            maxCategory.setArraysDays(arraysDays);
//            maxCategory.setArraysYear(arraysYear);
//            maxCategory.setMaxCategoryAllTime(maxCategoryAllTime);
//            return maxCategory;
//        }
//    }





}