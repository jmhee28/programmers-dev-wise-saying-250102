import java.util.Scanner;

public class App {

    private final Scanner sc;

    public App(Scanner sc) {
        this.sc = sc;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {

            System.out.println("명령 ) ");
            String cmd = sc.nextLine(); //

            if (cmd.equals("종료")) {

                System.out.println("명언앱을 종료합니다.");
                break;

            } else if(cmd.equals("등록")) {

                System.out.println("명언 : ");
                System.out.println("작가 : ");
                System.out.println("1번 명언이 등록되었습니다.");
            }
        }
    }
}
