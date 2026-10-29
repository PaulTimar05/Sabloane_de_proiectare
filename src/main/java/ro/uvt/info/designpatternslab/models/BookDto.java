package ro.uvt.info.designpatternslab.models;

import ro.uvt.info.designpatternslab.Author;
import ro.uvt.info.designpatternslab.Book;

public record BookDto(Integer id, String title, String author) {

    public Book toBook() {
        Book book = new Book(title);
        if (author != null && !author.isBlank()) {
            String[] parts = author.split(" ", 2);   // "Radu Pavel Gheo" -> "Radu" + "Pavel Gheo"
            book.addAuthor(new Author(parts[0], parts.length > 1 ? parts[1] : ""));
        }
        return book;
    }
}