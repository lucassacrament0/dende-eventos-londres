enum class Sexo { MASCULINO, FEMININO, NAO_INFORMADO }
enum class Tipo { COMUM, ORGANIZADOR }

data class Usuario(
    var nome: String,
    var dataNascimento: String,
    var sexo: Sexo,
    val email: String,
    var senha: String,
    val tipoUsuario: Tipo,
    var cnpj: String? = null,
    var razaoSocial: String? = null,
    var nomeFantasia: String? = null
)

fun main() {
    // Configurando memória local
    val listaUsuarios = mutableListOf<Usuario>()
    var continuar = true

    println("BEM-VINDO AO DENDÊ EVENTOS\n")

    // Loop do menu principal
    while (continuar) {
        println("MENU PRINCIPAL")
        println("1. Cadastrar Usuário")
        println("0. Sair")

        print("Digite: ")
        val opcao = readln()

        when (opcao) {
            "1" -> {
                println("\nNOVO USUÁRIO")

                print("E-mail: ")
                val cadastroEmail = readln()

                // Verifica se o e-mail já existe
                var emailRepetido = false
                for (usuario in listaUsuarios) {
                    when {
                        usuario.email == cadastroEmail -> emailRepetido = true
                    }
                }
                when (emailRepetido) {
                    true -> println("ERRO: Este e-mail já possui cadastro. Tente novamente.")
                    false -> {
                        // O e-mail é único, recebe as informações de cadastro
                        print("Nome completo: ")
                        val cadastroNome = readln()

                        print("Data de Nascimento (DD/MM/AAAA): ")
                        val cadastroNascimento = readln()

                        print("Senha: ")
                        val cadastroSenha = readln()

                        // Utiliza o enum para limitar as opções
                        println("Sexo: [1] MASCULINO, [2] FEMININO, [3] PREFIRO NÃO INFORMAR")
                        print("Digite: ")
                        val opcaoSexo = readln()

                        val cadastroSexo = when (opcaoSexo) {
                            "1" -> Sexo.MASCULINO
                            "2" -> Sexo.FEMININO
                            else -> Sexo.NAO_INFORMADO
                        }
                        println("SEXO DEFINIDO $cadastroSexo.")

                        // Verifica se é usuário organizador
                        println("Você é organizador de eventos? [1] SIM, [2] NÃO")
                        print("Digite: ")
                        val tipoUsuario = readln()

                        val cadastroOrganizador = when (tipoUsuario) {
                            "1" -> Tipo.ORGANIZADOR
                            else -> Tipo.COMUM
                        }
                        println("DEFINIDO USUÁRIO $cadastroOrganizador.")

                        // Variaveis para cadastrar empresas
                        val cadastroEmpresa: String
                        var cadastroCNPJ: String? = null
                        var cadastroRazao: String? = null
                        var cadastroFantasia: String? = null

                        // Para os organizadores...
                        when (tipoUsuario) {
                            "1" -> {
                                // Verifica se tem empresa
                                println("Você possui uma empresa? [1] SIM [2] NÃO")
                                print("Digite: ")
                                cadastroEmpresa = readln()

                                when (cadastroEmpresa) {
                                    "1" -> {
                                        println("CADASTRO DE EMPRESA")
                                        print("Digite CNPJ: ")
                                        cadastroCNPJ = readln()

                                        print("Digite Razão Social: ")
                                        cadastroRazao = readln()

                                        print("Digite Nome Fantasia: ")
                                        cadastroFantasia = readln()
                                    }
                                    else -> println("DEFINIDO USUÁRIO SEM EMPRESA.")
                                }
                                // Adicionando o usuario cadastrado
                                val cadastroUsuario = Usuario(
                                    nome = cadastroNome,
                                    dataNascimento = cadastroNascimento,
                                    sexo = cadastroSexo,
                                    email = cadastroEmail,
                                    senha = cadastroSenha,
                                    tipoUsuario = cadastroOrganizador,
                                    cnpj = cadastroCNPJ,
                                    razaoSocial = cadastroRazao,
                                    nomeFantasia = cadastroFantasia
                                )

                                listaUsuarios.add(cadastroUsuario)
                                println("USUÁRIO CADASTRADO COM SUCESSO.")
                            }
                        }
                    }
                }
            }
            "0" -> {
                println("Sessão finalizada.")
                continuar = false
            }

            else -> {
                println("ERRO: Opção inválida. Tente novamente.")
            }
        }
    }
}