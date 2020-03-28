package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        System.out.println("minValue: ");
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3})); //#=> 123
        System.out.println(minValue(new int[]{9, 8})); //#=> 89

        System.out.println("\noddOrEven");
        List<Integer> oddSum = Arrays.asList(2, 3);
        List<Integer> evenSum = Arrays.asList(3, 5, 2);
        System.out.println(oddOrEven(oddSum)); //#=>2
        System.out.println(oddOrEven(evenSum)); //#=> 3,5
    }

    /**
     * @param values массив цифр от 1 до 9
     * @return минимально возможное число, составленное из набора уникальных цифр
     */
    public static int minValue(int[] values) {
        List<Integer> list = Arrays.stream(values).boxed().collect(Collectors.toList());
        final long[] size = {list.stream().distinct().count()};
        return list.stream().distinct().sorted().map((s) -> {
            size[0] = size[0] - 1;
            return (int) (s * Math.pow(10, size[0]));
        }).mapToInt(s -> s).sum();
    }

    /**
     * @param integers список чисел
     * @return если сумма всех чисел нечетная - возвращает только четные числа <br>
     * если четная - возвращает только нечентные числа
     */
    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(s -> s).sum();
        if (sum % 2 == 0) {
            return integers.stream().filter((s) -> (s % 2 != 0)).collect(Collectors.toList());
        } else {
            return integers.stream().filter((s) -> (s % 2 == 0)).collect(Collectors.toList());
        }
    }
}
