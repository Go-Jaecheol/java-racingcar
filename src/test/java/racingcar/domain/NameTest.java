package racingcar.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {
    @Nested
    @DisplayName("자동차 이름 길이 테스트")
    class TestNameLength {
        private final int nameMinLength = Name.NAME_MIN_LENGTH;
        private final int nameMaxLength = Name.NAME_MAX_LENGTH;

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
}
