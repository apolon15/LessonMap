/*
Создать программу по работе со словарем. Например,
англо-испанский или французско-немецкий, или любое
другое направление.
Программа должна:
■ Обеспечивать начальный ввод данных для словаря.
■ Позволять отобразить слово и его переводы.
■ Позволять добавить, заменить, удалить переводы слова.
■ Позволять добавить, заменить, удалить слово.
■ Отображать топ-10 самых популярных слов (определяем популярность на основании счетчика обращений).
■ Отображать топ-10 самых непопулярных слов (определяем непопу
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LessonMap {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static HashMap<String, List<String>> translator = new HashMap<>();
    public static Map<String, Integer> popularWords = new HashMap<String, Integer>();
    public static Set<Map.Entry<String, Integer>> entrySet = popularWords.entrySet();


    public static void main(String[] args) throws IOException {
        boolean stop = false;
        init();
        while (!stop) {
            try {
                switch (showMenu()) {
                    case 1:
                        rusToEngl();
                        break;
                    case 3:
                        delete();
                        break;
                    case 2:
                        re();
                        break;
                    case 4:
                        reKey();
                        break;
                    case 6:
                        System.out.println("Good bye!");
                        stop = true;
                        break;
                    case 5:
                        popWord();
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Не правильный формат ввода. Введи число.");
            }
        }
    }

    /*
    метод реализации статистики.
     */
    private static void popWord() {
        ArrayList<Integer> massWordsCount = new ArrayList<Integer>();
        for (Map.Entry<String, Integer> pair : entrySet) {
            if (!massWordsCount.contains(pair.getValue()))
                massWordsCount.add(pair.getValue());
        }
        Collections.sort(massWordsCount);
        System.err.println("Kолличество использованных слов " + popularWords.size() + ". слова по возрастанию популярности -: ");
        int tempSize;
        if (massWordsCount.size() < 5) {
            tempSize = massWordsCount.size()-1;
        }else{
            tempSize=5;
        }
        for (int i = 0; i <= tempSize; i++) {
            for (Map.Entry<String, Integer> pair : entrySet) {
                if (pair.getValue() == massWordsCount.get(i)) {
                    System.out.println(pair.getKey() + "-кол-во запросов " + massWordsCount.get(i));
                }
            }
        }
        Collections.reverse(massWordsCount);
        System.err.println("Kолличество использованных слов " + popularWords.size() + ". слова по убыванию популярности -: ");
        for (int i = 0; i <= tempSize; i++) {
            for (Map.Entry<String, Integer> pair : entrySet) {
                if (pair.getValue() == massWordsCount.get(i)) {
                    System.out.println(pair.getKey() + "-кол-во запросов " + massWordsCount.get(i));
                  }
            }
        }
    }

    /*
    замена анг.слова(ключа)
     */
    private static void reKey() throws IOException {
        System.out.println("Введи слово(англ) :");
        String userWord = br.readLine();
        if (translator.containsKey(userWord)) {
            System.out.println(userWord + "-" + translator.get(userWord));
            System.out.println("Введи новое слово(англ) :");
            String newWord = br.readLine();
            translator.put(newWord, translator.get(userWord));
            System.out.println(newWord + "-" + translator.get(newWord));
        }
    }

    /*
    функция испарвления, замещения
     */
    private static void re() throws IOException {
        System.out.println("Введи слово(англ) :");
        String userWord = br.readLine();
        if (translator.containsKey(userWord.toLowerCase())) {
            System.out.println(userWord + "-" + translator.get(userWord.toLowerCase()));
            List<String> list = translator.get(userWord.toLowerCase());
            System.out.println("Введи слово,требуещее исправления: ");
            String oldWord = br.readLine();
            boolean re = false;
            if (list.contains(oldWord)) {
                System.out.println("Новое слово(перевод): ");
                String newWord = br.readLine();
                list.set(list.indexOf(oldWord), newWord);
                re = true;
                System.out.println(userWord + "-" + translator.get(userWord.toLowerCase()));
            }
            if (!re) {
                System.out.println("Sorry, слова нИма....");
            }
        }
    }

    /*
    удаление "слово-перевода"
     */
    private static void delete() throws IOException {
        System.out.println("Введи слово(англ) :");
        String userWord = br.readLine();
        if (translator.containsKey(userWord.toLowerCase())) {
            System.out.println(userWord + "-" + translator.get(userWord.toLowerCase()));
            System.out.println("Нажмите 1 для удаления всей коолекции\n2-введите порядковый номер элемента для удаления\n3-добавить слово ");
            int userChoise = Integer.parseInt(br.readLine());
            List<String> list = translator.get(userWord.toLowerCase());
            Iterator<String> it = list.iterator();
            switch (userChoise) {
                case 1:
                    translator.remove(userWord.toLowerCase());
                    System.out.println("Очищено ");
                    break;
                case 2:
                    System.out.println("Введи порядковый номер слова: ");
                    int del = Integer.parseInt(br.readLine());
                    boolean remove = false;
                    int index = 0;
                    while (it.hasNext()) {
                        if (index == del - 1) {
                            it.next();
                            it.remove();
                            remove = true;
                            System.out.println(userWord + "-" + translator.get(userWord.toLowerCase()));
                            break;
                        }
                        index++;
                    }
                    if (remove) {
                        System.out.println("delete");
                    }
                case 3:
                    System.out.println("Введи слово для добавления в словарь : ");
                    String newWord = br.readLine();
                    if (!list.contains(newWord)) {
                        list.add(newWord);
                        System.out.println(userWord + "-" + translator.get(userWord.toLowerCase()));

                    } else {
                        System.out.println("Такое слово есть");
                    }
            }
        } else {
            System.out.println("Sorry,нет слова...");
        }

    }

    /*
    функция перевода
     */
    private static Map<String, Integer> rusToEngl() throws IOException {
        int count = 1;
        System.out.println("Введи слово(англ) :");
        String userWord = br.readLine();
        if (translator.containsKey(userWord.toLowerCase())) {
            System.out.println(userWord + "-" + translator.get(userWord.toLowerCase()));
            if (popularWords.get(userWord) != null) {
                count = popularWords.get(userWord);
                popularWords.replace(userWord, popularWords.get(userWord), ++count);
            } else {
                popularWords.put(userWord, count);
            }
        } else {
            System.out.println("нет перевода...");
        }
        return popularWords;
    }

    /*
    меню для выбора пункта
     */
    private static int showMenu() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Выбери действие: \n1-перевод англ-русский" +
                "\n2-исправить перевод\n3-удалить\\добавить \n4-заменить англ.ориг. слово\n5-увидеть статистику запросов\n6-выйти из программы");
        return Integer.parseInt(br.readLine());
    }

    /*
    создание своего словаря
     */
