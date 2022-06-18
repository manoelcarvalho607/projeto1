/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cadastroclienteswing;

import dao.IClienteDAO;
import dao.ClienteMapDAO;
import dao.ClienteSetDAO;

import domain.Cliente;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author visitante
 */
public class App extends javax.swing.JFrame {

    private DefaultTableModel modelo = new DefaultTableModel();
    //private ClienteSetDAO clienteDAO = new ClienteSetDAO();
    private ClienteMapDAO clienteDAO = new ClienteMapDAO();

    /**
     * Creates new form App
     */
    public App() {
        initComponents();
        initCustomComponents();
    }

    private void creade() {
        String nome = txtNome.getText();
        String cpf = txtCPF.getText();
        String tel = txtTel.getText();
        String end = txtEnd.getText();
        String num = txtN.getText();
        String cidade = txtCidade.getText();
        String estado = txtEstado.getText();
        System.out.println(tel);

        boolean validarCpf = cpf.matches("[+-]?\\d*(\\.\\d+)?");
        boolean validarTel = tel.matches("[+-]?\\d*(\\.\\d+)?");

        if (validarCpf == false || validarTel == false) {
            JOptionPane.showInternalMessageDialog(null, "Os campos CPF e TEL podem ser preenchidos somente com caracteres numéricos (ex: 29497633382 e 00002222)", "ATENÇÃO",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!isCampoValidos(nome, cpf)) {
            JOptionPane.showInternalMessageDialog(null, "Existem campos a serem preenchidos", "ATENÇÃO",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(nome, cpf, tel, end, num, cidade, estado);
        Boolean isCadastrado = this.clienteDAO.cadastrar(cliente);

        if (isCadastrado) {
            modelo.addRow(new Object[]{cliente.getNome(), cliente.getCpf(), cliente.getTel(), cliente.getEnd(), cliente.getNumero(), cliente.getCidade(), cliente.getEstado()});
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(null, "Cliente já se encontra no cadastrado",
                    "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void upgrade() {

        String nome = txtNome.getText();
        String cpf = txtCPF.getText();
        String tel = txtTel.getText();
        String end = txtEnd.getText();
        String num = txtN.getText();
        String cidade = txtCidade.getText();
        String estado = txtEstado.getText();

        boolean validarCpf = cpf.matches("[+-]?\\d*(\\.\\d+)?");
        boolean validarTel = tel.matches("[+-]?\\d*(\\.\\d+)?");

        if (validarCpf == false || validarTel == false) {
            JOptionPane.showInternalMessageDialog(null, "Os campos CPF e TEL podem ser preenchidos somente com caracteres numéricos (ex: 29497633382 e 00002222)", "ATENÇÃO",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!isCampoValidos(nome, cpf, tel)) {
            JOptionPane.showInternalMessageDialog(null, "Preencha os campos ou selecione um cliente na tabela para alterar os dados", "ATENÇÃO",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int linhaTable = tblClientes.getSelectedRow();

        if (linhaTable >= 0) {

            Cliente cliente = new Cliente(nome, cpf, tel, end, num, cidade, estado);
            this.clienteDAO.alterar(cliente);

            modelo.removeRow(linhaTable);

            modelo.addRow(new Object[]{cliente.getNome(), cliente.getCpf(), cliente.getTel(), cliente.getEnd(), cliente.getNumero(), cliente.getCidade(), cliente.getEstado()});

            JOptionPane.showMessageDialog(null, " Dados do cliente alterado com sucesso",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar dados do cliente",
                    "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void delete() {
        String cpfExcluir = txtCPF.getText();

        //validação de campos preenchidos
        if (isCampoValidos(cpfExcluir)) {

            int linhaTable = tblClientes.getSelectedRow();
            if (linhaTable >= 0) {
                int result = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este cliente?", "ATENÇÃO", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    Long cpf = (Long) tblClientes.getValueAt(linhaTable, 1);
                    this.clienteDAO.excluir(cpf);
                    modelo.removeRow(linhaTable);

                    JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, " Selecione um cliente na tabela para excluir", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void read() {
        String cpf = txtCPF.getText();

        if (!isCampoValidos(cpf)) {
            JOptionPane.showInternalMessageDialog(null, "Preencher campo CPF para consulta", "ATENÇÃO",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {

            Long cpfConsulta = Long.parseLong(txtCPF.getText());
            Cliente resultado = this.clienteDAO.consultar(cpfConsulta);

            if (resultado != null) {
                JOptionPane.showInternalMessageDialog(null, "Cliente cadastrado: " + resultado, "MENSAGEM",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showInternalMessageDialog(null, "Cliente não cadastrado", "MENSAGEM",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    public void setar_campos() {
        int setar = tblClientes.getSelectedRow();
        txtNome.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtCPF.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtTel.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtEnd.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtN.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
        txtCidade.setText(tblClientes.getModel().getValueAt(setar, 5).toString());
        txtEstado.setText(tblClientes.getModel().getValueAt(setar, 6).toString());
        System.out.println(setar);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNome = new javax.swing.JLabel();
        lblCPF = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtCPF = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnAlterar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtEnd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtN = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        txtTel = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro de Cliente");
        setResizable(false);

        lblNome.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblNome.setText("Nome");

        lblCPF.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblCPF.setText("CPF");

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar Campos");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setText("Tel");

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("End");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setText("Nº");

        txtN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setText("Estado");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setText("Cidade");

        jMenu1.setText("Opções");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuItemSair.setText("Sair");
        menuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSairActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemSair);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(btnSalvar)
                        .addGap(47, 47, 47)
                        .addComponent(btnAlterar)
                        .addGap(60, 60, 60)
                        .addComponent(btnConsultar)
                        .addGap(55, 55, 55)
                        .addComponent(btnExcluir))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNome))
                                .addGap(15, 15, 15)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCPF)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtN, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtTel, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                            .addComponent(txtEstado))))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome)
                    .addComponent(lblCPF)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnAlterar)
                    .addComponent(btnConsultar)
                    .addComponent(btnExcluir))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(btnLimpar)
                .addGap(56, 56, 56))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        creade();


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void menuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSairActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Deseja sair da aplicação", "Sair", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }


    }//GEN-LAST:event_menuItemSairActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void txtNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNActionPerformed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparCampos();
        // ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        upgrade();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        delete();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        read();
    }//GEN-LAST:event_btnConsultarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblNome;
    private javax.swing.JMenuItem menuItemSair;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtEnd;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtN;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables

    private boolean isCampoValidos(String... campos) {
        for (String campo : campos) {
            if (campos == null || "".equals(campo)) {
                return false;
            }
        }
        return true;
    }

    // metodo para colocar informação na tabela
    private void initCustomComponents() {
        modelo.addColumn("Nome");
        modelo.addColumn("CPF");
        modelo.addColumn("Tel");
        modelo.addColumn("End");
        modelo.addColumn("Numero");
        modelo.addColumn("Cidade");
        modelo.addColumn("Estado");

        tblClientes.setModel(modelo);
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCPF.setText("");
        txtTel.setText("");
        txtEnd.setText("");
        txtN.setText("");
        txtCidade.setText("");
        txtEstado.setText("");
        // ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
    }
}
