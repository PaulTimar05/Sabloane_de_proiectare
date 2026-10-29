package ro.uvt.info.designpatternslab;

import lombok.Getter;

@Getter
public class ImageContent {
    private final String data;

    public ImageContent(String data) {
        this.data = data;
    }

}