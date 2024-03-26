package src.tabuleiroGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonCelula extends JButton {

	int linha;
	int coluna;
	int estado = CelulaEstado.LIVRE;
	
	private ClickHandler clickHandler;	
	
	public JButtonCelula(int linha, int coluna) {
		super(CelulaEstado.decodeEstadoString(CelulaEstado.LIVRE));
		this.linha = linha;
		this.coluna = coluna;
		setContentAreaFilled(false); // Definir como false para usar o estilo personalizado
		setOpaque(true); // Permitir que o botão seja opaco para aplicar a cor de fundo
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(new Color(47, 69, 56)); // Definir a cor 2F4538
		g.fillRect(0, 0, getWidth(), getHeight());

		// Desenhar o texto na célula
		g.setColor(Color.white);
		FontMetrics fm = g.getFontMetrics();
		int x = (getWidth() - fm.stringWidth(getText())) / 2;
		int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
		g.drawString(getText(), x, y);

		// Desenhar círculo preenchido para representar célula preta
		if (estado == CelulaEstado.PRETA) {
			g.setColor(Color.BLACK); // Cor do círculo preto
			int circleSize = Math.min(getWidth(), getHeight()) - 10; // Tamanho do círculo
			int xcp = (getWidth() - circleSize) / 2;
			int ycp = (getHeight() - circleSize) / 2;
			g.fillOval(xcp, ycp, circleSize, circleSize);
		}

		// Desenhar círculo preenchido para representar célula branca
		if (estado == CelulaEstado.BRANCA) {
			g.setColor(Color.WHITE); // Cor do círculo branco
			int circleSize = Math.min(getWidth(), getHeight()) - 10; // Tamanho do círculo
			int xcb = (getWidth() - circleSize) / 2;
			int ycb = (getHeight() - circleSize) / 2;
			g.fillOval(xcb, ycb, circleSize, circleSize);
		}
	}
	
	public String getEstadoString() {
		return CelulaEstado.decodeEstadoString(estado);
	}
	public int getEstado() {
		return estado;
	}

	public void setEstado(int linha, int coluna, int e) {
		this.estado = e;
		this.setText(CelulaEstado.decodeEstadoString(e));
	}
	
	public void setClickHandler(ClickHandler handler) {
		this.clickHandler = handler;
		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler.clicked(linha, coluna, estado);
			}
		};
		
		this.addActionListener(this.actionListener);
	}

	public void removeClickHandler() {
			this.removeActionListener(this.actionListener);
	}
	
}
