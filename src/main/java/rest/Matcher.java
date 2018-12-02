package rest;

import fakeDataBase.FakeDataBase;
import fakeDataBase.Product;

import java.io.*;
import java.util.Set;


public class Matcher {

    /**
     * метод ищет из входного файла совпадения продуктов в БД по штрих-коду
     * или по названию , при нахождении совпадения - записываются отформатированные данные из входящей строки
     * и найденные данные из БД
     *
     * @param fileName - имя въодного файла
     * @return - выходной файл с рузультатом поиска продукта в БД
     */
    public static String matcher(String fileName) {
        //результат записываем сюда
        StringBuilder stringBuilder = new StringBuilder();

        // получаем множество объектов из фейковой БД
        Set<Product> products = FakeDataBase.getData();
        try {
            BufferedReader bufferedReader = getBufferedReader(fileName);
            while (bufferedReader.ready()) {
                boolean findInDataBase = false;
                String row = bufferedReader.readLine();
                String[] splitedRow = row.split("\\t");
                for (String pieceOfRow : splitedRow) {
                    for (Product product : products) {
                        // если из строки входящего файла нашли свпадение из БД
                        // (по штрих коду или по названию) ставим флаг - true
                        // записываем type-docking (по какому значению было найдено совпадение)
                        if (pieceOfRow.equals(String.valueOf(product.getBarCode()))) {
                            stringBuilder.append(formatRows(row)).append(" type-docking-1")
                                    .append(" " + product.getArtId())
                                    .append(" " + product.getGr20())
                                    .append(" " + product.getGr21())
                                    .append(" " + product.getGr22())
                                    .append(" " + product.getGr23()).append("\r\n");
                            findInDataBase = true;
                            break;
                        } else if (pieceOfRow.equals(product.getName())) {
                            stringBuilder.append(formatRows(row)).append(" type-docking-2")
                                    .append(" " + product.getArtId())
                                    .append(" " + product.getGr20())
                                    .append(" " + product.getGr21())
                                    .append(" " + product.getGr22())
                                    .append(" " + product.getGr23()).append("\r\n");
                            findInDataBase = true;
                            break;
                        }
                    }
                    // если совпадение найдено - выходим из цикла
                    if (findInDataBase) {
                        break;
                    }
                }
                // если совпадение не было найдено
                if (!findInDataBase) {
                    stringBuilder.append(formatRows(row)).append(" - type-docking-0").append("\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * метод возвращает BufferedReader для файла имя которого передаётся в метод
     * имя файла ищется в директории где находится программа
     *
     * @param fileName - имя файла
     * @return - буфферизированный поток для чтения
     */
    private static BufferedReader getBufferedReader(String fileName) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader( fileName + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }

    /**
     * метод делает проверку и форматирование строки из исходного файла
     *
     * @param row - входящая строка исходного текста
     * @return - результат проверок и форматирования
     */
    private static String formatRows(String row) {
        String[] splitedRow = row.split("\\t");
        double priceWithoutDiscount = 0;
        double price = 0;

        String[] resoult = new String[6];
        for (String pieceOfrow : splitedRow) {
            if (pieceOfrow.matches("\\d{13}")) {
                // парсим из строки найденный штрих код
                resoult[2] = pieceOfrow + " - barcode ";
            } else if (pieceOfrow.matches(".*[а-я].*") || pieceOfrow.matches(".*[a-z].*")) {
                // парсим из строки product_name
                resoult[1] = pieceOfrow + " - product_name ";
            } else if (pieceOfrow.matches(".*[A-Z].*") || pieceOfrow.matches(".*[А-Я].*")) {
                // парсим из строки real name
                resoult[0] = pieceOfrow + " - real_name ";
            } else if (pieceOfrow.matches(".*\\d.*")) {
                // парсим из строки найденные цены
                pieceOfrow = pieceOfrow.replaceAll(",", ".");
                double parsePrice = Double.parseDouble(pieceOfrow);
                if (parsePrice > 2 && priceWithoutDiscount == 0) {
                    priceWithoutDiscount = parsePrice;
                } else if (parsePrice > 2 && priceWithoutDiscount != 0) {
                    price = parsePrice;
                }
            }
        }
        // Выставляем акциозность ( 0 - нет акциозности)
        if (price == priceWithoutDiscount) {
            resoult[5] = "accomplishments-0 ";
        } else {
            resoult[5] = "accomplishments-1 ";
        }
        //во входных данных может быть только одна цена
        if (priceWithoutDiscount != 0 && price == 0) {
            price = priceWithoutDiscount;
        }
        // если цены отличаются ставим большее число в значение price_without_discount
        if (price > priceWithoutDiscount) {
            resoult[3] = price + " - price_without_discount ";
            resoult[4] = priceWithoutDiscount + " - price ";
        } else {
            resoult[4] = price + " - price ";
            resoult[3] = priceWithoutDiscount + " - price_without_discount ";
        }
        StringBuilder stringBuilder = new StringBuilder();
        // соединяем распаршенные данные из строки
        stringBuilder.append(resoult[0]).append(resoult[1]).append(resoult[2]).append(resoult[3]).append(resoult[4]).append(resoult[5]);
        return stringBuilder.toString().replaceAll("null", "");
    }
}
