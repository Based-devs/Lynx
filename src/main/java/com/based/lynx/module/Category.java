package com.based.lynx.module;

public enum Category {
    COMBAT("Combat"),
    EXPLOIT("Exploit"),
    MISC("Misc"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    RENDER("Render"),
    CLIENT("Client");

    private String name;

    Category(String name) {
        this.setName(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
