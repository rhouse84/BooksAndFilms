package booksandfilms.client.entities;

public enum Genre {
	Bio("Biography"),
	Fic("Fiction"),
	Graph("Graphic Fiction"),
	Hist("History"),
	HistFic("Historical Fiction"),
	KidFic("Kid Fiction"),
	Mem("Memior"),
	Mys("Mystery"),
	Non("Non-Fiction"),
	Poetry("Poetry"),
	SciFi("Science Fiction");
	
	private String text;
	
	Genre(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public static String fromString(String text) {
		if (text != null) {
			for (Genre g : Genre.values()) {
				if (text.equalsIgnoreCase(g.name())) {
					return g.text.toString();
				}
			}
		
		}
		throw new IllegalArgumentException("No constant with text " + text + " found");
	}
}
