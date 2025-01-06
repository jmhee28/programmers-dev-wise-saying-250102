package app.global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandTest {

    @Test
    @DisplayName("command Test 최초 테스트")
    void t1() {
        Command cmd = new Command("");
    }

    @Test
    @DisplayName("action 네임을 얻어올 수 있다. 삭제?id=1 이면 삭제가 나와야 한다.")
    void t2() {

        Command cmd = new Command("삭제?id=1");
        String actionName = cmd.getActionName();

        assertThat(actionName).isEqualTo("삭제");

    }

}
