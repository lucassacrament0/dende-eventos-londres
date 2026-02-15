enum class Sexo { MASCULINO, FEMININO, NAO_INFORMADO }
enum class Tipo { COMUM, ORGANIZADOR }

data class Usuario(
    var statusConta: Boolean,
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
    println("MENU: DEFINIR DATA DE HOJE")
    print("Digite Dia (DD): ")
    var diaHoje = readln().toInt()
    print("Digite Mês (MM): ")
    var mesHoje = readln().toInt()
    print("Digite Ano (AAAA): ")
    var anoHoje = readln().toInt()
    println("OK: DATA DEFINIDA $diaHoje/$mesHoje/$anoHoje.")

    // Loop do menu inicial
    do {
        println("MENU INICIAL ($diaHoje/$mesHoje/$anoHoje)")
        println("1. Ajustar Data")
        println("2. Cadastrar Usuário")
        println("3. Acessar Usuário")
        println("0. Sair")

        print("Digite opção: ")
        val opcaoInicio = readln()

        when (opcaoInicio) {
            "1" -> {
                println("\nAjustar a data de hoje ($diaHoje/$mesHoje/$anoHoje)? [1] SIM [2] NÃO")
                print("Digite opção: ")
                val ajustarData = readln()

                when (ajustarData) {
                    "1" -> {
                        println("\nAJUSTAR DATA ($diaHoje/$mesHoje/$anoHoje)")
                        print("Digite Dia Atualizado (DD): ")
                        diaHoje = readln().toInt()
                        print("Digite Mês Atualizado (MM): ")
                        mesHoje = readln().toInt()
                        print("Digite Ano Atualizado (AAAA): ")
                        anoHoje = readln().toInt()
                        println("OK: Data atualizada para $diaHoje/$mesHoje/$anoHoje.")
                    }
                    "2" -> println("OK: Operação cancelada.")
                    else -> println("ERRO: Opção inválida. Tente novamente.")
                }
            }
            "2" -> {
                println("\nCadastrar um usuário? [1] SIM [2] NÃO")
                print("Digite opção: ")
                val cadastrarUsuario = readln()

                when (cadastrarUsuario) {
                    "1" -> {
                        println("\nCADASTRAR USUÁRIO")

                        // Variável e loop para cadastro e validação de e-mail
                        var cadastroEmail: String

                        do {
                            var emailRepetido = false
                            var emailInvalido = false

                            // Loop para inserir e-mail (validar em duas etapas)
                            do{
                                var emailConfirmado = false
                                print("Digite E-mail: ")
                                cadastroEmail = readln().lowercase()
                                print("Confirme E-mail: ")
                                val confirmarEmail = readln().lowercase()
                                when {
                                    cadastroEmail == confirmarEmail -> emailConfirmado = true
                                    else -> println("ERRO: E-mails não conferem. Tente novamente.\n")
                                }
                            } while (!emailConfirmado)

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
                                }
                            }
                        } while (emailRepetido || emailInvalido)
                        println("OK: E-MAIL DEFINIDO $cadastroEmail.")

                        print("Digite Nome: ")
                        val cadastroNome = readln().uppercase()

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
                            val diaInt = diaString.toIntOrNull()
                            val mesInt = mesString.toIntOrNull()
                            val anoInt = anoString.toIntOrNull()

                            // Verifica se a data é válida
                            when {
                                diaInt in 1..31 && mesInt in 1..12 && anoInt in 1920..2020 -> dataInvalida = false
                                else -> println("ERRO: Data de Nascimento inválida. Tente novamente.")
                            }

                            // Define a data válida e segue o cadastro
                            cadastroNascimento = "$diaString/$mesString/$anoString"

                        } while (dataInvalida)
                        println("OK: DATA DE NASCIMENTO DEFINIDA $cadastroNascimento.")

                        // Variável e loop para inserir senha (validar com duas etapas)
                        var cadastroSenha: String

                        do {
                            print("Digite Senha: ")
                            cadastroSenha = readln()
                            print("Confirme Senha: ")
                            val confirmarSenha = readln()
                            when {
                                cadastroSenha == confirmarSenha -> println("OK: SENHA DEFINIDA.")
                                else -> println("ERRO: Senhas não conferem. Tente novamente.\n")
                            }
                        } while (cadastroSenha != confirmarSenha)

                        // Utiliza o enum para limitar as opções
                        println("Sexo: [1] MASCULINO, [2] FEMININO, [3] PREFIRO NÃO INFORMAR")
                        print("Digite opção: ")
                        val opcaoSexo = readln()

                        val cadastroSexo = when (opcaoSexo) {
                            "1" -> Sexo.MASCULINO
                            "2" -> Sexo.FEMININO
                            else -> Sexo.NAO_INFORMADO
                        }
                        println("OK: SEXO DEFINIDO $cadastroSexo.")

                        // Verifica se é usuário organizador
                        println("Você é organizador de eventos? [1] SIM, [2] NÃO")
                        print("Digite opção: ")
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
                                print("Digite opção: ")
                                cadastroEmpresa = readln()

                                when (cadastroEmpresa) {
                                    "1" -> {
                                        println("\nCADASTRO DE EMPRESA")
                                        print("Digite CNPJ: ")
                                        cadastroCNPJ = readln()

                                        print("Digite Razão Social: ")
                                        cadastroRazao = readln().uppercase()

                                        print("Digite Nome Fantasia: ")
                                        cadastroFantasia = readln().uppercase()
                                    }

                                    else -> println("OK: DEFINIDO USUÁRIO SEM EMPRESA.")
                                }
                            }
                        }
                        // Adicionando o usuario cadastrado
                        val cadastroUsuario = Usuario(
                            statusConta = true,
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

                    "2" -> println("\nOK: Operação cancelada.")
                    else -> println("ERRO: Opção inválida. Tente novamente.")
                }
            }
            "3" -> {
                // Cria variável associada com o data class Usuario
                var usuarioEncontrado: Usuario? = null

                println("\nAcessar um usuário? [1] SIM [2] NÃO")
                print("Digite opção: ")
                val acessarUsuario = readln()

                when (acessarUsuario) {
                    "1" -> {
                        println("\nACESSAR USUÁRIO")
                        print("Digite o e-mail da sua conta: ")
                        val buscarEmail = readln().lowercase()
                        print("Digite a senha da sua conta: ")
                        val buscarSenha = readln()

                        // Busca na lista de usuários
                        for (usuario in listaUsuarios) {
                            when {
                                usuario.email == buscarEmail && usuario.senha == buscarSenha -> usuarioEncontrado = usuario
                            }
                        }
                        // Se o usuário não foi localizado, mostra mensagem de erro
                        when {
                            usuarioEncontrado == null -> println("ERRO: Email e/ou senha incorretos. Tente novamente.")
                            else -> when {
                                !usuarioEncontrado.statusConta -> {
                                    println("AVISO: Esta é uma conta desativada. Reativar para acessar? [1] SIM [2] NÃO")
                                    print("Digite opção: ")
                                    val reativarConta = readln()
                                    when (reativarConta) {
                                        "1" -> {
                                            usuarioEncontrado.statusConta = true
                                            println("OK: Conta reativada.")
                                        }

                                        "2" -> println("OK: Operação cancelada.")
                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                    }
                                }

                                usuarioEncontrado.statusConta -> {
                                    do {
                                        println("MENU PRINCIPAL - ÁREA LOGADA ($diaHoje/$mesHoje/$anoHoje)")
                                        println("USUÁRIO: ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                                        println("[1] Alterar Usuário")
                                        println("[2] Visualizar Usuário")
                                        println("[0] Encerrar Sessão")
                                        print("Digite opção: ")
                                        var opcaoMenu = readln()

                                        when (opcaoMenu) {
                                            "1" -> {
                                                println("\nAlterar o usuário? [1] SIM [2] NÃO")
                                                print("Digite opção: ")
                                                val alterarUsuario = readln()

                                                when (alterarUsuario) {
                                                    "1" -> {
                                                        // Alterar dados de conta ativada
                                                        println("MENU: Alterando ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                                                        println("OPÇÕES:")
                                                        println("[0] Voltar\n[1] Desativar Conta [2] Nome [3] Data de Nascimento [4] Sexo [5] Senha")
                                                        when {
                                                            usuarioEncontrado.tipoUsuario == Tipo.ORGANIZADOR -> println(
                                                                "[6] CNPJ [7] Razão Social [8] Nome Fantasia"
                                                            )
                                                        }
                                                        print("Digite opção: ")
                                                        val opcaoAlterar = readln()

                                                        when (opcaoAlterar) {
                                                            "0" -> println("OK: Selecionado Voltar.")
                                                            "1" -> {
                                                                println("Desativar a conta? [1] SIM [2] NÃO: ")
                                                                print("Digite opção: ")
                                                                val desativarConta = readln()
                                                                when (desativarConta) {
                                                                    "1" -> {
                                                                        usuarioEncontrado.statusConta = false
                                                                        println("OK: Conta desativada (${usuarioEncontrado.email}).")
                                                                        println("OK: Usuário desconectado.")
                                                                        opcaoMenu = "0"
                                                                    }

                                                                    else -> println("ERRO: Opção inválida. Tente novamente.")
                                                                }
                                                            }

                                                            "2" -> {
                                                                print("OK: Digite Nome atualizado: ")
                                                                usuarioEncontrado.nome = readln().uppercase()
                                                                println("OK: Nome atualizado para ${usuarioEncontrado.nome}.")
                                                            }

                                                            "3" -> {
                                                                do {
                                                                    var diaString = "00"
                                                                    var mesString = "00"
                                                                    var anoString = "0000"
                                                                    var dataInvalida = true

                                                                    print("OK: Digite Data de Nascimento atualizada (DD/MM/AAAA): ")
                                                                    val alterarNascimento = readln()
                                                                    val parteData = alterarNascimento.split("/")
                                                                    when (parteData.size) {
                                                                        3 -> {
                                                                            diaString = parteData[0]
                                                                            mesString = parteData[1]
                                                                            anoString = parteData[2]
                                                                        }
                                                                    }
                                                                    val diaInt = diaString.toIntOrNull()
                                                                    val mesInt = mesString.toIntOrNull()
                                                                    val anoInt = anoString.toIntOrNull()

                                                                    when {
                                                                        diaInt in 1..31 && mesInt in 1..12 && anoInt in 1920..2020 -> dataInvalida = false
                                                                        else -> println("ERRO: Data de Nascimento inválida. Tente novamente.")
                                                                    }
                                                                    usuarioEncontrado.dataNascimento = "$diaString/$mesString/$anoString"
                                                                } while (dataInvalida)
                                                                println("OK: DATA DE NASCIMENTO DEFINIDA ${usuarioEncontrado.dataNascimento}.")
                                                            }

                                                            "4" -> {
                                                                println("OK: Selecione Sexo atualizado: [1] MASCULINO, [2] FEMININO, [3] NÃO INFORMADO")
                                                                val alterarSexo = readln()
                                                                usuarioEncontrado.sexo =
                                                                    when (alterarSexo) {
                                                                        "1" -> Sexo.MASCULINO
                                                                        "2" -> Sexo.FEMININO
                                                                        else -> Sexo.NAO_INFORMADO
                                                                    }
                                                                println("OK: Sexo atualizado para ${usuarioEncontrado.sexo}.")
                                                            }

                                                            "5" -> {
                                                                do {
                                                                    print("OK: Digite Senha atualizada: ")
                                                                    usuarioEncontrado.senha = readln()
                                                                    print("OK: Confirme Senha atualizada: ")
                                                                    val confirmarSenha = readln()
                                                                    when {
                                                                        usuarioEncontrado.senha == confirmarSenha -> println("OK: Senha atualizada.")
                                                                        else -> println("ERRO: Senhas não conferem. Tente novamente.")
                                                                    }
                                                                } while (usuarioEncontrado.senha != confirmarSenha)
                                                            }

                                                            "6" -> {
                                                                when {
                                                                    usuarioEncontrado.tipoUsuario == Tipo.ORGANIZADOR -> {
                                                                        print("OK: Digite CNPJ atualizado: ")
                                                                        usuarioEncontrado.cnpj = readln()
                                                                        println("OK: CNPJ atualizado para ${usuarioEncontrado.cnpj}.")
                                                                    }

                                                                    else -> println("ERRO: Opção inválida. Tente novamente.")
                                                                }
                                                            }

                                                            "7" -> {
                                                                when {
                                                                    usuarioEncontrado.tipoUsuario == Tipo.ORGANIZADOR -> {
                                                                        print("OK: Digite Razão Social atualizada: ")
                                                                        usuarioEncontrado.razaoSocial = readln().uppercase()
                                                                        println("OK: Razão Social atualizada para ${usuarioEncontrado.razaoSocial}.")
                                                                    }

                                                                    else -> println("ERRO: Opção inválida. Tente novamente.")
                                                                }
                                                            }

                                                            "8" -> {
                                                                when {
                                                                    usuarioEncontrado.tipoUsuario == Tipo.ORGANIZADOR -> {
                                                                        print("OK: Digite Nome Fantasia Atualizado: ")
                                                                        usuarioEncontrado.nomeFantasia = readln().uppercase()
                                                                        println("OK: Nome Fantasia atualizado para ${usuarioEncontrado.nomeFantasia}.")
                                                                    }

                                                                    else -> println("ERRO: Opção inválida. Tente novamente.")
                                                                }
                                                            }

                                                            else -> println("ERRO: Opção inválida. Tente novamente.")
                                                        }
                                                    }
                                                }
                                            }

                                            "2" -> {
                                                println("\nVisualizar o usuário? [1] SIM [2] NÃO")
                                                print("Digite opção: ")
                                                val visualizarUsuario = readln()

                                                when (visualizarUsuario) {
                                                    "1" -> {
                                                        var diaInt = 0
                                                        var mesInt = 0
                                                        var anoInt = 0
                                                        val parteData = usuarioEncontrado.dataNascimento.split("/")
                                                        when (parteData.size) {
                                                            3 -> {
                                                                diaInt = parteData[0].toInt()
                                                                mesInt = parteData[1].toInt()
                                                                anoInt = parteData[2].toInt()
                                                            }
                                                        }
                                                        // Cálculo da idade com base no dia de hoje (informado) e nascimento cadastrado
                                                        var idadeDia = diaHoje - diaInt
                                                        var idadeMes = mesHoje - mesInt
                                                        var idadeAno = anoHoje - anoInt

                                                        // Aproximar a idade corretamente caso uma subtração resulte em Int negativo
                                                        when {
                                                            idadeDia < 0 -> {
                                                                idadeMes -= 1
                                                                idadeDia += 30
                                                            }
                                                        }
                                                        when {
                                                            idadeMes < 0 -> {
                                                                idadeAno -= 1
                                                                idadeMes += 12
                                                            }
                                                        }

                                                        // Mostrar dados ao usuário comum
                                                        println("\nSEU USUÁRIO:")
                                                        println("Nome: ${usuarioEncontrado.nome}")
                                                        println("E-mail: ${usuarioEncontrado.email}")
                                                        println("Data de Nascimento: ${usuarioEncontrado.dataNascimento}\n≈ $idadeAno anos, $idadeMes meses e $idadeDia dias")
                                                        println("Sexo: ${usuarioEncontrado.sexo}")
                                                        when {
                                                            usuarioEncontrado.tipoUsuario == Tipo.ORGANIZADOR -> {
                                                                println("EMPRESA")
                                                                when {
                                                                    usuarioEncontrado.cnpj != null -> println("CNPJ: ${usuarioEncontrado.cnpj}")
                                                                    else -> println("CNPJ não cadastrado.")
                                                                }
                                                                when {
                                                                    usuarioEncontrado.razaoSocial != null -> println("Razão Social: ${usuarioEncontrado.razaoSocial}")
                                                                    else -> println("Razão Social não cadastrada.")
                                                                }
                                                                when {
                                                                    usuarioEncontrado.nomeFantasia != null -> println("Nome Fantasia: ${usuarioEncontrado.nomeFantasia}")
                                                                    else -> println("Nome Fantasia não cadastrado.")
                                                                }
                                                            }
                                                        }
                                                        print("[QUALQUER TECLA] Encerrar Sessão\n")
                                                        readln()
                                                    }

                                                    "2" -> println("OK: Operação cancelada.")
                                                    else -> println("ERRO: Opção inválida. Tente novamente.")
                                                }
                                            }
                                        }
                                    } while (opcaoMenu != "0")
                                }
                            }
                        }
                    }

                    "2" -> println("\nOK: Operação cancelada.")
                    else -> println("ERRO: Opção inválida. Tente novamente.")
                }
            }
            "0" -> {
                print("OK: Operação finalizada.")
            }
            else -> {
                println("ERRO: Opção inválida. Tente novamente.")
            }
        }
    } while (opcaoInicio != "0")
}