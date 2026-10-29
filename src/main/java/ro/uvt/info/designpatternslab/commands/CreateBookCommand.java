package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.models.BookDto;
import ro.uvt.info.designpatternslab.services.BooksService;

public class CreateBookCommand implements Command<BookDto> {
    private final BooksService service;
    private final BookDto book;

    public CreateBookCommand(BooksService service, BookDto book) {
        this.service = service;
        this.book = book;
    }

    @Override
    public BookDto execute() {
        return service.create(book);
    }
}