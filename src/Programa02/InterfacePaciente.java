package Programa02;

import Programa02.Exception.CPFInvalidoException;
import Programa02.Exception.IdInvalidoException;
import Programa02.Exception.PeriodoInvalidoException;

import javax.swing.*;
import java.awt.*;

// View da Interface do Paciente
public class InterfacePaciente extends JFrame {

    // Instância da classe de lógica > PacienteService
    private PacienteService service = new PacienteService();

    // Componentes para o CardLayout
    private CardLayout cardLayout = new CardLayout();
    private JPanel painelPrincipal = new JPanel(cardLayout);

    // Componentes do painel de login
    private JTextField campoCPFPaciente;

    // Componentes do painel principal
    private JRadioButton opcao1, opcao2, opcao3;
    private JTextArea areaResultados;
    private JButton botaoExportar;


    //O PROGRAMA >> o inicializador da classe que chamarei na MAIN
    public InterfacePaciente() {
        super("Sistema Paciente"); // Title de JFrame (tipo title de uma pág html)

        service.fileReader();

        // --- 1. PAINEL DE LOGIN ---

        //Setup painel para o campo de login
        JPanel painelLogin = new JPanel(new GridBagLayout()); // Layout flexível
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing

        painelLogin.setBorder(BorderFactory.createTitledBorder("Olá Paciente! Digite seu CPF: (XXX.XXX.XXX-XX)"));

        painelLogin.add(new JLabel("CPF:"), gbc); // Label
        campoCPFPaciente = new JTextField(10);
        painelLogin.add(campoCPFPaciente, gbc); // Add

        JButton botaoEntrar = new JButton("Entrar"); // Botão
        painelLogin.add(botaoEntrar, gbc); // Add

        //Esse painel é setado no campo principal >> Config do Card Layout


        // --- 2. PAINEL PRINCIPAL ---
        JPanel painelMenu = new JPanel(new BorderLayout(10, 10));
        painelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        // Painel para as opções (RadioButtons)
        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new BoxLayout(painelOpcoes, BoxLayout.Y_AXIS));
        painelOpcoes.setBorder(BorderFactory.createTitledBorder("Opções de Consulta"));

        opcao1 = new JRadioButton("1. Consultar todos os médicos");
        opcao1.setSelected(true); // Deixar a primeira opção pré-selecionada
        opcao2 = new JRadioButton("2. Consultar agendamentos passados com médico específico");
        opcao3 = new JRadioButton("3. Consultar agendamentos futuros");

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

        // --- PAINEL DE EXPORTAÇÃO (NOVO) ---
        JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Alinhado à direita
        botaoExportar = new JButton("Exportar para .txt");
        botaoExportar.setEnabled(false); // Começa desabilitado
        painelSul.add(botaoExportar);
        painelMenu.add(painelSul, BorderLayout.SOUTH);

        // --- 3. CONFIGURAR CARDLAYOUT ---
        painelPrincipal.add(painelLogin, "LOGIN");
        painelPrincipal.add(painelMenu, "MENU");

        // Adicionar o painel principal à janela
        this.add(painelPrincipal);

        // --- 4. ADICIONAR LÓGICA AOS BOTÕES (ActionListeners) ---

        // Ação do botão "Entrar"
        botaoEntrar.addActionListener(e -> tentarLogin());

        // Ação do botão "Executar Consulta"
        botaoExecutar.addActionListener(e -> {
            try {
                executarConsulta();
            }
            catch (PeriodoInvalidoException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira datas de consulta válidas.", "Erro de Período", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um valor válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação do botão "Exportar"
        botaoExportar.addActionListener(e -> exportarResultado());

        // --- 5. CONFIGURAÇÕES FINAIS DA JANELA ---
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); // Ajusta o tamanho da janela aos componentes
        this.setLocationRelativeTo(null); // Centraliza
    }

    private void tentarLogin() {
        try {
            String cpf = campoCPFPaciente.getText();
            if (service.validarPaciente(cpf)) {
                // Se o login for válido, muda para o painel do menu
                JOptionPane.showMessageDialog(this, "Bem vindo(a)! " + service.getPaciente().getNome()); // Inserir aqui nome do paciente da sessão (criar sessão)
                cardLayout.show(painelPrincipal, "MENU");
                // Ajusta o tamanho da janela para o novo painel
                this.setSize(600, 400);
                this.setLocationRelativeTo(null);
            } else {
                JOptionPane.showMessageDialog(this, "ID de médico inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (CPFInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executarConsulta() throws Exception {
        String resultado = "";
        if (opcao1.isSelected()) {
            resultado = service.consultarTodosMedicos();

        } else if (opcao2.isSelected()) {
            // Pedir input para o ID Médico
            String id = JOptionPane.showInputDialog(this, "Digite o ID do médico que você deseja visualizar: (10XX)");
            if(id != null) { // Verifica se o usuário não cancelou
                resultado = service.consultarAgendamentosMedicoEspecifico(id);
            }
        } else if (opcao3.isSelected()) {
            //option 03
            resultado = service.consultarAgendamentoFuturos();
        }
        areaResultados.setText(resultado);

        botaoExportar.setEnabled(!resultado.isEmpty() && !resultado.startsWith("Os resultados"));
    }

    private void exportarResultado() {
        service.exportarResultado(areaResultados.getText());
    }

    // Metodo main para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfacePaciente().setVisible(true);
        });
    }
}