package org.example;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Log4j2
public class PitStop extends Thread {

    PitWorker[] workers = new PitWorker[4];

    volatile F1Cars current = null;
    final Object mutex = new Object();
    @Getter
    final CyclicBarrier barrier = new CyclicBarrier(4 + 1);

    public PitStop() {
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new PitWorker(i, this);
            workers[i].start();
        }
    }

    public void pitline(F1Cars f1Cars) {
        // TODO условие: на питстоп может заехать только 1 пилот
        // TODO держим поток до момента смены всех шин
        // TODO каждую шину меняет отдельный PitWowker поток
        // TODO дожидаемся когда все PitWorker завершат свою работу над машиной
        //TODO метод запускается из потока болида, нужна синхронизация с потоком питстопа
        try {
            synchronized (mutex) {
                current = f1Cars;
                log.info("car received {}", f1Cars.getId());
                mutex.notifyAll();
            }
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        } finally {
            synchronized (mutex) {
                current = null;
            }
        }
        // TODO отпускаем машину
    }


    @Override
    public void run() {
        while(!isInterrupted()){
            //синхронизируем поступающие болиды и работников питстопа при необходимости
        }
    }

    public F1Cars getCar() {
        //TODO Блокируем поток до момента поступления машины на питстоп и возвращаем ее

        synchronized (mutex) {
           while (current == null) {
               try {
                   mutex.wait();
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
           return current;
        }
    }
}
