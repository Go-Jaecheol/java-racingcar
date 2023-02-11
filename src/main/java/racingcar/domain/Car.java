package racingcar.domain;

import java.util.Map;
import racingcar.constants.CarConstant;

public class Car {
    private final String name;
    private int distance;

    public Car(String name, int distance) {
        validateNameLength(name);
        validateNameFormat(name);
        validateStartDistance(distance);
        this.name = name;
        this.distance = distance;
    }

    private void validateNameLength(String name) {
        if (name.length() > CarConstant.NAME_MAX_LENGTH.getNumber()
                || name.length() <= CarConstant.NAME_MIN_LENGTH.getNumber()) {
            throw new IllegalArgumentException("[ERROR] 자동차 이름의 길이는 1부터 5 사이여야 합니다.");
        }
    }

    private void validateNameFormat(String name) {
        if (!name.matches("^[a-zA-Z0-9]*$")) {
            throw new IllegalArgumentException("[ERROR] 자동차 이름은 영숫자로 이루어져야 합니다.");
        }
    }

    private void validateStartDistance(int distance) {
        if (distance != 0) {
            throw new IllegalArgumentException("[ERROR] 자동차의 시작 위치는 0으로 설정되어야 합니다.");
        }
    }

    public void validateCurrentDistance(int round) {
        if (this.distance > round) {
            throw new IllegalArgumentException("[ERROR] 자동차의 현재 위치는 해당 라운드보다 클 수 없습니다.");
        }
    }

    public void increaseDistance() {
        this.distance++;
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }
}
