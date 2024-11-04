package lotto.Validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.util.List;
import lotto.Util.Error.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputNumberValidatorTest {

    @DisplayName("문자열에 숫자가 아닌 값이 포함된 경우 isNotInteger 메서드가 true를 반환한다.")
    @Test
    void 문자열에_숫자가_아닌_값이_포함된_경우() {
        String invalidInput = "1, 2, a";
        boolean result = InputNumberValidator.isNotInteger(invalidInput);
        assertThat(result).isTrue();
    }

    @DisplayName("문자열이 모두 숫자인 경우 isNotInteger 메서드가 false를 반환한다.")
    @Test
    void 문자열이_모두_숫자인_경우() {
        String validInput = "1, 2, 3";
        boolean result = InputNumberValidator.isNotInteger(validInput);
        assertThat(result).isFalse();
    }

    @DisplayName("숫자가 Integer 범위를 벗어나는 경우 isOutOfIntegerRange 메서드가 true를 반환한다.")
    @Test
    void 숫자가_Integer_범위를_벗어나는_경우() {
        String outOfRangeInput = "2147483649";
        boolean result = InputNumberValidator.isOutOfIntegerRange(outOfRangeInput);
        assertThat(result).isTrue();
    }

    @DisplayName("숫자가 Integer 범위 내에 있는 경우 isOutOfIntegerRange 메서드가 false를 반환한다.")
    @Test
    void 숫자가_Integer_범위_내에_있는_경우() {
        String validInput = "1, 2147483647"; // Integer.MAX_VALUE
        boolean result = InputNumberValidator.isOutOfIntegerRange(validInput);
        assertThat(result).isFalse();
    }

    @DisplayName("로또 번호가 유효 범위를 벗어난 경우 isOutOfLottoRange 메서드가 true를 반환한다.")
    @Test
    void 로또_번호가_유효_범위를_벗어난_경우() {
        String outOfRangeInput = "0, 46";
        boolean result = InputNumberValidator.isOutOfLottoRange(outOfRangeInput);
        assertThat(result).isTrue();
    }

    @DisplayName("로또 번호가 유효 범위 내에 있는 경우 isOutOfLottoRange 메서드가 false를 반환한다.")
    @Test
    void 로또_번호가_유효_범위_내에_있는_경우() {
        String validInput = "1, 45";
        boolean result = InputNumberValidator.isOutOfLottoRange(validInput);
        assertThat(result).isFalse();
    }

    @DisplayName("로또 번호가 6개가 아닌 경우 isMatchedLottoSize 메서드가 true를 반환한다.")
    @Test
    void 로또_번호가_6개가_아닌_경우() {
        String invalidSizeInput = "1, 2, 3, 4, 5";
        boolean result = InputNumberValidator.isMatchedLottoSize(invalidSizeInput);
        assertThat(result).isTrue();
    }

    @DisplayName("로또 번호가 정확히 6개인 경우 isMatchedLottoSize 메서드가 false를 반환한다.")
    @Test
    void 로또_번호가_정확히_6개인_경우() {
        String validSizeInput = "1, 2, 3, 4, 5, 6";
        boolean result = InputNumberValidator.isMatchedLottoSize(validSizeInput);
        assertThat(result).isFalse();
    }

    @DisplayName("로또 번호에 중복이 있는 경우 isDuplicated 메서드가 true를 반환한다.")
    @Test
    void 로또_번호에_중복이_있는_경우() {
        String duplicatedInput = "1, 2, 3, 3, 4, 5";
        boolean result = InputNumberValidator.isDuplicated(duplicatedInput);
        assertThat(result).isTrue();
    }

    @DisplayName("로또 번호에 중복이 없는 경우 isDuplicated 메서드가 false를 반환한다.")
    @Test
    void 로또_번호에_중복이_없는_경우() {
        String uniqueInput = "1, 2, 3, 4, 5, 6";
        boolean result = InputNumberValidator.isDuplicated(uniqueInput);
        assertThat(result).isFalse();
    }

    @DisplayName("보너스 번호가 유효하지 않은 경우 예외를 발생시킨다.")
    @Test
    void 보너스_번호가_유효하지_않은_경우() {
        String invalidBonusNumber = "a";
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> InputNumberValidator.validateBonusNumberType(invalidBonusNumber))
                .isInstanceOf(NumberFormatException.class)
                .hasMessage(ErrorMessage.INVALID_BONUS_NUMBER_COUNT.getMessage());
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되는 경우 예외를 발생시킨다.")
    @Test
    void 보너스_번호가_중복되는_경우() {
        String invalidBonusNumber = "1";
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatCode(() -> InputNumberValidator.validateBonusNumberValue(invalidBonusNumber, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.DUPLICATE_LOTTO_NUMBER.getMessage());
    }

    @DisplayName("보너스 번호가 유효한 경우 예외가 발생하지 않는다.")
    @Test
    void 보너스_번호가_유효한_경우() {
        String validBonusNumber = "7";
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatCode(() -> InputNumberValidator.validateBonusNumberValue(validBonusNumber, winningNumbers))
                .doesNotThrowAnyException();
    }
}