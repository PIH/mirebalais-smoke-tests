package org.openmrs.module.mirebalais.smoke.helper;

public class NameGenerator {

	private static final String[] PATIENT_FIRST_NAMES = { "Alexandre", "Achint", 
			"Brittany", "Burke", "Cesar", "Cosmin", "Daniel", "Darius", "David", "Ellen",
			"Émerson", "Evan", "Fernando", "Gabou", "Glauber", "Hamish", "Louise", "Mário",
			"Mark", "Natália", "Neil", "Neissi", "Nelice", "Rafal", "Renee", "Wyclif" };
	
	private static final String[] PATIENT_LAST_NAMES = { "Barbosa", "Sethi", "Eddy",
			"Mamlin", "Vortmann", "Ioan", "Kayiwa", "Jazayeri", "Walton", "Ball", "Hernandez",
			"Waters", "Freire", "Mandy", "Ramos", "Fraser", "Sécordel", "Areias", "Goodrich",
			"Arsand", "Craven", "Lima", "Heck", "Korytkowski", "Orser", "Luyima" };
	
	private static final String[] USER_FIRST_NAMES = { "Ororo", "Wally",
			"Clint", "Charles", "Clark", "Bruce", "Peter", "Diana", "Tony",
			"Steve", "Reed", "James", "Hal", "Matt", "Dick", "Jean", "Barbara" };
	
	private static final String[] USER_LAST_NAMES = { "Kent", "Wayne",
			"Parker", "Banner", "Prince", "Stark", "Rogers", "Richards",
			"Howlett", "Jordan", "Murdock", "Grayson", "Gray", "Gordon",
			"Xavier", "Barton", "West", "Munroe" };
	
	public static String getPatientFirstName() {
		return PATIENT_FIRST_NAMES[(int) (Math.random() * PATIENT_FIRST_NAMES.length)];
	}

	public static String getPatientLastName() {
		return PATIENT_LAST_NAMES[(int) (Math.random() * PATIENT_LAST_NAMES.length)];
	}

	public static String getPatientName() {
		return new StringBuilder(NameGenerator.getPatientFirstName() + " " + NameGenerator.getPatientLastName()).toString();
	}
	
	public static String getUserFirstName() {
		return USER_FIRST_NAMES[(int) (Math.random() * USER_FIRST_NAMES.length)];
	}

	public static String getUserLastName() {
		return USER_LAST_NAMES[(int) (Math.random() * USER_LAST_NAMES.length)];
	}
	
	public static String getUserName() {
		return new StringBuilder(NameGenerator.getUserFirstName() + " " + NameGenerator.getUserLastName()).toString();
	}
}
