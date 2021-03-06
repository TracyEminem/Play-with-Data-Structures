package map;

import set.LinkedList;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String filename = "src/map/pride-and-prejudice.txt";
        BSTMap<String, Integer> bstMap = new BSTMap<>();
        System.out.println("BSTMap cost time: " + testMap(bstMap, filename));

        System.out.println();

        LinkedListMap<String, Integer> linkedListMap = new LinkedListMap<>();
        testMap(linkedListMap, filename);
        System.out.println("LinkedListMap cost time: " + testMap(linkedListMap, filename));
    }

    private static double testMap(Map<String, Integer> map, String filename) {
        long startTime = System.nanoTime();
        System.out.println(filename);

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());

            for (String word : words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1); // 词频统计
                } else {
                    map.add(word, 1);
                }
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        long endTime = System.nanoTime();
        return (endTime - startTime) / 100000000.0;
    }

    public static void testBSTMap() {
        System.out.println("BSTMap Test: pride and prejudice");

        ArrayList<String> words = new ArrayList<>();
        String filename = "src/map/pride-and-prejudice.txt";
        if (FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());

            BSTMap<String, Integer> map = new BSTMap<>();
            for (String word : words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1); // 词频统计
                } else {
                    map.add(word, 1);
                }
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));

        }
    }
}
