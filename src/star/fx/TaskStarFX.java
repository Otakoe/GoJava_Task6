package star.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.Math.*;

public class TaskStarFX extends Application {
    private static int MAX_HEIGHT =1000;
    private static int MAX_WIDTH =1500;
    private static int MIN_HEIGHT = 400;
    private static int MIN_WIDTH = 600;
    private static int HEIGHT = 800;
    private static int WIDTH = 1000;

    public static void main(String[] args){
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
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        windowSetup(stage);
        drawStar(pane);
        //Line line = new Line(5,8,12,45);
        //pane.getChildren().addAll(line);
        stage.setScene(new Scene(pane));
        stage.show();
    }

    public void drawStar(Pane pane) {

        int minRad=1;
        int maxRad=1;
        int cornersStar=1;
        int angle;
        int xa, xb, ya, yb;                // переменные координат условных точек "а" и "б" . нужно для построения линий
        int xFirst,yFirst;
        int centerX, centerY;
        Line line;
        Scanner scan = new Scanner(System.in);

        //инициализация переменных
        while(true){
            System.out.println("Введите\n\t1- количество углов звезды\n\t2- радиус внутренних углов\n\t3- радиус внешних углов");
            try {
                cornersStar = scan.nextInt();
                minRad = scan.nextInt();
                maxRad = scan.nextInt();
                if(cornersStar>20){
                    System.out.println("Много углов, будет шипастый ёжик а не звезда");
                    continue;
                }
                if(minRad<5){
                    System.out.println("Слишком маленький внутренний радиус");
                    continue;
                }
                if(maxRad<=minRad||maxRad>HEIGHT/2)
                {
                    System.out.println("Внешний радиус должен быть больше внутреннего но не очень");
                    continue;
                }
                else{
                    break;
                    }

            }
            catch(Exception e){
                System.out.println("Неверный ввод");
                //Затычка которая чистит буфер скана (СПАСАЕТ от зацикливания исключения)
                if(scan.hasNext()) {
                    scan.next();
                }
            }
        }
        while (true){
            try{
                System.out.println("Ещё введите пожалуйста Х и У координаты центра звезды");
                centerX=scan.nextInt();
                centerY=scan.nextInt();
                if(centerX<=maxRad||centerY<=maxRad||centerX>WIDTH-maxRad||centerY>HEIGHT-maxRad)
                {
                    System.out.println("Плохо указаны координаты, звезда не будет видна вся");
                    continue;
                }
                else{
                    break;
                }
            }catch (Exception e){
                System.out.println("Неверный ввод");
            }
        }
        angle = 360 / (cornersStar * 2);
        /*
        Циркуль для проверки что звезда вписана
        Circle cr=new Circle();
        cr.setCenterX(centerX);
        cr.setCenterY(centerY);
        cr.setRadius(maxRad);
        cr.setStrokeWidth(2);
        cr.setStroke(Paint.valueOf("#dddddd"));
        cr.setFill(Paint.valueOf("#00000000"));
        pane.getChildren().addAll(cr);
         */
        //Первая и последняя точка
        xFirst =(int) (minRad * cosDeg(angle+90) + centerX);
        yFirst =(int) (minRad * sinDeg(angle+90) + centerY);

        xb=xFirst;
        yb=yFirst;

        //получаем поочерёдно координаты остриев и промежностей звезды на основе введённых данных
        for (int i = 2; i < (cornersStar*2+1); i++) {
            if (i % 2 == 0) {
                xa = (int) (maxRad * cosDeg(angle * i+90) + centerX);
                ya = (int) (maxRad * sinDeg(angle * i+90) + centerY);
            } else {
                xa = (int) (minRad * cosDeg(angle * i+90) + centerX);
                ya = (int) (minRad * sinDeg(angle * i+90) + centerY);
            }
            //создаём отрезок от предыдущей кординаты до текущей
            pane.getChildren().addAll(generateLine(xa,ya,xb,yb));
            //переопределяем предыдущии координаты для следующей итерации
            xb = xa;
            yb = ya;
        }
        pane.getChildren().addAll(generateLine(xFirst,yFirst,xb,yb));
    }
    Line generateLine (int xa,int ya,int xb,int yb){
        Line line =new Line(xa,ya,xb,yb);
        line.setStroke(Paint.valueOf("#bbccdd"));
        line.setStrokeWidth(2);
        return line;

    }
    double cosDeg(int a){
        return cos(Math.toRadians(a));
    }
    double sinDeg (int a){
        return sin(Math.toRadians(a));
    }
    //x=r*cos(a);
    //y=r*sin(a);


}
