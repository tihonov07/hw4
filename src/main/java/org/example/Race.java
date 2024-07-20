package org.example;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Log4j2
public class Race {

    @Getter
    private long distance;

    @Getter
    private CountDownLatch start;

    private CountDownLatch finish;

    private List<F1Cars> participantCars = new java.util.ArrayList<>();

    private List<Team> teams = new java.util.ArrayList<>();

    public Race(long distance, Team[] participantCars) {
        this.distance = distance;
        start = new CountDownLatch(1);
        finish = new CountDownLatch(participantCars.length * 2);
        teams.addAll(List.of(participantCars));
    }

    /**
     * Запускаем гонку
     */
    public void start() {
        for (Team team : teams) {
            team.prepareRace(this);
        }
        //TODO даем команду на старт гонки
        start.countDown();
        //TODO блокируем поток до завершения гонки
        try {
            finish.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    //Регистрируем участников гонки
    public void register(F1Cars participantCar) {
        participantCars.add(participantCar);
    }


    public void start(F1Cars f1Cars) {
        //фиксация времени старта
        log.info("Start car {}", f1Cars.getId());
    }

    public long finish(F1Cars participant) {
        //фиксация времени финиша
        log.info("Finish car {}", participant.getId());
        finish.countDown();
        return 0; //длительность гонки у данного участника
    }

    public void printResults() {
        participantCars.sort(F1Cars::compareTo);
        log.info("Результат гонки:");
        int position = 0;
        for (F1Cars participant : participantCars) {
            log.info("Позиция: {} время: {}", position++, participant.getName());
        }
    }
}
