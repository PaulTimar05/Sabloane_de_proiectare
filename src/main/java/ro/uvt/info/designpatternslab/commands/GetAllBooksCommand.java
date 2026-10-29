package ro.uvt.info.designpatternslab.commands;

import ro.uvt.info.designpatternslab.models.BookDto;
import ro.uvt.info.designpatternslab.services.BooksService;

import java.util.List;

public class GetAllBooksCommand implements Command<List<BookDto>> {
    private final BooksService service;

    public GetAllBooksCommand(BooksService service) {
        this.service = service;
    }

    @Override
    public List<BookDto> execute() {
        return service.getAll();
    }
}