//    private static void init2() throws IOException {
//        System.out.println("Создадим словарь");
//        do {
//            System.out.println("Введи слово :");
//            String word = br.readLine();
//            ArrayList<String> wordNew = new ArrayList<>();
//            System.out.println("Введи перевод.Если слов несколько, разделяй их пробелами:");
//            String translat = br.readLine();
//            String[] wordMass = translat.split(" ");
//            wordNew.addAll(Arrays.asList(wordMass));
//            translator.put(word, wordNew);
//            System.out.println("0-продолжить заполнять/любая клавиша- выход");
//        } while (br.readLine().equals("0"));
//
//    }

    private static void init() {
        System.out.println("В коллекции представлены слова: hello, bye, ok, car, house, fly, phone.");
        ArrayList<String> hello = new ArrayList<>();
        hello.addAll(Arrays.asList("привет", "добрый день", "здравстуйте"));
        translator.put("hello", hello);
        ArrayList<String> bye = new ArrayList<>();
        bye.addAll(Arrays.asList("пока", "до связи", "увидимся"));
        translator.put("bye", bye);
        ArrayList<String> ok = new ArrayList<>();
        ok.addAll(Arrays.asList("ok", "отлично", "хорошо"));
        translator.put("ok", ok);
        ArrayList<String> car = new ArrayList<>();
        car.addAll(Arrays.asList("машина", "авто", "автомобиль"));
        translator.put("car", car);
        ArrayList<String> house = new ArrayList<>();
        house.addAll(Arrays.asList("дом", "жильё", "квартира"));
        translator.put("house", house);
        ArrayList<String> fly = new ArrayList<>();
        fly.addAll(Arrays.asList("летать", "парить"));
        translator.put("fly", fly);
        ArrayList<String> phone = new ArrayList<>();
        phone.addAll(Arrays.asList("телефон", "мобила", "смартфон"));
        translator.put("phone", phone);
    }
}



