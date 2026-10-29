package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.models.BookDto;
import ro.uvt.info.designpatternslab.services.BooksService;

public class GetBookByIdCommand implements Command<BookDto> {
    private final BooksService service;
    private final int id;

    public GetBookByIdCommand(BooksService service, int id) {
        this.service = service;
        this.id = id;
    }

    @Override
    public BookDto execute() {
        return service.getById(id);
    }
}