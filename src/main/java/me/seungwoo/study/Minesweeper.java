package me.seungwoo.study;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-08-10
 * Time: 11:45
 */
public class Minesweeper {

    public static void main(String[] args) {
        final int size = 10;
        String[][] square = new String[size][size];
        int[][] squarei = new int[size][size];

        //지뢰 10개 설치
        for (int i = 0; i < 10; i++) {
            int a = (int) (Math.random() * size);
            int b = (int) (Math.random() * size);
            if ("*".equals(square[a][b])) {
                i--;
                continue;
            }
            square[a][b] = "*";
        }

        //주변지뢰 카운팅
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                if ("*".equals(square[i][j])) continue; // 현재 위치가 지뢰일경우
                int count = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int h = j - 1; h <= j + 1; h++) {
                        if (k == -1 || k == size || h == -1 || h == size) continue; //현재 내위치 이거나 사각형 범위 넘어갈 경우
                        if ("*".equals(square[k][h])) count++;
                    }
                }
                squarei[i][j] = count;
            }
        }

        //결과 출력
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                if ("*".equals(square[i][j])) {
                    System.out.print(square[i][j] + " ");
                } else {
                    System.out.print(squarei[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}