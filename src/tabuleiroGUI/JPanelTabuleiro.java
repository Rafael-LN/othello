package src.tabuleiroGUI;
import javax.swing.JPanel;

import java.awt.GridLayout;

public class JPanelTabuleiro extends JPanel {

	public int nColunas;
	public int nLinhas;
	
	private JButtonCelula [][] tabuleiro;
	private ClickHandler clickHandler;
	
	public boolean readOnly = false;
	
	
	public JPanelTabuleiro() {
		this(8,8);
	}		
	
	public JPanelTabuleiro(int nLinhas, int nColunas) {
		super();
		this.nLinhas = nLinhas;
		this.nColunas = nColunas;
		
		tabuleiro = new JButtonCelula[nLinhas][nColunas];
		
		GridLayout l = new GridLayout(nLinhas,nColunas);
		System.out.println("Colunas " + l.getColumns());
		setLayout(l);
		
		for(int i=0; i<nLinhas; i++) {
			for(int j=0; j<nColunas; j++) {
				JButtonCelula celula = new JButtonCelula(i, j);
				add(celula);
				tabuleiro[i][j] = celula;
			}
		}
	}
	
	public void setReadOnly(boolean ro) {
		readOnly = ro;
		for(int i=0; i<nLinhas; i++) {
			for(int j=0; j<nColunas; j++) {
				tabuleiro[i][j].setEnabled(!ro);
			}
		}
	}
	
	public String getCelulaEstadoString(int linha, int coluna) {
		return tabuleiro[linha][coluna].getEstadoString();		
	}

	public int getCelulaEstado(int linha,int coluna) {
		return tabuleiro[linha][coluna].getEstado();
	}
	
	public void setCelula(int linha,int coluna, int estado) {
		tabuleiro[linha][coluna].setEstado(linha, coluna, estado);
	}
	public void setCelulaLivre(int linha,int coluna) {
		tabuleiro[linha][coluna].setEstado(linha, coluna, CelulaEstado.LIVRE);
	}
	// Estados da Batalha naval
	public void setCelulaPreta(int linha,int coluna) {
		tabuleiro[linha][coluna].setEstado(linha, coluna, CelulaEstado.PRETA);
	}
	public void setCelulaBranca(int linha,int coluna) {
		tabuleiro[linha][coluna].setEstado(linha, coluna, CelulaEstado.BRANCA);
	}

	public void setClickHandler(ClickHandler handler) {
		this.clickHandler = handler;
		for(int i=0; i<nLinhas; i++) {
			for(int j=0; j<nColunas; j++) {
				tabuleiro[i][j].setClickHandler(handler);
			}
		}
		
	}
	
	public void removeClickHandler() {
		for(int i=0; i<nLinhas; i++) {
			for(int j=0; j<nColunas; j++) {
				tabuleiro[i][j].removeClickHandler();
			}
		}
		
	}
}
