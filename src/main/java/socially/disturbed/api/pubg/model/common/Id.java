package socially.disturbed.api.pubg.model.common;

public abstract class Id {

    public String idValue;

    public Id(String idValue) { this.idValue = idValue; }

    public String getId() { return idValue; }

    @Override
    public String toString() {
        return idValue;
    }
}