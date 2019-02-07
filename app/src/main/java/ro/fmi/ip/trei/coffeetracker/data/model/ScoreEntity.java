package ro.fmi.ip.trei.coffeetracker.data.model;

public class ScoreEntity {

    public final long timestamp ;
    public final double score;

    public ScoreEntity(long timestamp, double score) {
        this.timestamp = timestamp;
        this.score = score;
    }

}
