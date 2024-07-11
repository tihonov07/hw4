package org.example;

public class PitStop extends Thread {

    PitWorker[] workers = new PitWorker[4];

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

        return null;
    }
}
