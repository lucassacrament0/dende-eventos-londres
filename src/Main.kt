fun main() {
    println("BEM-VINDO AO DENDÊ EVENTOS")
    println(Repositorio.dataHoje)

    // Loop do menu inicial
    do {
        println("MENU INICIAL")
        println("1. Cadastrar Usuário")
        println("2. Acessar Usuário")
        println("0. Sair")
        val opcaoMenuInicial = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", 0..2)

        // Opções do menu
        when (opcaoMenuInicial) {
            1 -> {
                Usuario.cadastrarUsuario()
            }

            2 -> {
                val usuarioEncontrado = Usuario.acessarUsuario()
                when {
                    usuarioEncontrado != null && usuarioEncontrado.statusConta -> {
                        println("OK: Acesso bem-sucedido.\n")
                        do {
                            println("MENU PRINCIPAL - ÁREA LOGADA")
                            println("USUÁRIO: ${usuarioEncontrado.nome} (${usuarioEncontrado.email}).")
                            println("[1] Alterar Usuário [2] Visualizar Usuário [3] Desativar Usuário")

                            // Condicional para tornar menu dinâmico com base no tipo de usuário
                            when {
                                usuarioEncontrado.tipoUsuario == TipoUsuario.COMUM -> {
                                    println("[4] Feed de Eventos [5] Visualizar Ingressos")
                                }

                                usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                                    println("[6] Cadastrar Evento [7] Alterar Evento [8] Visualizar Eventos [9] Desativar Evento")
                                }
                            }
                            println("[0] Encerrar Sessão")

                            // Variável para limitar as opções do menu em intervalos com base no tipo de usuário
                            val limiteOpcoes = when (usuarioEncontrado.tipoUsuario) {
                                TipoUsuario.COMUM -> (0..5)
                                else -> 0..9
                            }
                            var opcaoMenuLogado = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.", limiteOpcoes)

                            // Opções do menu logado
                            when (opcaoMenuLogado) {
                                1 -> {
                                    Usuario.alterarUsuario(usuarioEncontrado)
                                }

                                2 -> {
                                    Usuario.visualizarUsuario(usuarioEncontrado)
                                }

                                3 -> {
                                    Usuario.desativarUsuario(usuarioEncontrado)
                                    opcaoMenuLogado = 0
                                }

                                4 -> {
                                    Evento.feedEventos(usuarioEncontrado)
                                }

                                5 -> {
                                    Ingresso.visualizarIngressos(usuarioEncontrado)
                                }

                                6 -> {
                                    Evento.cadastrarEvento(usuarioEncontrado)
                                }

                                7 -> {
                                    Evento.alterarEvento(usuarioEncontrado)
                                }

                                8 -> {
                                    Evento.visualizarEventos(usuarioEncontrado)
                                }

                                9 -> {
                                    Evento.desativarEvento(usuarioEncontrado)
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

            // Opção para fechar o menu inicial
            0 -> {
                print("OK: Operação finalizada.")
            }
        }
    } while (opcaoMenuInicial != 0)
}