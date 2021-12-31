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

    public static void main(String[] args) {
        BYSTyper bysTyper = new BYSTyper();
        int[][] images2v = MnistReader.getImages2v(MnistReader.TRAIN_IMAGES_FILE);
        int[] label = MnistReader.getLabels(MnistReader.TRAIN_LABELS_FILE);
        int[][] t_images2v = MnistReader.getImages2v(MnistReader.TEST_IMAGES_FILE);
        int[] t_label = MnistReader.getLabels(MnistReader.TEST_LABELS_FILE);
        System.out.println("training...");
        for (int i = 0;i < images2v.length;i++)
            bysTyper.training(images2v[i],label[i]);
        System.out.println("finished!");

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("> ");
            String input = scanner.next();
            switch (input){
                case "test":
                    String number = scanner.next();//Serial number
                    if (number.equals("all")) {
                        int correct_num = 0;
                        for (int j = 0; j < 10000; j++) {
                            if (bysTyper.getNum(t_images2v[j]) == t_label[j])
                                correct_num++;
                            else
                                System.out.println("false at: " + j);
                        }
                        System.out.println("----------");
                        System.out.println("test count: 10000");
                        System.out.println("correct count: " + correct_num);
                    }else{
                        int number_ = Integer.valueOf(number);
                        System.out.println("answer: " + t_label[number_]);
                        System.out.println("result: " + bysTyper.getNum(t_images2v[number_]));
                    }
                    break;
            }
        }//test console
    }
}
