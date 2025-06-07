package Programa02.Swing;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FormSetter {


    public static void initialScreenConfig(JPanel painel) {
        painel.setLayout(null);

// rótulos e campos
//        JLabel rotuloSaudacao = new JLabel("<html>Bem-vindo ao sistema.<br>Por favor, preencha os dados abaixo.</html>");
        JLabel rotuloSaudacao = new JLabel("Olá Médico! Digite seu ID: (10XX)");
        rotuloSaudacao.setBounds(10, 10, 300, 25); //frame da label
        painel.add(rotuloSaudacao);

        // Exemplo de JLabel com quebra de linha usando HTML


        JLabel rotulo_usuario = new JLabel("ID: "); //Criar nova label para o campo de texto
        rotulo_usuario.setBounds(10, 50, 80, 25); //frame da label
        painel.add(rotulo_usuario); //add a label no panel

        JTextField campo_usuario = new JTextField(20); //criar novo campo de texto
        campo_usuario.setBounds(100, 50, 160, 25); //frame do campo de texto
        painel.add(campo_usuario); //add campo no panel

// botões:
        JButton botao_entrar = new JButton("Entrar"); //criar novo botao
        botao_entrar.setBounds(180, 90, 80, 25); //frame do botao
        painel.add(botao_entrar); //add botao no panel

        ActionListener leitor_botoes = new LeitorBotoes(); //criar um listener de botao
        botao_entrar.addActionListener(leitor_botoes); //adicionar um listener no botao de cadastro
    }

}
