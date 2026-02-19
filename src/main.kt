enum class SexoUsuario { MASCULINO, FEMININO, NAO_INFORMADO }
enum class TipoUsuario { COMUM, ORGANIZADOR }

data class Usuario(
    var statusConta: Boolean,
    var nome: String,
    var dataNascimento: String,
    var sexo: SexoUsuario,
    val email: String,
    var senha: String,
    val tipoUsuario: TipoUsuario,
    var cnpj: String? = null,
    var razaoSocial: String? = null,
    var nomeFantasia: String? = null
)

enum class TipoEvento {
    SOCIAL, CORPORATIVO, ACADEMICO, CULTURAL_ENTRETENIMENTO, RELIGIOSO, ESPORTIVO,
    FEIRA, CONGRESSO, OFICINA, CURSO, TREINAMENTO, AULA, SEMINARIO, PALESTRA, SHOW,
    FESTIVAL, EXPOSICAO, RETIRO, CULTO, CELEBRACAO, CAMPEONATO, CORRIDA, OUTRO
}

enum class ModalidadeEvento { PRESENCIAL, REMOTO, HIBRIDO }

data class Evento(
    val id: Int,
    val organizadorEmail: String,
    var pagina: String,
    var nome: String,
    var descricao: String,
    var diaInicio: Int, var mesInicio: Int, var anoInicio: Int,
    var horaInicio: Int, var minutoInicio: Int,
    var diaTermino: Int, var mesTermino: Int, var anoTermino: Int,
    var horaTermino: Int, var minutoTermino: Int,
    var tipo: TipoEvento,
    var idEventoPrincipal: Int?,
    var modalidade: ModalidadeEvento,
    var capacidadeMax: Int,
    var local: String,
    var statusEvento: Boolean,
    var precoIngresso: Double,
    var aceitaEstorno: Boolean,
    var taxaEstorno: Double = 0.0
)

data class Ingresso(
    val id: Int,
    val idEvento: Int,
    val emailUsuario: String,
    var statusDisponibilidade: Boolean,
    val valorPago: Double
)

