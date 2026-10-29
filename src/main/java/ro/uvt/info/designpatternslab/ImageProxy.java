package ro.uvt.info.designpatternslab;

public class ImageProxy extends Element implements Picture {
    private final String url;
    private Image realImage; // null până când avem nevoie de ea

    public ImageProxy(String url) {
        this.url = url; // rapid: nu încărcăm nimic aici
    }

    private Image loadImage() {
        if (realImage == null) {
            realImage = new Image(url); // aici se pierd cele 5 secunde, o singură dată
        }
        return realImage;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public ImageContent content() {
        return loadImage().content();
    }

    @Override
    public void print() {
        loadImage().print();
    }
}
