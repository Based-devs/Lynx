package dev.based.vampyrix.api.module;

//Module categories
public enum Category {
	
	COMBAT("Combat"), EXPLOITS("Exploits"), MISC("Misc"), MOVEMENT("Movement"), PLAYER("Player"), RENDER("Render"), CLIENT("Client");
	
	public String name;
	public int moduleIndex;
	
	Category(String name) {
		this.name = name;
	}
}
