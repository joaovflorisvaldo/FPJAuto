/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Service.ServiceCliente;
import Service.ServiceProduto;
import Service.ServiceVenda;
import dao.VendaDAO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import dto.ClienteDto;
import dto.ProdutoDto;
import dto.ItemVendaDto;
import java.util.ArrayList;
import java.util.List;
import modelo.ItemVenda;
import dto.VendaDto;
import java.time.LocalDate;
import modelo.Venda;

/**
 *
 * @author joaov
 */
public class Vendas extends javax.swing.JFrame {
          
    private VendaDAO vendaDao = new VendaDAO();
    private DefaultTableModel  modelo = new DefaultTableModel();
    private int linhaSelecionada = -1;

    /**
     * Creates new form NewJFramed
     */
    public Vendas() {
        initComponents();
        carregaTabela();
        carregarNomesDosClientes();
        carregarProdutos();
    }
    
    public void salvarApiVenda(Integer idCliente) {
        VendaDto vendaDto = new VendaDto();
        vendaDto.setObservacoes(taObservacoes.getText());
        vendaDto.setTotal(Double.parseDouble(tfValorTotalPedido.getText()));
        vendaDto.setData(java.sql.Date.valueOf(LocalDate.now()));
        vendaDto.setCliente(idCliente);

        ServiceVenda serviceVenda = new ServiceVenda();

        ServiceVenda.SaveResult vendaSalvaComSucesso = serviceVenda.salvarVenda(vendaDto);

        if (vendaSalvaComSucesso.isSuccess()) {
            ItemVendaDto itemVendaDto = new ItemVendaDto();
            vendaDto.setId(vendaSalvaComSucesso.getVendaId()); // Atualizar o VendaDto com o ID gerado
            itemVendaDto.setItens(obterItensVenda());  
            itemVendaDto.setVenda(vendaDto);
            
            ServiceVenda.SaveResult ItemVendaComSucesso = serviceVenda.salvarItemVenda(itemVendaDto);
            if (ItemVendaComSucesso.isSuccess()) {
                System.out.println("Item venda salvo com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar Item venda: " + ItemVendaComSucesso.getErrorMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }  
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar Venda: " + vendaSalvaComSucesso.getErrorMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void salvarApiItemVenda() {
        
        ItemVendaDto itemVendaDto = new ItemVendaDto();
        itemVendaDto.setItens (obterItensVenda());

        // Chama o método do ServiceVenda para salvar a venda na API
        ServiceVenda serviceVenda = new ServiceVenda();
        

       
        carregarProdutos();
    }
    
    public void carregaTabela(){
        
       modelo.addColumn("Item");
       modelo.addColumn("Quantidade");
       modelo.addColumn("Valor");
       modelo.addColumn("Valor Total");
       
       tbPedido.setModel(modelo);
       tbPedido.getColumnModel().getColumn(0).setPreferredWidth(0);
       tbPedido.getColumnModel().getColumn(1).setPreferredWidth(0);
       tbPedido.getColumnModel().getColumn(2).setPreferredWidth(0);
       tbPedido.getColumnModel().getColumn(3).setPreferredWidth(0);
    }
    
    // Metodo para carregar os nomes dos clientes da API
    private void carregarNomesDosClientes() {
        try {
            List<ClienteDto> clientes = (List<ClienteDto>) ServiceCliente.obterClientesDaAPI();
            // Limpa os itens existentes
            cbCliente.removeAllItems();
            // Adiciona os nomes dos clientes na lista
            cbCliente.addItem("Selecione um Cliente");
            
            for (ClienteDto cliente : clientes) {
                cbCliente.addItem(cliente.getNome());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os nomes dos clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private List<ItemVenda> obterItensVenda() {
        Integer idProduto = 0;
       
        List<ItemVenda> itens = new ArrayList<>();

        for (int i = 0; i < tbPedido.getRowCount(); i++) {
            ItemVenda item = new ItemVenda();
            
            try {
                List<ProdutoDto> produtos = ServiceProduto.obterProdutosDaAPI();

                for (ProdutoDto produto : produtos) {
                    if (produto.getNome().equals(tbPedido.getValueAt(i, 0))) {
                        idProduto = produto.getId();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao obter Cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } 
            
            Integer quantidade = Integer.parseInt(tbPedido.getValueAt(i, 1).toString());
            Double valorUnitario = Double.parseDouble(tbPedido.getValueAt(i, 2).toString());
            Double valorTotal = Double.parseDouble(tbPedido.getValueAt(i, 3).toString());
            
            item.setQuantidade(quantidade);
            item.setValorUnitario(valorUnitario);
            item.setValorTotal(valorTotal);
            item.setProduto(idProduto);
            
            itens.add(item);
        }

        return itens;
    } 
    
    // Metodo para carregar os nomes dos produtos da API
    private void carregarProdutos() {
        try {
            List<ProdutoDto> produtos = (List<ProdutoDto>) ServiceProduto.obterProdutosDaAPI();
            // Limpa os itens existentes
            cbProduto.removeAllItems();
            // Adiciona os nomes dos clientes na lista
            cbProduto.addItem("Selecione um Produto");
            
            for (ProdutoDto produto : produtos) {
                cbProduto.addItem(produto.getNome());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os nomes dos produtos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cbCliente = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cbProduto = new javax.swing.JComboBox<>();
        tfQuantidadeItem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfValorItem = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfValorTotalItem = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tfValorTotalPedido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btAdicionarItens = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taObservacoes = new javax.swing.JTextArea();
        btSalvarPedido = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPedido = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClienteActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Cliente");

        cbProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProdutoActionPerformed(evt);
            }
        });

        tfQuantidadeItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfQuantidadeItemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfQuantidadeItemKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfQuantidadeItemKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Produto");

        jLabel2.setText("Quantidade");

        tfValorItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfValorItemActionPerformed(evt);
            }
        });

        jLabel4.setText("Valor Item");

        jLabel5.setText("Valor Total Item");

        jLabel6.setText("Valor Total");

        tfValorTotalPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfValorTotalPedidoActionPerformed(evt);
            }
        });

        jLabel7.setText("Observações");

        btAdicionarItens.setText("Adicionar Item");
        btAdicionarItens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarItensActionPerformed(evt);
            }
        });

        taObservacoes.setColumns(20);
        taObservacoes.setRows(5);
        jScrollPane2.setViewportView(taObservacoes);

        btSalvarPedido.setText("Salvar");
        btSalvarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarPedidoActionPerformed(evt);
            }
        });

        tbPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane1.setViewportView(tbPedido);

        jButton1.setText("Relatório Geral");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Relatório Detalhado");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfValorItem))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfQuantidadeItem, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(tfValorTotalItem, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btAdicionarItens))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(177, 177, 177))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(tfValorTotalPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btSalvarPedido)))
                            .addComponent(jButton1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btAdicionarItens))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfValorItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfQuantidadeItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfValorTotalItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfValorTotalPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btSalvarPedido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbClienteActionPerformed
        
    }//GEN-LAST:event_cbClienteActionPerformed

    private void cbProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProdutoActionPerformed
        try {
            String produtoSelecionado = (String) cbProduto.getSelectedItem();
            List<ProdutoDto> produtos = ServiceProduto.obterProdutosDaAPI();
            
            // Busca o produto na lista pelo nome 
            for (ProdutoDto produto : produtos) {
                if (produto.getNome().equals(produtoSelecionado)) {
                    tfValorItem.setText(String.valueOf(produto.getValor()));
                    tfValorItem.disable();
                    break;
                }
            }          
            // Se o produto não for encontrado na lista, exibe uma mensagem de erro
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao obter valor do produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cbProdutoActionPerformed

    private void tfQuantidadeItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfQuantidadeItemKeyPressed

    }//GEN-LAST:event_tfQuantidadeItemKeyPressed

    private void tfValorItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfValorItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfValorItemActionPerformed

    private void btAdicionarItensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarItensActionPerformed
        Double quantidadeItens = Double.parseDouble(tfQuantidadeItem.getText());
        Double valorItem = Double.parseDouble(tfValorItem.getText());
        Double valorTotalItens = Double.parseDouble(tfValorTotalItem.getText());

        valorTotalItens = (Double.valueOf(quantidadeItens) * Double.valueOf(valorItem));

        if( linhaSelecionada >= 0 ){
            modelo.removeRow(linhaSelecionada);
            modelo.insertRow(linhaSelecionada, new Object[] {cbProduto.getSelectedItem().toString(), tfQuantidadeItem.getText(), valorItem.toString(), valorTotalItens.toString()});
        }else{
            modelo.addRow(new Object[] {cbProduto.getSelectedItem().toString(), tfQuantidadeItem.getText(), valorItem.toString(), valorTotalItens.toString()});
        }

        JOptionPane.showMessageDialog(jPanel1, "Item adicionado com Sucesso!");

        tfQuantidadeItem.setText("");
        tfValorItem.setText("");
        tfValorTotalItem.setText("");

        linhaSelecionada = -1;

        Double valorTotalPedido = 0.0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            valorTotalPedido += Double.parseDouble(modelo.getValueAt(i, 3).toString());
        }

        tfValorTotalPedido.setText(String.valueOf(valorTotalPedido));
        
        cbCliente.disable();
        cbProduto.setSelectedIndex(0);
    }//GEN-LAST:event_btAdicionarItensActionPerformed

    private void btSalvarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarPedidoActionPerformed
        Integer idCliente = 0;
        
        try {
            String clienteSelecionado = (String) cbCliente.getSelectedItem();
            List<ClienteDto> clientes = ServiceCliente.obterClientesDaAPI();

            for (ClienteDto cliente : clientes) {
                if (cliente.getNome().equals(clienteSelecionado)) {
                    idCliente = cliente.getId();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao obter Cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Venda venda = new Venda();
        venda.setObservacoes(taObservacoes.getText());
        venda.setTotal(Double.parseDouble(tfValorTotalPedido.getText()));
        venda.setCliente(idCliente);
        venda.setItens(obterItensVenda());
        
        if( taObservacoes.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Informe uma observação!");
            return;
        }
        
        if (vendaDao.salvar(venda)) {
            salvarApiVenda(idCliente);
            salvarApiItemVenda();
            JOptionPane.showMessageDialog(this, "Venda salva com sucesso!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
            DefaultTableModel model = (DefaultTableModel) tbPedido.getModel();
            model.setRowCount(0);
            taObservacoes.setText("");
            tfValorTotalPedido.setText("");
            cbCliente.enable();
            carregarNomesDosClientes();
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar Venda, solicite suporte!", "Erro", JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_btSalvarPedidoActionPerformed

    private void tfQuantidadeItemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfQuantidadeItemKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tfQuantidadeItemKeyTyped

    private void tfQuantidadeItemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfQuantidadeItemKeyReleased
        Double quantidadeItem = Double.parseDouble(tfQuantidadeItem.getText());
        Double valorItem = Double.parseDouble(tfValorItem.getText());

        Double valorTotalItem = valorItem * quantidadeItem;

        tfValorTotalItem.setText(String.valueOf(valorTotalItem));
    }//GEN-LAST:event_tfQuantidadeItemKeyReleased

    private void tfValorTotalPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfValorTotalPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfValorTotalPedidoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        VendaDAO vendaDao = new VendaDAO();
        vendaDao.gerarRelatorioGeral();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        VendaDAO vendaDao = new VendaDAO();
        vendaDao.gerarRelatorio();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionarItens;
    private javax.swing.JButton btSalvarPedido;
    private javax.swing.JComboBox<String> cbCliente;
    private javax.swing.JComboBox<String> cbProduto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea taObservacoes;
    private javax.swing.JTable tbPedido;
    private javax.swing.JTextField tfQuantidadeItem;
    private javax.swing.JTextField tfValorItem;
    private javax.swing.JTextField tfValorTotalItem;
    private javax.swing.JTextField tfValorTotalPedido;
    // End of variables declaration//GEN-END:variables
}