fun main() {

    // Configurando memória local
    val listaUsuarios = mutableListOf<Usuario>()
    val listaEventos = mutableListOf<Evento>()
    val listaIngressos = mutableListOf<Ingresso>()
    var dataValida: Boolean
    var diaHoje: Int
    var mesHoje: Int
    var anoHoje: Int
    var dataHoje: Int

    println("BEM-VINDO AO DENDÊ EVENTOS")

    // Loop de definir e validar data
    do {
        println("MENU: DEFINIR DATA DE HOJE")
        print("Digite Somente Dia (DD): ")
        diaHoje = readln().toIntOrNull() ?: 0
        print("Digite Somente Mês (MM): ")
        mesHoje = readln().toIntOrNull() ?: 0
        print("Digite Somente Ano (AAAA): ")
        anoHoje = readln().toIntOrNull() ?: 0

        dataValida = false
        when {
            diaHoje in 1..31 && mesHoje in 1..12 && anoHoje >= 2026 -> dataValida = true
            else -> println("ERRO: Data inválida. Tente novamente.")
        }
    } while (!dataValida)
    println("OK: DATA DEFINIDA $diaHoje/$mesHoje/$anoHoje.\n")
    dataHoje = (anoHoje * 10000) + (mesHoje * 100) + diaHoje

    // Loop do menu inicial
    do {
        println("MENU INICIAL ($diaHoje/$mesHoje/$anoHoje)")
        println("1. Cadastrar Usuário")
        println("2. Acessar Usuário")
        println("3. Ajustar Data")
        println("0. Sair")

        print("Digite opção: ")
        val opcaoInicio = readln()

        // Opções do menu
        when (opcaoInicio) {
            "1" -> {
                println("\nCADASTRAR USUÁRIO")

                // Variável e loop para cadastro e validação de e-mail
                var cadastroEmail: String

                do {
                    var emailRepetido = false
                    var emailInvalido = false

                    // Loop para inserir e-mail (validar em duas etapas)
                    do {
                        var emailConfirmado = false
                        print("Digite E-mail: ")
                        cadastroEmail = readln().lowercase()
                        print("Confirme E-mail: ")
                        val confirmarEmail = readln().lowercase()

                        // Verifica se os e-mails inseridos conferem
                        when {
                            cadastroEmail == confirmarEmail -> emailConfirmado = true
                            else -> println("ERRO: E-mails não conferem. Tente novamente.\n")
                        }
                    } while (!emailConfirmado)

                    // Verifica se o e-mail contém um @ e um .
                    when {
                        !cadastroEmail.contains("@") || !cadastroEmail.contains(".") -> {
                            emailInvalido = true
                            println("ERRO: E-mail inválido. Tente novamente.\n")
                        }

                        cadastroEmail.contains("@") && cadastroEmail.contains(".") -> emailInvalido = false
                    }

                    // Verifica se o e-mail já existe
                    for (usuario in listaUsuarios) {
                        when {
                            usuario.email == cadastroEmail -> {
                                emailRepetido = true
                                println("ERRO: E-mail já cadastrado. Tente novamente.\n")
                            }
                        }
                    }
                } while (emailRepetido || emailInvalido)
                println("OK: E-MAIL DEFINIDO '$cadastroEmail'.\n")

                print("Digite Nome: ")
                val cadastroNome = readln().uppercase()

                // Variável e loop para cadastro e validação de data de nascimento
                var cadastroNascimento = "0"

                do {
                    println("\nMENU: DEFINIR DATA DE NASCIMENTO")
                    print("Digite Somente Dia de Nascimento (DD): ")
                    val diaNascimento = readln().toIntOrNull() ?: 0
                    print("Digite Somente Mês de Nascimento (MM): ")
                    val mesNascimento = readln().toIntOrNull() ?: 0
                    print("Digite Somente Ano de Nascimento (AAAA): ")
                    val anoNascimento = readln().toIntOrNull() ?: 0

                    dataValida = false
                    when {
                        diaNascimento in 1..31 && mesNascimento in 1..12 && anoNascimento in 1920..2020 -> {
                            dataValida = true
                            cadastroNascimento = "$diaNascimento/$mesNascimento/$anoNascimento"
                        }

                        else -> println("ERRO: Data inválida. Tente novamente.")
                    }
                } while (!dataValida)
                println("OK: DATA DE NASCIMENTO DEFINIDA $cadastroNascimento.\n")

                // Variável e loop para inserir senha (validar com duas etapas)
                var cadastroSenha: String

                do {
                    print("Digite Nova Senha: ")
                    cadastroSenha = readln()
                    print("Confirme Nova Senha: ")
                    val confirmarSenha = readln()
                    when {
                        cadastroSenha.length < 8 -> println("ERRO: A senha deve ter no mínimo 8 caracteres.\n")
                        cadastroSenha != confirmarSenha -> println("ERRO: Senhas não conferem. Tente novamente.\n")
                        else -> println("OK: SENHA DEFINIDA.\n")
                    }
                } while (cadastroSenha.length < 8 || cadastroSenha != confirmarSenha)

                // Define o sexo (Utiliza o enum para limitar as opções)
                println("Sexo: [1] MASCULINO, [2] FEMININO, [3] PREFIRO NÃO INFORMAR")
                print("Digite opção: ")
                val opcaoSexo = readln()

                val cadastroSexo = when (opcaoSexo) {
                    "1" -> SexoUsuario.MASCULINO
                    "2" -> SexoUsuario.FEMININO
                    else -> SexoUsuario.NAO_INFORMADO
                }
                println("OK: SEXO DEFINIDO $cadastroSexo.\n")

                // Verifica se é usuário organizador
                println("Você é organizador de eventos? [1] SIM, [2] NÃO")
                print("Digite opção: ")
                val tipoUsuario = readln()

                val cadastroOrganizador = when (tipoUsuario) {
                    "1" -> TipoUsuario.ORGANIZADOR
                    else -> TipoUsuario.COMUM
                }
                println("OK: DEFINIDO USUÁRIO $cadastroOrganizador.\n")

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

                                // Variável e loop para validar o CNPJ
                                var cnpjValido = false
                                do {
                                    print("Digite CNPJ (14 dígitos): ")
                                    cadastroCNPJ = readln()

                                    when {
                                        cadastroCNPJ.length == 14 -> cnpjValido = true
                                        else -> println("ERRO: CNPJ inválido. Tente novamente.\n")
                                    }
                                } while (!cnpjValido)
                                println("OK: CNPJ DEFINIDO '$cadastroCNPJ'.\n")

                                print("Digite Razão Social: ")
                                cadastroRazao = readln().uppercase()

                                print("Digite Nome Fantasia: ")
                                cadastroFantasia = readln().uppercase()
                            }

                            else -> println("OK: DEFINIDO USUÁRIO SEM EMPRESA.\n")
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

            "2" -> {

                // Cria variável associada com o data class Usuario
                var usuarioEncontrado: Usuario? = null

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
                    usuarioEncontrado == null -> println("ERRO: E-mail e/ou senha incorretos. Solicite novamente.\n")
                    else -> when {
                        !usuarioEncontrado.statusConta -> {
                            println("\nAVISO: Esta é uma conta desativada. Reativar para acessar? [1] SIM [2] NÃO")
                            print("Digite opção: ")
                            val reativarConta = readln()
                            when (reativarConta) {
                                "1" -> {
                                    usuarioEncontrado.statusConta = true
                                    println("OK: Conta reativada. Acesse novamente.")
                                }

                                "2" -> println("OK: Operação cancelada.")
                                else -> println("ERRO: Opção inválida. Solicite novamente.")
                            }
                        }

                        // Se a conta estiver ativa...
                        usuarioEncontrado.statusConta -> {
                            println("OK: Acesso bem-sucedido.\n")
                            do {
                                println("MENU PRINCIPAL - ÁREA LOGADA ($diaHoje/$mesHoje/$anoHoje)")
                                println("USUÁRIO: ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                                println("[1] Alterar Usuário [2] Visualizar Usuário [3] Desativar Usuário")

                                // Condicional para tornar menu dinâmico com base no tipo de usuário
                                when {
                                    usuarioEncontrado.tipoUsuario == TipoUsuario.COMUM -> {
                                        println("[4] Feed de Eventos [5] Visualizar Ingressos")
                                    }

                                    usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                        println("[6] Cadastrar Evento [7] Visualizar Eventos [8] Alterar Evento [9] Desativar Evento")
                                    }
                                }
                                println("[0] Encerrar Sessão")
                                println("Digite opção: ")
                                var opcaoMenuLogado = readln()

                                // Opções do menu logado
                                when (opcaoMenuLogado) {
                                    "1" -> {
                                        var menuAlterarUsuario = true

                                        // Alterar dados de conta ativa
                                        do {
                                            println("MENU: Alterando ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                                            println("OPÇÕES:")
                                            println("[0] Voltar\n[1] Nome [2] Data de Nascimento [3] Sexo [4] Senha")
                                            when {
                                                usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> println(
                                                    "[5] CNPJ [6] Razão Social [7] Nome Fantasia"
                                                )
                                            }
                                            println("Digite opção: ")
                                            val opcaoAlterarUsuario = readln()

                                            when (opcaoAlterarUsuario) {
                                                "0" -> {
                                                    println("OK: Selecionado Voltar.\n")
                                                    menuAlterarUsuario = false
                                                }

                                                "1" -> {
                                                    print("OK: Digite Nome atualizado: ")
                                                    usuarioEncontrado.nome = readln().uppercase()
                                                    println("OK: NOME DEFINIDO '${usuarioEncontrado.nome}'.\n")
                                                }

                                                "2" -> {

                                                    // Loop e validação de data de nascimento
                                                    do {
                                                        println("MENU: ALTERAR DATA DE NASCIMENTO")
                                                        print("Digite Somente Dia de Nascimento (DD): ")
                                                        val diaNascimento = readln().toIntOrNull() ?: 0
                                                        print("Digite Somente Mês de Nascimento (MM): ")
                                                        val mesNascimento = readln().toIntOrNull() ?: 0
                                                        print("Digite Somente Ano de Nascimento (AAAA): ")
                                                        val anoNascimento = readln().toIntOrNull() ?: 0

                                                        dataValida = false
                                                        when {
                                                            diaNascimento in 1..31 && mesNascimento in 1..12 && anoNascimento in 1920..2020 -> {
                                                                dataValida = true
                                                                usuarioEncontrado.dataNascimento = "$diaNascimento/$mesNascimento/$anoNascimento"
                                                            }

                                                            else -> println("ERRO: Data inválida. Tente novamente.")
                                                        }
                                                    } while (!dataValida)
                                                    println("OK: DATA DE NASCIMENTO DEFINIDA '${usuarioEncontrado.dataNascimento}'.")
                                                }

                                                "3" -> {
                                                    println("ALTERANDO: Sexo [1] MASCULINO, [2] FEMININO, [3] NÃO INFORMADO")
                                                    val alterarSexo = readln()
                                                    usuarioEncontrado.sexo =
                                                        when (alterarSexo) {
                                                            "1" -> SexoUsuario.MASCULINO
                                                            "2" -> SexoUsuario.FEMININO
                                                            else -> SexoUsuario.NAO_INFORMADO
                                                        }
                                                    println("OK: SEXO DEFINIDO ${usuarioEncontrado.sexo}.\n")
                                                }

                                                "4" -> {

                                                    // Variável e loop para inserir senha (validar com duas etapas)
                                                    var cadastroSenha: String
                                                    do {
                                                        print("OK: Digite Senha atualizada: ")
                                                        cadastroSenha = readln()
                                                        print("Confirme Senha atualizada: ")
                                                        val confirmarSenha = readln()
                                                        when {
                                                            cadastroSenha.length < 8 -> println("ERRO: A senha deve ter no mínimo 8 caracteres.\n")
                                                            cadastroSenha != confirmarSenha -> println("ERRO: Senhas não conferem. Tente novamente.")
                                                            else -> println("OK: SENHA ATUALIZADA.\n")
                                                        }
                                                    } while (cadastroSenha.length < 8 || cadastroSenha != confirmarSenha)
                                                    usuarioEncontrado.senha = cadastroSenha
                                                }

                                                "5" -> {

                                                    // Validação de usuário (organizador) e loop para validar CNPJ
                                                    when {
                                                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                            var cnpjValido = false
                                                            var alterarCNPJ: String
                                                            do {
                                                                print("Digite CNPJ atualizado (14 dígitos): ")
                                                                alterarCNPJ = readln()

                                                                when {
                                                                    alterarCNPJ.length == 14 -> cnpjValido = true
                                                                    else -> println("ERRO: CNPJ inválido. Tente novamente.\n")
                                                                }
                                                            } while (!cnpjValido)

                                                            usuarioEncontrado.cnpj = alterarCNPJ
                                                            println("OK: CNPJ DEFINIDO '${usuarioEncontrado.cnpj}'.\n")
                                                        }

                                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                                    }
                                                }

                                                "6" -> {
                                                    when {
                                                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                            print("OK: Digite Razão Social atualizada: ")
                                                            usuarioEncontrado.razaoSocial = readln().uppercase()
                                                            println("OK: RAZÃO SOCIAL DEFINIDA '${usuarioEncontrado.razaoSocial}'.\n")
                                                        }

                                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                                    }
                                                }

                                                "7" -> {
                                                    when {
                                                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                            print("OK: Digite Nome Fantasia Atualizado: ")
                                                            usuarioEncontrado.nomeFantasia = readln().uppercase()
                                                            println("OK: NOME FANTASIA DEFINIDO '${usuarioEncontrado.nomeFantasia}'.\n")
                                                        }

                                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                                    }
                                                }

                                                else -> println("ERRO: Opção inválida. Tente novamente.")
                                            }
                                        } while (menuAlterarUsuario)
                                    }

                                    "2" -> {

                                        // Variáveis e condicional para dividir a data de nascimento (String) em 3 partes
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

                                        // Cálculo da idade com base na data de hoje (informada) e nascimento cadastrado
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

                                        // Mostrar dados ao usuário
                                        println("\nSEU USUÁRIO:")
                                        println("Nome: ${usuarioEncontrado.nome}")
                                        println("E-mail: ${usuarioEncontrado.email}")
                                        println("Data de Nascimento: ${usuarioEncontrado.dataNascimento}")
                                        println("≈ $idadeAno anos, $idadeMes meses e $idadeDia dias")
                                        println("Sexo: ${usuarioEncontrado.sexo}")

                                        // Dados específicos para organizadores
                                        when {
                                            usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                println("EMPRESA")
                                                when {
                                                    usuarioEncontrado.cnpj != null ->
                                                        println("CNPJ: ${usuarioEncontrado.cnpj}")

                                                    else -> println("CNPJ não cadastrado.")
                                                }
                                                when {
                                                    usuarioEncontrado.razaoSocial != null ->
                                                        println("Razão Social: ${usuarioEncontrado.razaoSocial}")

                                                    else -> println("Razão Social não cadastrada.")
                                                }
                                                when {
                                                    usuarioEncontrado.nomeFantasia != null ->
                                                        println("Nome Fantasia: ${usuarioEncontrado.nomeFantasia}")

                                                    else -> println("Nome Fantasia não cadastrado.")
                                                }
                                            }
                                        }
                                        print("[QUALQUER TECLA] Voltar\n")
                                        readln()
                                    }

                                    "3" -> {
                                        println("Desativar a conta? [1] SIM [2] NÃO: ")
                                        print("Digite opção: ")
                                        val desativarConta = readln()

                                        when (desativarConta) {
                                            "1" -> {

                                                // Verifica se é usuário organizador e busca por eventos ativados
                                                var possuiEventos = false
                                                when (usuarioEncontrado.tipoUsuario) {
                                                    TipoUsuario.ORGANIZADOR -> {
                                                        for (evento in listaEventos) {
                                                            when {
                                                                evento.organizadorEmail == usuarioEncontrado.email && evento.statusEvento ->
                                                                    possuiEventos = true
                                                            }
                                                        }
                                                    }

                                                    else -> possuiEventos = false
                                                }

                                                // Caso haja eventos ativos para o organizador, não desativa a conta
                                                when (possuiEventos) {
                                                    true -> println("ERRO: Você tem eventos ativos. Desativação não permitida.")
                                                    false -> {
                                                        usuarioEncontrado.statusConta = false
                                                        println("\nOK: Conta desativada (${usuarioEncontrado.email}).")
                                                        println("OK: Usuário desconectado.\n")
                                                        opcaoMenuLogado = "0"
                                                    }
                                                }
                                            }

                                            else -> println("ERRO: Opção inválida. Solicite novamente.")
                                        }
                                    }

                                    "4" -> {
                                        when (usuarioEncontrado.tipoUsuario) {
                                            TipoUsuario.COMUM -> {
                                                println("\nFEED DE EVENTOS")

                                                // Lista (temporária) de eventos disponíveis
                                                val eventosDisponiveis = mutableListOf<Evento>()

                                                for (evento in listaEventos) {

                                                    // Condensa dia/mes/ano do evento em uma data completa (como dataHoje)
                                                    val dataEvento = (evento.anoInicio * 10000) + (evento.mesInicio * 100) + evento.diaInicio

                                                    // Busca por eventos lotados para não exibir na lista
                                                    var ingressosVendidos = 0
                                                    for (ingresso in listaIngressos) {
                                                        when {
                                                            ingresso.idEvento == evento.id && !ingresso.statusDisponibilidade -> ingressosVendidos++
                                                        }
                                                    }
                                                    when {
                                                        evento.statusEvento && dataEvento >= dataHoje && ingressosVendidos < evento.capacidadeMax -> eventosDisponiveis.add(evento)
                                                    }
                                                }

                                                // Ordenação da lista por data e nome
                                                eventosDisponiveis.sortWith(
                                                    compareBy(
                                                        { it.anoInicio }, { it.mesInicio }, { it.diaInicio }, { it.horaInicio }, { it.nome }
                                                    ))

                                                println("Eventos disponíveis:")
                                                println("\nDados em ordem:")
                                                println("ID | NOME | DATA | LOCAL | PREÇO | INGRESSOS")

                                                // Define eventos com ingressos disponíveis para exibir em lista
                                                var existemEventos = false
                                                for (evento in eventosDisponiveis) {
                                                    var ingressosVendidos = 0
                                                    for (ingresso in listaIngressos) {
                                                        when {
                                                            ingresso.idEvento == evento.id && !ingresso.statusDisponibilidade -> ingressosVendidos++
                                                        }
                                                    }

                                                    // Calcula a quantidade de ingressos disponíveis
                                                    val ingressosDisponiveis = evento.capacidadeMax - ingressosVendidos
                                                    println(
                                                        "${evento.id} | ${evento.nome} | ${evento.diaInicio}/${evento.mesInicio}/${evento.anoInicio} | " +
                                                                "${evento.local} | R$ ${evento.precoIngresso} | $ingressosDisponiveis"
                                                    )
                                                    existemEventos = true
                                                }

                                                // Se eventos existirem na lista, possibilita expandir um evento
                                                when (existemEventos) {
                                                    false -> println("AVISO: Nenhum evento encontrado.")
                                                    true -> {
                                                        print("\nDigite ID do evento para expandir/comprar ingresso (ou [0] Voltar): ")
                                                        val opcaoID = readln().toIntOrNull() ?: 0

                                                        when (opcaoID) {
                                                            0 -> println("OK: Selecionado Voltar.")
                                                            else -> {
                                                                var eventoDetalhes: Evento? = null

                                                                // Busca pelo ID inserido para expandir evento
                                                                for (evento in listaEventos) {
                                                                    when {
                                                                        evento.id == opcaoID && evento.statusEvento -> eventoDetalhes = evento
                                                                    }
                                                                }

                                                                // Com base no ID inserido, exibe detalhes do evento ou não
                                                                when (eventoDetalhes) {
                                                                    null -> println("ERRO: Nenhum evento encontrado.")
                                                                    else -> {
                                                                        println("Nome: ${eventoDetalhes.nome}")
                                                                        println("Descrição: ${eventoDetalhes.descricao}")
                                                                        println("Página: ${eventoDetalhes.pagina}")
                                                                        println(
                                                                            "Início: ${eventoDetalhes.diaInicio}/${eventoDetalhes.mesInicio}/${eventoDetalhes.anoInicio} " +
                                                                                    "às ${eventoDetalhes.horaInicio}:${eventoDetalhes.minutoInicio}"
                                                                        )
                                                                        println(
                                                                            "Término: ${eventoDetalhes.diaTermino}/${eventoDetalhes.mesTermino}/${eventoDetalhes.anoTermino} " +
                                                                                    "às ${eventoDetalhes.horaTermino}:${eventoDetalhes.minutoTermino}"
                                                                        )
                                                                        println("Tipo: ${eventoDetalhes.tipo}")
                                                                        println("Modalidade: ${eventoDetalhes.modalidade}")
                                                                        println("Local: ${eventoDetalhes.local}")
                                                                        println("Capacidade Máxima: ${eventoDetalhes.capacidadeMax}")
                                                                        println("Preço do Ingresso: R$ ${eventoDetalhes.precoIngresso}")

                                                                        // Substitui exibição "true" ou "false" por "Sim" ou "Não"
                                                                        val textoEstorno = when (eventoDetalhes.aceitaEstorno) {
                                                                            true -> "Sim (Taxa: ${eventoDetalhes.taxaEstorno}%)"
                                                                            false -> "Não"
                                                                        }
                                                                        println("Aceita Estorno: $textoEstorno")

                                                                        when (eventoDetalhes.idEventoPrincipal) {
                                                                            null -> println("Evento Independente.")
                                                                            else -> println("Evento Principal ID ${eventoDetalhes.idEventoPrincipal}")
                                                                        }
                                                                        println("Evento Atual ID ${eventoDetalhes.id}")

                                                                        // Mostra opção de comprar ingresso do evento exibido e opção voltar
                                                                        println("[1] Comprar Ingresso  [0] Voltar")
                                                                        print("Digite opção: ")
                                                                        val opcaoCompra = readln()

                                                                        when (opcaoCompra) {
                                                                            "1" -> {

                                                                                // Confirma a compra em caso de evento sem atribuição a outro
                                                                                when (eventoDetalhes.idEventoPrincipal) {
                                                                                    null -> {
                                                                                        println("\nCOMPRAR INGRESSO")
                                                                                        println("\nEvento: ${eventoDetalhes.nome}")
                                                                                        println("Preço Total: R$ ${eventoDetalhes.precoIngresso}")
                                                                                        println("\n[1] Confirmar Compra  [0] Cancelar")
                                                                                        print("Digite opção: ")
                                                                                        val confirmar = readln()

                                                                                        // Cria o ingresso, adicionando a data class Ingresso
                                                                                        when (confirmar) {
                                                                                            "1" -> {
                                                                                                val novoIngresso = Ingresso(
                                                                                                    id = listaIngressos.size + 1,
                                                                                                    idEvento = eventoDetalhes.id,
                                                                                                    emailUsuario = usuarioEncontrado.email,
                                                                                                    statusDisponibilidade = false,
                                                                                                    valorPago = eventoDetalhes.precoIngresso
                                                                                                )
                                                                                                listaIngressos.add(novoIngresso)
                                                                                                println("OK: Ingresso comprado. (ID: ${novoIngresso.id})\n")
                                                                                            }

                                                                                            "0" -> println("OK: Compra cancelada.")
                                                                                            else -> println("ERRO: Opção inválida.")
                                                                                        }
                                                                                    }

                                                                                    // Valida o ID de evento digitado
                                                                                    else -> {
                                                                                        var eventoPrincipal: Evento? = null
                                                                                        for (evento in listaEventos) {
                                                                                            when {
                                                                                                evento.id == eventoDetalhes.idEventoPrincipal && evento.statusEvento -> eventoPrincipal = evento
                                                                                            }
                                                                                        }

                                                                                        when (eventoPrincipal) {
                                                                                            null -> println("ERRO: Evento inexistente ou indisponível.\n")

                                                                                            // Calcula se o evento principal tem ingressos disponíveis
                                                                                            else -> {
                                                                                                var vendidosPrincipal = 0
                                                                                                for (ingresso in listaIngressos) {
                                                                                                    when {
                                                                                                        ingresso.idEvento == eventoPrincipal.id && !ingresso.statusDisponibilidade -> vendidosPrincipal++
                                                                                                    }
                                                                                                }
                                                                                                val ingressosPrincipal = eventoPrincipal.capacidadeMax - vendidosPrincipal

                                                                                                when {
                                                                                                    ingressosPrincipal <= 0 -> println("ERRO: Evento Principal '${eventoPrincipal.nome}' indisponível.")

                                                                                                    // Confirma a compra em caso de sub-evento com atribuição a evento principal
                                                                                                    else -> {
                                                                                                        val ingressosSomados = eventoDetalhes.precoIngresso + eventoPrincipal.precoIngresso
                                                                                                        println("\nAVISO: Este evento exige compra dupla.")
                                                                                                        println("- Sub-Evento: ${eventoDetalhes.nome} (R$ ${eventoDetalhes.precoIngresso})")
                                                                                                        println("- Evento Principal:  ${eventoPrincipal.nome} (R$ ${eventoPrincipal.precoIngresso})")
                                                                                                        println("VALOR TOTAL:  R$ $ingressosSomados")

                                                                                                        println("\n[1] Confirmar Compra Dupla  [0] Cancelar")
                                                                                                        print("Digite opção: ")
                                                                                                        val confirmarDupla = readln()

                                                                                                        when (confirmarDupla) {
                                                                                                            "1" -> {

                                                                                                                // Cria os ingressos, adicionando-os a data class Ingresso
                                                                                                                val ingressoSubEvento = Ingresso(
                                                                                                                    id = listaIngressos.size + 1,
                                                                                                                    idEvento = eventoDetalhes.id,
                                                                                                                    emailUsuario = usuarioEncontrado.email,
                                                                                                                    statusDisponibilidade = false,
                                                                                                                    valorPago = eventoDetalhes.precoIngresso
                                                                                                                )
                                                                                                                listaIngressos.add(ingressoSubEvento)

                                                                                                                val ingressoEventoPrincipal = Ingresso(
                                                                                                                    id = listaIngressos.size + 1,
                                                                                                                    idEvento = eventoPrincipal.id,
                                                                                                                    emailUsuario = usuarioEncontrado.email,
                                                                                                                    statusDisponibilidade = false,
                                                                                                                    valorPago = eventoPrincipal.precoIngresso
                                                                                                                )
                                                                                                                listaIngressos.add(ingressoEventoPrincipal)

                                                                                                                println("OK: Dois ingressos comprados.")
                                                                                                                println("1. Ingresso ID ${ingressoSubEvento.id} (${eventoDetalhes.nome})")
                                                                                                                println("2. Ingresso ID ${ingressoEventoPrincipal.id} (${eventoPrincipal.nome})\n")
                                                                                                            }

                                                                                                            "0" -> println("OK: Compra cancelada.")
                                                                                                            else -> println("ERRO: Opção inválida.")
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }

                                                                            "0" -> println("OK: Operação cancelada.\n")
                                                                            else -> println("ERRO: Opção inválida. Tente novamente.\n")
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            TipoUsuario.ORGANIZADOR -> println("ERRO: Opção inválida. Tente novamente.")
                                        }
                                    }

                                    "5" -> {
                                        when (usuarioEncontrado.tipoUsuario) {
                                            TipoUsuario.COMUM -> {
                                                println("\nVISUALIZAR INGRESSOS")
                                                var possuiIngressos = false

                                                // Lista (temporária) de eventos com ingresso(s)
                                                val eventosOrdenados = mutableListOf<Evento>()
                                                for (evento in listaEventos) {
                                                    eventosOrdenados.add(evento)
                                                }

                                                // Ordenação da lista por data e nome
                                                eventosOrdenados.sortWith(
                                                    compareBy(
                                                        { it.anoInicio }, { it.mesInicio }, { it.diaInicio }, { it.horaInicio }, { it.nome }
                                                    ))

                                                // Exibe ingressos ativos e futuros
                                                for (evento in eventosOrdenados) {
                                                    val dataEvento = (evento.anoInicio * 10000) + (evento.mesInicio * 100) + evento.diaInicio
                                                    when {
                                                        evento.statusEvento && dataEvento >= dataHoje -> {
                                                            for (ingresso in listaIngressos) {
                                                                when {
                                                                    ingresso.emailUsuario == usuarioEncontrado.email &&
                                                                            ingresso.idEvento == evento.id &&
                                                                            !ingresso.statusDisponibilidade -> {
                                                                        println(
                                                                            "ID: ${ingresso.id} | Evento: ${evento.nome} | Data: ${evento.diaInicio}/${evento.mesInicio}/${evento.anoInicio} " +
                                                                                    "às ${evento.horaInicio}:${evento.minutoInicio} " +
                                                                                    "| Valor: R$ ${ingresso.valorPago} | Status: [OK]"
                                                                        )
                                                                        possuiIngressos = true
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                // Exibe ingressos cancelados e inativos
                                                for (evento in eventosOrdenados) {
                                                    val dataEvento = (evento.anoInicio * 10000) + (evento.mesInicio * 100) + evento.diaInicio
                                                    for (ingresso in listaIngressos) {
                                                        when {
                                                            ingresso.emailUsuario == usuarioEncontrado.email && ingresso.idEvento == evento.id -> {
                                                                when {
                                                                    ingresso.statusDisponibilidade || !evento.statusEvento || dataEvento < dataHoje -> {
                                                                        val statusTexto = when (ingresso.statusDisponibilidade) {
                                                                            true -> "[CANCELADO]"
                                                                            false -> "[REALIZADO/INATIVO]"
                                                                        }
                                                                        println(
                                                                            "ID: ${ingresso.id} | Evento: ${evento.nome} | Data: ${evento.diaInicio}/${evento.mesInicio}/${evento.anoInicio} " +
                                                                                    "às ${evento.horaInicio}:${evento.minutoInicio} " +
                                                                                    "| Valor: R$ ${ingresso.valorPago} | Status: $statusTexto"
                                                                        )
                                                                        possuiIngressos = true
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                when (possuiIngressos) {
                                                    false -> println("AVISO: Você não possui ingressos.")

                                                    // Possibilita expandir um ingresso
                                                    true -> {
                                                        print("\nDigite ID do ingresso para expandir/cancelar (ou [0] Voltar): ")
                                                        val idIngresso = readln().toIntOrNull() ?: 0

                                                        when (idIngresso) {
                                                            0 -> println("OK: Voltando ao Menu Principal.")
                                                            else -> {
                                                                var ingressoExpandido: Ingresso? = null
                                                                for (ingresso in listaIngressos) {
                                                                    when {
                                                                        ingresso.id == idIngresso && ingresso.emailUsuario == usuarioEncontrado.email -> ingressoExpandido = ingresso
                                                                    }
                                                                }

                                                                when (ingressoExpandido) {
                                                                    null -> println("ERRO: Ingresso inexistente ou indisponível.")
                                                                    else -> {
                                                                        var eventoDoIngresso: Evento? = null
                                                                        for (evento in listaEventos) {
                                                                            when (evento.id) {
                                                                                ingressoExpandido.idEvento -> eventoDoIngresso = evento
                                                                            }
                                                                        }

                                                                        when (eventoDoIngresso) {
                                                                            null -> println("ERRO: Nenhum evento encontrado.")
                                                                            else -> {
                                                                                println("\nDETALHES DO INGRESSO")
                                                                                println("ID: ${ingressoExpandido.id}")
                                                                                println("Evento: ${eventoDoIngresso.nome}")
                                                                                println(
                                                                                    "Data: ${eventoDoIngresso.diaInicio}/${eventoDoIngresso.mesInicio}/${eventoDoIngresso.anoInicio} " +
                                                                                            "às ${eventoDoIngresso.horaInicio}:${eventoDoIngresso.minutoInicio}"
                                                                                )
                                                                                println("Local: ${eventoDoIngresso.local}")
                                                                                println("Valor Pago: R$ ${ingressoExpandido.valorPago}")

                                                                                val statusAtual = when (ingressoExpandido.statusDisponibilidade) {
                                                                                    true -> "CANCELADO"
                                                                                    false -> "OK"
                                                                                }
                                                                                println("Status Atual: $statusAtual")

                                                                                // Possibilita cancelar o ingresso atual
                                                                                println("Cancelar Ingresso? [1] SIM [2] NÃO:")
                                                                                print("Digite opção: ")
                                                                                val opcaoCancelamento = readln()

                                                                                when (opcaoCancelamento) {
                                                                                    "1" -> {

                                                                                        // Cria validação de data para validar cancelamento de ingresso somente para eventos futuros
                                                                                        val dataEvento = (eventoDoIngresso.anoInicio * 10000) + (eventoDoIngresso.mesInicio * 100) + eventoDoIngresso.diaInicio

                                                                                        when {
                                                                                            ingressoExpandido.statusDisponibilidade -> println("\nERRO: Ingresso já cancelado.")
                                                                                            dataEvento < dataHoje -> println("\nERRO: Evento passado. Cancelamento indisponível.")
                                                                                            !eventoDoIngresso.statusEvento -> println("\nERRO: Evento desativado pelo organizador.")
                                                                                            else -> {
                                                                                                println("\nCANCELAMENTO DE INGRESSO")
                                                                                                println("Sobre o Evento:")

                                                                                                var valorReembolso = 0.0

                                                                                                // Exibe para o usuário se o ingresso pode ser reembolsado ou não
                                                                                                when (eventoDoIngresso.aceitaEstorno) {
                                                                                                    true -> {
                                                                                                        val descontoTaxa = (ingressoExpandido.valorPago * eventoDoIngresso.taxaEstorno) / 100
                                                                                                        valorReembolso = ingressoExpandido.valorPago - descontoTaxa
                                                                                                        println("- Aceita Reembolso: SIM")
                                                                                                        println("- Taxa de Retenção: ${eventoDoIngresso.taxaEstorno}%")
                                                                                                        println("- Valor a ser estornado: R$ $valorReembolso")
                                                                                                    }

                                                                                                    false -> {
                                                                                                        println("- Aceita Reembolso: NÃO")
                                                                                                        println("- O cancelamento não devolve valores.")
                                                                                                    }
                                                                                                }

                                                                                                // Confirma o cancelamento do ingresso
                                                                                                println("\nDeseja realmente cancelar este ingresso? [1] SIM [2] NÃO: ")
                                                                                                print("Digite opção:")
                                                                                                val confirmarCancelamento = readln()

                                                                                                when (confirmarCancelamento) {
                                                                                                    "1" -> {
                                                                                                        ingressoExpandido.statusDisponibilidade = true
                                                                                                        println("OK: Ingresso cancelado.")
                                                                                                        println("Estorno de R$ $valorReembolso solicitado.\n")
                                                                                                    }

                                                                                                    "2" -> println("OK: Cancelamento cancelada.")
                                                                                                    else -> println("ERRO: Opção inválida. Solicite novamente.")
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }

                                                                                    "2" -> println("OK: Operação cancelada.\n")
                                                                                    else -> println("ERRO: Opção inválida. Solicite novamente.\n")
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            // Opção indisponível para organizadores
                                            TipoUsuario.ORGANIZADOR -> println("ERRO: Opção inválida. Tente novamente.")
                                        }
                                    }

                                    "6" -> {

                                        // Para organizadores criarem eventos
                                        when (usuarioEncontrado.tipoUsuario) {
                                            TipoUsuario.ORGANIZADOR -> {
                                                println("\nNOVO EVENTO")

                                                print("Digite Nome do Evento: ")
                                                val cadastroNome = readln()

                                                print("Digite Página do Evento: ")
                                                val cadastroPagina = readln()

                                                print("Digite Descrição do Evento: ")
                                                val cadastroDescricao = readln()

                                                // Variáveis para validar as datas
                                                var dataValida = false
                                                var diaInicio: Int?
                                                var mesInicio: Int?
                                                var anoInicio: Int?
                                                var horaInicio: Int?
                                                var minutoInicio: Int?

                                                var diaFinal: Int?
                                                var mesFinal: Int?
                                                var anoFinal: Int?
                                                var horaFinal: Int?
                                                var minutoFinal: Int?

                                                // Loop para validar as datas
                                                do {
                                                    println("\nDEFINIR PERÍODO DO EVENTO")

                                                    println("MENU: DATA DE INÍCIO")
                                                    print("Digite Somente Dia (DD): ")
                                                    diaInicio = readln().toIntOrNull() ?: 0
                                                    print("Digite Somente Mês (MM): ")
                                                    mesInicio = readln().toIntOrNull() ?: 0
                                                    print("Digite Somente Ano (AAAA): ")
                                                    anoInicio = readln().toIntOrNull() ?: 0
                                                    print("Digite Somente Hora (HH): ")
                                                    horaInicio = readln().toIntOrNull() ?: 0
                                                    print("Digite Somente Minuto (MM): ")
                                                    minutoInicio = readln().toIntOrNull() ?: 0

                                                    println("MENU: DATA DE TÉRMINO")
                                                    print("Digite Somente Dia (DD): ")
                                                    diaFinal = readln().toIntOrNull() ?: 0
                                                    print("Digite Somente Mês (MM): ")
                                                    mesFinal = readln().toIntOrNull() ?: 0
                                                    print("Digite Somente Ano (AAAA): ")
                                                    anoFinal = readln().toIntOrNull() ?: 0
                                                    print("Digite Somente Hora (HH): ")
                                                    horaFinal = readln().toIntOrNull() ?: 0
                                                    print("Digite Somente Minuto (MM): ")
                                                    minutoFinal = readln().toIntOrNull() ?: 0

                                                    // Junta tudo num número (AAAAMMDD) para validações
                                                    val dataInicio = (anoInicio * 10000) + (mesInicio * 100) + diaInicio
                                                    val dataFim = (anoFinal * 10000) + (mesFinal * 100) + diaFinal

                                                    // Cálculo de minutos totais para checar duração
                                                    val minutagemInicio = (horaInicio * 60) + minutoInicio
                                                    val minutagemFim = (horaFinal * 60) + minutoFinal

                                                    when {

                                                        // A data de fim do evento não pode ser anterior a data corrente.
                                                        dataInicio < dataHoje ->
                                                            println("ERRO: O evento não pode ser no passado.")

                                                        // A data de fim do evento não pode ser anterior a data de início.
                                                        dataFim < dataInicio ->
                                                            println("ERRO: Data de término antes da data de início.")

                                                        // A hora de fim do evento não pode ser anterior a hora de início. (Se for mesmo dia)
                                                        dataFim == dataInicio && minutagemFim < minutagemInicio ->
                                                            println("ERRO: Hora de término antes da hora de início.")

                                                        // Os eventos devem ter no mínimo 30 minutos de duração. (Se for mesmo dia)
                                                        dataFim == dataInicio && (minutagemFim - minutagemInicio) < 30 ->
                                                            println("ERRO: A duração mínima é de 30 minutos.")

                                                        // Horários dentro de 24 horas e 60 minutos
                                                        horaInicio !in 0..23 || horaFinal !in 0..23 || minutoInicio !in 0..59 || minutoFinal !in 0..59 ->
                                                            println("ERRO: Horário inválido. Use 0-23 para horas e 0-59 para minutos.")

                                                        else -> {
                                                            println("OK:\nDATA DE INÍCIO DEFINIDA $diaInicio/$mesInicio/$anoInicio.")
                                                            println("DATA DE TÉRMINO DEFINIDA $diaFinal/$mesFinal/$anoFinal.\n")
                                                            dataValida = true
                                                        }
                                                    }
                                                } while (!dataValida)

                                                println("Tipo de evento:")
                                                println("[1] Social [2] Corporativo [3] Acadêmico [4] Cultural/Entretenimento [5] Religioso")
                                                println("[6] Esportivo [7] Feira [8] Congresso [9] Oficina [10] Curso [11] Treinamento")
                                                println("[12] Aula [13] Seminário [14] Palestra [15] Show [16] Festival [17] Exposição")
                                                println("[18] Retiro [19] Culto [20] Celebração [21] Campeonato [22] Corrida [23] Outro")
                                                print("Digite opção: ")
                                                val cadastroTipo = readln()
                                                val tipoEvento: TipoEvento
                                                when (cadastroTipo) {
                                                    "1" -> tipoEvento = TipoEvento.SOCIAL
                                                    "2" -> tipoEvento = TipoEvento.CORPORATIVO
                                                    "3" -> tipoEvento = TipoEvento.ACADEMICO
                                                    "4" -> tipoEvento = TipoEvento.CULTURAL_ENTRETENIMENTO
                                                    "5" -> tipoEvento = TipoEvento.RELIGIOSO
                                                    "6" -> tipoEvento = TipoEvento.ESPORTIVO
                                                    "7" -> tipoEvento = TipoEvento.FEIRA
                                                    "8" -> tipoEvento = TipoEvento.CONGRESSO
                                                    "9" -> tipoEvento = TipoEvento.OFICINA
                                                    "10" -> tipoEvento = TipoEvento.CURSO
                                                    "11" -> tipoEvento = TipoEvento.TREINAMENTO
                                                    "12" -> tipoEvento = TipoEvento.AULA
                                                    "13" -> tipoEvento = TipoEvento.SEMINARIO
                                                    "14" -> tipoEvento = TipoEvento.PALESTRA
                                                    "15" -> tipoEvento = TipoEvento.SHOW
                                                    "16" -> tipoEvento = TipoEvento.FESTIVAL
                                                    "17" -> tipoEvento = TipoEvento.EXPOSICAO
                                                    "18" -> tipoEvento = TipoEvento.RETIRO
                                                    "19" -> tipoEvento = TipoEvento.CULTO
                                                    "20" -> tipoEvento = TipoEvento.CELEBRACAO
                                                    "21" -> tipoEvento = TipoEvento.CAMPEONATO
                                                    "22" -> tipoEvento = TipoEvento.CORRIDA
                                                    else -> tipoEvento = TipoEvento.OUTRO
                                                }
                                                println("OK: TIPO DE EVENTO DEFINIDO $tipoEvento.\n")

                                                println("Vincular novo evento a evento principal? [1] SIM [2] NÃO")
                                                print("Digite opção: ")
                                                val vincularPrincipal = readln()
                                                var idEventoPrincipal: Int? = null

                                                when (vincularPrincipal) {
                                                    "1" -> {
                                                        println("Seus eventos ativos:")
                                                        for (evento in listaEventos) {
                                                            when {
                                                                evento.organizadorEmail == usuarioEncontrado.email && evento.statusEvento -> {
                                                                    println("ID: ${evento.id} | NOME: ${evento.nome}")
                                                                }

                                                                else -> println("Nenhum evento encontrado.")
                                                            }
                                                        }
                                                        print("\nDigite ID do Evento Principal (ou 0 para cancelar): ")
                                                        val eventoPrincipal = readln().toIntOrNull()
                                                        var eventoEncontrado = false
                                                        when (eventoPrincipal) {
                                                            0 -> {
                                                                idEventoPrincipal = null
                                                                println("OK: EVENTO PRINCIPAL NÃO VINCULADO.\n")
                                                            }

                                                            else -> {
                                                                for (evento in listaEventos) {
                                                                    when {
                                                                        evento.id == eventoPrincipal && evento.organizadorEmail == usuarioEncontrado.email && evento.statusEvento ->
                                                                            eventoEncontrado = true
                                                                    }
                                                                }
                                                                when (eventoEncontrado) {
                                                                    true -> {
                                                                        idEventoPrincipal = eventoPrincipal
                                                                        println("OK: ID $eventoPrincipal DEFINIDO COMO EVENTO PRINCIPAL DE '$cadastroNome'.\n")
                                                                    }

                                                                    false -> println("ERRO: ID $eventoPrincipal não encontrado. Vinculação mal-sucedida.\n")
                                                                }
                                                            }
                                                        }
                                                    }

                                                    "2" -> println("OK: DEFINIDO EVENTO INDEPENDENTE.\n")
                                                    else -> print("ERRO: Opção inválida. Vinculação mal-sucedida.\n")
                                                }

                                                println("Modalidade: [1] PRESENCIAL [2] REMOTO [3] HÍBRIDO")
                                                print("Digite opção: ")
                                                val cadastroModalidade = readln()
                                                val modalidadeEvento: ModalidadeEvento =
                                                    when (cadastroModalidade) {
                                                        "1" -> ModalidadeEvento.PRESENCIAL
                                                        "2" -> ModalidadeEvento.REMOTO
                                                        else -> ModalidadeEvento.HIBRIDO
                                                    }
                                                println("OK: MODALIDADE DEFINIDA $modalidadeEvento.\n")

                                                var capacidadeValida = false
                                                var cadastroCapacidade: Int
                                                do {
                                                    print("Digite Capacidade Máxima de Pessoas: ")
                                                    cadastroCapacidade = readln().toIntOrNull() ?: 0

                                                    // Valida capacidade máxima acima de 0
                                                    when {
                                                        cadastroCapacidade > 0 -> capacidadeValida = true
                                                        else -> println("ERRO: Capacidade inválida. Tente novamente.\n")
                                                    }
                                                } while (!capacidadeValida)
                                                println("OK: CAPACIDADE DEFINIDA $cadastroCapacidade.\n")

                                                print("Digite Local do Evento (endereço ou link): ")
                                                val cadastroLocal = readln()

                                                var precoValido = false
                                                var cadastroPreco: Double
                                                do {
                                                    print("Digite Preço do Ingresso: ")
                                                    cadastroPreco = readln().toDoubleOrNull() ?: 0.0

                                                    // Valida preços acima ou igual a 0
                                                    when {
                                                        cadastroPreco >= 0 -> precoValido = true
                                                        else -> println("ERRO: Preço inválido. Tente novamente.\n")
                                                    }
                                                } while (!precoValido)
                                                println("OK: PREÇO DEFINIDO $cadastroPreco.\n")

                                                println("Aceita estorno/devolução de ingresso? [1] SIM [2] NÃO")
                                                print("Digite opção: ")
                                                val cadastroEstorno = readln()
                                                val aceitaEstorno: Boolean =
                                                    when (cadastroEstorno) {
                                                        "1" -> true
                                                        else -> false
                                                    }
                                                val estornoTexto = when (aceitaEstorno) {
                                                    true -> "[SIM]"
                                                    false -> "[NÃO]"
                                                }
                                                println("OK: ACEITA ESTORNO DEFINIDO $estornoTexto.\n")

                                                var cadastroTaxa: Double
                                                when (aceitaEstorno) {
                                                    true -> {
                                                        var taxaInvalida: Boolean
                                                        do {
                                                            print("Digite Taxa de Estorno (%): ")
                                                            cadastroTaxa = readln().toDoubleOrNull() ?: -1.0

                                                            // Valida a taxa entre 0% e 100%
                                                            when {
                                                                cadastroTaxa !in 0.0..100.0 -> {
                                                                    taxaInvalida = true
                                                                    println("ERRO: Taxa inválida. Tente novamente.\n")
                                                                }

                                                                else -> taxaInvalida = false
                                                            }
                                                        } while (taxaInvalida)
                                                        println("OK: TAXA DE ESTORNO DEFINIDA $cadastroTaxa.\n")
                                                    }

                                                    false -> {
                                                        cadastroTaxa = 0.0
                                                    }
                                                }

                                                // Cria o evento com os dados em data class Evento
                                                val novoEvento = Evento(
                                                    id = listaEventos.size + 1,
                                                    organizadorEmail = usuarioEncontrado.email,
                                                    nome = cadastroNome,
                                                    pagina = cadastroPagina,
                                                    descricao = cadastroDescricao,
                                                    diaInicio = diaInicio, mesInicio = mesInicio, anoInicio = anoInicio,
                                                    horaInicio = horaInicio, minutoInicio = minutoInicio,
                                                    diaTermino = diaFinal, mesTermino = mesFinal, anoTermino = anoFinal,
                                                    horaTermino = horaFinal, minutoTermino = minutoFinal,
                                                    tipo = tipoEvento,
                                                    idEventoPrincipal = idEventoPrincipal,
                                                    modalidade = modalidadeEvento,
                                                    capacidadeMax = cadastroCapacidade,
                                                    local = cadastroLocal,
                                                    statusEvento = true,
                                                    precoIngresso = cadastroPreco,
                                                    aceitaEstorno = aceitaEstorno,
                                                    taxaEstorno = cadastroTaxa
                                                )

                                                listaEventos.add(novoEvento)
                                                println("OK: Evento cadastrado (ID ${novoEvento.id}).")
                                            }

                                            else -> println("ERRO: Opção inválida. Tente novamente.")
                                        }
                                    }

                                    "7" -> {
                                        when (usuarioEncontrado.tipoUsuario) {
                                            TipoUsuario.ORGANIZADOR -> {
                                                println("\nVISUALIZAR EVENTOS")

                                                // Lista (temporária) de eventos do organizador
                                                val organizadorEventos = mutableListOf<Evento>()
                                                for (evento in listaEventos) {
                                                    when {
                                                        (evento.organizadorEmail == usuarioEncontrado.email) -> organizadorEventos.add(evento)
                                                    }
                                                }

                                                // Ordenação da lista por data e nome
                                                organizadorEventos.sortWith(
                                                    compareBy(
                                                        { it.anoInicio }, { it.mesInicio }, { it.diaInicio }, { it.horaInicio }, { it.nome }
                                                    ))

                                                println("Seus eventos ativos:")
                                                println("ID | STATUS | NOME | PERÍODO | LOCAL | PREÇO | CAPACIDADE")

                                                var possuiEventos = false

                                                for (evento in organizadorEventos) {
                                                    when (evento.organizadorEmail) {
                                                        usuarioEncontrado.email -> {
                                                            val statusTexto = when (evento.statusEvento) {
                                                                true -> "[ATIVO]"
                                                                false -> "[INATIVO]"
                                                            }

                                                            println(
                                                                "${evento.id} | $statusTexto | ${evento.nome} | " +
                                                                        "${evento.diaInicio}/${evento.mesInicio}/${evento.anoInicio} | " +
                                                                        "${evento.local} | R$ ${evento.precoIngresso} | " +
                                                                        "${evento.capacidadeMax}"
                                                            )

                                                            possuiEventos = true
                                                        }
                                                    }
                                                }

                                                when (possuiEventos) {
                                                    false -> println("AVISO: Nenhum evento encontrado.")

                                                    // Opção para expandir um evento
                                                    true -> {
                                                        print("\nDigite ID para expandir detalhes ou [0] Voltar: ")
                                                        val opcaoID = readln().toIntOrNull() ?: 0

                                                        when (opcaoID) {
                                                            0 -> println("OK: Selecionado Voltar.")
                                                            else -> {
                                                                var eventoDetalhes: Evento? = null

                                                                for (evento in listaEventos) {
                                                                    when {
                                                                        evento.id == opcaoID && evento.organizadorEmail == usuarioEncontrado.email -> eventoDetalhes = evento
                                                                    }
                                                                }

                                                                when (eventoDetalhes) {
                                                                    null -> println("ERRO: Nenhum evento encontrado.")
                                                                    else -> {
                                                                        println("\n${eventoDetalhes.nome} EXPANDIDO")
                                                                        println("Nome: ${eventoDetalhes.nome}")
                                                                        println("Descrição: ${eventoDetalhes.descricao}")
                                                                        println("Página: ${eventoDetalhes.pagina}")
                                                                        println(
                                                                            "Início: ${eventoDetalhes.diaInicio}/${eventoDetalhes.mesInicio}/${eventoDetalhes.anoInicio} " +
                                                                                    "às ${eventoDetalhes.horaInicio}:${eventoDetalhes.minutoInicio}"
                                                                        )
                                                                        println(
                                                                            "Término: ${eventoDetalhes.diaTermino}/${eventoDetalhes.mesTermino}/${eventoDetalhes.anoTermino} " +
                                                                                    "às ${eventoDetalhes.horaTermino}:${eventoDetalhes.minutoTermino}"
                                                                        )
                                                                        println("Tipo: ${eventoDetalhes.tipo}")
                                                                        println("Modalidade: ${eventoDetalhes.modalidade}")
                                                                        println("Local: ${eventoDetalhes.local}")
                                                                        println("Capacidade Máxima: ${eventoDetalhes.capacidadeMax}")
                                                                        println("Preço do Ingresso: R$ ${eventoDetalhes.precoIngresso}")

                                                                        val textoEstorno = when (eventoDetalhes.aceitaEstorno) {
                                                                            true -> "Sim (Taxa: ${eventoDetalhes.taxaEstorno}%)"
                                                                            false -> "Não"
                                                                        }
                                                                        println("Aceita Estorno: $textoEstorno")

                                                                        when (eventoDetalhes.idEventoPrincipal) {
                                                                            null -> println("Evento Independente.")
                                                                            else -> println("Vinculado ao Evento Principal ID: ${eventoDetalhes.idEventoPrincipal}")
                                                                        }

                                                                        print("[QUALQUER TECLA] Voltar.\n")
                                                                        readln()
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            // Opção indisponível para usuários comuns
                                            TipoUsuario.COMUM -> println("ERRO: Opção inválida. Tente novamente.")
                                        }
                                    }

                                    "8" -> {
                                        var possuiEventos = false

                                        // Busca todos os eventos do organizador e lista
                                        when (usuarioEncontrado.tipoUsuario) {
                                            TipoUsuario.ORGANIZADOR -> {
                                                println("\nALTERAR EVENTO")
                                                println("Seus eventos:")
                                                for (evento in listaEventos) {
                                                    when {
                                                        evento.organizadorEmail == usuarioEncontrado.email -> {
                                                            println("ID: ${evento.id} | NOME: ${evento.nome}")
                                                            possuiEventos = true
                                                        }
                                                    }
                                                }

                                                // Busca o evento selecionado
                                                when (possuiEventos) {
                                                    false -> println("ERRO: Nenhum evento encontrado.")
                                                    true -> {
                                                        print("Digite o ID do evento para alterar: ")
                                                        val idEvento = readln().toIntOrNull() ?: 0

                                                        // Variável para selecionar o evento a ser alterado
                                                        var eventoAlterando: Evento? = null

                                                        for (evento in listaEventos) {
                                                            when {
                                                                evento.id == idEvento && evento.organizadorEmail == usuarioEncontrado.email -> eventoAlterando = evento
                                                            }
                                                        }

                                                        when (eventoAlterando) {
                                                            null -> println("ERRO: Evento inválido. Tente novamente.")
                                                            else -> {

                                                                // Caso o evento esteja desativado, é necessário reativar para alterar dados
                                                                when {
                                                                    !eventoAlterando.statusEvento -> {
                                                                        println("\nAVISO: Este é um evento desativado. Reativar para alterar? [1] SIM [2] NÃO")
                                                                        print("Digite opção: ")
                                                                        val reativarEvento = readln()
                                                                        when (reativarEvento) {
                                                                            "1" -> {
                                                                                eventoAlterando.statusEvento = true
                                                                                println("OK: Evento reativado. Solicite novamente.\n")
                                                                            }

                                                                            "2" -> println("OK: Operação cancelada.")
                                                                            else -> println("ERRO: Opção inválida. Solicite novamente.")
                                                                        }
                                                                    }

                                                                    else -> {
                                                                        var menuAlterarEvento = true

                                                                        // Menu para alterar os dados do evento
                                                                        do {
                                                                            println("MENU: EDITANDO ${eventoAlterando.nome} (${eventoAlterando.id}).")
                                                                            println("OPÇÕES:")
                                                                            println("[0] Voltar\n[1] Nome [2] Página [3] Descrição [4] Período [5] Tipo")
                                                                            println("[6] Evento Vinculado [7] Modalidade [8] Capacidade [9] Local [10] Preço/Estorno")
                                                                            println("Digite opção: ")
                                                                            val opcaoAlterarEvento = readln()

                                                                            when (opcaoAlterarEvento) {
                                                                                "0" -> {
                                                                                    println("OK: Selecionado Voltar.\n")
                                                                                    menuAlterarEvento = false
                                                                                }

                                                                                "1" -> {
                                                                                    print("Digite Nome atualizado: ")
                                                                                    eventoAlterando.nome = readln()
                                                                                    println("OK: NOME DEFINIDO '${eventoAlterando.nome}'.")
                                                                                }

                                                                                "2" -> {
                                                                                    print("Digite Página atualizada: ")
                                                                                    eventoAlterando.pagina = readln()
                                                                                    println("OK: PÁGINA DEFINIDA '${eventoAlterando.pagina}'.")
                                                                                }

                                                                                "3" -> {
                                                                                    print("Digite Descrição atualizada: ")
                                                                                    eventoAlterando.descricao = readln()
                                                                                    println("OK: DESCRIÇÃO DEFINIDA '${eventoAlterando.descricao}'.")
                                                                                }

                                                                                "4" -> {
                                                                                    var dataValida = false

                                                                                    do {
                                                                                        println("\nALTERAR PERÍODO DO EVENTO")

                                                                                        println("MENU: ALTERAR DATA DE INÍCIO")
                                                                                        print("Digite Somente Dia atualizado (DD): ")
                                                                                        val diaInicio = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Somente Mês atualizado (MM): ")
                                                                                        val mesInicio = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Somente Ano atualizado (AAAA): ")
                                                                                        val anoInicio = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Somente Hora atualizado (HH): ")
                                                                                        val horaInicio = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Somente Minuto atualizado (MM): ")
                                                                                        val minutoInicio = readln().toIntOrNull() ?: 0

                                                                                        println("MENU: ALTERAR DATA DE TÉRMINO")
                                                                                        print("Digite Somente Dia atualizado (DD): ")
                                                                                        val diaFinal = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Somente Mês atualizado (MM): ")
                                                                                        val mesFinal = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Somente Ano atualizado (AAAA): ")
                                                                                        val anoFinal = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Somente Hora atualizado (HH): ")
                                                                                        val horaFinal = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Somente Minuto atualizado (MM): ")
                                                                                        val minutoFinal = readln().toIntOrNull() ?: 0

                                                                                        val dataInicio = (anoInicio * 10000) + (mesInicio * 100) + diaInicio
                                                                                        val dataFim = (anoFinal * 10000) + (mesFinal * 100) + diaFinal

                                                                                        val minutagemInicio = (horaInicio * 60) + minutoInicio
                                                                                        val minutagemFim = (horaFinal * 60) + minutoFinal

                                                                                        // Validação de data igual a de cadastro de evento
                                                                                        when {
                                                                                            dataInicio < dataHoje ->
                                                                                                println("ERRO: O evento não pode ser no passado.")

                                                                                            dataFim < dataInicio ->
                                                                                                println("ERRO: Data de término antes da data de início.")

                                                                                            dataFim == dataInicio && minutagemFim < minutagemInicio ->
                                                                                                println("ERRO: Hora de término antes da hora de início.")

                                                                                            dataFim == dataInicio && (minutagemFim - minutagemInicio) < 30 ->
                                                                                                println("ERRO: A duração mínima é de 30 minutos.")

                                                                                            horaInicio !in 0..23 || horaFinal !in 0..23 || minutoInicio !in 0..59 || minutoFinal !in 0..59 ->
                                                                                                println("ERRO: Horário inválido. Use 0-23 para horas e 0-59 para minutos.")

                                                                                            else -> {
                                                                                                eventoAlterando.diaInicio = diaInicio
                                                                                                eventoAlterando.mesInicio = mesInicio
                                                                                                eventoAlterando.anoInicio = anoInicio
                                                                                                eventoAlterando.horaInicio = horaInicio
                                                                                                eventoAlterando.minutoInicio = minutoInicio
                                                                                                eventoAlterando.diaTermino = diaFinal
                                                                                                eventoAlterando.mesTermino = mesFinal
                                                                                                eventoAlterando.anoTermino = anoFinal
                                                                                                eventoAlterando.horaTermino = horaFinal
                                                                                                eventoAlterando.minutoTermino = minutoFinal
                                                                                                println("\nOK:\nDATA DE INÍCIO DEFINIDA $diaInicio/$mesInicio/$anoInicio.")
                                                                                                println("DATA DE TÉRMINO DEFINIDA $diaFinal/$mesFinal/$anoFinal.")
                                                                                                dataValida = true
                                                                                            }
                                                                                        }
                                                                                    } while (!dataValida)
                                                                                }

                                                                                "5" -> {
                                                                                    println("ALTERANDO: Tipo de evento")
                                                                                    println("[1] Social [2] Corporativo [3] Acadêmico [4] Cultural/Entretenimento")
                                                                                    println("[5] Religioso [6] Esportivo [7] Feira [8] Congresso [9] Oficina")
                                                                                    println("[10] Curso [11] Treinamento [12] Aula [13] Seminário [14] Palestra")
                                                                                    println("[15] Show [16] Festival [17] Exposição [18] Retiro [19] Culto [20]")
                                                                                    println("[21] Celebração [22] Campeonato [23] Corrida [24] Outro")
                                                                                    print("Digite opção: ")
                                                                                    val alterarTipo = readln()
                                                                                    eventoAlterando.tipo =
                                                                                        when (alterarTipo) {
                                                                                            "1" -> TipoEvento.SOCIAL
                                                                                            "2" -> TipoEvento.CORPORATIVO
                                                                                            "3" -> TipoEvento.ACADEMICO
                                                                                            "4" -> TipoEvento.CULTURAL_ENTRETENIMENTO
                                                                                            "5" -> TipoEvento.RELIGIOSO
                                                                                            "6" -> TipoEvento.ESPORTIVO
                                                                                            "7" -> TipoEvento.FEIRA
                                                                                            "8" -> TipoEvento.CONGRESSO
                                                                                            "9" -> TipoEvento.OFICINA
                                                                                            "10" -> TipoEvento.CURSO
                                                                                            "11" -> TipoEvento.TREINAMENTO
                                                                                            "12" -> TipoEvento.AULA
                                                                                            "13" -> TipoEvento.SEMINARIO
                                                                                            "14" -> TipoEvento.PALESTRA
                                                                                            "15" -> TipoEvento.SHOW
                                                                                            "16" -> TipoEvento.FESTIVAL
                                                                                            "17" -> TipoEvento.EXPOSICAO
                                                                                            "18" -> TipoEvento.RETIRO
                                                                                            "19" -> TipoEvento.CULTO
                                                                                            "20" -> TipoEvento.CELEBRACAO
                                                                                            "21" -> TipoEvento.CAMPEONATO
                                                                                            "22" -> TipoEvento.CORRIDA
                                                                                            else -> TipoEvento.OUTRO
                                                                                        }
                                                                                    println("OK: TIPO DE EVENTO DEFINIDO ${eventoAlterando.tipo}.\n")
                                                                                }

                                                                                "6" -> {
                                                                                    when {
                                                                                        eventoAlterando.idEventoPrincipal == null -> {
                                                                                            println("ERRO: Nenhum evento principal vinculado.")
                                                                                            println("Vincular novo evento a evento principal? [1] SIM [2] NÃO")
                                                                                        }

                                                                                        else -> {
                                                                                            println("Sub-Evento '${eventoAlterando.nome}' | Principal (ID ${eventoAlterando.idEventoPrincipal}).")
                                                                                            println("Alterar principal? [1] SIM [2] NÃO")
                                                                                        }
                                                                                    }
                                                                                    print("Digite opção: ")
                                                                                    val vincularPrincipal = readln()

                                                                                    when (vincularPrincipal) {
                                                                                        "1" -> {
                                                                                            print("Digite ID do Evento Principal (ou 0 para desvincular): ")
                                                                                            val eventoPrincipal = readln().toIntOrNull()

                                                                                            when (eventoPrincipal) {
                                                                                                0 -> {
                                                                                                    eventoAlterando.idEventoPrincipal = null
                                                                                                    println("OK: EVENTO PRINCIPAL DESVINCULADO.\n")
                                                                                                }

                                                                                                else -> {
                                                                                                    var idValido = false
                                                                                                    for (evento in listaEventos) {

                                                                                                        // Validações para vincular um evento principal
                                                                                                        when {

                                                                                                            // Se ID do evento principal for igual ao digitado...
                                                                                                            evento.id == eventoPrincipal &&

                                                                                                                    // ... ID do evento principal for diferente do sub-evento...
                                                                                                                    evento.id != eventoAlterando.id &&

                                                                                                                    // ... Evento pertence ao organizador...
                                                                                                                    evento.organizadorEmail == usuarioEncontrado.email &&

                                                                                                                    // ... Evento estiver ativado...
                                                                                                                    evento.statusEvento ->

                                                                                                                // Então ID do evento principal é válido
                                                                                                                idValido = true
                                                                                                        }
                                                                                                    }

                                                                                                    when (idValido) {
                                                                                                        true -> {
                                                                                                            eventoAlterando.idEventoPrincipal = eventoPrincipal
                                                                                                            println(
                                                                                                                "OK: ID $eventoPrincipal DEFINIDO COMO " +
                                                                                                                        "EVENTO PRINCIPAL DE '${eventoAlterando.nome}'\n"
                                                                                                            )
                                                                                                        }

                                                                                                        false -> println("ERRO: Vinculação mal-sucedida.")
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }

                                                                                        "2" -> println("OK: Operação cancelada.")
                                                                                        else -> print("ERRO: Opção inválida. Vinculação mal-sucedida.")
                                                                                    }
                                                                                }

                                                                                "7" -> {
                                                                                    println("ALTERANDO: Modalidade [1] PRESENCIAL [2] REMOTO [3] HÍBRIDO")
                                                                                    print("Digite opção: ")
                                                                                    val cadastroModalidade = readln()
                                                                                    eventoAlterando.modalidade =
                                                                                        when (cadastroModalidade) {
                                                                                            "1" -> ModalidadeEvento.PRESENCIAL
                                                                                            "2" -> ModalidadeEvento.REMOTO
                                                                                            else -> ModalidadeEvento.HIBRIDO
                                                                                        }
                                                                                    println("OK: MODALIDADE DEFINIDA ${eventoAlterando.modalidade}.\n")
                                                                                }

                                                                                "8" -> {
                                                                                    var ingressosVendidos = 0
                                                                                    for (ingresso in listaIngressos) {
                                                                                        when {
                                                                                            ingresso.idEvento == eventoAlterando.id && !ingresso.statusDisponibilidade -> ingressosVendidos++
                                                                                        }
                                                                                    }
                                                                                    var capacidadeValida = false
                                                                                    var alterarCapacidade: Int
                                                                                    do {
                                                                                        print("Digite Capacidade Máxima de Pessoas atualizada: ")
                                                                                        alterarCapacidade = readln().toIntOrNull() ?: 0

                                                                                        // A capacidade máxima de pessoas não pode ficar menor que a quantidade de ingressos vendidos
                                                                                        when {
                                                                                            alterarCapacidade >= ingressosVendidos && alterarCapacidade > 0 -> capacidadeValida = true
                                                                                            else -> println("ERRO: Capacidade inválida ou menor que ingressos vendidos. Tente novamente.\n")
                                                                                        }
                                                                                    } while (!capacidadeValida)
                                                                                    eventoAlterando.capacidadeMax = alterarCapacidade
                                                                                    println("OK: CAPACIDADE DEFINIDA ${eventoAlterando.capacidadeMax}.\n")
                                                                                }

                                                                                "9" -> {
                                                                                    print("Digite Local atualizado: ")
                                                                                    eventoAlterando.local = readln()
                                                                                    println("OK: LOCAL DEFINIDO ${eventoAlterando.local}.")
                                                                                }

                                                                                "10" -> {
                                                                                    var precoValido = false
                                                                                    var cadastroPreco: Double
                                                                                    do {
                                                                                        print("Digite Preço do Ingresso atualizado: ")
                                                                                        cadastroPreco = readln().toDoubleOrNull() ?: 0.0
                                                                                        when {
                                                                                            cadastroPreco >= 0 -> precoValido = true
                                                                                            else -> println("ERRO: Preço inválido. Tente novamente.\n")
                                                                                        }
                                                                                    } while (!precoValido)

                                                                                    eventoAlterando.precoIngresso = cadastroPreco
                                                                                    println("OK: PREÇO DEFINIDO ${eventoAlterando.precoIngresso}.\n")

                                                                                    println("ALTERANDO: Aceita estorno/devolução de ingresso? [1] SIM [2] NÃO")
                                                                                    print("Digite opção: ")
                                                                                    val alterarEstorno = readln()

                                                                                    when (alterarEstorno) {
                                                                                        "1" -> {
                                                                                            eventoAlterando.aceitaEstorno = true
                                                                                            val statusTexto = when (eventoAlterando.aceitaEstorno) {
                                                                                                true -> "[SIM]"
                                                                                                false -> "[NÃO]"
                                                                                            }
                                                                                            println("OK: ACEITA ESTORNO DEFINIDO $statusTexto.")

                                                                                            var alterarTaxa: Double
                                                                                            var taxaInvalida: Boolean
                                                                                            do {
                                                                                                print("Digite Taxa de Estorno (%): ")
                                                                                                alterarTaxa = readln().toDoubleOrNull() ?: -1.0

                                                                                                when {
                                                                                                    alterarTaxa !in 0.0..100.0 -> {
                                                                                                        taxaInvalida = true
                                                                                                        println("ERRO: Taxa inválida. Tente novamente.\n")
                                                                                                    }

                                                                                                    else -> taxaInvalida = false
                                                                                                }
                                                                                            } while (taxaInvalida)
                                                                                            eventoAlterando.taxaEstorno = alterarTaxa
                                                                                            println("OK: TAXA DE ESTORNO DEFINIDA ${eventoAlterando.taxaEstorno}.\n")
                                                                                        }

                                                                                        else -> {
                                                                                            eventoAlterando.aceitaEstorno = false
                                                                                            val statusTexto = when (eventoAlterando.aceitaEstorno) {
                                                                                                true -> "[SIM]"
                                                                                                false -> "[NÃO]"
                                                                                            }
                                                                                            eventoAlterando.taxaEstorno = 0.0
                                                                                            println("OK: ACEITA ESTORNO DEFINIDO $statusTexto.")
                                                                                            println("OK: TAXA DE ESTORNO DEFINIDA ${eventoAlterando.taxaEstorno}.\n")
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        } while (menuAlterarEvento)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            // Opção indisponível para usuários comuns
                                            TipoUsuario.COMUM -> println("ERRO: Opção inválida. Tente novamente.")
                                        }
                                    }

                                    "9" -> {
                                        var possuiEventos = false

                                        // Busca todos os eventos do organizador
                                        when (usuarioEncontrado.tipoUsuario) {
                                            TipoUsuario.ORGANIZADOR -> {
                                                println("\nDESATIVAR EVENTO")
                                                println("Seus eventos ativos:")
                                                for (evento in listaEventos) {
                                                    when {
                                                        evento.organizadorEmail == usuarioEncontrado.email && evento.statusEvento -> {
                                                            println("ID: ${evento.id} | NOME: ${evento.nome}")
                                                            possuiEventos = true
                                                        }
                                                    }
                                                }

                                                // Busca o evento selecionado
                                                when (possuiEventos) {
                                                    false -> println("ERRO: Nenhum evento encontrado.")
                                                    true -> {
                                                        print("Digite o ID do evento a desativar: ")
                                                        val idEvento = readln().toIntOrNull() ?: 0

                                                        // Variável e loop para selecionar o evento a ser alterado
                                                        var eventoAlterando: Evento? = null
                                                        for (evento in listaEventos) {
                                                            when {
                                                                evento.id == idEvento && evento.organizadorEmail == usuarioEncontrado.email -> eventoAlterando = evento
                                                            }
                                                        }
                                                        when (eventoAlterando) {
                                                            null -> println("ERRO: Evento inválido. Tente novamente.\n")
                                                            else -> {
                                                                println("AVISO: Caso o evento tenha vendido ingressos, os valores serão reembolsados aos compradores.")
                                                                println("Confirma desativar o evento '${eventoAlterando.nome}'? [1] SIM, CONFIRMAR. [2] NÃO, CANCELAR.")
                                                                print("Digite opção: ")
                                                                val desativarEvento = readln()

                                                                when (desativarEvento) {
                                                                    "1" -> {
                                                                        eventoAlterando.statusEvento = false

                                                                        var ingressosCancelados = 0
                                                                        var totalReembolsado = 0.0

                                                                        // Loop para desvincular todos os ingressos do evento
                                                                        for (ingresso in listaIngressos) {
                                                                            when {
                                                                                ingresso.idEvento == eventoAlterando.id && !ingresso.statusDisponibilidade -> {
                                                                                    ingresso.statusDisponibilidade = true
                                                                                    ingressosCancelados++
                                                                                    totalReembolsado += ingresso.valorPago
                                                                                }
                                                                            }
                                                                        }

                                                                        when {
                                                                            ingressosCancelados > 0 -> {
                                                                                println("OK: $ingressosCancelados ingresso(s) cancelado(s).")
                                                                                println("OK: Total reembolsado de R$$totalReembolsado")
                                                                                println("OK: Evento desativado (${eventoAlterando.nome}).\n")
                                                                            }

                                                                            else -> println("OK: Evento desativado (${eventoAlterando.nome}).\n")
                                                                        }
                                                                    }

                                                                    "2" -> println("OK: Operação cancelada.")
                                                                    else -> println("ERRO: Opção inválida. Solicite novamente.")
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            // Opção indisponível para usuários comuns
                                            TipoUsuario.COMUM -> println("ERRO: Opção inválida. Tente novamente.")
                                        }
                                    }

                                    // Opção para fechar o menu principal
                                    "0" -> {
                                        println("OK: Sessão encerrada.\n")
                                        opcaoMenuLogado = "0"
                                    }

                                    else -> println("ERRO: Opção inválida. Tente novamente.")
                                }
                            } while (opcaoMenuLogado != "0")
                        }
                    }
                }
            }

            "3" -> {

                // Loop para ajustar data (validando valores inseridos)
                do {
                    println("MENU: DEFINIR DATA DE HOJE")
                    print("Digite Somente Dia (DD): ")
                    diaHoje = readln().toIntOrNull() ?: 0
                    print("Digite Somente Mês (MM): ")
                    mesHoje = readln().toIntOrNull() ?: 0
                    print("Digite Somente Ano (AAAA): ")
                    anoHoje = readln().toIntOrNull() ?: 0

                    dataValida = false
                    when {
                        diaHoje in 1..31 && mesHoje in 1..12 && anoHoje >= 2026 -> dataValida = true
                        else -> println("ERRO: Data inválida. Tente novamente.")
                    }
                } while (!dataValida)
                println("OK: DATA DEFINIDA $diaHoje/$mesHoje/$anoHoje.\n")
                dataHoje = (anoHoje * 10000) + (mesHoje * 100) + diaHoje
            }

            // Opção para fechar o menu inicial
            "0" -> {
                print("OK: Operação finalizada.")
            }

            else -> {
                println("ERRO: Opção inválida. Tente novamente.")
            }
        }
    } while (opcaoInicio != "0")
}