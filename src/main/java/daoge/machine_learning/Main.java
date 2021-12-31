package daoge.machine_learning;

import daoge.machine_learning.bystyper.BYSTyper;

import java.util.Scanner;

public class Main {
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
