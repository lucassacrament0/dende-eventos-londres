object Ingresso {
    fun comprarIngresso(usuarioEncontrado: DadosUsuario) {
        when (usuarioEncontrado.tipoUsuario) {
            TipoUsuario.COMUM -> {
                val idEvento = readInt("Digite o ID do evento para visualizar/comprar ingresso (0 para voltar): ", "ERRO: ID inválido. Tente novamente.\n")

                when (idEvento) {
                    0 -> println("OK: Selecionado Voltar.")
                    else -> {
                        when (val eventoDetalhes = Repositorio.buscarIdEventoAtivo(idEvento)) {
                            null -> println("ERRO: Nenhum evento encontrado.")
                            else -> {
                                println("Nome: ${eventoDetalhes.nome}")
                                println("Descrição: ${eventoDetalhes.descricao}")
                                println("Página: ${eventoDetalhes.pagina}")
                                println("Início: ${Repositorio.formatarDataHora(eventoDetalhes.dataInicio)}")
                                println("Término: ${Repositorio.formatarDataHora(eventoDetalhes.dataTermino)}")
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
                                    else -> println("Evento Principal ID ${eventoDetalhes.idEventoPrincipal}")
                                }

                                println("Evento Atual ID ${eventoDetalhes.id}")
                                println("[1] Comprar Ingresso  [0] Voltar")
                                val opcaoCompra = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 0..1)

                                when (opcaoCompra) {
                                    1 -> {
                                        when (eventoDetalhes.idEventoPrincipal) {
                                            null -> {
                                                println("\nCOMPRAR INGRESSO")
                                                println("\nEvento: ${eventoDetalhes.nome}")
                                                println("Preço Total: R$${eventoDetalhes.precoIngresso}")
                                                println("\n[1] Confirmar Compra  [0] Cancelar")
                                                val confirmarCompra = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 0..1)
                                                when (confirmarCompra) {
                                                    1 -> {
                                                        val novoIngresso = DadosIngresso(
                                                            id = Repositorio.buscarProximoIngressoId(),
                                                            idEvento = eventoDetalhes.id,
                                                            emailUsuario = usuarioEncontrado.email,
                                                            statusDisponibilidade = false,
                                                            valorPago = eventoDetalhes.precoIngresso
                                                        )
                                                        Repositorio.adicionarDadosIngresso(novoIngresso)
                                                        println("OK: Ingresso comprado. (ID: ${novoIngresso.id})\n")
                                                    }

                                                    else -> println("OK: Compra cancelada.")
                                                }
                                            }

                                            else -> {
                                                when (val eventoPrincipal = Repositorio.buscarIdEventoAtivo(eventoDetalhes.idEventoPrincipal!!)) {
                                                    null -> println("ERRO: Evento inexistente ou indisponível.\n")
                                                    else -> {
                                                        val vendidosPrincipal = Repositorio.contarIngressosVendidos(eventoPrincipal.id)
                                                        val ingressosPrincipal = eventoPrincipal.capacidadeMax - vendidosPrincipal
                                                        when {
                                                            ingressosPrincipal <= 0 -> println("ERRO: Evento Principal '${eventoPrincipal.nome}' indisponível.")
                                                            else -> {
                                                                val ingressosSomados = eventoDetalhes.precoIngresso + eventoPrincipal.precoIngresso
                                                                println("\nAVISO: Este evento exige compra dupla.")
                                                                println("- Sub-Evento: ${eventoDetalhes.nome} (R$ ${eventoDetalhes.precoIngresso})")
                                                                println("- Evento Principal:  ${eventoPrincipal.nome} (R$ ${eventoPrincipal.precoIngresso})")
                                                                println("VALOR TOTAL:  R$$ingressosSomados")

                                                                println("\n[1] Confirmar Compra Dupla  [0] Cancelar")
                                                                val confirmarCompraDupla = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 0..1)

                                                                when (confirmarCompraDupla) {
                                                                    1 -> {
                                                                        val ingressoSubEvento = DadosIngresso(
                                                                            id = Repositorio.buscarProximoIngressoId(),
                                                                            idEvento = eventoDetalhes.id,
                                                                            emailUsuario = usuarioEncontrado.email,
                                                                            statusDisponibilidade = false,
                                                                            valorPago = eventoDetalhes.precoIngresso
                                                                        )
                                                                        Repositorio.adicionarDadosIngresso(ingressoSubEvento)

                                                                        val ingressoEventoPrincipal = DadosIngresso(
                                                                            id = Repositorio.buscarProximoIngressoId(),
                                                                            idEvento = eventoPrincipal.id,
                                                                            emailUsuario = usuarioEncontrado.email,
                                                                            statusDisponibilidade = false,
                                                                            valorPago = eventoPrincipal.precoIngresso
                                                                        )
                                                                        Repositorio.adicionarDadosIngresso(ingressoEventoPrincipal)

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
                                }
                            }
                        }
                    }
                }
            }

            TipoUsuario.ORGANIZADOR -> println("ERRO: Opção inválida. Tente novamente.")
        }
    }

    fun cancelarIngresso(ingressoExpandido: DadosIngresso, eventoDoIngresso: DadosEvento) {
        val dataEvento = eventoDoIngresso.dataInicio

        when {
            ingressoExpandido.statusDisponibilidade -> println("\nERRO: Ingresso já cancelado.")
            dataEvento.isBefore(Repositorio.dataHoje) -> println("\nERRO: Evento passado. Cancelamento indisponível.")
            !eventoDoIngresso.statusEvento -> println("\nERRO: Evento desativado pelo organizador.")
            else -> {
                println("\nCANCELAMENTO DE INGRESSO")
                println("Sobre o Evento:")

                var valorReembolso = 0.0
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

                println("\nDeseja realmente cancelar este ingresso? [1] SIM [2] NÃO: ")
                val confirmarCancelamento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)

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

    fun visualizarIngressos(usuarioEncontrado: DadosUsuario) {
        when (usuarioEncontrado.tipoUsuario) {
            TipoUsuario.COMUM -> {
                println("\nVISUALIZAR INGRESSOS")

                val ingressosUsuario = Repositorio.listarIngressosUsuario(usuarioEncontrado.email)
                when (ingressosUsuario.isEmpty()) {
                    true -> println("AVISO: Você não possui ingressos cadastrados.")
                    false -> {
                        val ingressosOrdenados = Repositorio.ingressosUsuarioOrdenados(usuarioEncontrado.email)
                        val colunas = listOf("ID", "EVENTO", "VALOR", "STATUS")
                        val linhas = ingressosOrdenados.map { ingresso ->
                            val eventoDoIngresso = Repositorio.buscarEventoDoIngresso(ingresso)

                            val statusTexto = when (ingresso.statusDisponibilidade) {
                                true -> "CANCELADO/REALIZADO"
                                false -> "OK"
                            }

                            listOf(
                                ingresso.id.toString(),
                                eventoDoIngresso.nome,
                                "R$${ingresso.valorPago}",
                                statusTexto
                            )
                        }
                        printTable("SEUS INGRESSOS:", colunas, linhas)

                        val idIngresso = readInt("Digite ID do ingresso para expandir/cancelar (0 para Voltar): ", "ERRO: ID inválido. Tente novamente.\n")
                        when (idIngresso) {
                            0 -> println("OK: Voltando ao Menu Principal.")
                            else -> {
                                val ingressoExpandido = Repositorio.buscarIngressoUsuario(usuarioEncontrado.email, idIngresso)
                                val eventoDoIngresso = Repositorio.buscarEventoDoIngresso(ingressoExpandido)
                                println("\nDETALHES DO INGRESSO")
                                println("ID: ${ingressoExpandido.id}")
                                println("Evento: ${eventoDoIngresso.nome}")
                                println("Data: ${Repositorio.formatarDataHora(eventoDoIngresso.dataInicio)}")
                                println("Local: ${eventoDoIngresso.local}")
                                println("Valor Pago: R$ ${ingressoExpandido.valorPago}")

                                val statusAtual = when (ingressoExpandido.statusDisponibilidade) {
                                    true -> "CANCELADO"
                                    false -> "OK"
                                }
                                println("Status Atual: $statusAtual")

                                println("Cancelar Ingresso? [1] SIM [2] NÃO:")
                                val opcaoCancelamento = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)

                                when (opcaoCancelamento) {
                                    1 -> {
                                        cancelarIngresso(ingressoExpandido, eventoDoIngresso)
                                    }

                                    else -> println("OK: Ingresso não cancelado.")
                                }
                            }
                        }
                    }
                }
            }

            TipoUsuario.ORGANIZADOR -> println("ERRO: Opção inválida. Tente novamente.")
        }
    }
}
