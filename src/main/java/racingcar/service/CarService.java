package racingcar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import racingcar.domain.Car;
import racingcar.domain.Cars;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class CarService {
    private Cars cars = new Cars(new ArrayList<Car>());

    public void initializeService() {
        OutputView.printNameInput();
        List<String> carNames = splitCarNames(InputView.readCarNames());
        for(String carName : carNames) {
            Car car = new Car(carName, 0);
            cars.addCarInformation(car);
        }
    }

    private List<String> splitCarNames(String carNames) {
        return List.of(carNames.split(","));
    }
}