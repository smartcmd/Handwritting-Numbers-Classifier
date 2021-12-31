package daoge.machine_learning.bystyper;

import daoge.machine_learning.MnistReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BYSTyper{

    public Map<Integer,double[][]> data = new HashMap<>();//存储每个数字每个像素点为黑色的个数
    public Map<Integer,Double> num_count = new HashMap<>();//存储每个数字对应的样例个数
    public int trained_count = 0;//总样例个数

    {
        for (int i = 0;i<=9;i++) {
            data.put(i, new double[28][28]);
            num_count.put(i,0D);
        }//init
    }

    public void training(int[] image,int label){
        for (int x = 0;x < 28;x++){
            for (int y = 0;y < 28;y++){
                if (image[x*28+y] == 0)
                    data.get(label)[x][y] += 1;
            }
        }
        trained_count++;
        num_count.put(label,num_count.get(label) + 1);
        System.out.println("Trained count: " + trained_count);
    }

    public int getNum(int[] image){
        Map<Integer,Double> P = new HashMap<>();//存储数字概率

        for (int n = 0;n <= 9 ;n++) {
            P.put(n, 0.0);
        }//init

        for (int x = 0;x < 28;x++){
            for (int y = 0;y < 28;y++){
                if (image[x*28+y] == 0){
                    for (int num = 0;num <= 9; num++){
                        P.put(num,P.get(num) + ((data.get(num)[x][y]/num_count.get(num))*(num_count.get(num)/trained_count)));//贝叶斯公式
                    }
                }
            }
        }

        double maxP = -1;
        int result = -1;
        for (int i = 0;i <= 9;i++){
            if (P.get(i) > maxP) {
                maxP = P.get(i);
                result = i;
            }
        }//getmax

        return result;
    }
}
