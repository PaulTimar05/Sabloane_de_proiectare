package ro.uvt.info.designpatternslab.commands;

public interface Command<T> {
    T execute();
}