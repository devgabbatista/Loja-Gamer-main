import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ANSI para cores no terminal
class ConsoleColor {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";
}

// Classe Produto
class Produto {
    private String nome;
    private String categoria;
    private double preco;
    private int quantidade;
    private LocalDate dataCadastro;

    public Produto(String nome, String categoria, double preco, int quantidade) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.dataCadastro = LocalDate.now();
    }

    public double getValorTotal() {
        return preco * quantidade;
    }

    public void vender(int qtdVendida) {
        if (qtdVendida > quantidade) {
            System.out.println(ConsoleColor.RED + "Estoque insuficiente para: " + nome + ConsoleColor.RESET);
        } else {
            quantidade -= qtdVendida;
            System.out.println(ConsoleColor.GREEN + "Venda realizada: " + qtdVendida + " unidade(s) de " + nome + ConsoleColor.RESET);
        }
    }

    public void exibir() {
        System.out.println(ConsoleColor.CYAN + "Produto: " + nome + ConsoleColor.RESET);
        System.out.println("Categoria: " + categoria);
        System.out.println("Preço: R$ " + preco);
        System.out.println("Quantidade em estoque: " + quantidade);
        System.out.println("Data de cadastro: " + dataCadastro);
        System.out.println("Valor total em estoque: R$ " + getValorTotal());
        System.out.println("-------------------------------");
    }

    public String getNome() {
        return nome;
    }
}

// Classe para gerenciamento de produtos
class ProdutoService {
    private List<Produto> produtos = new ArrayList<>();

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        System.out.println(ConsoleColor.GREEN + "Produto adicionado com sucesso!" + ConsoleColor.RESET);
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println(ConsoleColor.YELLOW + "Nenhum produto cadastrado." + ConsoleColor.RESET);
        } else {
            for (Produto p : produtos) {
                p.exibir();
            }
        }
    }

    public void venderProduto(String nome, int quantidade) {
        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                p.vender(quantidade);
                return;
            }
        }
        System.out.println(ConsoleColor.RED + "Produto não encontrado: " + nome + ConsoleColor.RESET);
    }

    public double calcularValorTotalEstoque() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getValorTotal();
        }
        return total;
    }
}

// Classe principal
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ProdutoService service = new ProdutoService();

        while (true) {
            limparConsole();
            System.out.println(ConsoleColor.PURPLE + "===== MENU MEN — Loja Gamer =====" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.BLUE + "1 - Adicionar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Vender produto");
            System.out.println("4 - Ver valor total em estoque");
            System.out.println("0 - Sair" + ConsoleColor.RESET);
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            limparConsole();
            switch (opcao) {
                case 1:
                    System.out.print("Nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Preço (ex: 199.99): ");
                    double preco = scanner.nextDouble();
                    System.out.print("Quantidade: ");
                    int quantidade = scanner.nextInt();
                    Produto novoProduto = new Produto(nome, categoria, preco, quantidade);
                    service.adicionarProduto(novoProduto);
                    pausar();
                    break;
                case 2:
                    System.out.println(ConsoleColor.YELLOW + "Lista de produtos cadastrados:" + ConsoleColor.RESET);
                    service.listarProdutos();
                    pausar();
                    break;
                case 3:
                    scanner.nextLine(); // limpar buffer antes de ler String
                    System.out.print("Nome do produto para vender: ");
                    String nomeVenda = scanner.nextLine();
                    System.out.print("Quantidade a vender: ");
                    int qtd = scanner.nextInt();
                    service.venderProduto(nomeVenda, qtd);
                    pausar();
                    break;
                case 4:
                    double total = service.calcularValorTotalEstoque();
                    System.out.println(ConsoleColor.CYAN + "Valor total em estoque: R$ " + total + ConsoleColor.RESET);
                    pausar();
                    break;
                case 0:
                    System.out.println(ConsoleColor.GREEN + "Encerrando sistema..." + ConsoleColor.RESET);
                    scanner.close();
                    Thread.sleep(1000);
                    limparConsole();
                    return;
                default:
                    System.out.println(ConsoleColor.RED + "Opção inválida!" + ConsoleColor.RESET);
                    pausar();
            }
        }
    }

    // Método para pausar até o usuário apertar Enter
    private static void pausar() {
        System.out.print("\nPressione Enter para continuar...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    // Método para limpar o console
    private static void limparConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
        }
    }
}