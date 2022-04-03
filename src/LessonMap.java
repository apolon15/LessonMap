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
    public static HashMap<String, List<String>> translator = new HashMap();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static
    TreeMap<Integer, String> popular = new TreeMap();
    static Set<Map.Entry<Integer, String>> entrySet = popular.entrySet();


    public static void main(String[] args) throws IOException {
        boolean stop = false;
        int min = 0;
        int max = 0;
        int count;
        init2();
        while (!stop) {
            try {
                switch (showMenu()) {
                    case 1:
                        count = rusToEngl();
                        if (count > max) {//сдесь введен count для способа создания статисктики.НЕ ПОЛУЧИЛОСЬ.Думаю дальше...
                            max = count;
                        }
                        if (count < max) {
                            min = count;
                        }
                        if (count == -1) {
                            System.out.println("Давай еще разок ;) ");
                        }
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
                        System.err.println(popular.size() + " колличество использованных слов. Популярное слово -:" + popular.get(max) + "; не популярное -: " + popular.get(min));
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Не правильный формат ввода. Введи число.");
            }
        }
    }

/*
метод реализации статистики.НЕ РАБОТАЕТ КАК Я ХОТЕЛ.Ищу другой спооб
 */
    private static int popWord(String userWord) {
        int cnt = 0;
        if (popular.containsValue(userWord)) {
            Object O = new Object();//что хотим найти
            for (Map.Entry<Integer, String> pair : entrySet) {
                pair.getValue();
                if (!O.equals(pair.getValue())) {
                    cnt = pair.getKey();// нашли наше значение и возвращаем  ключ
                    cnt++;
                    popular.put(cnt, userWord);
                    break;
                }
            }
        }
        if (!popular.containsValue(userWord)) {
            cnt = 1;//установил ключ как первое обращение к слову
            popular.put(cnt, userWord);
        }
        return cnt;
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
    private static int rusToEngl() throws IOException {
        int count = -1;
        System.out.println("Введи слово(англ) :");
        String userWord = br.readLine();
        if (translator.containsKey(userWord.toLowerCase())) {
            System.out.println(userWord + "-" + translator.get(userWord.toLowerCase()));
            count = popWord(userWord);
        } else {
            System.out.println("нет перевода...");
        }
        return count;
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
    private static void init2() throws IOException {
        String resume = " ";
        System.out.println("Создадим словарь");
        do {
            System.out.println("Введи слово :");
            String word = br.readLine();
            ArrayList<String> wordNew = new ArrayList<>();
            System.out.println("Введи перевод :");
            String translat = br.readLine();
            String[] wordMass = translat.split(" ");
            wordNew.addAll(Arrays.asList(wordMass));
            translator.put(word, wordNew);
            System.out.println("0-продолжить заполнять/любая клавиша- выход");
        } while (br.readLine().equals("0"));

    }

//    private static void init() {
//        System.out.println("В коллекции представлены слова: hello, bye, ok, car, house, fly, phone.");
//        ArrayList<String> hello = new ArrayList<>();
//        hello.addAll(Arrays.asList("привет", "добрый день", "здравстуйте"));
//        translator.put("hello", hello);
//        ArrayList<String> bye = new ArrayList<>();
//        bye.addAll(Arrays.asList("пока", "до связи", "увидимся"));
//        translator.put("bye", bye);
//        ArrayList<String> ok = new ArrayList<>();
//        ok.addAll(Arrays.asList("ok", "отлично", "хорошо"));
//        translator.put("ok", ok);
//        ArrayList<String> car = new ArrayList<>();
//        car.addAll(Arrays.asList("машина", "авто", "автомобиль"));
//        translator.put("car", car);
//        ArrayList<String> house = new ArrayList<>();
//        house.addAll(Arrays.asList("дом", "жильё", "квартира"));
//        translator.put("house", house);
//        ArrayList<String> fly = new ArrayList<>();
//        fly.addAll(Arrays.asList("летать", "парить"));
//        translator.put("fly", fly);
//        ArrayList<String> phone = new ArrayList<>();
//        phone.addAll(Arrays.asList("телефон", "мобила", "смартфон"));
//        translator.put("phone", phone);
//    }
}
//        HashMap<String, Integer> testMap = new HashMap();
//        testMap.put("user1", 111);
//        testMap.put("user2", 222);
//        testMap.put("user1", 333);
//        System.out.println(testMap.size());
//        testMap.get("use1");//333
//        String name = "Petr";
//        if (!testMap.containsKey(name)) {
//            testMap.put(name, 444);
//        }
//        Set<String> blockedSet = new HashSet<>();
//        Set<Map.Entry<String, Integer>> entrySet = testMap.entrySet();
//        for (Map.Entry<String, Integer> entry : testMap.entrySet())
//            if (blockedSet.contains((entry.getKey()))) {
//                testMap.remove(entry);
//            }
//        testMap.keySet();

//       testMap.entrySet();


