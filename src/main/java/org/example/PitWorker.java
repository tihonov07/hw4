package org.example;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.BrokenBarrierException;

/**
 * Работник питстопа, меняет шину на прибывшей машине на своем месте
 */
@Log4j2
public class PitWorker extends Thread {

    //Место работника, он же номер колеса от 0 до 3
    private final int position;

    //Ссылка на сущность питстопа для связи
    private final PitStop pitStop;

    public PitWorker(int position, PitStop pitStop) {
        this.position = position;
        this.pitStop = pitStop;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            F1Cars car = pitStop.getCar();
            log.info("replace wheel {} {}", position, car.getId());
            //TODO работник ждет машину на питстопе и меняет шину на своей позиции
            car.getWheel(position).replaceWheel();
            //TODO работник сообщает о готовности
            try {
                pitStop.getBarrier().await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
