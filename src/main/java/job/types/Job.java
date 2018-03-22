package job.types;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by taras on 22.03.18.
 */
public class Job implements Serializable{

    private UUID id;
    private String text;
    private String translation;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Job() {
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Job(String type) {
        this.id = UUID.randomUUID();
        this.type = type;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }
}
