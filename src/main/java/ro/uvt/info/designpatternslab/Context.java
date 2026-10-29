package ro.uvt.info.designpatternslab;

import lombok.Getter;

@Getter
public class Context {
    private int width;
    public Context(int width) {
        this.width = width;
    }
}