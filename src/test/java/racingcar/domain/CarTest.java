package racingcar.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.service.CarService;

class CarTest {
    @Nested
    @DisplayName("자동차 이름 길이 테스트")
    class TestNameLength {
        private final int nameMinLength = Car.NAME_MIN_LENGTH;
        private final int nameMaxLength = Car.NAME_MAX_LENGTH;

        @ParameterizedTest(name = "{index} ==> name : ''{0}''")
        @ValueSource(strings = {"", "abcdef", "pobiiiiii"})
        @DisplayName("자동차 이름의 길이가 " + nameMinLength + " 이하거나 " + nameMaxLength + "보다 클 경우 예외 발생")
        void Should_ThrowException_길이가_0이하거나_5보다_큰_경우(String name) {
            assertThatThrownBy(() -> new Car(name, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(String.format("[ERROR] 자동차 이름의 길이는 %d부터 %d 사이여야 합니다.", nameMinLength, nameMaxLength));
        }

        @ParameterizedTest(name = "{index} ==> name : ''{0}''")
        @ValueSource(strings = {"a", "abc", "abcde"})
        @DisplayName("자동차 이름의 길이가 1 이상 5 이하인 경우 성공")
        void Should_Success_길이가_1이상_5이하인_경우(String name) {
            assertDoesNotThrow(() -> new Car(name, 0));
        }
    }

    @Nested
    @DisplayName("자동차 이름 형식 테스트")
    class TestNameFormat {
        @ParameterizedTest(name = "{index} ==> name : ''{0}''")
        @ValueSource(strings = {" ", "abc!", "123* "})
        @DisplayName("자동차 이름이 영숫자가 아닌 경우 예외 발생")
        void Should_ThrowException_이름이_영숫자가_아닌_경우(String name) {
            assertThatThrownBy(() -> new Car(name, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 자동차 이름은 영숫자로 이루어져야 합니다.");
        }

        @ParameterizedTest(name = "{index} ==> name : ''{0}''")
        @ValueSource(strings = {"a1", "a2cd", "ab34e"})
        @DisplayName("자동차 이름이 영숫자인 경우 성공")
        void Should_Success_이름이_영숫자인_경우(String name) {
            assertDoesNotThrow(() -> new Car(name, 0));
        }
    }

    @Nested
    @DisplayName("전진 테스트")
    class TestForward {
        CarService carService = new CarService();
        Car car = new Car("test", 0);

        @ParameterizedTest(name = "{index} ==> Distance : ''{0}''")
        @ValueSource(ints = {0, 2})
        @DisplayName("랜덤 값이 4보다 작은 경우 멈춤")
        void Should_Success_랜덤값이_4보다_작은_경우(int number) {
            carService.runForward(car, number);
            assertThat(car.getPosition()).isEqualTo(0);
        }

        @ParameterizedTest(name = "{index} ==> Distance : ''{0}''")
        @ValueSource(ints = {4, 7})
        @DisplayName("랜덤 값이 4 이상인 경우 전진")
        void Should_Success_랜덤값이_4이상인_경우(int number) {
            carService.runForward(car, number);
            assertThat(car.getPosition()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("자동차 시작 위치 테스트")
    class TestStartDistance {
        private final int startDistance = Car.START_POSITION_VALUE;

        @ParameterizedTest(name = "{index} ==> distance : ''{0}''")
        @ValueSource(ints = {1, -1, 100})
        @DisplayName("자동차 시작 위치가 " + startDistance + "이 아닌 경우 예외 발생")
        void Should_ThrowException_시작_위치가_0이_아닌_경우(int distance) {
            assertThatThrownBy(() -> new Car("test", distance))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(String.format("[ERROR] 자동차의 시작 위치는 %d으로 설정되어야 합니다.", startDistance));
        }

        @Test
        @DisplayName("자동차 시작 위치가 0인 경우 성공")
        void Should_Success_시작_위치가_0인_경우() {
            assertDoesNotThrow(() -> new Car("test", 0));
        }
    }

    @Nested
    @DisplayName("자동차 현재 위치 테스트")
    class TestCurrentDistance {
        Car car = new Car("test", 0);
        @ParameterizedTest(name = "{index} ==> round : ''{0}''")
        @ValueSource(ints = {-1, -5})
        @DisplayName("자동차 현재 위치가 라운드보다 큰 경우 예외 발생")
        void Should_ThrowException_현재_위치가_라운드보다_큰_경우(int round) {
            assertThatThrownBy(() -> car.validateCurrentPosition(round))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 자동차의 현재 위치는 해당 라운드보다 클 수 없습니다.");
        }

        @ParameterizedTest(name = "{index} ==> round : ''{0}''")
        @ValueSource(ints = {2, 4, 7})
        @DisplayName("자동차 현재 위치가 라운드보다 작은 경우 성공")
        void Should_Success_현재_위치가_라운드보다_작은_경우(int round) {
            assertDoesNotThrow(() -> car.validateCurrentPosition(round));
        }
    }
}
