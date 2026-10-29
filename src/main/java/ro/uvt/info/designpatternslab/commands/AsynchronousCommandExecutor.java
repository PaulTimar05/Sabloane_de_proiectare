package ro.uvt.info.designpatternslab.commands;

import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class AsynchronousCommandExecutor implements CommandExecutor {
    private final ExecutorService pool = Executors.newFixedThreadPool(4);

    @Override
    public <T> CompletableFuture<T> execute(Command<T> command) {
        return CompletableFuture.supplyAsync(command::execute, pool); // rulează pe un thread din pool
    }
}