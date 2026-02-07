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
        println("2. Alterar Usuário")
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
                        println("OK: SEXO DEFINIDO $cadastroSexo.")

                        // Verifica se é usuário organizador
                        println("Você é organizador de eventos? [1] SIM, [2] NÃO")
                        print("Digite: ")
                        val tipoUsuario = readln()

                        val cadastroOrganizador = when (tipoUsuario) {
                            "1" -> Tipo.ORGANIZADOR
                            else -> Tipo.COMUM
                        }
                        println("OK: DEFINIDO USUÁRIO $cadastroOrganizador.")

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
                                        println("\nCADASTRO DE EMPRESA")
                                        print("Digite CNPJ: ")
                                        cadastroCNPJ = readln()

                                        print("Digite Razão Social: ")
                                        cadastroRazao = readln()

                                        print("Digite Nome Fantasia: ")
                                        cadastroFantasia = readln()
                                    }
                                    else -> println("OK: DEFINIDO USUÁRIO SEM EMPRESA.")
                                }
                            }
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
                                println("OK: USUÁRIO CADASTRADO COM SUCESSO.\n")
                    }
                }
            }
            "2" ->  {
                println("\nALTERAR CADASTRO")
                print("Digite o e-mail da sua conta: ")
                val buscarEmail = readln()
                print("Digite a senha da sua conta: ")
                val buscarSenha = readln()

                // Cria variável associada com o data class Usuario
                var usuarioEncontrado: Usuario? = null

                // Busca na lista de usuários
                for (usuario in listaUsuarios) {
                    when {
                        usuario.email == buscarEmail && usuario.senha == buscarSenha -> usuarioEncontrado = usuario
                    }
                }
                val tipo = usuarioEncontrado?.tipoUsuario
                // Execução para usuário (separado por tipo) depois da busca
                when (tipo) {
                    Tipo.COMUM -> {
                        println("OK: Alterando ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                        println("ALTERAR DADOS")
                        println("[1] Nome [2] Data de nascimento [3] Sexo [4] Senha")
                        print("Digite: ")
                        val opcaoAlterar = readln()
                        println("")
                        when (opcaoAlterar) {
                            "1" -> {
                                print("Digite Nome: ")
                                usuarioEncontrado.nome = readln()
                                println("OK: Nome atualizado para ${usuarioEncontrado.nome}.")
                            }
                            "2" -> {
                                print("Digite Data de nascimento (DD/MM/AAAA): ")
                                usuarioEncontrado.dataNascimento = readln()
                                println("OK: Data de nascimento atualizada para ${usuarioEncontrado.dataNascimento}.")
                            }
                            "3" -> {
                                println("Digite Sexo: [1] MASCULINO, [2] FEMININO, [3] NÃO INFORMADO")
                                val alterarSexo = readln()
                                usuarioEncontrado.sexo = when (alterarSexo) {
                                    "1" -> Sexo.MASCULINO
                                    "2" -> Sexo.FEMININO
                                    else -> Sexo.NAO_INFORMADO
                                }
                                println("OK: Sexo atualizado para ${usuarioEncontrado.sexo}.")
                            }
                            "4" -> {
                                print("Digite Senha: ")
                                usuarioEncontrado.senha = readln()
                                print("Confirme Senha: ")
                                val confirmarSenha = readln()
                                when {usuarioEncontrado.senha == confirmarSenha -> println("OK: Senha atualizada.")
                                else -> println("ERRO: Senhas diferentes. Solicite nova alteração.")}
                            }
                            else -> println("ERRO: Opção inválida. Solicite nova alteração.")
                        }
                    }
                    Tipo.ORGANIZADOR -> {
                        println("Usuário: ${usuarioEncontrado.nome}")
                        println("ALTERAR CADASTRO")
                        println("[1] Nome [2] Data de nascimento [3] Sexo [4] Senha \n[5] CNPJ [6] Razão Social [7] Nome Fantasia")
                        print("Opção: ")
                        val opcaoAlterar = readln()

                        when (opcaoAlterar) {
                            "1" -> {
                                print("Digite Nome: ")
                                usuarioEncontrado.nome = readln()
                                println("OK: Nome atualizado para ${usuarioEncontrado.nome}.")
                            }
                            "2" -> {
                                print("Digite Data de nascimento (DD/MM/AAAA): ")
                                usuarioEncontrado.dataNascimento = readln()
                                println("OK: Data de nascimento atualizada para ${usuarioEncontrado.dataNascimento}.")
                            }
                            "3" -> {
                                println("Digite Sexo: [1] MASCULINO, [2] FEMININO, [3] NÃO INFORMADO")
                                val alterarSexo = readln()
                                usuarioEncontrado.sexo = when (alterarSexo) {
                                    "1" -> Sexo.MASCULINO
                                    "2" -> Sexo.FEMININO
                                    else -> Sexo.NAO_INFORMADO
                                }
                                println("OK: Sexo atualizado para ${usuarioEncontrado.sexo}.")
                            }
                            "4" -> {
                                print("Digite Senha: ")
                                usuarioEncontrado.senha = readln()
                                print("Confirme Senha: ")
                                val confirmarSenha = readln()
                                when {usuarioEncontrado.senha == confirmarSenha -> println("OK: Senha atualizada.")
                                    else -> println("ERRO: Senhas diferentes. Solicite nova alteração.")}
                            }
                            "5" -> {
                                print("Digite CNPJ: ")
                                usuarioEncontrado.cnpj = readln()
                                println("OK: CNPJ atualizado para ${usuarioEncontrado.cnpj}.")
                            }
                            "6" -> {
                                print("Digite Razão Social: ")
                                usuarioEncontrado.razaoSocial = readln()
                                println("OK: Razão Social atualizada para ${usuarioEncontrado.razaoSocial}.")
                            }
                            "7" -> {
                                print("Digite Nome Fantasia: ")
                                usuarioEncontrado.nomeFantasia = readln()
                                println("OK: Nome Fantasia atualizado para ${usuarioEncontrado.nomeFantasia}.")
                            }
                        }
                        println("OK: Operação finalizada.\n")
                    }
                    else -> println("ERRO: Email e/ou senha incorretos. Solicite nova alteração.")
                }
            }
            "0" -> {
                print("OK: Operação finalizada.")
                continuar = false
            }

            else -> {
                println("ERRO: Opção inválida. Tente novamente.")
            }
        }
    }
}