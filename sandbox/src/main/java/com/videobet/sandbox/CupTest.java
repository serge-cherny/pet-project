package com.videobet.sandbox;

public class CupTest {
    public static void main(String[] args) {
        double[] vol = {4, 3, 2} ;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 3 ; j++) {
                for (int k = 0; k < 3 ; k++) {
                    if (j == k) {
                        vol[k] /= 2;
                    } else  if (k < j) {
                        vol[k] += vol[j] / 4;
                    } else {
                        vol[k] += vol[j] / 2;
                    }
                }
                System.out.println(String.format("%.6f, %.6f, %.6f" , vol[0], vol[1], vol[2]));
            }
        }
    }
}
