package ro.uvt.info.designpatternslab.commands;

import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class SynchronousCommandExecutor implements CommandExecutor {
    @Override
    public <T> CompletableFuture<T> execute(Command<T> command) {
        return CompletableFuture.completedFuture(command.execute()); // rulează acum, pe thread-ul curent
    }
}