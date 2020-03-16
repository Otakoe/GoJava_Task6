package fx;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;

public class MainFX extends Application {
    private Random rand=new Random();
    private static int MAX_HEIGHT =1000;
    private static int MAX_WIDTH =1500;
    private static int MIN_HEIGHT = 400;
    private static int MIN_WIDTH = 600;
    private static int HEIGHT = 800;
    private static int WIDTH = 1000;

    public static void main(String[] args) {
        launch(args);
    }

    void windowSetup(Stage stage){
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);

        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);

        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

    }

    String generateColor (){
        String color="#";
        for(int i=0;i<6;i++){
            color+=(char)('0'+1+Math.abs(rand.nextInt()%8));
        }
        System.out.println("color "+ color);
        return color;
    }

    private void drawSnowman (Pane root) {
        int count ;
        int minRad ;
        int maxRad ;
        int prevY = 0;                                  //координата У предыдущего круга
        int prevRad = 0;                                //радиус предудущего круга
        int rad = 0;
        int x = WIDTH / 2;
        int y = HEIGHT - 40;
        int face;
        int allY[];
        int allRad[];
        int allRadiusSum=0;                                          //сумма всех радиусов основных кругов
        System.out.println("Введите в консоли \'число кругов\' \'минимальный радиус\' \'максимальный радиус\'");
        while(true){
            try {
                Scanner scan = new Scanner(System.in);
                count = scan.nextInt();
                minRad = scan.nextInt();
                maxRad = scan.nextInt();
                if (count > 11) {
                    System.out.println("Снеговик упадёт, введите число до 11");
                    continue;
                }
                if ((maxRad+minRad)/2*(count-1)>=(HEIGHT/2)) {                                    //Если количество на среднее арифметическое мин и мак радиуса больше или равно половины высоты окна с отступом
                    System.out.println("Снеговик может не поместиться, введите радиусы поменьше");
                    System.out.println((maxRad+minRad)/2*count);
                    System.out.println(HEIGHT/2);
                    continue;
                }
                if (minRad < 6) {
                    System.out.println("Снеговика с таким минимальным радиусом неудобно лепить");
                    continue;
                }
                if(minRad>=maxRad){
                    System.out.println("Мин радиус должен быть меньше");
                }
                else
                    break;
            }
            catch (Exception e){
                System.out.println("Неверный ввод");
            }
        }
        allRad=new int[count];
        allY = new int[count];
        int tries=0;
        /*генерим радиусы пока сумма радиусов не будет меньше чем пол высоты окна - отступ, но не долго.
         * если сгенерить не получится то ориентируемся на МАКС размеры окна
         * следующие два ифа не относятся как к таковому заданию, просто для избегания косяков
         */
        do {
            for (int i = 0; i < count; i++) {
                rad = Math.abs(rand.nextInt() % (maxRad - minRad)) + minRad;
                y -= (rad + prevRad);
                prevRad = rad;
                allY[i]=y;
                allRad[i]=rad;
                allRadiusSum+= rad;
                System.out.println(rad);
            }
            tries++;
        }while(allRadiusSum>=(HEIGHT/2-40)&&tries<20);
        // следующий иф генерит радиусы и координаты ориентируясь на макс размеры окна если по базовому размеру окна не удалось вместить
        if(tries==20){
            y = MAX_HEIGHT - 40;
            for (int i = 0; i < count; i++) {
                rad = Math.abs(rand.nextInt() % (maxRad - minRad)) + minRad;
                y -= (rad + prevRad);
                prevRad = rad;
                allY[i]=y;
                allRad[i]=rad;
                allRadiusSum+= rad;
                System.out.println(rad);
            }
        }
        // Для красоты и удобства сдвигаем ввверх снеговика по окну под верх окна  с небольшим отступом
        if(y>rad+20){
            for (int i = 0; i < count; i++) {
                allY[i]-=(y-rad-20);
            }
            y=allY[count-1];
        }
        // рисуем все телесные круги снеговика
        for (int i=0;i<count;i++){
            root.getChildren().addAll(generateCircle( x, allY[i], allRad[i], 3));
        }
        //System.out.println(x+" "+y+" "+rad);       // для дебага. раскоментить если что-то сломалось
        prevY=y;
        face=rad/3;                     //просто число для удобства рисования елементов лица в пределах круга лица

        //рот
        y=prevY+face;
        rad=rand.nextInt((int)(prevRad/3.5))+2;
        root.getChildren().addAll(generateCircle(x,y,rad,2));
        //глаз левый
        y=prevY-face;
        x-=face;
        rad=rand.nextInt((int)(prevRad/3.5))+2;
        root.getChildren().addAll(generateCircle(x,y,rad,2));
        //глаз правый
        x+=face*2;
        rad=rand.nextInt((int)(prevRad/3.5))+2;
        root.getChildren().addAll(generateCircle(x,y,rad,2));

    }

    private Circle generateCircle(int x, int y, int rad,int stroke){
        Circle crl;
        crl = new Circle(x,y,rad);
        crl.setFill(Paint.valueOf("#00000000"));
        crl.setStrokeWidth(stroke);
        crl.setStroke(Paint.valueOf(generateColor()));
        return crl;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        windowSetup(stage);
        drawSnowman(root);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
