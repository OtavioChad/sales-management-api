package com.chad.sales.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chad.sales.dto.ItemVendaDTO;
import com.chad.sales.dto.VendaDTO;
import com.chad.sales.exception.ClienteNotFoundException;
import com.chad.sales.exception.EstoqueInsuficienteException;
import com.chad.sales.exception.ProdutoNotFoundException;
import com.chad.sales.exception.UsuarioNotFoundException;
import com.chad.sales.exception.VendaNotFoundException;
import com.chad.sales.model.Cliente;
import com.chad.sales.model.ItemVenda;
import com.chad.sales.model.Produto;
import com.chad.sales.model.Usuario;
import com.chad.sales.model.Venda;
import com.chad.sales.repository.ClienteRepository;
import com.chad.sales.repository.ProdutoRepository;
import com.chad.sales.repository.UsuarioRepository;
import com.chad.sales.repository.VendaRepository;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(VendaRepository vendaRepository,
                        UsuarioRepository usuarioRepository,
                        ClienteRepository clienteRepository,
                        ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    // Salvar venda direta
    public Venda salvar(Venda venda) {
        Usuario usuario = getUsuarioLogado();
        venda.setUsuario(usuario);
        return vendaRepository.save(venda);
    }

    // Salvar venda via DTO
    @Transactional
    public Venda salvarComDTO(VendaDTO dto) {
        // 1️⃣ Pega o usuário logado
        Usuario usuario = getUsuarioLogado();

        // 2️⃣ Valida o cliente e garante que pertence ao usuário logado
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ClienteNotFoundException(
                        "Cliente com ID " + dto.getClienteId() + " não encontrado"));

        // Se a entidade Cliente tiver vínculo com usuário, valida aqui
        if (cliente.getUsuario() == null || !cliente.getUsuario().getId().equals(usuario.getId())) {
            throw new ClienteNotFoundException(
                    "Cliente " + cliente.getNome() + " não pertence ao usuário logado");
        }

        // 3️⃣ Cria a venda
        Venda venda = new Venda();
        venda.setUsuario(usuario);
        venda.setCliente(cliente);
        venda.setFormaPagamento(dto.getFormaPagamento());
        venda.setData(LocalDateTime.now());

        // 4️⃣ Valida e adiciona itens da venda
        List<ItemVenda> itens = dto.getItens().stream().map(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new ProdutoNotFoundException(
                            "Produto com ID " + itemDTO.getProdutoId() + " não encontrado"));

            // Garante que o produto pertence ao usuário logado
            if (produto.getUsuario() == null || !produto.getUsuario().getId().equals(usuario.getId())) {
                throw new ProdutoNotFoundException(
                        "Produto " + produto.getNome() + " não pertence ao usuário logado");
            }

            // Verifica estoque
            if (produto.getEstoque() < itemDTO.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o produto: " + produto.getNome());
            }

            // Cria o item da venda
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPreco(produto.getPrecoVenda());
            item.setVenda(venda);

            // Atualiza estoque
            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());
            produtoRepository.save(produto);

            return item;
        }).toList();

        // 5️⃣ Finaliza a venda
        venda.setItens(itens);
        double total = itens.stream().mapToDouble(i -> i.getPreco() * i.getQuantidade()).sum();
        venda.setValorTotal(total);

        // 6️⃣ Salva no banco
        return vendaRepository.save(venda);
    }

    // Listar todas as vendas do usuário logado
    public List<Venda> listarTodosDoUsuario() {
        Usuario usuario = getUsuarioLogado();
        return vendaRepository.findByUsuarioId(usuario.getId());
    }

    // Buscar venda específica do usuário logado
    public Venda buscarPorIdDoUsuario(Long id) {
        Usuario usuario = getUsuarioLogado();
        return vendaRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new VendaNotFoundException("Venda com ID " + id + " não encontrada"));
    }

    // Deletar venda do usuário logado
    public void deletar(Long id) {
        Venda venda = buscarPorIdDoUsuario(id);
        vendaRepository.delete(venda);
    }

    // Helper: pega o usuário logado
    private Usuario getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (principal instanceof UserDetails)
                ? ((UserDetails) principal).getUsername()
                : principal.toString();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário logado não encontrado"));
    }
}