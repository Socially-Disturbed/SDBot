package socially.disturbed.api.pubg.model.common;

public abstract class Entity {

    private final Id id;

    protected Entity(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }
}
