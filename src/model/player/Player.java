package model.player;

import model.player.representation.Representation;

public abstract class Player {
    private final int id;
    private final Representation representation;

    public Player(int id, Representation representation) {
        this.id = id;
        this.representation = representation;
    }

    public Representation getRepresentation() {
        return this.representation;
    }

    public String render() {
        return this.representation.render(false);
    }

    public int getId() {
        return this.id;
    }
}