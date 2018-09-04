package neo.vn.test365children.Models;

public class MessageEvent {
    public final String message;
    public final float point;
    public final long time;

    public MessageEvent(String message, float point, long time) {
        this.message = message;
        this.point = point;
        this.time = time;
    }
}
