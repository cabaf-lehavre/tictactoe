package fr.cabaf.backend.handler;

public abstract class BaseGameContext {
    private boolean playing;

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
