package Programa02;

import javax.swing.*;
import java.awt.*;


// View da Interface do Médico
public class InterfaceMedico extends JFrame {

    // Instância da classe de lógica > MedicoService
    private MedicoService service = new MedicoService();

    // Componentes para o CardLayout
    private CardLayout cardLayout = new CardLayout();
    private JPanel painelPrincipal = new JPanel(cardLayout);

    // Componentes do painel de login
    private JTextField campoIdMedico;

    // Componentes do painel principal
    private JRadioButton opcao1, opcao2, opcao3;
    private JTextArea areaResultados;


    //O PROGRAMA >> classe que chamarei na MAIN
    public InterfaceMedico() {
        super("Sistema de Médico"); // Title (tipo do html)

        service.csvReader();

        // --- 1. PAINEL DE LOGIN ---

        //Setup painel para o campo de login

        //Setup painel para o campo de Input
        JPanel painelLogin = new JPanel(new GridBagLayout()); // Layout flexível
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing

        painelLogin.setBorder(BorderFactory.createTitledBorder("Olá Médico! Digite seu ID: (10XX)"));

        painelLogin.add(new JLabel("ID:"), gbc); // Label
        campoIdMedico = new JTextField(10);
        painelLogin.add(campoIdMedico, gbc); // Add

        JButton botaoEntrar = new JButton("Entrar"); // Botão
        painelLogin.add(botaoEntrar, gbc); // Add


        // --- 2. PAINEL PRINCIPAL ---
        JPanel painelMenu = new JPanel(new BorderLayout(10, 10));
        painelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel para as opções (RadioButtons)
        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new BoxLayout(painelOpcoes, BoxLayout.Y_AXIS));
        painelOpcoes.setBorder(BorderFactory.createTitledBorder("Opções de Consulta"));

        opcao1 = new JRadioButton("1. Consultar todos os pacientes");
        opcao1.setSelected(true); // Deixar a primeira opção pré-selecionada
        opcao2 = new JRadioButton("2. Consultar agendamentos por período");
        opcao3 = new JRadioButton("3. Pacientes ausentes há X meses");

        ButtonGroup grupoOpcoes = new ButtonGroup();
        grupoOpcoes.add(opcao1);
        grupoOpcoes.add(opcao2);
        grupoOpcoes.add(opcao3);

        painelOpcoes.add(opcao1);
        painelOpcoes.add(opcao2);
        painelOpcoes.add(opcao3);

        JButton botaoExecutar = new JButton("Executar Consulta");
        painelOpcoes.add(Box.createVerticalStrut(10)); // Espaço
        painelOpcoes.add(botaoExecutar);


        painelMenu.add(painelOpcoes, BorderLayout.NORTH); //Norte

        // Área de texto para resultados (Centro)
        areaResultados = new JTextArea("Os resultados aparecerão aqui.");
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados); // Scroll Bar (caso necessite)
        painelMenu.add(scrollPane, BorderLayout.CENTER);


        // --- 3. CONFIGURAR CARDLAYOUT ---
        painelPrincipal.add(painelLogin, "LOGIN");
        painelPrincipal.add(painelMenu, "MENU");

        // Adicionar o painel principal à janela
        this.add(painelPrincipal);

        // --- 4. ADICIONAR LÓGICA AOS BOTÕES (ActionListeners) ---

        // Ação do botão "Entrar"
        botaoEntrar.addActionListener(e -> tentarLogin());

        // Ação do botão "Executar Consulta"
        botaoExecutar.addActionListener(e -> executarConsulta());

        // --- 5. CONFIGURAÇÕES FINAIS DA JANELA ---
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); // Ajusta o tamanho da janela aos componentes
        this.setLocationRelativeTo(null); // Centraliza
    }

    private void tentarLogin() {
        try {
            int id = Integer.parseInt(campoIdMedico.getText());
            if (service.validarMedico(id)) {
                // Se o login for válido, muda para o painel do menu
                cardLayout.show(painelPrincipal, "MENU");
                // Ajusta o tamanho da janela para o novo painel
                this.setSize(600, 400);
                this.setLocationRelativeTo(null);
            } else {
                JOptionPane.showMessageDialog(this, "ID de médico inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID numérico.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executarConsulta() {
        String resultado = "";
        if (opcao1.isSelected()) {
            resultado = service.consultarTodosPacientes();
        } else if (opcao2.isSelected()) {
            // Pedir input para o período
            String dataInicio = JOptionPane.showInputDialog(this, "Digite a data de início (dd/mm/aaaa):");
            String dataFim = JOptionPane.showInputDialog(this, "Digite a data de fim (dd/mm/aaaa):");
            if(dataInicio != null && dataFim != null) { // Verifica se o usuário não cancelou
                resultado = service.consultarPorPeriodo(dataInicio, dataFim);
            }
        } else if (opcao3.isSelected()) {
            // Pedir input para os meses
            try {
                String mesesStr = JOptionPane.showInputDialog(this, "Digite a quantidade de meses:");
                if(mesesStr != null){
                    int meses = Integer.parseInt(mesesStr);
                    resultado = service.consultarAusentes(meses);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para os meses.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
        areaResultados.setText(resultado);
    }

    // Método main para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfaceMedico().setVisible(true);
        });
    }
}