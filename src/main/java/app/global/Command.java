package app.global;

public class Command {

    String actionName;

    public Command(String cmd) {


        // 삭제
        String[] cmdBits = cmd.split("\\?");
        actionName = cmdBits[0];
    }

    public String getActionName() {
        return actionName;
    }

}
