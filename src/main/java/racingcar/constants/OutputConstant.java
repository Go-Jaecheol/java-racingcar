package racingcar.constants;

public enum OutputConstant {
    RESULT_MESSAGE("실행 결과"),
    CAR_INFORMATION_DELIMITER(" : "),
    POSITION_COMMAND("-"),
    WINNER_MESSAGE("가 최종 우승했습니다.");

    private final String message;

    OutputConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
