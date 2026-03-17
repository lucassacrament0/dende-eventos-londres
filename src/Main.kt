fun main() {

    // Configurando memória local
    val listaUsuarios = mutableListOf<Usuario>()
    val listaEventos = mutableListOf<Evento>()
    val listaIngressos = mutableListOf<Ingresso>()
    var diaHoje: Int
    var mesHoje: Int
    var anoHoje: Int
    var dataHoje: Int

    println("BEM-VINDO AO DENDÊ EVENTOS")

    // Definir data (válida)
    println("MENU: DEFINIR DATA DE HOJE")
    diaHoje = readInt("Digite Somente Dia (DD): ", "ERRO: Dia inválido. Tente novamente.", 1..31)
    mesHoje = readInt("Digite Somente Mês (MM): ", "ERRO: Mês inválido. Tente novamente.", 1..12)
    anoHoje = readInt("Digite Somente Ano (AAAA): ", "ERRO: Ano inválido. Tente novamente.", 2026..Int.MAX_VALUE)
    println("OK: DATA DEFINIDA $diaHoje/$mesHoje/$anoHoje.\n")
    dataHoje = (anoHoje * 10000) + (mesHoje * 100) + diaHoje

    // Loop do menu inicial
    do {
        println("MENU INICIAL ($diaHoje/$mesHoje/$anoHoje)")
        println("1. Cadastrar Usuário")
        println("2. Acessar Usuário")
        println("3. Ajustar Data")
        println("0. Sair")
        val opcaoMenuInicial = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 0..3)

        // Opções do menu
        when (opcaoMenuInicial) {
            1 -> {
                println("\nCADASTRAR USUÁRIO")

                // Variáveis e loop para cadastro e validação de e-mail
                var cadastroEmail: String
                do {
                    var emailInvalido = false
                    var emailConfirmado = false
                    cadastroEmail = readString("Digite E-mail: ", "ERRO: E-mail inválido. Tente novamente.", 3).lowercase()
                    val confirmarEmail = readString("Confirme E-mail: ", "ERRO: E-mail inválido. Tente novamente.", 3).lowercase()

                    // Verifica se os e-mails inseridos conferem
                    when {
                        cadastroEmail == confirmarEmail -> emailConfirmado = true
                        else -> println("ERRO: E-mails não conferem. Tente novamente.\n")
                    }

                    // Verifica se o e-mail contém um @ e um .
                    when {
                        !cadastroEmail.contains("@") || !cadastroEmail.contains(".") -> {
                            emailInvalido = true
                            println("ERRO: E-mail inválido. Tente novamente.\n")
                        }

                        cadastroEmail.contains("@") && cadastroEmail.contains(".") -> emailInvalido = false
                    }

                    // Verifica se o e-mail já existe
                    when {
                        listaUsuarios.any { it.email == cadastroEmail } -> {
                            emailInvalido = true
                            println("ERRO: E-mail já cadastrado. Tente novamente.")
                        }
                    }
                } while (emailInvalido || !emailConfirmado)
                println("OK: E-MAIL DEFINIDO '$cadastroEmail'.\n")

                val cadastroNome = readString("Digite Nome: ", "ERRO: Nome inválido. Tente novamente.", 2).uppercase()
                println("OK: NOME DEFINIDO '$cadastroNome'.\n")

                // Variável e cadastro de data de nascimento (válida)
                println("\nMENU: DEFINIR DATA DE NASCIMENTO")
                val diaNascimento = readInt("Digite Somente Dia de Nascimento (DD): ", "ERRO: Dia inválido. Tente novamente.", 1..31)
                val mesNascimento = readInt("Digite Somente Mês de Nascimento (MM): ", "ERRO: Mês inválido. Tente novamente.", 1..12)
                val anoNascimento = readInt("Digite Somente Ano de Nascimento (AAAA): ", "ERRO: Ano inválido. Tente novamente.", 1920..2020)
                val cadastroNascimento = "$diaNascimento/$mesNascimento/$anoNascimento"
                println("OK: DATA DE NASCIMENTO DEFINIDA $cadastroNascimento.\n")

                // Variável e loop para inserir senha (validar com duas etapas)
                var cadastroSenha: String
                do {
                    cadastroSenha = readString("Digite Nova Senha: ", "ERRO: A senha deve ter no mínimo 8 caracteres. Tente novamente. ", 8)
                    val confirmarSenha = readString("Confirme Nova Senha: ", "ERRO: A senha deve ter no mínimo 8 caracteres. Tente novamente. ", 8)

                    when {
                        cadastroSenha != confirmarSenha -> println("ERRO: Senhas não conferem. Tente novamente.\n")
                        else -> println("OK: SENHA DEFINIDA.\n")
                    }
                } while (cadastroSenha != confirmarSenha)

                // Define o sexo (Utiliza o enum para limitar as opções)
                println("Sexo: [1] MASCULINO, [2] FEMININO, [3] PREFIRO NÃO INFORMAR")
                val opcaoSexo = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..3)

                val cadastroSexo = when (opcaoSexo) {
                    1 -> SexoUsuario.MASCULINO
                    2 -> SexoUsuario.FEMININO
                    else -> SexoUsuario.NAO_INFORMADO
                }
                println("OK: SEXO DEFINIDO $cadastroSexo.\n")

                // Verifica se é usuário organizador
                println("Você é organizador de eventos? [1] SIM, [2] NÃO")
                val tipoUsuario = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)

                val cadastroOrganizador = when (tipoUsuario) {
                    1 -> TipoUsuario.ORGANIZADOR
                    else -> TipoUsuario.COMUM
                }
                println("OK: DEFINIDO USUÁRIO $cadastroOrganizador.\n")

                // Variaveis para cadastrar empresas
                val cadastroEmpresa: Int
                var cadastroCNPJ: String? = null
                var cadastroRazaoSocial: String? = null
                var cadastroNomeFantasia: String? = null

                // Para os organizadores...
                when (tipoUsuario) {
                    1 -> {

                        // Verifica se tem empresa
                        println("Você possui uma empresa? [1] SIM [2] NÃO")
                        cadastroEmpresa = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)

                        when (cadastroEmpresa) {
                            1 -> {
                                println("\nCADASTRO DE EMPRESA")

                                // Cadastro de CNPJ (válido)
                                cadastroCNPJ = readString("Digite CNPJ (14 dígitos): ", "ERRO: CNPJ inválido. Tente novamente.", 14)
                                println("OK: CNPJ DEFINIDO '$cadastroCNPJ'.\n")

                                cadastroRazaoSocial = readString("Digite Razão Social: ", "ERRO: Razão Social inválida. Tente novamente.", 2).uppercase()
                                println("OK: RAZÃO SOCIAL DEFINIDA '$cadastroRazaoSocial'.\n")

                                cadastroNomeFantasia = readString("Digite Nome Fantasia: ", "ERRO: Nome Fantasia inválido. Tente novamente.", 2).uppercase()
                                println("OK: NOME FANTASIA DEFINIDO '$cadastroNomeFantasia'.\n")
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
                    razaoSocial = cadastroRazaoSocial,
                    nomeFantasia = cadastroNomeFantasia
                )
                listaUsuarios.add(cadastroUsuario)
                println("OK: USUÁRIO CADASTRADO COM SUCESSO.\n")
            }

            2 -> {
                println("\nACESSAR USUÁRIO")
                val buscarEmail = readString("Digite o e-mail da sua conta: ", "ERRO: E-mail inválido. Tente novamente.", 3).lowercase()
                val buscarSenha = readString("Digite a senha da sua conta: ", "ERRO: Senha inválida. Tente novamente.", 8)

                // Cria variável associada com o data class Usuario e busca login do usuário
                val usuarioEncontrado: Usuario? = listaUsuarios.find { it.email == buscarEmail && it.senha == buscarSenha }

                // Se o usuário não foi localizado, mostra mensagem de erro
                when {
                    usuarioEncontrado == null -> println("ERRO: E-mail e/ou senha incorretos. Solicite novamente.\n")
                    else -> when {
                        !usuarioEncontrado.statusConta -> {
                            println("\nAVISO: Esta é uma conta desativada. Reativar para acessar? [1] SIM [2] NÃO")
                            val reativarConta = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)
                            when (reativarConta) {
                                1 -> {
                                    usuarioEncontrado.statusConta = true
                                    println("OK: Conta reativada. Acesse novamente.")
                                }

                                else -> println("OK: Operação cancelada.")
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
                                val limiteOpcoes = when (usuarioEncontrado.tipoUsuario) {
                                    TipoUsuario.COMUM -> (0..5)
                                    else -> 0..9
                                }
                                var opcaoMenuLogado = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", limiteOpcoes)

                                // Opções do menu logado
                                when (opcaoMenuLogado) {
                                    1 -> {
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
                                            val limiteOpcoes = when (usuarioEncontrado.tipoUsuario) {
                                                TipoUsuario.ORGANIZADOR -> 0..7
                                                else -> 0..4
                                            }
                                            val opcaoAlterarUsuario = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", limiteOpcoes)

                                            when (opcaoAlterarUsuario) {
                                                0 -> {
                                                    println("OK: Selecionado Voltar.\n")
                                                    menuAlterarUsuario = false
                                                }

                                                1 -> {
                                                    usuarioEncontrado.nome = readString("Digite Nome atualizado: ", "ERRO: Nome inválido. Tente novamente.", 2).uppercase()
                                                    println("OK: NOME DEFINIDO '${usuarioEncontrado.nome}'.\n")
                                                }

                                                2 -> {

                                                    // Alterar data de nascimento (válida)
                                                    println("MENU: ALTERAR DATA DE NASCIMENTO")
                                                    val diaNascimento = readInt("Digite Somente Dia de Nascimento (DD) atualizado: ", "ERRO: Dia inválido. Tente novamente.", 1..31)
                                                    val mesNascimento = readInt("Digite Somente Mês de Nascimento (MM) atualizado: ", "ERRO: Mês inválido. Tente novamente.", 1..12)
                                                    val anoNascimento = readInt("Digite Somente Ano de Nascimento (AAAA) atualizado: ", "ERRO: Ano inválido. Tente novamente.", 1920..2020)
                                                    usuarioEncontrado.dataNascimento = "$diaNascimento/$mesNascimento/$anoNascimento"
                                                    println("OK: DATA DE NASCIMENTO DEFINIDA '${usuarioEncontrado.dataNascimento}'.")
                                                }

                                                3 -> {
                                                    println("ALTERANDO: Sexo \n[1] MASCULINO, [2] FEMININO, [3] NÃO INFORMADO")
                                                    val alterarSexo = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..3)
                                                    usuarioEncontrado.sexo =
                                                        when (alterarSexo) {
                                                            1 -> SexoUsuario.MASCULINO
                                                            2 -> SexoUsuario.FEMININO
                                                            else -> SexoUsuario.NAO_INFORMADO
                                                        }
                                                    println("OK: SEXO DEFINIDO ${usuarioEncontrado.sexo}.\n")
                                                }

                                                4 -> {

                                                    // Variável e loop para inserir senha (validar com duas etapas)
                                                    var cadastroSenha: String
                                                    do {
                                                        cadastroSenha = readString("Digite Senha atualizada: ", "ERRO: A senha deve ter no mínimo 8 caracteres. Tente novamente. ", 8)
                                                        val confirmarSenha = readString("Confirme Senha atualizada: ", "ERRO: A senha deve ter no mínimo 8 caracteres. Tente novamente. ", 8)

                                                        when {
                                                            cadastroSenha != confirmarSenha -> println("ERRO: Senhas não conferem. Tente novamente.\n")
                                                            else -> println("OK: SENHA ATUALIZADA.\n")
                                                        }
                                                    } while (cadastroSenha != confirmarSenha)
                                                    usuarioEncontrado.senha = cadastroSenha
                                                }

                                                5 -> {

                                                    // Validação de usuário (organizador) e digitar CNPJ (válido)
                                                    when {
                                                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                            usuarioEncontrado.cnpj = readString("Digite CNPJ (14 dígitos) atualizado: ", "ERRO: CNPJ inválido. Tente novamente.", 14)
                                                            println("OK: CNPJ DEFINIDO '${usuarioEncontrado.cnpj}'.\n")
                                                        }

                                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                                    }
                                                }

                                                6 -> {
                                                    when {
                                                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                            usuarioEncontrado.razaoSocial = readString("Digite Razão Social atualizada: ", "ERRO: Razão Social inválida. Tente novamente.", 2).uppercase()
                                                            println("OK: RAZÃO SOCIAL DEFINIDA '${usuarioEncontrado.razaoSocial}'.\n")
                                                        }

                                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                                    }
                                                }

                                                7 -> {
                                                    when {
                                                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                                            usuarioEncontrado.nomeFantasia = readString("Digite Nome Fantasia atualizado: ", "ERRO: Nome Fantasia inválido. Tente novamente.", 2).uppercase()
                                                            println("OK: NOME FANTASIA DEFINIDO '${usuarioEncontrado.nomeFantasia}'.\n")
                                                        }

                                                        else -> println("ERRO: Opção inválida. Tente novamente.")
                                                    }
                                                }
                                            }
                                        } while (menuAlterarUsuario)
                                    }

                                    2 -> {

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

                                    3 -> {
                                        println("Desativar a conta? [1] SIM [2] NÃO: ")
                                        val desativarConta = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)

                                        when (desativarConta) {
                                            1 -> {

                                                // Verifica se é usuário organizador e busca por eventos ativados
                                                val possuiEventos = when (usuarioEncontrado.tipoUsuario) {
                                                    TipoUsuario.ORGANIZADOR -> {
                                                        listaEventos.any { it.organizadorEmail == usuarioEncontrado.email && it.statusEvento }
                                                    }

                                                    else -> false
                                                }

                                                // Caso haja eventos ativos para o organizador, não desativa a conta
                                                when (possuiEventos) {
                                                    true -> println("ERRO: Você tem eventos ativos. Desativação não permitida.")
                                                    false -> {
                                                        usuarioEncontrado.statusConta = false
                                                        println("\nOK: Conta desativada (${usuarioEncontrado.email}).")
                                                        println("OK: Usuário desconectado.\n")
                                                        opcaoMenuLogado = 0
                                                    }
                                                }
                                            }

                                            else -> println("ERRO: Opção inválida. Solicite novamente.")
                                        }
                                    }

                                    4 -> {
                                        when (usuarioEncontrado.tipoUsuario) {
                                            TipoUsuario.COMUM -> {
                                                println("\nFEED DE EVENTOS")

                                                // Lista com eventos disponíveis
                                                val eventosDisponiveis = listaEventos.filter { evento ->
                                                    val dataEvento = (evento.anoInicio * 10000) + (evento.mesInicio * 100) + evento.diaInicio
                                                    val ingressosVendidos = listaIngressos.count { ingresso ->
                                                        ingresso.idEvento == evento.id && !ingresso.statusDisponibilidade
                                                    }

                                                    evento.statusEvento && dataEvento >= dataHoje && ingressosVendidos < evento.capacidadeMax
                                                }

                                                // Eventos ordenados por data e nome
                                                val eventosOrdenados = eventosDisponiveis.sortedWith(
                                                    compareBy({ evento -> evento.anoInicio }, { evento -> evento.mesInicio }, { evento -> evento.diaInicio }, { evento -> evento.nome })
                                                )

                                                when (eventosOrdenados.isEmpty()) {
                                                    true -> println("AVISO: Nenhum evento disponível no momento.")
                                                    false -> {
                                                        val existemEventos = true
                                                        val colunas = listOf("ID", "NOME", "DATA", "LOCAL", "PREÇO", "VAGAS")

                                                        // Exibição da lista
                                                        val linhas = eventosOrdenados.map { evento ->
                                                            val ingressosVendidos = listaIngressos.count { ingresso -> ingresso.idEvento == evento.id && !ingresso.statusDisponibilidade }
                                                            val vagasRestantes = evento.capacidadeMax - ingressosVendidos

                                                            listOf(
                                                                evento.id.toString(),
                                                                evento.nome,
                                                                "${evento.diaInicio.toString().padStart(2, '0')}/${evento.mesInicio.toString().padStart(2, '0')}/${evento.anoInicio}",
                                                                evento.local,
                                                                "R$${evento.precoIngresso}",
                                                                vagasRestantes.toString()
                                                            )
                                                        }

                                                        printTable("EVENTOS DISPONÍVEIS", colunas, linhas)

                                                        // Se eventos existirem na lista, possibilita expandir um evento
                                                        when (existemEventos) {
                                                            false -> println("AVISO: Nenhum evento encontrado.\n")
                                                            true -> {
                                                                val opcaoID = readInt("Digite o ID do evento para expandir/comprar ingresso (0 para voltar): ", "ERRO: ID inválido. Tente novamente.")

                                                                when (opcaoID) {
                                                                    0 -> println("OK: Selecionado Voltar.")
                                                                    else -> {

                                                                        // Busca pelo ID inserido para expandir evento
                                                                        val eventoDetalhes: Evento? = listaEventos.find { it.id == opcaoID && it.statusEvento }

                                                                        // Com base no ID inserido, exibe detalhes do evento ou não
                                                                        when (eventoDetalhes) {
                                                                            null -> println("ERRO: Nenhum evento encontrado.")
                                                                            else -> {
                                                                                println("Nome: ${eventoDetalhes.nome}")
                                                                                println("Descrição: ${eventoDetalhes.descricao}")
                                                                                println("Página: ${eventoDetalhes.pagina}")
                                                                                println(
                                                                                    "Início: ${eventoDetalhes.diaInicio}/${eventoDetalhes.mesInicio}/${eventoDetalhes.anoInicio} " +
                                                                                            "às ${eventoDetalhes.horaInicio}:${eventoDetalhes.minutoInicio.toString().padStart(2, '0')}"
                                                                                )
                                                                                println(
                                                                                    "Término: ${eventoDetalhes.diaTermino}/${eventoDetalhes.mesTermino}/${eventoDetalhes.anoTermino} " +
                                                                                            "às ${eventoDetalhes.horaTermino}:${eventoDetalhes.minutoTermino.toString().padStart(2, '0')}"
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
                                                                                val opcaoCompra = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 0..1)

                                                                                when (opcaoCompra) {
                                                                                    1 -> {

                                                                                        // Confirma a compra em caso de evento sem atribuição a outro
                                                                                        when (eventoDetalhes.idEventoPrincipal) {
                                                                                            null -> {
                                                                                                println("\nCOMPRAR INGRESSO")
                                                                                                println("\nEvento: ${eventoDetalhes.nome}")
                                                                                                println("Preço Total: R$${eventoDetalhes.precoIngresso}")
                                                                                                println("\n[1] Confirmar Compra  [0] Cancelar")
                                                                                                val confirmarCompra = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 0..1)

                                                                                                // Cria o ingresso, adicionando a data class Ingresso
                                                                                                when (confirmarCompra) {
                                                                                                    1 -> {
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

                                                                                                    else -> println("OK: Compra cancelada.")
                                                                                                }
                                                                                            }

                                                                                            // Valida o ID de evento digitado
                                                                                            else -> {
                                                                                                val eventoPrincipal: Evento? = listaEventos.find { it.id == eventoDetalhes.idEventoPrincipal && it.statusEvento }

                                                                                                when (eventoPrincipal) {
                                                                                                    null -> println("ERRO: Evento inexistente ou indisponível.\n")

                                                                                                    // Calcula se o evento principal tem ingressos disponíveis
                                                                                                    else -> {
                                                                                                        val vendidosPrincipal = listaIngressos.count { it.idEvento == eventoPrincipal.id && !it.statusDisponibilidade }
                                                                                                        val ingressosPrincipal = eventoPrincipal.capacidadeMax - vendidosPrincipal

                                                                                                        when {
                                                                                                            ingressosPrincipal <= 0 -> println("ERRO: Evento Principal '${eventoPrincipal.nome}' indisponível.")

                                                                                                            // Confirma a compra em caso de sub-evento com atribuição a evento principal
                                                                                                            else -> {
                                                                                                                val ingressosSomados = eventoDetalhes.precoIngresso + eventoPrincipal.precoIngresso
                                                                                                                println("\nAVISO: Este evento exige compra dupla.")
                                                                                                                println("- Sub-Evento: ${eventoDetalhes.nome} (R$ ${eventoDetalhes.precoIngresso})")
                                                                                                                println("- Evento Principal:  ${eventoPrincipal.nome} (R$ ${eventoPrincipal.precoIngresso})")
                                                                                                                println("VALOR TOTAL:  R$$ingressosSomados")

                                                                                                                println("\n[1] Confirmar Compra Dupla  [0] Cancelar")
                                                                                                                val confirmarCompraDupla = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 0..1)

                                                                                                                when (confirmarCompraDupla) {
                                                                                                                    1 -> {

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

                                                                                                                    else -> println("OK: Compra cancelada.")
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }

                                                                                    else -> println("OK: Operação cancelada.\n")
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

                                    5 -> {
                                        when (usuarioEncontrado.tipoUsuario) {
                                            TipoUsuario.COMUM -> {
                                                println("\nVISUALIZAR INGRESSOS")

                                                // Filtro de ingressos do usuário
                                                val ingressosUsuario = listaIngressos.filter { ingresso ->
                                                    ingresso.emailUsuario == usuarioEncontrado.email
                                                }

                                                when (ingressosUsuario.isEmpty()) {
                                                    true -> println("AVISO: Você não possui ingressos cadastrados.")
                                                    false -> {

                                                        // Listas para ingressos
                                                        val ingressosAtivos = mutableListOf<Ingresso>()
                                                        val ingressosInativos = mutableListOf<Ingresso>()
                                                        val possuiIngressos = true

                                                        ingressosUsuario.forEach { ingresso ->
                                                            val eventoEncontrado = listaEventos.find { evento -> evento.id == ingresso.idEvento }

                                                            val dataEvento = when (eventoEncontrado) {
                                                                null -> 0
                                                                else -> (eventoEncontrado.anoInicio * 10000) + (eventoEncontrado.mesInicio * 100) + eventoEncontrado.diaInicio
                                                            }

                                                            // Lista de cancelados/realizados e lista de ingressos futuros
                                                            when {
                                                                ingresso.statusDisponibilidade || dataEvento < dataHoje -> ingressosInativos.add(ingresso)
                                                                else -> ingressosAtivos.add(ingresso)
                                                            }
                                                        }

                                                        // Ordenação dos ingressos por data
                                                        val ordenarIngressos = compareBy<Ingresso>(
                                                            { ingresso -> listaEventos.find { evento -> evento.id == ingresso.idEvento }?.anoInicio },
                                                            { ingresso -> listaEventos.find { evento -> evento.id == ingresso.idEvento }?.mesInicio },
                                                            { ingresso -> listaEventos.find { evento -> evento.id == ingresso.idEvento }?.diaInicio },
                                                            { ingresso -> listaEventos.find { evento -> evento.id == ingresso.idEvento }?.nome }
                                                        )

                                                        // Listas ordenadas com base no modelo de ordenação
                                                        val ingressosOrdenados = ingressosAtivos.sortedWith(ordenarIngressos) + ingressosInativos.sortedWith(ordenarIngressos)

                                                        // Exibição de listas
                                                        val colunas = listOf("ID", "EVENTO", "VALOR", "STATUS")
                                                        val linhas = ingressosOrdenados.map { ingresso ->
                                                            val eventoDoIngresso = listaEventos.find { evento -> evento.id == ingresso.idEvento }

                                                            val statusTexto = when (ingresso.statusDisponibilidade) {
                                                                true -> "CANCELADO/REALIZADO"
                                                                false -> "OK"
                                                            }

                                                            listOf(
                                                                ingresso.id.toString(),
                                                                eventoDoIngresso?.nome ?: "N/A",
                                                                "R$${ingresso.valorPago}",
                                                                statusTexto
                                                            )
                                                        }
                                                        printTable("SEUS INGRESSOS:", colunas, linhas)

                                                        when (possuiIngressos) {
                                                            false -> println("AVISO: Você não possui ingressos.")

                                                            // Possibilita expandir um ingresso
                                                            true -> {
                                                                val idIngresso = readInt("Digite ID do ingresso para expandir/cancelar (0 para Voltar): ", "ERRO: ID inválido. Tente novamente.")

                                                                when (idIngresso) {
                                                                    0 -> println("OK: Voltando ao Menu Principal.")
                                                                    else -> {
                                                                        val ingressoExpandido = listaIngressos.find { it.id == idIngresso && it.emailUsuario == usuarioEncontrado.email }

                                                                        when (ingressoExpandido) {
                                                                            null -> println("ERRO: Ingresso inexistente ou indisponível.")
                                                                            else -> {
                                                                                val eventoDoIngresso = listaEventos.find { it.id == ingressoExpandido.idEvento }

                                                                                when (eventoDoIngresso) {
                                                                                    null -> println("ERRO: Nenhum ingresso encontrado.")
                                                                                    else -> {
                                                                                        println("\nDETALHES DO INGRESSO")
                                                                                        println("ID: ${ingressoExpandido.id}")
                                                                                        println("Evento: ${eventoDoIngresso.nome}")
                                                                                        println(
                                                                                            "Data: ${eventoDoIngresso.diaInicio}/${eventoDoIngresso.mesInicio}/${eventoDoIngresso.anoInicio} " +
                                                                                                    "às ${eventoDoIngresso.horaInicio}:${eventoDoIngresso.minutoInicio.toString().padStart(2, '0')}"
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
                                                                                        val opcaoCancelamento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)

                                                                                        when (opcaoCancelamento) {
                                                                                            1 -> {

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
                                                                                                                println("- Valor a ser estornado: R$$valorReembolso")
                                                                                                            }

                                                                                                            false -> {
                                                                                                                println("- Aceita Reembolso: NÃO")
                                                                                                                println("- O cancelamento não devolve valores.")
                                                                                                            }
                                                                                                        }

                                                                                                        // Confirma o cancelamento do ingresso
                                                                                                        println("\nDeseja realmente cancelar este ingresso? [1] SIM [2] NÃO: ")
                                                                                                        val confirmarCancelamento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)

                                                                                                        when (confirmarCancelamento) {
                                                                                                            1 -> {
                                                                                                                ingressoExpandido.statusDisponibilidade = true
                                                                                                                println("OK: Ingresso cancelado.")
                                                                                                                println("Estorno de R$ $valorReembolso solicitado.\n")
                                                                                                            }

                                                                                                            else -> println("OK: Cancelamento cancelada.")
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }

                                                                                            else -> println("OK: Operação cancelada.\n")
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
                                                }
                                            }

                                            // Opção indisponível para organizadores
                                            TipoUsuario.ORGANIZADOR -> println("ERRO: Opção inválida. Tente novamente.")
                                        }
                                    }

                                    6 -> {

                                        // Para organizadores criarem eventos
                                        println("\nNOVO EVENTO")

                                        val cadastroNome = readString("Digite Nome do Evento: ", "ERRO: Nome inválido. Tente novamente.", 4)
                                        val cadastroPagina = readString("Digite Página do Evento: ", "ERRO: Página inválida. Tente novamente.")
                                        val cadastroDescricao = readString("Digite Descrição do Evento: ", "ERRO: Descrição inválida. Tente novamente.")

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
                                            diaInicio = readInt("Digite Somente Dia (DD): ", "ERRO: Dia inválido. Tente novamente.", 1..31)
                                            mesInicio = readInt("Digite Somente Mês (MM): ", "ERRO: Mês inválido. Tente novamente.", 1..12)
                                            anoInicio = readInt("Digite Somente Ano (AAAA): ", "ERRO: Ano inválido. Tente novamente.", 2026..2100)
                                            horaInicio = readInt("Digite Somente Hora (HH): ", "ERRO: Hora inválida. Tente novamente.", 0..23)
                                            minutoInicio = readInt("Digite Somente Minuto (MM): ", "ERRO: Minuto inválido. Tente novamente.", 0..59)

                                            println("MENU: DATA DE TÉRMINO")
                                            diaFinal = readInt("Digite Somente Dia (DD): ", "ERRO: Dia inválido. Tente novamente.", 1..31)
                                            mesFinal = readInt("Digite Somente Mês (MM): ", "ERRO: Mês inválido. Tente novamente.", 1..12)
                                            anoFinal = readInt("Digite Somente Ano (AAAA): ", "ERRO: Ano inválido. Tente novamente.", 2026..2100)
                                            horaFinal = readInt("Digite Somente Hora (HH): ", "ERRO: Hora inválida. Tente novamente.", 0..23)
                                            minutoFinal = readInt("Digite Somente Minuto (MM): ", "ERRO: Minuto inválido. Tente novamente.", 0..59)

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
                                        val cadastroTipo = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 0..23)
                                        val tipoEvento: TipoEvento
                                        when (cadastroTipo) {
                                            1 -> tipoEvento = TipoEvento.SOCIAL
                                            2 -> tipoEvento = TipoEvento.CORPORATIVO
                                            3 -> tipoEvento = TipoEvento.ACADEMICO
                                            4 -> tipoEvento = TipoEvento.CULTURAL_ENTRETENIMENTO
                                            5 -> tipoEvento = TipoEvento.RELIGIOSO
                                            6 -> tipoEvento = TipoEvento.ESPORTIVO
                                            7 -> tipoEvento = TipoEvento.FEIRA
                                            8 -> tipoEvento = TipoEvento.CONGRESSO
                                            9 -> tipoEvento = TipoEvento.OFICINA
                                            10 -> tipoEvento = TipoEvento.CURSO
                                            11 -> tipoEvento = TipoEvento.TREINAMENTO
                                            12 -> tipoEvento = TipoEvento.AULA
                                            13 -> tipoEvento = TipoEvento.SEMINARIO
                                            14 -> tipoEvento = TipoEvento.PALESTRA
                                            15 -> tipoEvento = TipoEvento.SHOW
                                            16 -> tipoEvento = TipoEvento.FESTIVAL
                                            17 -> tipoEvento = TipoEvento.EXPOSICAO
                                            18 -> tipoEvento = TipoEvento.RETIRO
                                            19 -> tipoEvento = TipoEvento.CULTO
                                            20 -> tipoEvento = TipoEvento.CELEBRACAO
                                            21 -> tipoEvento = TipoEvento.CAMPEONATO
                                            22 -> tipoEvento = TipoEvento.CORRIDA
                                            else -> tipoEvento = TipoEvento.OUTRO
                                        }
                                        println("OK: TIPO DE EVENTO DEFINIDO $tipoEvento.\n")

                                        println("Vincular novo evento a evento principal? [1] SIM [2] NÃO")
                                        val vincularPrincipal = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)
                                        var idEventoPrincipal: Int? = null

                                        when (vincularPrincipal) {
                                            1 -> {
                                                val eventosOrganizador = listaEventos.filter { it.organizadorEmail == usuarioEncontrado.email && it.statusEvento }

                                                when (eventosOrganizador.isEmpty()) {
                                                    true -> println("AVISO: Nenhum evento encontrado para vincular.")
                                                    false -> {
                                                        val colunas = listOf("ID", "NOME")
                                                        val linhas = eventosOrganizador.map { listOf(it.id.toString(), it.nome) }
                                                        printTable("SEUS EVENTOS ATIVOS:", colunas, linhas)

                                                        val eventoPrincipal = readInt("Digite ID do Evento Principal (0 para cancelar): ", "ERRO: ID inválido. Tente novamente.")
                                                        val eventoEncontrado = listaEventos.any { it.id == eventoPrincipal && it.organizadorEmail == usuarioEncontrado.email && it.statusEvento }

                                                        when {
                                                            eventoPrincipal == 0 -> println("OK: EVENTO PRINCIPAL NÃO VINCULADO.\n")
                                                            eventoEncontrado -> {
                                                                idEventoPrincipal = eventoPrincipal
                                                                println("OK: ID $eventoPrincipal VINCULADO COM SUCESSO.\n")
                                                            }

                                                            else -> println("ERRO: ID $eventoPrincipal não encontrado ou inválido.\n")
                                                        }
                                                    }
                                                }
                                            }

                                            else -> println("OK: DEFINIDO EVENTO INDEPENDENTE.\n")
                                        }

                                        println("Modalidade: [1] PRESENCIAL [2] REMOTO [3] HÍBRIDO")
                                        val cadastroModalidade = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..3)
                                        val modalidadeEvento: ModalidadeEvento =
                                            when (cadastroModalidade) {
                                                1 -> ModalidadeEvento.PRESENCIAL
                                                2 -> ModalidadeEvento.REMOTO
                                                else -> ModalidadeEvento.HIBRIDO
                                            }
                                        println("OK: MODALIDADE DEFINIDA $modalidadeEvento.\n")

                                        val cadastroCapacidade = readInt("Digite Capacidade Máxima de Pessoas: ", "ERRO: Número inválido. Tente novamente.", 1..Int.MAX_VALUE)
                                        println("OK: CAPACIDADE DEFINIDA $cadastroCapacidade.\n")

                                        val cadastroLocal = readString("Digite Local do Evento (endereço ou link): ", "ERRO: Local inválido. Tente novamente.")

                                        val cadastroPreco = readDouble("Digite Preço do Ingresso: ", "ERRO: Preço inválido. Tente novamente.", 0.0)
                                        println("OK: PREÇO DEFINIDO $cadastroPreco.\n")

                                        println("Aceita estorno/devolução de ingresso? [1] SIM [2] NÃO")
                                        val cadastroEstorno = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)
                                        val aceitaEstorno: Boolean =
                                            when (cadastroEstorno) {
                                                1 -> true
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
                                                cadastroTaxa = readDouble("Digite Taxa de Estorno (%): ", "ERRO: Taxa inválida. Tente novamente.", 0.0, 100.0)
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

                                    7 -> {
                                        println("\nVISUALIZAR EVENTOS")

                                        // Eventos do usuário organizador
                                        val eventosOrganizador = listaEventos.filter { evento ->
                                            evento.organizadorEmail == usuarioEncontrado.email
                                        }

                                        // Eventos ordenados por data e nome
                                        val eventosOrdenados = eventosOrganizador.sortedWith(
                                            compareBy({ evento -> evento.anoInicio }, { evento -> evento.mesInicio }, { evento -> evento.diaInicio }, { evento -> evento.nome })
                                        )

                                        when (eventosOrdenados.isEmpty()) {
                                            true -> println("AVISO: Você ainda não cadastrou nenhum evento.")
                                            false -> {
                                                val possuiEventos = true
                                                val colunas = listOf("ID", "STATUS", "NOME", "DATA", "LOCAL", "PREÇO", "INGRESSOS")

                                                val linhas = eventosOrdenados.map { evento ->
                                                    val statusTexto = when (evento.statusEvento) {
                                                        true -> "ATIVADO"
                                                        false -> "DESATIVADO"
                                                    }
                                                    val ingressosVendidos = listaIngressos.count { ingresso -> ingresso.idEvento == evento.id && !ingresso.statusDisponibilidade }

                                                    listOf(
                                                        evento.id.toString(),
                                                        statusTexto,
                                                        evento.nome,
                                                        "${evento.diaInicio.toString().padStart(2, '0')}/${evento.mesInicio.toString().padStart(2, '0')}",
                                                        evento.local,
                                                        "R$${evento.precoIngresso}",
                                                        "$ingressosVendidos / ${evento.capacidadeMax}"
                                                    )
                                                }

                                                printTable("SEUS EVENTOS:", colunas, linhas)

                                                when (possuiEventos) {
                                                    false -> println("AVISO: Nenhum evento encontrado.\n")

                                                    // Opção para expandir um evento
                                                    true -> {
                                                        val idEvento = readInt("Digite ID para expandir detalhes de evento (0 para Voltar): ", "ERRO: ID inválido. Tente novamente.")

                                                        when (idEvento) {
                                                            0 -> println("OK: Selecionado Voltar.")
                                                            else -> {
                                                                val eventoDetalhes = listaEventos.find { it.id == idEvento && it.organizadorEmail == usuarioEncontrado.email }

                                                                when (eventoDetalhes) {
                                                                    null -> println("ERRO: Nenhum evento encontrado.")
                                                                    else -> {
                                                                        println("\n${eventoDetalhes.nome} EXPANDIDO")
                                                                        println("Nome: ${eventoDetalhes.nome}")
                                                                        println("Descrição: ${eventoDetalhes.descricao}")
                                                                        println("Página: ${eventoDetalhes.pagina}")
                                                                        println(
                                                                            "Início: ${eventoDetalhes.diaInicio}/${eventoDetalhes.mesInicio}/${eventoDetalhes.anoInicio} " +
                                                                                    "às ${eventoDetalhes.horaInicio}:${eventoDetalhes.minutoInicio.toString().padStart(2, '0')}"
                                                                        )
                                                                        println(
                                                                            "Término: ${eventoDetalhes.diaTermino}/${eventoDetalhes.mesTermino}/${eventoDetalhes.anoTermino} " +
                                                                                    "às ${eventoDetalhes.horaTermino}:${eventoDetalhes.minutoTermino.toString().padStart(2, '0')}"
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
                                        }
                                    }

                                    8 -> {
                                        println("\nALTERAR EVENTO")

                                        // Busca todos os eventos do organizador e lista
                                        val eventosOrganizador = listaEventos.filter { it.organizadorEmail == usuarioEncontrado.email }
                                        when (eventosOrganizador.isEmpty()) {
                                            true -> println("ERRO: Nenhum evento encontrado.")
                                            false -> {
                                                val possuiEventos = true
                                                val colunas = listOf("ID", "NOME")
                                                val linhas = eventosOrganizador.map { evento ->
                                                    listOf(evento.id.toString(), evento.nome)
                                                }
                                                printTable("SEUS EVENTOS:", colunas, linhas)

                                                // Busca o evento selecionado
                                                when (possuiEventos) {
                                                    false -> println("ERRO: Nenhum evento encontrado.")
                                                    true -> {
                                                        val idEvento = readInt("Digite ID do evento para alterar (0 para Voltar): ", "ERRO: ID inválido. Tente novamente.")

                                                        when (idEvento) {
                                                            0 -> println("OK: Selecionado Voltar.")
                                                            else -> {
                                                                val eventoAlterando = listaEventos.find { it.id == idEvento && it.organizadorEmail == usuarioEncontrado.email }

                                                                when (eventoAlterando) {
                                                                    null -> println("ERRO: Evento inválido. Tente novamente.")
                                                                    else -> {

                                                                        // Caso o evento esteja desativado, é necessário reativar para alterar dados
                                                                        when {
                                                                            !eventoAlterando.statusEvento -> {
                                                                                println("\nAVISO: Este é um evento desativado. Reativar para alterar? [1] SIM [2] NÃO")
                                                                                val reativarEvento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)
                                                                                when (reativarEvento) {
                                                                                    1 -> {
                                                                                        eventoAlterando.statusEvento = true
                                                                                        println("OK: Evento reativado. Solicite novamente.\n")
                                                                                    }

                                                                                    else -> println("OK: Operação cancelada.")
                                                                                }
                                                                            }

                                                                            else -> {
                                                                                var menuAlterarEvento = true

                                                                                // Menu para alterar os dados do evento
                                                                                do {
                                                                                    println("MENU: EDITANDO EVENTO ${eventoAlterando.nome} (${eventoAlterando.id}).")
                                                                                    println("OPÇÕES:")
                                                                                    println("[0] Voltar\n[1] Nome [2] Página [3] Descrição [4] Período [5] Tipo")
                                                                                    println("[6] Evento Vinculado [7] Modalidade [8] Capacidade [9] Local [10] Preço/Estorno")
                                                                                    val opcaoAlterarEvento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 0..10)

                                                                                    when (opcaoAlterarEvento) {
                                                                                        0 -> {
                                                                                            println("OK: Selecionado Voltar.\n")
                                                                                            menuAlterarEvento = false
                                                                                        }

                                                                                        1 -> {
                                                                                            eventoAlterando.nome = readString("Digite Nome atualizado: ", "ERRO: Nome inválido. Tente novamente.", 4)
                                                                                            println("OK: NOME DEFINIDO '${eventoAlterando.nome}'.")
                                                                                        }

                                                                                        2 -> {
                                                                                            eventoAlterando.pagina = readString("Digite Página atualizada: ", "ERRO: Página inválida. Tente novamente.")
                                                                                            println("OK: PÁGINA DEFINIDA '${eventoAlterando.pagina}'.")
                                                                                        }

                                                                                        3 -> {
                                                                                            eventoAlterando.descricao = readString("Digite Descrição atualizada: ", "ERRO: Descrição inválida. Tente novamente.")
                                                                                            println("OK: DESCRIÇÃO DEFINIDA '${eventoAlterando.descricao}'.")
                                                                                        }

                                                                                        4 -> {
                                                                                            var dataValida = false

                                                                                            do {
                                                                                                println("\nALTERAR PERÍODO DO EVENTO")

                                                                                                println("MENU: ALTERAR DATA DE INÍCIO")
                                                                                                val diaInicio = readInt("Digite Somente Dia (DD) atualizado: ", "ERRO: Dia inválido. Tente novamente.", 1..31)
                                                                                                val mesInicio = readInt("Digite Somente Mês (MM) atualizado: ", "ERRO: Mês inválido. Tente novamente.", 1..12)
                                                                                                val anoInicio = readInt("Digite Somente Ano (AAAA) atualizado: ", "ERRO: Ano inválido. Tente novamente.", 2026..2100)
                                                                                                val horaInicio = readInt("Digite Somente Hora (HH) atualizada: ", "ERRO: Hora inválida. Tente novamente.", 0..23)
                                                                                                val minutoInicio = readInt("Digite Somente Minuto (MM) atualizado: ", "ERRO: Minuto inválido. Tente novamente.", 0..59)

                                                                                                println("MENU: ALTERAR DATA DE TÉRMINO")
                                                                                                val diaFinal = readInt("Digite Somente Dia (DD) atualizado: ", "ERRO: Dia inválido. Tente novamente.", 1..31)
                                                                                                val mesFinal = readInt("Digite Somente Mês (MM) atualizado: ", "ERRO: Mês inválido. Tente novamente.", 1..12)
                                                                                                val anoFinal = readInt("Digite Somente Ano (AAAA) atualizado: ", "ERRO: Ano inválido. Tente novamente.", 2026..2100)
                                                                                                val horaFinal = readInt("Digite Somente Hora (HH) atualizada: ", "ERRO: Hora inválida. Tente novamente.", 0..23)
                                                                                                val minutoFinal = readInt("Digite Somente Minuto (MM) atualizado: ", "ERRO: Minuto inválido. Tente novamente.", 0..59)

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

                                                                                        5 -> {
                                                                                            println("ALTERANDO: Tipo de evento")
                                                                                            println("[1] Social [2] Corporativo [3] Acadêmico [4] Cultural/Entretenimento")
                                                                                            println("[5] Religioso [6] Esportivo [7] Feira [8] Congresso [9] Oficina")
                                                                                            println("[10] Curso [11] Treinamento [12] Aula [13] Seminário [14] Palestra")
                                                                                            println("[15] Show [16] Festival [17] Exposição [18] Retiro [19] Culto")
                                                                                            println("[20] Celebração [21] Campeonato [22] Corrida [23] Outro")
                                                                                            val alterarTipo = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..23)
                                                                                            eventoAlterando.tipo = when (alterarTipo) {
                                                                                                1 -> TipoEvento.SOCIAL
                                                                                                2 -> TipoEvento.CORPORATIVO
                                                                                                3 -> TipoEvento.ACADEMICO
                                                                                                4 -> TipoEvento.CULTURAL_ENTRETENIMENTO
                                                                                                5 -> TipoEvento.RELIGIOSO
                                                                                                6 -> TipoEvento.ESPORTIVO
                                                                                                7 -> TipoEvento.FEIRA
                                                                                                8 -> TipoEvento.CONGRESSO
                                                                                                9 -> TipoEvento.OFICINA
                                                                                                10 -> TipoEvento.CURSO
                                                                                                11 -> TipoEvento.TREINAMENTO
                                                                                                12 -> TipoEvento.AULA
                                                                                                13 -> TipoEvento.SEMINARIO
                                                                                                14 -> TipoEvento.PALESTRA
                                                                                                15 -> TipoEvento.SHOW
                                                                                                16 -> TipoEvento.FESTIVAL
                                                                                                17 -> TipoEvento.EXPOSICAO
                                                                                                18 -> TipoEvento.RETIRO
                                                                                                19 -> TipoEvento.CULTO
                                                                                                20 -> TipoEvento.CELEBRACAO
                                                                                                21 -> TipoEvento.CAMPEONATO
                                                                                                22 -> TipoEvento.CORRIDA
                                                                                                else -> TipoEvento.OUTRO
                                                                                            }
                                                                                            println("OK: TIPO DE EVENTO DEFINIDO ${eventoAlterando.tipo}.\n")
                                                                                        }

                                                                                        6 -> {
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
                                                                                            val vincularEventoPrincipal = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)

                                                                                            when (vincularEventoPrincipal) {
                                                                                                1 -> {
                                                                                                    val eventoPrincipal = readInt("Digite ID do Evento Principal (0 para desvincular): ", "ERRO: ID inválido. Tente novamente.")

                                                                                                    when (eventoPrincipal) {
                                                                                                        0 -> {
                                                                                                            eventoAlterando.idEventoPrincipal = null
                                                                                                            println("OK: EVENTO PRINCIPAL DESVINCULADO.\n")
                                                                                                        }

                                                                                                        else -> {
                                                                                                            val idValido = listaEventos.any {
                                                                                                                it.id == eventoPrincipal &&
                                                                                                                        it.id != eventoAlterando.id &&
                                                                                                                        it.organizadorEmail == usuarioEncontrado.email &&
                                                                                                                        it.statusEvento
                                                                                                            }

                                                                                                            when (idValido) {
                                                                                                                true -> {
                                                                                                                    eventoAlterando.idEventoPrincipal = eventoPrincipal
                                                                                                                    println(
                                                                                                                        "OK: ID $eventoPrincipal DEFINIDO COMO EVENTO PRINCIPAL DE '${eventoAlterando.nome}'\n"
                                                                                                                    )
                                                                                                                }

                                                                                                                false -> println("ERRO: ID inválido. Vinculação mal-sucedida.")
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }

                                                                                                else -> println("OK: Operação cancelada.")
                                                                                            }
                                                                                        }

                                                                                        7 -> {
                                                                                            println("ALTERANDO: Modalidade [1] PRESENCIAL [2] REMOTO [3] HÍBRIDO")
                                                                                            val alterarModalidade = readInt("Digite opção: ", "Opção inválida. Tente novamente.", 1..3)
                                                                                            eventoAlterando.modalidade = when (alterarModalidade) {
                                                                                                1 -> ModalidadeEvento.PRESENCIAL
                                                                                                2 -> ModalidadeEvento.REMOTO
                                                                                                else -> ModalidadeEvento.HIBRIDO
                                                                                            }
                                                                                            println("OK: MODALIDADE DEFINIDA ${eventoAlterando.modalidade}.\n")
                                                                                        }

                                                                                        8 -> {
                                                                                            val ingressosVendidos = listaIngressos.count { it.idEvento == eventoAlterando.id && !it.statusDisponibilidade }
                                                                                            var capacidadeValida = false
                                                                                            var alterarCapacidade: Int
                                                                                            do {
                                                                                                alterarCapacidade = readInt("Digite Capacidade Máxima de Pessoas atualizada: ", "ERRO: Número inválido. Tente novamente.", 1..Int.MAX_VALUE)

                                                                                                // A capacidade máxima de pessoas não pode ficar menor que a quantidade de ingressos vendidos
                                                                                                when {
                                                                                                    alterarCapacidade >= ingressosVendidos -> capacidadeValida = true
                                                                                                    else -> println("ERRO: Capacidade menor que ingressos vendidos. Tente novamente.\n")
                                                                                                }
                                                                                            } while (!capacidadeValida)
                                                                                            eventoAlterando.capacidadeMax = alterarCapacidade
                                                                                            println("OK: CAPACIDADE DEFINIDA ${eventoAlterando.capacidadeMax}.\n")
                                                                                        }

                                                                                        9 -> {
                                                                                            eventoAlterando.local = readString("Digite Local atualizado: ", "ERRO: Local inválido. Tente novamente.")
                                                                                            println("OK: LOCAL DEFINIDO ${eventoAlterando.local}.")
                                                                                        }

                                                                                        10 -> {
                                                                                            val alterarPreco = readDouble("Digite Preço do Ingresso atualizado: ", "ERRO: Preço inválido. Tente novamente.")
                                                                                            eventoAlterando.precoIngresso = alterarPreco
                                                                                            println("OK: PREÇO DEFINIDO ${eventoAlterando.precoIngresso}.\n")

                                                                                            println("ALTERANDO: Aceita estorno/devolução de ingresso? [1] SIM [2] NÃO")
                                                                                            val alterarEstorno = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)

                                                                                            when (alterarEstorno) {
                                                                                                1 -> {
                                                                                                    eventoAlterando.aceitaEstorno = true
                                                                                                    val statusTexto = when (eventoAlterando.aceitaEstorno) {
                                                                                                        true -> "[SIM]"
                                                                                                        false -> "[NÃO]"
                                                                                                    }
                                                                                                    println("OK: ACEITA ESTORNO DEFINIDO $statusTexto.")

                                                                                                    val alterarTaxa = readDouble("Digite Taxa de Estorno (%): ", "ERRO: Taxa inválida. Tente novamente.", 0.0, 100.0)
                                                                                                    eventoAlterando.taxaEstorno = alterarTaxa
                                                                                                    println("OK: TAXA DE ESTORNO DEFINIDA ${eventoAlterando.taxaEstorno}.\n")
                                                                                                }

                                                                                                else -> {
                                                                                                    eventoAlterando.aceitaEstorno = false
                                                                                                    val statusTexto = when (eventoAlterando.aceitaEstorno) {
                                                                                                        true -> "[SIM]"
                                                                                                        false -> "[NÃO]"
                                                                                                    }
                                                                                                    println("OK: \nACEITA ESTORNO DEFINIDO $statusTexto.")

                                                                                                    eventoAlterando.taxaEstorno = 0.0
                                                                                                    println("TAXA DE ESTORNO DEFINIDA ${eventoAlterando.taxaEstorno}.\n")
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
                                                }
                                            }
                                        }
                                    }

                                    9 -> {

                                        // Busca todos os eventos do organizador
                                        println("\nDESATIVAR EVENTO")
                                        val eventosOrganizador = listaEventos.filter { it.organizadorEmail == usuarioEncontrado.email }

                                        when (eventosOrganizador.isEmpty()) {
                                            true -> println("ERRO: Nenhum evento encontrado.")
                                            false -> {
                                                val possuiEventos = true
                                                val colunas = listOf("ID", "NOME")
                                                val linhas = eventosOrganizador.map { evento ->
                                                    listOf(evento.id.toString(), evento.nome)
                                                }
                                                printTable("SEUS EVENTOS:", colunas, linhas)

                                                // Busca o evento selecionado
                                                when (possuiEventos) {
                                                    false -> println("ERRO: Nenhum evento encontrado.")
                                                    true -> {
                                                        val idEvento = readInt("Digite ID de evento a ser desativado: ", "ERRO: ID inválido. Tente novamente.")

                                                        // Variável e busca para selecionar o evento a ser alterado
                                                        val eventoAlterando = listaEventos.find { it.id == idEvento && it.organizadorEmail == usuarioEncontrado.email }

                                                        when (eventoAlterando) {
                                                            null -> println("ERRO: Evento inválido. Tente novamente.\n")
                                                            else -> {
                                                                println("AVISO: Caso o evento tenha vendido ingressos, os valores serão reembolsados aos compradores.")
                                                                println("Confirma desativar o evento '${eventoAlterando.nome}'? [1] SIM, CONFIRMAR. [2] NÃO, CANCELAR.")
                                                                val desativarEvento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 1..2)

                                                                when (desativarEvento) {
                                                                    1 -> {
                                                                        eventoAlterando.statusEvento = false

                                                                        // Busca ingressos para cancelar
                                                                        val ingressosParaReembolso = listaIngressos.filter { it.idEvento == eventoAlterando.id && !it.statusDisponibilidade }

                                                                        // Cancela todos os ingressos
                                                                        ingressosParaReembolso.forEach { it.statusDisponibilidade = true }

                                                                        // Calcula todos os valores para exibir o total reembolsado
                                                                        val totalReembolsado = ingressosParaReembolso.sumOf { it.valorPago }

                                                                        when {
                                                                            ingressosParaReembolso.isNotEmpty() -> {
                                                                                println("OK: \n${ingressosParaReembolso.size} ingresso(s) cancelado(s).")
                                                                                println("Total reembolsado de R$$totalReembolsado.")
                                                                            }
                                                                        }
                                                                        println("OK: Evento desativado (${eventoAlterando.nome}).\n")
                                                                    }

                                                                    else -> println("OK: Operação cancelada.")
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    // Opção para fechar o menu principal
                                    0 -> {
                                        println("OK: Sessão encerrada.\n")
                                        opcaoMenuLogado = 0
                                    }
                                }
                            } while (opcaoMenuLogado != 0)
                        }
                    }
                }
            }

            3 -> {

                // Ajustar data (válida)
                println("MENU: DEFINIR DATA DE HOJE")
                diaHoje = readInt("Digite Somente Dia (DD): ", "ERRO: Dia inválido. Tente novamente.", 1..31)
                mesHoje = readInt("Digite Somente Mês (MM): ", "ERRO: Mês inválido. Tente novamente.", 1..12)
                anoHoje = readInt("Digite Somente Ano (AAAA): ", "ERRO: Ano inválido. Tente novamente.", 2026..2100)
                println("OK: DATA DEFINIDA $diaHoje/$mesHoje/$anoHoje.\n")
                dataHoje = (anoHoje * 10000) + (mesHoje * 100) + diaHoje
            }

            // Opção para fechar o menu inicial
            0 -> {
                print("OK: Operação finalizada.")
            }
        }
    } while (opcaoMenuInicial != 0)
}