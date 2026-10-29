package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.models.BookDto;
import ro.uvt.info.designpatternslab.services.BooksService;

public class UpdateBookCommand implements Command<BookDto> {
    private final BooksService service;
    private final int id;
    private final BookDto book;

    public UpdateBookCommand(BooksService service, int id, BookDto book) {
        this.service = service;
        this.id = id;
        this.book = book;
    }

    @Override
    public BookDto execute() {
        return service.update(id, book);
    }
}