import java.time.LocalDateTime

object Evento {
    fun feedEventos(usuarioEncontrado: DadosUsuario) {
        when (usuarioEncontrado.tipoUsuario) {
            TipoUsuario.COMUM -> {
                println("\nFEED DE EVENTOS")

                val eventosOrdenados = Repositorio.eventosDisponiveisOrdenados(Repositorio.dataHoje)
                when (eventosOrdenados.isEmpty()) {
                    true -> println("AVISO: Nenhum evento disponível no momento.")
                    false -> {
                        val existemEventos = true
                        val colunas = listOf("ID", "NOME", "DATA", "LOCAL", "PREÇO", "VAGAS")
                        val linhas = eventosOrdenados.map { evento ->
                            val ingressosVendidos = Repositorio.contarIngressosVendidos(evento.id)
                            val vagasRestantes = evento.capacidadeMax - ingressosVendidos

                            listOf(
                                evento.id.toString(),
                                evento.nome,
                                Repositorio.formatarData(evento.dataInicio),
                                evento.local,
                                "R$${evento.precoIngresso}",
                                vagasRestantes.toString()
                            )
                        }

                        printTable("EVENTOS DISPONÍVEIS", colunas, linhas)

                        when (existemEventos) {
                            false -> println("AVISO: Nenhum evento encontrado.\n")
                            true -> {
                                Ingresso.comprarIngresso(usuarioEncontrado)
                            }
                        }
                    }
                }
            }

            TipoUsuario.ORGANIZADOR -> println("ERRO: Opção inválida. Tente novamente.")
        }
    }

