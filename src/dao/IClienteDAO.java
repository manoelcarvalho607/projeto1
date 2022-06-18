/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Cliente;
import java.util.Collection;

/**
 *
 * @author manoel.carvalho
 */
public interface IClienteDAO {
    
    public Boolean cadastrar(Cliente cliente);
    
    public void excluir(Long cpf);
    
    public Boolean alterar(Cliente cliente);
    
    public Cliente consultar(Long cpf);
    
    public Collection<Cliente> buscarTodos();
    
}
