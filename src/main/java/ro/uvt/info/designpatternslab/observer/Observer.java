package ro.uvt.info.designpatternslab.observer;

import ro.uvt.info.designpatternslab.Book;

public interface Observer {
    void update(Book book);
}