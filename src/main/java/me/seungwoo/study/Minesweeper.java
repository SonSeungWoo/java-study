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
        String[][] boardc = new String[size][size];
        int[][] boardi = new int[size][size];
        int i, j;

        //지뢰 설치
        for (i = 0; i < 10; i++) {
            int a = (int) (Math.random() * size);
            int b = (int) (Math.random() * size);
            if ("*".equals(boardc[a][b])) {
                i--;
                continue;
            }
            boardc[a][b] = "*";
        }

        //사각형에 표시될 숫자
        for (i = 0; i < boardc.length; i++) { // 내 위치
            for (j = 0; j < boardc[i].length; j++) {
                if ("*".equals(boardc[i][j])) continue; // 현재 위치가 지뢰일경우
                int count = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int h = j - 1; h <= j + 1; h++) {
                        if (k == -1 || k == size || h == -1 || h == size) continue; //현재 내위치 이거나 사각형 범위 넘어갈 경우
                        if ("*".equals(boardc[k][h])) count++;
                    }
                }
                boardi[i][j] = count;
            }
        }

        //결과 출력
        for (i = 0; i < boardc.length; i++) {
            for (j = 0; j < boardc[i].length; j++) {
                if ("*".equals(boardc[i][j])) {
                    System.out.print(boardc[i][j] + " ");
                } else {
                    System.out.print(boardi[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}