package ro.uvt.info.designpatternslab.observer;

import org.springframework.stereotype.Component;
import ro.uvt.info.designpatternslab.Book;

@Component
public class AllBooksSubject extends Subject {

    public void add(Book book) {
        notifyObservers(book);   // "starea" s-a schimbat: avem o carte nouă
    }
}