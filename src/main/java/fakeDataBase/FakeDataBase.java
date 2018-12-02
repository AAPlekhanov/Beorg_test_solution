package fakeDataBase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Как написано в задании имитация БД которая отдаёт множество сущностей {@link Product}
 */
public class FakeDataBase {

    public static Set<Product> getData() {
        Set<Product> products = new HashSet<>();
        try {
            // читаем текстовый файл и парсим из него объекты Product
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\main\\resources\\matrix.txt"));
            while (bufferedReader.ready()) {
                String row = bufferedReader.readLine();
                String[] splitRows = row.split("\\t");
                // заменяем прочерк в штрихкоде на 0
                if (splitRows[1].equals("-")) {
                    splitRows[1] = "0";
                }
                Product product = new Product(Integer.parseInt(splitRows[0]),
                        Long.parseLong(splitRows[1]), splitRows[2], splitRows[3], splitRows[4], splitRows[5], splitRows[6]);
                products.add(product);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
