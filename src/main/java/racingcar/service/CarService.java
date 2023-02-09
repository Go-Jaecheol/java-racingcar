package racingcar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import racingcar.domain.Car;
import racingcar.domain.Cars;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class CarService {
    private Cars cars = new Cars(new ArrayList<Car>());
    private int tryCount;
    private List<String> winner = new ArrayList<>();

    public void initializeService() {
        while (validateNameInput()) {
            cars = new Cars(new ArrayList<Car>());
        }
        while (validateCountInput()) {
        }
    }

    public void runService() {
        OutputView.printResultMessage();
        for (int i = 0; i < tryCount; i++) {
            runRound();
            System.out.println("");
        }
    }

    private void runRound() {
        for (Car car : cars.getCarInformation()) {
            Random random = new Random();
            int randomNumber = random.nextInt(10);
            runForward(car, randomNumber);
            OutputView.printRoundResult(car.getName(), car.getDistance());
        }
    }

    public void runForward(Car car, int randomNumber) {
        if (randomNumber >= 4) {
            car.increaseDistance();
        }
    }

    public void finishService() {
        for (Car car : cars.getCarInformation()) {
            OutputView.printRoundResult(car.getName(), car.getDistance());
        }
        findWinner();
        OutputView.printWinners(winner);
    }

    private void findWinner() {
        int maxDistance = findMaxDistance();
        for(Car car : cars.getCarInformation()) {
            compareDistance(car, maxDistance);
        }
    }

    private int findMaxDistance() {
        int maxDistance = -1;
        for (Car car : cars.getCarInformation()) {
            maxDistance = Math.max(car.getDistance(), maxDistance);
        }
        return maxDistance;
    }

    private void compareDistance(Car car, int maxDistance) {
        if(maxDistance == car.getDistance()) {
            winner.add(car.getName());
        }
    }

    private boolean validateNameInput() {
        try {
            OutputView.printNameInput();
            List<String> carNames = splitCarNames(InputView.readCarNames());
            makeCar(carNames);
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    private void makeCar(List<String> carNames) {
        for (String carName : carNames) {
            Car car = new Car(carName, 0);
            cars.addCarInformation(car);
        }
    }

    private List<String> splitCarNames(String carNames) {
        return List.of(carNames.split(","));
    }

    private boolean validateCountInput() {
        try {
            OutputView.printCountInput();
            tryCount = InputView.readTryCount();
            validateNegativeCount(tryCount);
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    private void validateNegativeCount(int tryCount) {
        if (tryCount <= 0) {
            throw new IllegalArgumentException("[ERROR] 시도할 횟수는 0보다 큰 숫자여야 합니다.");
        }
    }
}
