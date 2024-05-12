package tabuleiroGUI;

public class CelulaEstado {
	public final static int LIVRE 	= 0;
	//Estados para o tabuleiro da Batalha Naval
	public final static int PRETA 	= 1;
	public final static int BRANCA 	= 2;
	
	public static String decodeEstadoString(int e) {
		switch (e) {
		case LIVRE: return " ";
		case PRETA: return "O";
		case BRANCA: return "~";
		default: 	return "?";
		}
	}
	public static int encodeEstadoString(String e) {
		switch (e) {
		case ".": return LIVRE;
		case "O": return PRETA;
		case "~": return BRANCA;
		default: 	return -1;
		}
	}
	
	//Converter coordenada de inteiro para letra
	public static String encodeCoordIntChar(int coord) {
		return String.valueOf((char)('A' + coord));
	}
	//Converter coordenada de letra para inteiro
	public static int decodeCoordIntChar(String coord) {
		return  coord.charAt(0) - 'A';
	}
}
