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

    println("BEM-VINDO AO DENDÊ EVENTOS")

    // Loop do menu principal
    do {
        println("MENU PRINCIPAL")
        println("1. Cadastrar Usuário")
        println("2. Alterar Usuário")
        println("0. Sair")

        print("Digite: ")
        val opcaoMenu = readln()

        when (opcaoMenu) {
            "1" -> {
                println("\nNOVO USUÁRIO")

                // Variável e loop para cadastro e validação de e-mail
                var cadastroEmail: String

                do {
                    var emailRepetido = false
                    var emailInvalido = false

                    print("E-mail: ")
                    cadastroEmail = readln()

                    // Verifica se o e-mail contém um @ e um .
                    when {
                        !cadastroEmail.contains("@") || !cadastroEmail.contains(".") -> {
                            emailInvalido = true
                            println("ERRO: E-mail inválido. Tente novamente.")
                        }
                        cadastroEmail.contains("@") && cadastroEmail.contains(".") -> emailInvalido = false
                    }

                    // Verifica se o e-mail já existe
                    for (usuario in listaUsuarios) {
                        when {
                            usuario.email == cadastroEmail -> {
                                emailRepetido = true
                                println("ERRO: E-mail já cadastrado. Tente novamente.")
                            }
                            usuario.email != cadastroEmail -> emailRepetido = false
                        }
                    }
                } while (emailRepetido || emailInvalido)
                println("OK: E-MAIL DEFINIDO $cadastroEmail.")

                print("Nome: ")
                val cadastroNome = readln()

                // Variável e loop para cadastro e validação de data de nascimento
                var cadastroNascimento: String

                do {
                    var diaString = "00"
                    var mesString = "00"
                    var anoString = "0000"
                    var dataInvalida = true

                    print("Data de Nascimento (DD/MM/AAAA): ")
                    cadastroNascimento = readln()

                    // Verifica a data em três partes (para o DD/MM/AAAA)
                    val parteData = cadastroNascimento.split("/")
                    when (parteData.size) {
                        3 -> {
                            diaString = parteData[0]
                            mesString = parteData[1]
                            anoString = parteData[2]
                        }
                    }

                    // Variáveis convertendo os valores para int
                    val diaInt = diaString.toInt()
                    val mesInt = mesString.toInt()
                    val anoInt = anoString.toInt()

                    // Verifica se a data é válida
                    when {
                        diaInt in 1..31 && mesInt in 1..12 && anoInt in 1920..2020 -> dataInvalida = false
                        else -> println("ERRO: Data de Nascimento inválida. Tente novamente.")
                    }

                    // Define a data válida e segue o cadastro
                    cadastroNascimento = "$diaString/$mesString/$anoString"

                } while (dataInvalida)
                println("OK: DATA DE NASCIMENTO DEFINIDA $cadastroNascimento.")

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
            "2" -> {
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

                // Se o usuário não foi localizado, mostra mensagem de erro
                when {
                    usuarioEncontrado == null -> println("ERRO: Email e/ou senha incorretos. Solicite nova alteração.")
                }

                // Seleciona o usuário e executa um menu de alteração com base no tipo de usuário
                val tipo = usuarioEncontrado?.tipoUsuario
                var opcaoAlterar: String?
                do {
                    when (tipo) {
                        Tipo.COMUM -> {
                            println("MENU: Alterando ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                            println("OPÇÕES:")
                            println("[1] Nome [2] Data de nascimento [3] Sexo [4] Senha [0] Encerrar sessão")
                            print("Digite opção: ")
                            opcaoAlterar = readln()

                            when (opcaoAlterar) {
                                "1" -> {
                                    print("OK: Digite Nome Atualizado: ")
                                    usuarioEncontrado.nome = readln()
                                    println("OK: Nome atualizado para ${usuarioEncontrado.nome}.")
                                }

                                "2" -> {
                                    do {
                                        var diaString = "00"
                                        var mesString = "00"
                                        var anoString = "0000"
                                        var dataInvalida = true

                                        print("OK: Digite Data de Nascimento Atualizada (DD/MM/AAAA): ")
                                        val alterarNascimento = readln()
                                        val parteData = alterarNascimento.split("/")
                                        when (parteData.size) {
                                            3 -> {
                                                diaString = parteData[0]
                                                mesString = parteData[1]
                                                anoString = parteData[2]
                                            }
                                        }
                                        val diaInt = diaString.toInt()
                                        val mesInt = mesString.toInt()
                                        val anoInt = anoString.toInt()

                                        when {
                                            diaInt in 1..31 && mesInt in 1..12 && anoInt in 1920..2020 -> dataInvalida = false
                                            else -> println("ERRO: Data de Nascimento inválida. Tente novamente.")
                                        }

                                        usuarioEncontrado.dataNascimento = "$diaString/$mesString/$anoString"

                                    } while (dataInvalida)
                                    println("OK: DATA DE NASCIMENTO DEFINIDA ${usuarioEncontrado.dataNascimento}.")
                                }
                                "3" -> {
                                    println("OK: Selecione Sexo Atualizado: [1] MASCULINO, [2] FEMININO, [3] NÃO INFORMADO")
                                    val alterarSexo = readln()
                                    usuarioEncontrado.sexo = when (alterarSexo) {
                                        "1" -> Sexo.MASCULINO
                                        "2" -> Sexo.FEMININO
                                        else -> Sexo.NAO_INFORMADO
                                    }
                                    println("OK: Sexo atualizado para ${usuarioEncontrado.sexo}.")
                                }

                                "4" -> {
                                    do {
                                        print("OK: Digite Senha Atualizada: ")
                                        usuarioEncontrado.senha = readln()
                                        print("OK: Confirme Senha Atualizada: ")
                                        val confirmarSenha = readln()
                                        when {
                                            usuarioEncontrado.senha == confirmarSenha -> println("OK: Senha atualizada.")
                                            else -> println("ERRO: Senhas diferentes. Tente novamente.")
                                        }
                                    } while (usuarioEncontrado.senha != confirmarSenha)
                                }
                                "0" -> println("OK: Sessão cancelada.")
                                else -> println("ERRO: Opção inválida. Tente novamente.")
                            }
                        }

                        Tipo.ORGANIZADOR -> {
                            println("MENU: Alterando ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                            println("OPÇÕES:")
                            println("[1] Nome [2] Data de nascimento [3] Sexo [4] Senha \n[5] CNPJ [6] Razão Social [7] Nome Fantasia [0] Encerrar sessão")
                            print("Digite opção: ")
                            opcaoAlterar = readln()

                            when (opcaoAlterar) {
                                "1" -> {
                                    print("OK: Digite Nome Atualizado: ")
                                    usuarioEncontrado.nome = readln()
                                    println("OK: Nome atualizado para ${usuarioEncontrado.nome}.")
                                }

                                "2" -> {
                                    do {
                                        var diaString = "00"
                                        var mesString = "00"
                                        var anoString = "0000"
                                        var dataInvalida = true

                                        print("OK: Digite Data de Nascimento Atualizada (DD/MM/AAAA): ")
                                        val alterarNascimento = readln()
                                        val parteData = alterarNascimento.split("/")
                                        when (parteData.size) {
                                            3 -> {
                                                diaString = parteData[0]
                                                mesString = parteData[1]
                                                anoString = parteData[2]
                                            }
                                        }
                                        val diaInt = diaString.toInt()
                                        val mesInt = mesString.toInt()
                                        val anoInt = anoString.toInt()

                                        when {
                                            diaInt in 1..31 && mesInt in 1..12 && anoInt in 1920..2020 -> dataInvalida = false
                                            else -> println("ERRO: Data de Nascimento inválida. Tente novamente.")
                                        }

                                        usuarioEncontrado.dataNascimento = "$diaString/$mesString/$anoString"

                                    } while (dataInvalida)
                                    println("OK: DATA DE NASCIMENTO DEFINIDA ${usuarioEncontrado.dataNascimento}.")
                                }

                                "3" -> {
                                    println("OK: Selecione Sexo Atualizado: [1] MASCULINO, [2] FEMININO, [3] NÃO INFORMADO")
                                    val alterarSexo = readln()
                                    usuarioEncontrado.sexo = when (alterarSexo) {
                                        "1" -> Sexo.MASCULINO
                                        "2" -> Sexo.FEMININO
                                        else -> Sexo.NAO_INFORMADO
                                    }
                                    println("OK: Sexo atualizado para ${usuarioEncontrado.sexo}.")
                                }

                                "4" -> {
                                    do {
                                        print("OK: Digite Senha Atualizada: ")
                                        usuarioEncontrado.senha = readln()
                                        print("OK: Confirme Senha Atualizada: ")
                                        val confirmarSenha = readln()
                                        when {
                                            usuarioEncontrado.senha == confirmarSenha -> println("OK: Senha atualizada.")
                                            else -> println("ERRO: Senhas diferentes. Tente novamente.")
                                        }
                                    } while (usuarioEncontrado.senha != confirmarSenha)
                                }

                                "5" -> {
                                    print("OK: Digite CNPJ Atualizado: ")
                                    usuarioEncontrado.cnpj = readln()
                                    println("OK: CNPJ atualizado para ${usuarioEncontrado.cnpj}.")
                                }

                                "6" -> {
                                    print("OK: Digite Razão Social Atualizada: ")
                                    usuarioEncontrado.razaoSocial = readln()
                                    println("OK: Razão Social atualizada para ${usuarioEncontrado.razaoSocial}.")
                                }

                                "7" -> {
                                    print("OK: Digite Nome Fantasia Atualizado: ")
                                    usuarioEncontrado.nomeFantasia = readln()
                                    println("OK: Nome Fantasia atualizado para ${usuarioEncontrado.nomeFantasia}.")
                                }
                                "0" -> println("OK: Sessão encerrada.")
                                else -> println("ERRO: Opção inválida. Tente novamente.")
                            }
                        }

                        // Se o usuário não foi localizado, sai após a mensagem de erro
                        else -> opcaoAlterar = "0"
                    }
                } while (opcaoAlterar != "0")
            }
            "0" -> {
                print("OK: Operação finalizada.")
            }
            else -> {
                println("ERRO: Opção inválida. Tente novamente.")
            }
        }
    } while (opcaoMenu != "0")
}