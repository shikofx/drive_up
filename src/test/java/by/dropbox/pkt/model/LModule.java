package by.dropbox.pkt.model;

import java.net.URL;
import java.util.List;

public class LModule {
    private String id;
    private String name;
    private URL url;

    private List<SoundTrack> soundTracks;

    public String getId() {
        return id;
    }

    public LModule withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LModule withName(String name) {
        this.name = name;
        return this;
    }

    public URL getUrl() {
        return url;
    }

    public LModule withUrl(URL url) {
        this.url = url;
        return this;
    }

    public List<SoundTrack> getSoundTracks() {
        return soundTracks;
    }

    public LModule withSoundTracks(List<SoundTrack> soundTracks) {
        this.soundTracks = soundTracks;
        return  this;
    }

    @Override
    public String toString() {
        return "LModule{" +
                "name='" + name + '\'' +
                ", soundTracks=" + soundTracks.toString() +
                '}';
    }
}
