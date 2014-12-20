package fr.cabaf.backend;

import java.util.concurrent.ThreadFactory;

public class ClientThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(/*@NotNull*/Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(false);
        return thread;
    }
}