    fun cadastrarEvento(usuarioEncontrado: DadosUsuario) {
        when (usuarioEncontrado.tipoUsuario) {
            TipoUsuario.ORGANIZADOR -> {
                println("\nNOVO EVENTO")

                val cadastroNome = readString("Digite Nome do Evento: ", "ERRO: Nome inválido. Tente novamente.\n", 4)
                val cadastroPagina = readString("Digite Página do Evento: ", "ERRO: Página inválida. Tente novamente.\n")
                val cadastroDescricao = readString("Digite Descrição do Evento: ", "ERRO: Descrição inválida. Tente novamente.\n")

                var dataValida = false
                var dataInicio: LocalDateTime
                var dataFinal: LocalDateTime
                do {
                    println("\nDEFINIR PERÍODO DO EVENTO")

                    println("MENU: DATA DE INÍCIO")
                    val diaInicio = readInt("Digite Somente Dia (DD): ", "ERRO: Dia inválido. Tente novamente.\n", 1..31)
                    val mesInicio = readInt("Digite Somente Mês (MM): ", "ERRO: Mês inválido. Tente novamente.\n", 1..12)
                    val anoInicio = readInt("Digite Somente Ano (AAAA): ", "ERRO: Ano inválido. Tente novamente.\n", 2026..2100)
                    val horaInicio = readInt("Digite Somente Hora (HH): ", "ERRO: Hora inválida. Tente novamente.\n", 0..23)
                    val minutoInicio = readInt("Digite Somente Minuto (MM): ", "ERRO: Minuto inválido. Tente novamente.\n", 0..59)

                    println("MENU: DATA DE TÉRMINO")
                    val diaFinal = readInt("Digite Somente Dia (DD): ", "ERRO: Dia inválido. Tente novamente.\n", 1..31)
                    val mesFinal = readInt("Digite Somente Mês (MM): ", "ERRO: Mês inválido. Tente novamente.\n", 1..12)
                    val anoFinal = readInt("Digite Somente Ano (AAAA): ", "ERRO: Ano inválido. Tente novamente.\n", 2026..2100)
                    val horaFinal = readInt("Digite Somente Hora (HH): ", "ERRO: Hora inválida. Tente novamente.\n", 0..23)
                    val minutoFinal = readInt("Digite Somente Minuto (MM): ", "ERRO: Minuto inválido. Tente novamente.\n", 0..59)

                    dataInicio = LocalDateTime.of(anoInicio, mesInicio, diaInicio, horaInicio, minutoInicio)
                    dataFinal = LocalDateTime.of(anoFinal, mesFinal, diaFinal, horaFinal, minutoFinal)
                    val duracaoMinutos = java.time.Duration.between(dataInicio, dataFinal).toMinutes()
                    when {
                        dataInicio.isBefore(Repositorio.dataHoje) ->
                            println("ERRO: O evento não pode ser no passado.")

                        dataFinal.isBefore(dataInicio) ->
                            println("ERRO: Data/hora de término antes da data/hora de início.")

                        duracaoMinutos < 30 ->
                            println("ERRO: A duração mínima é de 30 minutos.")

                        else -> {
                            println("OK:\nDATA DE INÍCIO DEFINIDA $diaInicio/$mesInicio/$anoInicio às $horaInicio:" + minutoInicio.toString().padStart(2, '0'))
                            println("DATA DE TÉRMINO DEFINIDA $diaFinal/$mesFinal/$anoFinal às $horaFinal:" + minutoFinal.toString().padStart(2, '0'))
                            dataValida = true
                        }
                    }
                } while (!dataValida)

                println("Tipo de evento:")
                println("[1] Social [2] Corporativo [3] Acadêmico [4] Cultural/Entretenimento [5] Religioso")
                println("[6] Esportivo [7] Feira [8] Congresso [9] Oficina [10] Curso [11] Treinamento")
                println("[12] Aula [13] Seminário [14] Palestra [15] Show [16] Festival [17] Exposição")
                println("[18] Retiro [19] Culto [20] Celebração [21] Campeonato [22] Corrida [23] Outro")
                val cadastroTipo = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 0..23)
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
                val vincularPrincipal = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)
                var idEventoPrincipal: Int? = null

                when (vincularPrincipal) {
                    1 -> {
                        val eventosOrganizador = Repositorio.buscarEventosAtivosOrganizador(usuarioEncontrado.email)

                        when (eventosOrganizador.isEmpty()) {
                            true -> println("AVISO: Nenhum evento encontrado para vincular.")
                            false -> {
                                val colunas = listOf("ID", "NOME")
                                val linhas = eventosOrganizador.map { listOf(it.id.toString(), it.nome) }
                                printTable("SEUS EVENTOS ATIVOS:", colunas, linhas)

                                val eventoPrincipal = readInt("Digite ID do Evento Principal (0 para cancelar): ", "ERRO: ID inválido. Tente novamente.\n")
                                val eventoPrincipalValido = Repositorio.buscarEventoPrincipalOrganizador(eventoPrincipal, usuarioEncontrado.email)

                                when {
                                    eventoPrincipal == 0 -> println("OK: EVENTO PRINCIPAL NÃO VINCULADO.\n")
                                    eventoPrincipalValido -> {
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
                val cadastroModalidade = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..3)
                val modalidadeEvento: ModalidadeEvento =
                    when (cadastroModalidade) {
                        1 -> ModalidadeEvento.PRESENCIAL
                        2 -> ModalidadeEvento.REMOTO
                        else -> ModalidadeEvento.HIBRIDO
                    }
                println("OK: MODALIDADE DEFINIDA $modalidadeEvento.\n")

                val cadastroCapacidade = readInt("Digite Capacidade Máxima de Pessoas: ", "ERRO: Número inválido. Tente novamente.\n", 1..Int.MAX_VALUE)
                println("OK: CAPACIDADE DEFINIDA $cadastroCapacidade.\n")

                val cadastroLocal = readString("Digite Local do Evento (endereço ou link): ", "ERRO: Local inválido. Tente novamente.\n")

                val cadastroPreco = readDouble("Digite Preço do Ingresso: ", "ERRO: Preço inválido. Tente novamente.\n", 0.0)
                println("OK: PREÇO DEFINIDO $cadastroPreco.\n")

                println("Aceita estorno/devolução de ingresso? [1] SIM [2] NÃO")
                val cadastroEstorno = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)
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
                        cadastroTaxa = readDouble("Digite Taxa de Estorno (%): ", "ERRO: Taxa inválida. Tente novamente.\n", 0.0, 100.0)
                        println("OK: TAXA DE ESTORNO DEFINIDA $cadastroTaxa.\n")
                    }

                    false -> {
                        cadastroTaxa = 0.0
                    }
                }

                val novoEvento = DadosEvento(
                    id = Repositorio.buscarProximoEventoId(),
                    organizadorEmail = usuarioEncontrado.email,
                    nome = cadastroNome,
                    pagina = cadastroPagina,
                    descricao = cadastroDescricao,
                    dataInicio = dataInicio,
                    dataTermino = dataFinal,
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

                Repositorio.adicionarDadosEvento(novoEvento)
                println("OK: Evento cadastrado (ID ${novoEvento.id}).")
            }

            TipoUsuario.COMUM -> println("ERRO: Opção inválida. Tente novamente.")
        }
    }

    fun alterarEvento(usuarioEncontrado: DadosUsuario) {
        when (usuarioEncontrado.tipoUsuario) {
            TipoUsuario.ORGANIZADOR -> {
                println("\nALTERAR EVENTO")

                val eventosOrganizador = Repositorio.buscarEventosOrganizador(usuarioEncontrado.email)
                when (eventosOrganizador.isEmpty()) {
                    true -> println("ERRO: Nenhum evento encontrado.")
                    false -> {
                        val possuiEventos = true
                        val colunas = listOf("ID", "NOME")
                        val linhas = eventosOrganizador.map { evento ->
                            listOf(evento.id.toString(), evento.nome)
                        }
                        printTable("SEUS EVENTOS:", colunas, linhas)

                        when (possuiEventos) {
                            false -> println("ERRO: Nenhum evento encontrado.")
                            true -> {
                                val idEvento = readInt("Digite ID do evento para alterar (0 para Voltar): ", "ERRO: ID inválido. Tente novamente.\n")

                                when (idEvento) {
                                    0 -> println("OK: Selecionado Voltar.")
                                    else -> {
                                        val eventoAlterando = Repositorio.buscarIdEventoOrganizador(idEvento, usuarioEncontrado.email)
                                        when {
                                            !eventoAlterando.statusEvento -> {
                                                println("\nAVISO: Este é um evento desativado. Reativar para alterar? [1] SIM [2] NÃO")
                                                val reativarEvento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)
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
                                                do {
                                                    println("MENU: EDITANDO EVENTO ${eventoAlterando.nome} (${eventoAlterando.id}).")
                                                    println("OPÇÕES:")
                                                    println("[0] Voltar\n[1] Nome [2] Página [3] Descrição [4] Período [5] Tipo")
                                                    println("[6] Evento Vinculado [7] Modalidade [8] Capacidade [9] Local [10] Preço/Estorno")
                                                    val opcaoAlterarEvento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 0..10)

                                                    when (opcaoAlterarEvento) {
                                                        0 -> {
                                                            println("OK: Selecionado Voltar.\n")
                                                            menuAlterarEvento = false
                                                        }

                                                        1 -> {
                                                            eventoAlterando.nome = readString("Digite Nome atualizado: ", "ERRO: Nome inválido. Tente novamente.\n", 4)
                                                            println("OK: NOME DEFINIDO '${eventoAlterando.nome}'.")
                                                        }

                                                        2 -> {
                                                            eventoAlterando.pagina = readString("Digite Página atualizada: ", "ERRO: Página inválida. Tente novamente.\n")
                                                            println("OK: PÁGINA DEFINIDA '${eventoAlterando.pagina}'.")
                                                        }

                                                        3 -> {
                                                            eventoAlterando.descricao = readString("Digite Descrição atualizada: ", "ERRO: Descrição inválida. Tente novamente.\n")
                                                            println("OK: DESCRIÇÃO DEFINIDA '${eventoAlterando.descricao}'.")
                                                        }

                                                        4 -> {
                                                            var dataValida = false

                                                            do {
                                                                println("\nALTERAR PERÍODO DO EVENTO")

                                                                println("MENU: ALTERAR DATA DE INÍCIO")
                                                                val diaInicio = readInt("Digite Somente Dia (DD) atualizado: ", "ERRO: Dia inválido. Tente novamente.\n", 1..31)
                                                                val mesInicio = readInt("Digite Somente Mês (MM) atualizado: ", "ERRO: Mês inválido. Tente novamente.\n", 1..12)
                                                                val anoInicio = readInt("Digite Somente Ano (AAAA) atualizado: ", "ERRO: Ano inválido. Tente novamente.\n", 2026..2100)
                                                                val horaInicio = readInt("Digite Somente Hora (HH) atualizada: ", "ERRO: Hora inválida. Tente novamente.\n", 0..23)
                                                                val minutoInicio = readInt("Digite Somente Minuto (MM) atualizado: ", "ERRO: Minuto inválido. Tente novamente.\n", 0..59)

                                                                println("MENU: ALTERAR DATA DE TÉRMINO")
                                                                val diaFinal = readInt("Digite Somente Dia (DD) atualizado: ", "ERRO: Dia inválido. Tente novamente.\n", 1..31)
                                                                val mesFinal = readInt("Digite Somente Mês (MM) atualizado: ", "ERRO: Mês inválido. Tente novamente.\n", 1..12)
                                                                val anoFinal = readInt("Digite Somente Ano (AAAA) atualizado: ", "ERRO: Ano inválido. Tente novamente.\n", 2026..2100)
                                                                val horaFinal = readInt("Digite Somente Hora (HH) atualizada: ", "ERRO: Hora inválida. Tente novamente.\n", 0..23)
                                                                val minutoFinal = readInt("Digite Somente Minuto (MM) atualizado: ", "ERRO: Minuto inválido. Tente novamente.\n", 0..59)

                                                                val dataInicio = LocalDateTime.of(anoInicio, mesInicio, diaInicio, horaInicio, minutoInicio)
                                                                val dataFinal = LocalDateTime.of(anoFinal, mesFinal, diaFinal, horaFinal, minutoFinal)
                                                                val duracaoMinutos = java.time.Duration.between(dataInicio, dataFinal).toMinutes()
                                                                when {
                                                                    dataInicio.isBefore(Repositorio.dataHoje) ->
                                                                        println("ERRO: O evento não pode ser no passado.")

                                                                    dataFinal.isBefore(dataInicio) ->
                                                                        println("ERRO: Data de término antes da data de início.")

                                                                    duracaoMinutos < 30 ->
                                                                        println("ERRO: A duração mínima é de 30 minutos.")

                                                                    else -> {
                                                                        eventoAlterando.dataInicio = dataInicio
                                                                        eventoAlterando.dataTermino = dataFinal
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
                                                            val alterarTipo = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..23)
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
                                                            val vincularEventoPrincipal = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)

                                                            when (vincularEventoPrincipal) {
                                                                1 -> {
                                                                    val idEventoPrincipal = readInt("Digite ID do Evento Principal (0 para desvincular): ", "ERRO: ID inválido. Tente novamente.\n")

                                                                    when (idEventoPrincipal) {
                                                                        0 -> {
                                                                            eventoAlterando.idEventoPrincipal = null
                                                                            println("OK: EVENTO PRINCIPAL DESVINCULADO.\n")
                                                                        }

                                                                        else -> when {
                                                                            Repositorio.vincularEventoPrincipal(idEventoPrincipal, eventoAlterando.id, usuarioEncontrado.email) -> {
                                                                                eventoAlterando.idEventoPrincipal = idEventoPrincipal
                                                                                println("OK: ID $idEventoPrincipal DEFINIDO COMO EVENTO PRINCIPAL DE '${eventoAlterando.nome}'\n")
                                                                            }

                                                                            else -> println("ERRO: ID $idEventoPrincipal não encontrado ou inválido.")
                                                                        }
                                                                    }
                                                                }

                                                                else -> println("OK: Operação cancelada.")
                                                            }
                                                        }

                                                        7 -> {
                                                            println("ALTERANDO: Modalidade [1] PRESENCIAL [2] REMOTO [3] HÍBRIDO")
                                                            val alterarModalidade = readInt("Digite opção: ", "Opção inválida. Tente novamente.\n", 1..3)
                                                            eventoAlterando.modalidade = when (alterarModalidade) {
                                                                1 -> ModalidadeEvento.PRESENCIAL
                                                                2 -> ModalidadeEvento.REMOTO
                                                                else -> ModalidadeEvento.HIBRIDO
                                                            }
                                                            println("OK: MODALIDADE DEFINIDA ${eventoAlterando.modalidade}.\n")
                                                        }

                                                        8 -> {
                                                            val ingressosVendidos = Repositorio.contarIngressosVendidos(eventoAlterando.id)
                                                            var capacidadeValida = false
                                                            var alterarCapacidade: Int
                                                            do {
                                                                alterarCapacidade = readInt("Digite Capacidade Máxima de Pessoas atualizada: ", "ERRO: Número inválido. Tente novamente.\n", 1..Int.MAX_VALUE)

                                                                when {
                                                                    alterarCapacidade >= ingressosVendidos -> capacidadeValida = true
                                                                    else -> println("ERRO: Capacidade menor que ingressos vendidos. Tente novamente.\n")
                                                                }
                                                            } while (!capacidadeValida)
                                                            eventoAlterando.capacidadeMax = alterarCapacidade
                                                            println("OK: CAPACIDADE DEFINIDA ${eventoAlterando.capacidadeMax}.\n")
                                                        }

                                                        9 -> {
                                                            eventoAlterando.local = readString("Digite Local atualizado: ", "ERRO: Local inválido. Tente novamente.\n")
                                                            println("OK: LOCAL DEFINIDO ${eventoAlterando.local}.")
                                                        }

                                                        10 -> {
                                                            val alterarPreco = readDouble("Digite Preço do Ingresso atualizado: ", "ERRO: Preço inválido. Tente novamente.\n")
                                                            eventoAlterando.precoIngresso = alterarPreco
                                                            println("OK: PREÇO DEFINIDO ${eventoAlterando.precoIngresso}.\n")

                                                            println("ALTERANDO: Aceita estorno/devolução de ingresso? [1] SIM [2] NÃO")
                                                            val alterarEstorno = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)

                                                            when (alterarEstorno) {
                                                                1 -> {
                                                                    eventoAlterando.aceitaEstorno = true
                                                                    val statusTexto = "[SIM]"
                                                                    println("OK: ACEITA ESTORNO DEFINIDO $statusTexto.")

                                                                    val alterarTaxa = readDouble("Digite Taxa de Estorno (%): ", "ERRO: Taxa inválida. Tente novamente.\n", 0.0, 100.0)
                                                                    eventoAlterando.taxaEstorno = alterarTaxa
                                                                    println("OK: TAXA DE ESTORNO DEFINIDA ${eventoAlterando.taxaEstorno}.\n")
                                                                }

                                                                else -> {
                                                                    eventoAlterando.aceitaEstorno = false
                                                                    val statusTexto = "[NÃO]"
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

            TipoUsuario.COMUM -> println("ERRO: Opção inválida. Tente novamente.")
        }
    }

    fun visualizarEventos(usuarioEncontrado: DadosUsuario) {
        when (usuarioEncontrado.tipoUsuario) {
            TipoUsuario.ORGANIZADOR -> {
                println("\nVISUALIZAR EVENTOS")

                val eventosOrdenados = Repositorio.eventosOrganizadorOrdenados(usuarioEncontrado.email)
                when (eventosOrdenados.isEmpty()) {
                    true -> println("AVISO: Você ainda não cadastrou nenhum evento.")
                    false -> {
                        val possuiEventos = true
                        val colunas = listOf("ID", "STATUS", "NOME", "DATA", "LOCAL", "PREÇO", "INGRESSOS VENDIDOS")

                        val linhas = eventosOrdenados.map { evento ->
                            val statusTexto = when (evento.statusEvento) {
                                true -> "ATIVADO"
                                false -> "DESATIVADO"
                            }
                            val ingressosVendidos = Repositorio.contarIngressosVendidos(evento.id)

                            listOf(
                                evento.id.toString(),
                                statusTexto,
                                evento.nome,
                                Repositorio.formatarData(evento.dataInicio),
                                evento.local,
                                "R$${evento.precoIngresso}",
                                "$ingressosVendidos / ${evento.capacidadeMax}"
                            )
                        }

                        printTable("SEUS EVENTOS:", colunas, linhas)

                        when (possuiEventos) {
                            false -> println("AVISO: Nenhum evento encontrado.\n")
                            true -> {
                                val idEvento = readInt("Digite ID para expandir detalhes de evento (0 para Voltar): ", "ERRO: ID inválido. Tente novamente.\n")

                                when (idEvento) {
                                    0 -> println("OK: Selecionado Voltar.")
                                    else -> {
                                        val eventoDetalhes = Repositorio.buscarIdEventoOrganizador(idEvento, usuarioEncontrado.email)
                                        println("\n${eventoDetalhes.nome} EXPANDIDO")
                                        println("Nome: ${eventoDetalhes.nome}")
                                        println("Descrição: ${eventoDetalhes.descricao}")
                                        println("Página: ${eventoDetalhes.pagina}")
                                        println("Início: ${Repositorio.formatarData(eventoDetalhes.dataInicio)}")
                                        println("Término: ${Repositorio.formatarData(eventoDetalhes.dataTermino)}")
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

                                        readString("[QUALQUER TECLA] Voltar\n", "ERRO: Um caractere é necessário. Tente novamente.\n")
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

    fun desativarEvento(usuarioEncontrado: DadosUsuario) {
        when (usuarioEncontrado.tipoUsuario) {
            TipoUsuario.ORGANIZADOR -> {
                println("\nDESATIVAR EVENTO")
                val eventosOrganizador = Repositorio.buscarEventosAtivosOrganizador(usuarioEncontrado.email)

                when (eventosOrganizador.isEmpty()) {
                    true -> println("ERRO: Nenhum evento encontrado.")
                    false -> {
                        val possuiEventos = true
                        val colunas = listOf("ID", "NOME")
                        val linhas = eventosOrganizador.map { evento ->
                            listOf(evento.id.toString(), evento.nome)
                        }
                        printTable("SEUS EVENTOS:", colunas, linhas)

                        when (possuiEventos) {
                            false -> println("ERRO: Nenhum evento encontrado.")
                            true -> {
                                val idEvento = readInt("Digite ID de evento a ser desativado: ", "ERRO: ID inválido. Tente novamente.")
                                val eventoAlterando = Repositorio.buscarIdEventoOrganizador(idEvento, usuarioEncontrado.email)
                                println("AVISO: Caso o evento tenha vendido ingressos, os valores serão reembolsados aos compradores.")
                                println("Confirma desativar o evento '${eventoAlterando.nome}'? [1] SIM, CONFIRMAR. [2] NÃO, CANCELAR.")
                                val desativarEvento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)

                                when (desativarEvento) {
                                    1 -> {
                                        eventoAlterando.statusEvento = false
                                        Repositorio.cancelarIngressosEvento(eventoAlterando.id)
                                        val listaIngressosReembolsados = Repositorio.listarIngressosReembolsados(eventoAlterando.id)
                                        val totalReembolsado = Repositorio.valorIngressosReembolsados(eventoAlterando.id)
                                        when {
                                            listaIngressosReembolsados.isNotEmpty() -> {
                                                println("OK: \n${listaIngressosReembolsados.size} ingresso(s) cancelado(s).")
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

            TipoUsuario.COMUM -> println("ERRO: Opção inválida. Tente novamente.")
        }
    }
}
