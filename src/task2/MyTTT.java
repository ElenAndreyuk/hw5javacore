package task2;
//package ru.gb.lesson5.seminar;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 2. Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3],
 * и представляют собой, например, состояния ячеек поля для игры в крестики-нолики,
 * где 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле с ноликом, 3 – резервное значение.
 * Такое предположение позволит хранить в одном числе типа int всё поле 3х3.
 * Записать в файл 9 значений так, чтобы они заняли три байта.
 *  3 (*) - В продолжение 2 дописать "разворачивание" поля игры крестики-нолики
 *  из сохраненного в файле состояния (распарсить файл, в зависимости от значений (0-3)
 *  нарисовать поле со значками 'х' 'о' '.')
 *
 */
public class MyTTT {
    public static void main(String[] args) {
        int[] cellValues = {0, 1, 0, 2, 3, 0, 1, 2, 0};

        System.out.println(ticTacToeSave(cellValues));

        int[] arr = ticTacToeDownload();
        for (int i : arr) {
            System.out.printf(i + ", ");
        }
        System.out.println();

        saveData(cellValues);
        readData();
        printGame(arr);
    }

    /**
     * Это решение мое, но оно не соответствует условию уместиться в 3 байта.
     * Но я все равно немного горжусь)))
     */
    static boolean ticTacToeSave(int[] cellValues) {
        int factor = 1;
        int res = 0;
        try (FileWriter fileWriter = new FileWriter("TicTacToeSave.txt")) {
            for (int i = cellValues.length - 1; i >= 0; i--) {  // преобразуем массив в число
                res = res + cellValues[i] * factor;
                factor = factor * 10;
            }
            StringBuilder result = new StringBuilder(Integer.toString(res));
            while (result.length() < 9) {              // добавляем нули спереди если они нужны
                result.insert(0, "0");
            }
            fileWriter.write(String.valueOf(result));   // записываем в файл
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    static int[] ticTacToeDownload() {  //  выводим массив из файла
        int[] result = new int[9];
        try (FileInputStream inStream = new FileInputStream("TicTacToeSave.txt")) {
            Scanner scanner = new Scanner(inStream);
            int tmp = scanner.nextInt();
            int factor = 10;
            for (int i = result.length - 1; i >= 0; i--) {
                result[i] = tmp % factor;
                tmp = tmp / factor;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    /**
     * А вот побитовые сдвиги пришлось подсмотреть у других.
     */
    static void saveData(int[] cellValues) {      // сохраняем игровое поле
        try (FileOutputStream fos = new FileOutputStream("TicTacToeSave2.txt")) {
            for (int b = 0; b < 3; b++) { // write to 3 bytes
                byte wr = 0;
                for (int v = 0; v < 3; v++) { // write by 3 values in each
                    wr += (byte) (cellValues[3 * b + v] << (v * 2));
                }
                fos.write(wr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            ;
        }
    }

    static void readData() {
        int[] ar20 = new int[9];
        try (FileInputStream fis = new FileInputStream("TicTacToeSave2.txt")) {
            int b;
            int i = 0;
            while ((b = fis.read()) != -1) {
                for (int v = 0; v < 3; ++v) { // 3 values of four possible
                    ar20[i++] = b >> (v * 2) & 0x3;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(ar20));
    }

    static void printGame(int[] result) {
        String [] array = new String[9];
        for (int i = 0; i < result.length; i++) {
            switch (result[i]){
                case 0:
                    array[i] = " ";
                    break;
                case 1:
                    array[i] = "X";
                    break;
                case 2:
                    array[i] = "O";
                    break;
                case 3:
                    array[i] = ".";
                    break;
            }
        }
        System.out.println("_____________");
        System.out.println("| " + array[0] + " | " + array[1] + " | " + array[2] + " | ");
        System.out.println("_____________");
        System.out.println("| " + array[3] + " | " + array[4] + " | " + array[5] + " | ");
        System.out.println("_____________");
        System.out.println("| " + array[6] + " | " + array[7] + " | " + array[8] + " | ");
        System.out.println("_____________");


    }

}
