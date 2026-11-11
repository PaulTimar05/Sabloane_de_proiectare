package ro.uvt.info.designpatternslab.observer;

import ro.uvt.info.designpatternslab.Book;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Subject {
    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(Book book) {
        for (Observer observer : observers) {
            try {
                observer.update(book);
            } catch (RuntimeException e) {
                observers.remove(observer);   // abonat căzut (browser închis): îl scoatem
            }
        }
    }
}