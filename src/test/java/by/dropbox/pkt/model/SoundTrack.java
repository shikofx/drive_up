package by.dropbox.pkt.model;

import java.net.URL;

public class SoundTrack {

    private String id;
    private String name;
    private URL url;

    public String getId() {
        return id;
    }

    public SoundTrack withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SoundTrack withName(String name) {
        this.name = name;
        return this;
    }

    public URL getUrl() {
        return url;
    }

    public SoundTrack withSourceUrl(URL url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "SoundTrack{" +
                "name='" + name + '\'' +
                ", url=" + url +
                '}';
    }
}
