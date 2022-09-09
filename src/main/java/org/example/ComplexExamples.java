package org.example;

import java.util.*;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        /**
         * Task1.
         * Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени
         *
         *             Что должно получиться
         *                 Key: Amelia
         *                 Value:4
         *                 Key: Emily
         *                 Value:1
         *                 Key: Harry
         *                 Value:3
         *                 Key: Jack
         *                 Value:1
         */
        System.out.println("task1:");
        task1();


        /**
         * Task2.
         * [3, 4, 2, 7], 10 -> [3, 7] - вывести пару именно в скобках, которые дают сумму - 10 */
        System.out.println("task2:");
        task2();

        /**
         * Task3.
         *             Реализовать функцию нечеткого поиска
         *
         *                     fuzzySearch("car", "ca6$$#_rtwheel"); // true
         *                     fuzzySearch("cwhl", "cartwheel"); // true
         *                     fuzzySearch("cwhee", "cartwheel"); // true
         *                     fuzzySearch("cartwheel", "cartwheel"); // true
         *                     fuzzySearch("cwheeel", "cartwheel"); // false
         *                     fuzzySearch("lw", "cartwheel"); // false
         *          */
        System.out.println("task3:");
        task3();
    }

    private static void task1() {
        final List<Person> personListWithUniqueId = Arrays.stream(RAW_DATA)
                .distinct()
                .sorted(Comparator.comparing(Person::getName))
                .toList();

        Map<String, Integer> resultMap = new HashMap<>();
        for (Person person : personListWithUniqueId) {
            Integer orDefault = resultMap.getOrDefault(person.getName(), 0);
            resultMap.put(person.getName(), ++orDefault);
        }
        System.out.println(resultMap + "\n");
    }

    private static void task2() {
        final int[] arr = new int[]{3, 4, 2, 7};
        System.out.println(twoSum(arr, 10) + "\n");
    }

    private static ArrayList<Integer> twoSum(int[] arr, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int j : arr) {
            Integer complement = map.get(j);
            if (complement != null) {
                return new ArrayList<>(Arrays.asList(complement, j));
            }
            map.put(target - j, j);
        }
        throw new RuntimeException("not two numbers creating target");
    }

    private static void task3() {
        System.out.println(fuzzySearch("car", "ca6$$#_rtwheel"));
        System.out.println(fuzzySearch("cwhl", "cartwheel"));
        System.out.println(fuzzySearch("cwhee", "cartwheel"));
        System.out.println(fuzzySearch("cartwheel", "cartwheel"));
        System.out.println(fuzzySearch("cwheeel", "cartwheel"));
        System.out.println(fuzzySearch("lw", "cartwheel"));
    }

    private static boolean fuzzySearch(String pat, String text) {
        for (int j = 0, i = 0; j < pat.length() && i < text.length(); ) {
            if (pat.charAt(j) == text.charAt(i)) {
                j++;
                i++;
                if (j == pat.length()) {
                    return true;
                }
            } else {
                while (pat.charAt(j) != text.charAt(i)) {
                    i++;
                    if (i == text.length()) {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}


