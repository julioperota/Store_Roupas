package Roupas;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Menu{
	public static void main(String[] args) {
		//Define a janela
		Roupas roupas = new Roupas();
		roupas.igualaID();
		JFrame janela = new JFrame("Guarda-Roupa"); // Janela Normal
		janela.setResizable(false); // A janela não poderá ter o tamanho ajustado
		janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		janela.setSize(700, 500); // Define tamanho da janela
		//Define o layout da janela
		Container caixa = janela.getContentPane();
		caixa.setLayout(null);
		//Define os labels dos campos
		JLabel labelQuantidade = new JLabel("Quantidade: ");
		JLabel labelCor = new JLabel("Cor: ");
		JLabel labelTipo = new JLabel("Tipo: ");
		JLabel labelID = new JLabel("ID: ");
		//Posiciona os labels na janela
		labelQuantidade.setBounds(50, 40, 100, 20); // coluna, linha, largura, tamanho
		labelCor.setBounds(50, 80, 150, 20); // coluna, linha, largura, tamanho
		labelTipo.setBounds(50, 120, 100, 20); // coluna, linha, largura, tamanho
		labelID.setBounds(50, 150, 100, 40);
		//Define os input box
		JTextField jTextQuantidade = new JTextField();
		JTextField jTextCor = new JTextField();
		JTextField jTextTipo = new JTextField();
		JTextField jTextID = new JTextField();
		//Define se os campos estão habilitados ou não no início
		jTextQuantidade.setEnabled(true);
		jTextCor.setEnabled(true);
		jTextTipo.setEnabled(true);
		jTextID.setEnabled(false);
		//Posiciona os input box
		jTextQuantidade.setBounds(180, 40, 50, 20);
		jTextCor.setBounds(180, 80, 150, 20);
		jTextTipo.setBounds(180, 120, 150, 20);
		jTextID.setBounds(180, 160, 150, 20);
		//Adiciona os rótulos e os input box na janela
		janela.add(labelQuantidade);
		janela.add(labelCor);
		janela.add(labelTipo);
		janela.add(labelID);
		janela.add(jTextQuantidade);
		janela.add(jTextCor);
		janela.add(jTextTipo);
		janela.add(jTextID);
		//Define botões e a localização deles na janela
		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(360, 160, 100, 20);
		botaoConsultar.setEnabled(false);
		janela.add(botaoConsultar);
		JButton botaoRemocao = new JButton("Remover");
		botaoRemocao.setBounds(270, 200, 100, 20);
		botaoRemocao.setEnabled(false);
		janela.add(botaoRemocao);
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 200, 100, 20);
		botaoGravar.setEnabled(true);
		janela.add(botaoGravar);
		JButton botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.setBounds(380, 200, 100, 20);
		botaoAtualizar.setEnabled(false);
		janela.add(botaoAtualizar);
		JButton botaoLimpar = new JButton("Limpar");
		botaoLimpar.setBounds(160, 200, 100, 20);
		botaoLimpar.setEnabled(true);
		janela.add(botaoLimpar);
		if(roupas.checaTabela()) {
			botaoAtualizar.setEnabled(true);
			botaoRemocao.setEnabled(true);
			botaoConsultar.setEnabled(true);
			jTextID.setEnabled(true);
		}
		//Define objeto conta para pesquisar no banco de dados
		//Define ações dos botões
		
		botaoConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(jTextID.getText());
					String tipo, cor;
					int quantidade=0;
					if (!roupas.consultarRoupas(id)) {
						tipo = "";
						cor = "";
					}
					else {
						tipo = roupas.getTipo();
						quantidade = roupas.getQuantidade();
						cor = roupas.getCor();
						id = roupas.getId();
					}
					jTextTipo.setText(tipo);
					jTextQuantidade.setText(String.valueOf(quantidade));
					jTextCor.setText(cor);
					jTextID.setText(String.valueOf(id));
					jTextID.setEnabled(false);
					jTextTipo.setEnabled(false);
					botaoConsultar.setEnabled(false);
					jTextQuantidade.setEnabled(false);
					jTextCor.setEnabled(false);
					jTextTipo.requestFocus();
					jTextID.requestFocus();
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janela,
							"Preencha os campos corretamente!!");
				}
			}
		});
		
		botaoGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int quantidade = Integer.parseInt(jTextQuantidade.getText());
				String tipo = jTextTipo.getText().trim(); // Retira os espaços em branco
				String cor = jTextCor.getText().trim();
				if (tipo.length()==0) {
					JOptionPane.showMessageDialog(janela, "Preencha o campo tipo!");
					jTextTipo.requestFocus();
				}
				else if(cor.length() == 0) {
							JOptionPane.showMessageDialog(janela, "Preencha o campo cor!");
							jTextCor.requestFocus();
				}
				if(!roupas.cadastrarRoupas(tipo, quantidade, cor)) {
					JOptionPane.showMessageDialog(janela, "Erro no Cadastro!");
				}
				else {
					JOptionPane.showMessageDialog(janela, "Cadastro da roupa realizado com sucesso!");
					botaoLimpar.setEnabled(true);
					botaoAtualizar.setEnabled(true);
					botaoRemocao.setEnabled(true);
					botaoConsultar.setEnabled(true);
				}
			}
		});
		
		botaoAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(jTextID.getText());
				int quantidade = Integer.parseInt(jTextQuantidade.getText());
				String tipo = jTextTipo.getText().trim(); // Retira os espaços em branco
				String cor = jTextCor.getText().trim();
				if (tipo.length()==0) {
					JOptionPane.showMessageDialog(janela, "Preencha o campo tipo!");
					jTextTipo.requestFocus();
				}
				else if(cor.length() == 0) {
							JOptionPane.showMessageDialog(janela, "Preencha o campo cor!");
							jTextCor.requestFocus();
				}
				if(!roupas.atualizarRoupas(id, cor, tipo, quantidade)) {
					JOptionPane.showMessageDialog(janela, "Erro na Atualização!");
				}
				else {
					JOptionPane.showMessageDialog(janela, "Atualização do produto realizado com sucesso!");
				}
			}
		});
		
		botaoRemocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(jTextID.getText());
				if(!roupas.consultarRoupas(id)) {
					JOptionPane.showMessageDialog(janela, "Roupa não existe ou não encontrada.");
				}
				else {
					if(roupas.deletarRoupas(id));
					JOptionPane.showMessageDialog(janela, "Roupa removida!");
				}
				if(roupas.checaTabela()) {
					botaoAtualizar.setEnabled(true);
					botaoRemocao.setEnabled(true);
					botaoConsultar.setEnabled(true);
					jTextID.setEnabled(true);
				}
				else {
					botaoAtualizar.setEnabled(false);
					botaoRemocao.setEnabled(false);
					botaoConsultar.setEnabled(false);
					jTextID.setEnabled(false);
				}
		}
			
		});
		
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextQuantidade.setText(""); // Limpar campo
				jTextCor.setText(""); // Limpar campo
				jTextTipo.setText(""); // Limpar campo
				jTextID.setText("");
				jTextQuantidade.setEnabled(true);
				jTextCor.setEnabled(true);
				jTextTipo.setEnabled(true);
				jTextID.setEnabled(true);
				botaoConsultar.setEnabled(true);
				botaoGravar.setEnabled(true);
			}
		});
		janela.setVisible(true); // Exibe a janela
	}
}
