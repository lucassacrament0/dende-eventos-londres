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

fun main() {
    // Configurando memória local
    val listaUsuarios = mutableListOf<Usuario>()
    val listaEventos = mutableListOf<Evento>()

    println("BEM-VINDO AO DENDÊ EVENTOS")
    println("MENU: DEFINIR DATA DE HOJE")
    print("Digite Dia (DD): ")
    var diaHoje = readln().toIntOrNull() ?: 20
    print("Digite Mês (MM): ")
    var mesHoje = readln().toIntOrNull() ?: 2
    print("Digite Ano (AAAA): ")
    var anoHoje = readln().toIntOrNull() ?: 2026
    println("OK: DATA DEFINIDA $diaHoje/$mesHoje/$anoHoje.")
    var dataHoje = (anoHoje * 10000) + (mesHoje * 100) + diaHoje

    // Loop do menu inicial
    do {
        println("MENU INICIAL ($diaHoje/$mesHoje/$anoHoje)")
        println("1. Cadastrar Usuário")
        println("2. Acessar Usuário")
        println("3. Ajustar Data")
        println("0. Sair")

        print("Digite opção: ")
        val opcaoInicio = readln()

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
                println("OK: E-MAIL DEFINIDO $cadastroEmail.\n")

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
                        else -> println("ERRO: Data de Nascimento inválida. Tente novamente.\n")
                    }

                    // Define a data válida e segue o cadastro
                    cadastroNascimento = "$diaString/$mesString/$anoString"

                } while (dataInvalida)
                println("OK: DATA DE NASCIMENTO DEFINIDA $cadastroNascimento.\n")

                // Variável e loop para inserir senha (validar com duas etapas)
                var cadastroSenha: String

                do {
                    print("Digite Senha: ")
                    cadastroSenha = readln()
                    print("Confirme Senha: ")
                    val confirmarSenha = readln()
                    when {
                        cadastroSenha == confirmarSenha -> println("OK: SENHA DEFINIDA.\n")
                        else -> println("ERRO: Senhas não conferem. Tente novamente.\n")
                    }
                } while (cadastroSenha != confirmarSenha)

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
                                print("Digite CNPJ: ")
                                cadastroCNPJ = readln()

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
                    usuarioEncontrado == null -> println("ERRO: Email e/ou senha incorretos. Tente novamente.")
                    else -> when {
                        !usuarioEncontrado.statusConta -> {
                            println("\nAVISO: Esta é uma conta desativada. Reativar para acessar? [1] SIM [2] NÃO")
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
                            println("OK: Acesso bem-sucedido.\n")
                            do {
                                println("MENU PRINCIPAL - ÁREA LOGADA ($diaHoje/$mesHoje/$anoHoje)")
                                println("USUÁRIO: ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                                println("[1] Alterar Usuário")
                                println("[2] Visualizar Usuário")
                                println("[3] Desativar Usuário")
                                when {
                                    usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                        println("[7] Cadastrar Evento")
                                        println("[8] Alterar Evento")
                                        println("[9] Desativar Evento")
                                    }
                                }
                                println("[0] Encerrar Sessão")
                                print("Digite opção: ")
                                var opcaoMenuLogado = readln()

                                when (opcaoMenuLogado) {
                                    "1" -> {
                                        var menuAlterarUsuario = true
                                        // Alterar dados de conta ativada
                                        do {
                                            println("MENU: Alterando ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                                            println("OPÇÕES:")
                                            println("[0] Voltar\n[1] Nome [2] Data de Nascimento [3] Sexo [4] Senha")
                                            when {
                                                usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> println(
                                                    "[5] CNPJ [6] Razão Social [7] Nome Fantasia"
                                                )
                                            }
                                            print("Digite opção: ")
                                            val opcaoAlterarUsuario = readln()

                                            when (opcaoAlterarUsuario) {
                                                "0" -> {
                                                    println("OK: Selecionado Voltar.")
                                                    menuAlterarUsuario = false
                                                }

                                                "1" -> {
                                                    print("OK: Digite Nome atualizado: ")
                                                    usuarioEncontrado.nome = readln().uppercase()
                                                    println("OK: NOME DEFINIDO ${usuarioEncontrado.nome}.")
                                                }

                                                "2" -> {
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
                                                            diaInt in 1..31 && mesInt in 1..12 && anoInt in 1920..2020 ->
                                                                dataInvalida = false

                                                            else -> println("ERRO: Data de Nascimento inválida. Tente novamente.")
                                                        }
                                                        usuarioEncontrado.dataNascimento = "$diaString/$mesString/$anoString"
                                                    } while (dataInvalida)
                                                    println("OK: DATA DE NASCIMENTO DEFINIDA ${usuarioEncontrado.dataNascimento}.")
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
                                                    println("OK: SEXO DEFINIDO ${usuarioEncontrado.sexo}.")
                                                }

                                                "4" -> {
                                                    do {
                                                        print("OK: Digite Senha atualizada: ")
                                                        usuarioEncontrado.senha = readln()
                                                        print("OK: Confirme Senha atualizada: ")
                                                        val confirmarSenha = readln()
                                                        when {
                                                            usuarioEncontrado.senha == confirmarSenha -> println("OK: SENHA ATUALIZADA.")
                                                            else -> println("ERRO: Senhas não conferem. Tente novamente.")
                                                        }
                                                    } while (usuarioEncontrado.senha != confirmarSenha)
                                                }

                                                "5" -> {
                                                    when {
                                                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                            print("OK: Digite CNPJ atualizado: ")
                                                            usuarioEncontrado.cnpj = readln()
                                                            println("OK: CNPJ DEFINIDO ${usuarioEncontrado.cnpj}.")
                                                        }

                                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                                    }
                                                }

                                                "6" -> {
                                                    when {
                                                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                            print("OK: Digite Razão Social atualizada: ")
                                                            usuarioEncontrado.razaoSocial = readln().uppercase()
                                                            println("OK: RAZÃO SOCIAL DEFINIDA ${usuarioEncontrado.razaoSocial}.")
                                                        }

                                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                                    }
                                                }

                                                "7" -> {
                                                    when {
                                                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                            print("OK: Digite Nome Fantasia Atualizado: ")
                                                            usuarioEncontrado.nomeFantasia = readln().uppercase()
                                                            println("OK: NOME FANTASIA DEFINIDO ${usuarioEncontrado.nomeFantasia}.")
                                                        }

                                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                                    }
                                                }

                                                else -> println("ERRO: Opção inválida. Tente novamente.")
                                            }
                                        } while (menuAlterarUsuario)
                                    }

                                    "2" -> {
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

                                        // Mostrar dados ao usuário comum
                                        println("\nSEU USUÁRIO:")
                                        println("Nome: ${usuarioEncontrado.nome}")
                                        println("E-mail: ${usuarioEncontrado.email}")
                                        println("Data de Nascimento: ${usuarioEncontrado.dataNascimento}\n")
                                        println("≈ $idadeAno anos, $idadeMes meses e $idadeDia dias")
                                        println("Sexo: ${usuarioEncontrado.sexo}")
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
                                        print("[QUALQUER TECLA] Encerrar Sessão\n")
                                        readln()
                                    }

                                    "3" -> {
                                        println("Desativar a conta? [1] SIM [2] NÃO: ")
                                        print("Digite opção: ")
                                        val desativarConta = readln()

                                        when (desativarConta) {
                                            "1" -> {
                                                // Verifica se é organizador e tem eventos ativados
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
                                                when (possuiEventos) {
                                                    true -> println("ERRO: Você tem eventos ativos. Desativação não permitida.")
                                                    false -> {
                                                        usuarioEncontrado.statusConta = false
                                                        println("\nOK: Conta desativada (${usuarioEncontrado.email}).")
                                                        println("OK: Usuário desconectado.")
                                                        opcaoMenuLogado = "0"
                                                    }
                                                }
                                            }

                                            else -> println("ERRO: Opção inválida. Tente novamente.")
                                        }
                                    }

                                    "7" -> {
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
                                                    print("Digite Dia (DD): ")
                                                    diaInicio = readln().toIntOrNull() ?: 0
                                                    print("Digite Mês (MM): ")
                                                    mesInicio = readln().toIntOrNull() ?: 0
                                                    print("Digite Ano (AAAA): ")
                                                    anoInicio = readln().toIntOrNull() ?: 0
                                                    print("Digite Hora (HH): ")
                                                    horaInicio = readln().toIntOrNull() ?: 0
                                                    print("Digite Minuto (MM): ")
                                                    minutoInicio = readln().toIntOrNull() ?: 0

                                                    println("MENU: DATA DE TÉRMINO")
                                                    print("Digite Dia (DD): ")
                                                    diaFinal = readln().toIntOrNull() ?: 0
                                                    print("Digite Mês (MM): ")
                                                    mesFinal = readln().toIntOrNull() ?: 0
                                                    print("Digite Ano (AAAA): ")
                                                    anoFinal = readln().toIntOrNull() ?: 0
                                                    print("Digite Hora (HH): ")
                                                    horaFinal = readln().toIntOrNull() ?: 0
                                                    print("Digite Minuto (MM): ")
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

                                                        else -> {
                                                            println("OK: Datas OK.")
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
                                                        print("Digite ID do Evento Principal (ou 0 para pular): ")
                                                        val eventoPrincipal = readln().toIntOrNull()
                                                        var eventoEncontrado = false
                                                        for (evento in listaEventos) {
                                                            when (evento.id) {
                                                                eventoPrincipal -> eventoEncontrado = true
                                                            }
                                                        }
                                                        when (eventoEncontrado) {
                                                            true -> {
                                                                idEventoPrincipal = eventoPrincipal
                                                                println("OK: ID $eventoPrincipal DEFINIDO COMO EVENTO PRINCIPAL DE $cadastroNome.\n")
                                                            }

                                                            false -> println("ERRO: ID $eventoPrincipal não encontrado. Vinculação não realizada.\n")
                                                        }
                                                    }

                                                    "2" -> println("OK: DEFINIDO EVENTO INDEPENDENTE.")
                                                    else -> print("ERRO: Opção inválida. Tente novamente.")
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

                                                print("Digite Capacidade Máxima de Pessoas: ")
                                                val cadastroCapacidade = readln().toIntOrNull() ?: 0
                                                println("OK: CAPACIDADE DEFINIDA $cadastroCapacidade.\n")

                                                print("Digite Local do Evento (endereço ou link): ")
                                                val cadastroLocal = readln()

                                                print("Digite Preço do Ingresso: ")
                                                val cadastroPreco = readln().toDoubleOrNull() ?: 0.0
                                                println("OK: PREÇO DEFINIDO $cadastroPreco.\n")

                                                println("Aceita estorno/devolução de ingresso? [1] SIM [2] NÃO")
                                                print("Digite opção: ")
                                                val cadastroEstorno = readln()
                                                val aceitaEstorno: Boolean =
                                                    when (cadastroEstorno) {
                                                        "1" -> true
                                                        else -> false
                                                    }
                                                println("OK: ACEITA ESTORNO DEFINIDO $aceitaEstorno.")
                                                val cadastroTaxa: Double
                                                when (aceitaEstorno) {
                                                    true -> {
                                                        print("Digite Taxa de Estorno: ")
                                                        cadastroTaxa = readln().toDoubleOrNull() ?: 0.0
                                                        println("OK: TAXA DE ESTORNO DEFINIDA $cadastroTaxa.\n")
                                                    }

                                                    false -> {
                                                        cadastroTaxa = 0.0
                                                    }
                                                }

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
                                                println("SUCESSO: Evento cadastrado (ID ${novoEvento.id}).")
                                            }

                                            else -> println("ERRO: Opção inválida. Tente novamente.")
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
                                                                when {
                                                                    !eventoAlterando.statusEvento -> {
                                                                        println("\nAVISO: AVISO: Este é um evento desativado. Reativar para alterar? [1] SIM [2] NÃO")
                                                                        print("Digite opção: ")
                                                                        val reativarEvento = readln()
                                                                        when (reativarEvento) {
                                                                            "1" -> {
                                                                                eventoAlterando.statusEvento = true
                                                                                println("OK: Evento reativado.")
                                                                            }

                                                                            "2" -> println("OK: Operação cancelada.")
                                                                            else -> println("ERRO: Opção inválida. Tente novamente.")
                                                                        }
                                                                    }

                                                                    else -> {
                                                                        var menuAlterarEvento = true
                                                                        do {
                                                                            println("\nMENU: EDITANDO ${eventoAlterando.nome} (${eventoAlterando.id}).")
                                                                            println("OPÇÕES:")
                                                                            println("[0] Voltar\n[1] Nome [2] Página [3] Descrição [4] Período [5] Tipo")
                                                                            println("[6] Evento Vinculado [7] Modalidade [8] Capacidade [9] Local [10] Preço/Estorno")
                                                                            print("Digite opção: ")
                                                                            val opcaoAlterarEvento = readln()

                                                                            when (opcaoAlterarEvento) {
                                                                                "0" -> {
                                                                                    println("OK: Selecionado Voltar.")
                                                                                    menuAlterarEvento = false
                                                                                }

                                                                                "1" -> {
                                                                                    print("Digite Nome atualizado: ")
                                                                                    eventoAlterando.nome = readln()
                                                                                    println("OK: NOME DEFINIDO ${eventoAlterando.nome}.")
                                                                                }

                                                                                "2" -> {
                                                                                    print("Digite Página atualizada: ")
                                                                                    eventoAlterando.pagina = readln()
                                                                                    println("OK: PÁGINA DEFINIDA ${eventoAlterando.pagina}.")
                                                                                }

                                                                                "3" -> {
                                                                                    print("Digite Descrição atualizada: ")
                                                                                    eventoAlterando.descricao = readln()
                                                                                    println("OK: DESCRIÇÃO DEFINIDA ${eventoAlterando.descricao}.")
                                                                                }

                                                                                "4" -> {
                                                                                    var dataValida = false

                                                                                    do {
                                                                                        println("\nALTERAR PERÍODO DO EVENTO")

                                                                                        println("MENU: DATA DE INÍCIO")
                                                                                        print("Digite Dia (DD): ")
                                                                                        val diaInicio = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Mês (MM): ")
                                                                                        val mesInicio = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Ano (AAAA): ")
                                                                                        val anoInicio = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Hora (HH): ")
                                                                                        val horaInicio = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Minuto (MM): ")
                                                                                        val minutoInicio = readln().toIntOrNull() ?: 0

                                                                                        println("MENU: DATA DE TÉRMINO")
                                                                                        print("Digite Dia (DD): ")
                                                                                        val diaFinal = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Mês (MM): ")
                                                                                        val mesFinal = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Ano (AAAA): ")
                                                                                        val anoFinal = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Hora (HH): ")
                                                                                        val horaFinal = readln().toIntOrNull() ?: 0
                                                                                        print("Digite Minuto (MM): ")
                                                                                        val minutoFinal = readln().toIntOrNull() ?: 0

                                                                                        val dataInicio = (anoInicio * 10000) + (mesInicio * 100) + diaInicio
                                                                                        val dataFim = (anoFinal * 10000) + (mesFinal * 100) + diaFinal

                                                                                        val minutagemInicio = (horaInicio * 60) + minutoInicio
                                                                                        val minutagemFim = (horaFinal * 60) + minutoFinal

                                                                                        when {
                                                                                            dataInicio < dataHoje ->
                                                                                                println("ERRO: O evento não pode ser no passado.")

                                                                                            dataFim < dataInicio ->
                                                                                                println("ERRO: Data de término antes da data de início.")

                                                                                            dataFim == dataInicio && minutagemFim < minutagemInicio ->
                                                                                                println("ERRO: Hora de término antes da hora de início.")

                                                                                            dataFim == dataInicio && (minutagemFim - minutagemInicio) < 30 ->
                                                                                                println("ERRO: A duração mínima é de 30 minutos.")

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
                                                                                                println("OK:\nDATA DE INÍCIO DEFINIDA $diaInicio/$mesInicio/$anoInicio.")
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
                                                                                            println("Sub-Evento ${eventoAlterando.nome} Principal (ID ${eventoAlterando.idEventoPrincipal}).")
                                                                                            println("Alterar principal? [1] SIM [2] NÃO")
                                                                                        }
                                                                                    }
                                                                                    print("Digite opção: ")
                                                                                    val vincularPrincipal = readln()

                                                                                    when (vincularPrincipal) {
                                                                                        "1" -> {
                                                                                            print("Digite ID do Evento Principal (ou 0 para desvincular): ")
                                                                                            val eventoPrincipal = readln().toIntOrNull()
                                                                                            var idValido = false
                                                                                            when {
                                                                                                eventoPrincipal == 0 -> eventoAlterando.idEventoPrincipal = null
                                                                                            }
                                                                                            for (evento in listaEventos) {
                                                                                                when {
                                                                                                    evento.id == eventoPrincipal && evento.id != eventoAlterando.id && evento.organizadorEmail ==
                                                                                                            usuarioEncontrado.email && evento.statusEvento -> idValido = true
                                                                                                }
                                                                                            }
                                                                                            when (idValido) {
                                                                                                true -> {
                                                                                                    eventoAlterando.idEventoPrincipal = eventoPrincipal
                                                                                                    println("OK: ID $eventoPrincipal DEFINIDO COMO EVENTO PRINCIPAL DE ${eventoAlterando.nome}\n")
                                                                                                }

                                                                                                else -> println("AVISO: Vinculação não realizada.")
                                                                                            }
                                                                                        }

                                                                                        "2" -> println("OK: Operação cancelada.")
                                                                                        else -> print("ERRO: Opção inválida. Tente novamente.")
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
                                                                                    print("Digite Capacidade atualizada: ")
                                                                                    eventoAlterando.capacidadeMax = readln().toIntOrNull() ?: 0
                                                                                    println("OK: CAPACIDADE DEFINIDA ${eventoAlterando.capacidadeMax}.")
                                                                                }

                                                                                "9" -> {
                                                                                    print("Digite Local atualizado: ")
                                                                                    eventoAlterando.local = readln()
                                                                                    println("OK: LOCAL DEFINIDO ${eventoAlterando.local}.")
                                                                                }

                                                                                "10" -> {
                                                                                    print("Digite Preço do Ingresso atualizado: ")
                                                                                    eventoAlterando.precoIngresso = readln().toDoubleOrNull() ?: 0.0
                                                                                    println("OK: PREÇO DEFINIDO ${eventoAlterando.precoIngresso}.")
                                                                                    println("ALTERANDO: Aceita estorno/devolução de ingresso? [1] SIM [2] NÃO")
                                                                                    print("Digite opção: ")
                                                                                    val alterarEstorno = readln()
                                                                                    when (alterarEstorno) {
                                                                                        "1" -> {
                                                                                            eventoAlterando.aceitaEstorno = true
                                                                                            println("OK: ACEITA ESTORNO DEFINIDO ${eventoAlterando.aceitaEstorno}.")
                                                                                            print("Digite Taxa de Estorno atualizada: ")
                                                                                            val alterarTaxa = readln().toDoubleOrNull() ?: 0.0
                                                                                            eventoAlterando.taxaEstorno = alterarTaxa
                                                                                            println("OK: TAXA DE ESTORNO DEFINIDA ${eventoAlterando.taxaEstorno}.\n")
                                                                                        }

                                                                                        else -> {
                                                                                            eventoAlterando.aceitaEstorno = false
                                                                                            eventoAlterando.taxaEstorno = 0.0
                                                                                            println("OK: ACEITA ESTORNO DEFINIDO ${eventoAlterando.aceitaEstorno}.")
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
                                            }

                                            TipoUsuario.COMUM -> println("ERRO: Opção inválida. Tente novamente.")
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
                                                    null -> println("ERRO: Evento inválido. Tente novamente.")
                                                    else -> {
                                                        println("Desativar o evento ${eventoAlterando.nome}? [1] SIM [2] NÃO: ")
                                                        print("Digite opção: ")
                                                        val desativarEvento = readln()

                                                        when (desativarEvento) {
                                                            "1" -> {
                                                                eventoAlterando.statusEvento = false
                                                                println("\nOK: Evento desativado (${eventoAlterando.nome}).")
                                                            }

                                                            "2" -> println("OK: Operação cancelada.")
                                                            else -> println("ERRO: Opção inválida. Tente novamente.")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } while (opcaoMenuLogado != "0")
                        }
                    }
                }
            }

            "3" -> {
                println("\nAJUSTAR DATA ($diaHoje/$mesHoje/$anoHoje)")
                print("Digite Dia Atualizado (DD): ")
                diaHoje = readln().toIntOrNull() ?: 20
                print("Digite Mês Atualizado (MM): ")
                mesHoje = readln().toIntOrNull() ?: 2
                print("Digite Ano Atualizado (AAAA): ")
                anoHoje = readln().toIntOrNull() ?: 2026
                println("OK: Data atualizada para $diaHoje/$mesHoje/$anoHoje.\n")
                dataHoje = (anoHoje * 10000) + (mesHoje * 100) + diaHoje
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