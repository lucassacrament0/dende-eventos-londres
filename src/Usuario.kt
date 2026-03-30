import java.time.LocalDate

object Usuario {
    fun validarEmailCadastro(emailInserido: String, emailConfirmado: String): Boolean {
        var emailValido = false
        when {
            emailInserido == emailConfirmado &&
                    emailConfirmado.contains("@") && emailConfirmado.contains(".") &&
                    !Repositorio.verificarEmailRepetido(emailInserido) ->
                emailValido = true
        }
        return emailValido
    }

    fun validarSenhaCadastro(senhaInserida: String, senhaConfirmada: String): Boolean {
        var senhaValida = false
        when {
            senhaInserida == senhaConfirmada -> {
                senhaValida = true
            }
        }
        return senhaValida
    }

    fun cadastrarUsuario() {
        println("\nCADASTRAR USUÁRIO")

        val cadastroEmail = readString("Digite E-mail: ", "ERRO: E-mail inválido. Tente novamente.\n", 3).lowercase()
        val confirmarEmail = readString("Confirme E-mail: ", "ERRO: E-mail inválido. Tente novamente.\n", 3).lowercase()
        when {
            validarEmailCadastro(cadastroEmail, confirmarEmail) -> {
                println("OK: E-MAIL DEFINIDO '$cadastroEmail'.\n")

                val cadastroNome = readString("Digite Nome: ", "ERRO: Nome inválido. Tente novamente.\n", 2).uppercase()
                println("OK: NOME DEFINIDO '$cadastroNome'.\n")

                var cadastroNascimento: LocalDate = LocalDate.now()
                var dataValida = false
                do {
                    try {
                        println("MENU: DEFINIR DATA DE NASCIMENTO")
                        val diaNascimento = readInt("Digite Somente Dia de Nascimento (DD): ", "ERRO: Dia inválido. Tente novamente.\n", 1..31)
                        val mesNascimento = readInt("Digite Somente Mês de Nascimento (MM): ", "ERRO: Mês inválido. Tente novamente.\n", 1..12)
                        val anoNascimento = readInt("Digite Somente Ano de Nascimento (AAAA): ", "ERRO: Ano inválido. Tente novamente.\n", 1920..2010)
                        cadastroNascimento = LocalDate.of(anoNascimento, mesNascimento, diaNascimento)
                        dataValida = true
                    } catch (_: java.time.DateTimeException) {
                        println("ERRO: Data inválida. Tente novamente.\n")
                    }
                } while (!dataValida)
                println("OK: DATA DE NASCIMENTO DEFINIDA ${Repositorio.formatarData(cadastroNascimento)}.\n")

                val cadastroSenha = readString("Digite Nova Senha: ", "ERRO: A senha deve ter no mínimo 8 caracteres. Tente novamente.\n", 8)
                val confirmarSenha = readString("Confirme Nova Senha: ", "ERRO: A senha deve ter no mínimo 8 caracteres. Tente novamente.\n", 8)
                when {
                    validarSenhaCadastro(cadastroSenha, confirmarSenha) -> {
                        println("OK: SENHA DEFINIDA.\n")

                        println("Sexo: [1] MASCULINO, [2] FEMININO, [3] PREFIRO NÃO INFORMAR")
                        val opcaoSexo = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..3)

                        val cadastroSexo = when (opcaoSexo) {
                            1 -> SexoUsuario.MASCULINO
                            2 -> SexoUsuario.FEMININO
                            else -> SexoUsuario.NAO_INFORMADO
                        }
                        println("OK: SEXO DEFINIDO $cadastroSexo.\n")

                        println("Você é organizador de eventos? [1] SIM, [2] NÃO")
                        val tipoUsuario = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)

                        val cadastroOrganizador = when (tipoUsuario) {
                            1 -> TipoUsuario.ORGANIZADOR
                            else -> TipoUsuario.COMUM
                        }
                        println("OK: DEFINIDO USUÁRIO $cadastroOrganizador.\n")

                        val cadastroEmpresa: Int
                        var cadastroCNPJ: String? = null
                        var cadastroRazaoSocial: String? = null
                        var cadastroNomeFantasia: String? = null

                        when (tipoUsuario) {
                            1 -> {
                                println("Você possui uma empresa? [1] SIM [2] NÃO")
                                cadastroEmpresa = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)

                                when (cadastroEmpresa) {
                                    1 -> {
                                        println("\nCADASTRO DE EMPRESA")

                                        cadastroCNPJ = readString("Digite CNPJ (14 dígitos): ", "ERRO: CNPJ inválido. Tente novamente.\n", 14)
                                        println("OK: CNPJ DEFINIDO '$cadastroCNPJ'.\n")

                                        cadastroRazaoSocial = readString("Digite Razão Social: ", "ERRO: Razão Social inválida. Tente novamente.\n", 2).uppercase()
                                        println("OK: RAZÃO SOCIAL DEFINIDA '$cadastroRazaoSocial'.\n")

                                        cadastroNomeFantasia = readString("Digite Nome Fantasia: ", "ERRO: Nome Fantasia inválido. Tente novamente.\n", 2).uppercase()
                                        println("OK: NOME FANTASIA DEFINIDO '$cadastroNomeFantasia'.\n")
                                    }

                                    else -> println("OK: DEFINIDO USUÁRIO SEM EMPRESA.\n")
                                }
                            }
                        }

                        val cadastroUsuario = DadosUsuario(
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
                        Repositorio.adicionarDadosUsuario(cadastroUsuario)
                        println("OK: USUÁRIO CADASTRADO COM SUCESSO.\n")
                    }

                    !validarSenhaCadastro(cadastroSenha, confirmarSenha) -> println("ERRO: Senhas não conferem. Tente novamente.\n")
                }
            }

            else -> println("ERRO: E-mail inválido. Solicite novamente.\n")
        }
    }

    fun acessarUsuario(): DadosUsuario? {
        println("\nACESSAR USUÁRIO")
        val buscarEmail = readString("Digite o e-mail da sua conta: ", "ERRO: E-mail inválido. Tente novamente.\n", 3).lowercase()
        val buscarSenha = readString("Digite a senha da sua conta: ", "ERRO: Senha inválida. Tente novamente.\n", 8)
        val usuarioEncontrado = Repositorio.buscarUsuarioCadastrado(buscarEmail, buscarSenha)
        when {
            usuarioEncontrado == null -> println("ERRO: E-mail e/ou senha incorretos. Solicite novamente.\n")
            else -> when {
                !usuarioEncontrado.statusConta -> {
                    println("\nAVISO: Esta é uma conta desativada. Reativar para acessar? [1] SIM [2] NÃO")
                    val reativarConta = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)
                    when (reativarConta) {
                        1 -> {
                            usuarioEncontrado.statusConta = true
                            println("OK: Conta reativada. Acesse novamente.\n")
                        }

                        else -> println("OK: Operação cancelada.\n")
                    }
                }
            }
        }

        return usuarioEncontrado
    }

    fun alterarUsuario(usuarioEncontrado: DadosUsuario) {
        var menuAlterarUsuario = true
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
            val opcaoAlterarUsuario = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", limiteOpcoes)

            when (opcaoAlterarUsuario) {
                0 -> {
                    println("OK: Selecionado Voltar.\n")
                    menuAlterarUsuario = false
                }

                1 -> {
                    usuarioEncontrado.nome = readString("Digite Nome atualizado: ", "ERRO: Nome inválido. Tente novamente.\n", 2).uppercase()
                    println("OK: NOME DEFINIDO '${usuarioEncontrado.nome}'.\n")
                }

                2 -> {
                    var dataValida = false
                    do {
                        try {
                            println("MENU: DEFINIR DATA DE NASCIMENTO")
                            val diaNascimento = readInt("Digite Somente Dia de Nascimento (DD) atualizado: ", "ERRO: Dia inválido. Tente novamente.\n", 1..31)
                            val mesNascimento = readInt("Digite Somente Mês de Nascimento (MM) atualizado: ", "ERRO: Mês inválido. Tente novamente.\n", 1..12)
                            val anoNascimento = readInt("Digite Somente Ano de Nascimento (AAAA) atualizado: ", "ERRO: Ano inválido. Tente novamente.\n", 1920..2010)
                            usuarioEncontrado.dataNascimento = LocalDate.of(anoNascimento, mesNascimento, diaNascimento)
                            println("OK: DATA DE NASCIMENTO DEFINIDA '${usuarioEncontrado.dataNascimento}'.\n")
                            dataValida = true
                        } catch (_: java.time.DateTimeException) {
                            println("ERRO: Data inválida. Tente novamente.\n")
                        }
                    } while (!dataValida)
                }

                3 -> {
                    println("ALTERANDO: Sexo \n[1] MASCULINO, [2] FEMININO, [3] NÃO INFORMADO")
                    val alterarSexo = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..3)
                    usuarioEncontrado.sexo =
                        when (alterarSexo) {
                            1 -> SexoUsuario.MASCULINO
                            2 -> SexoUsuario.FEMININO
                            else -> SexoUsuario.NAO_INFORMADO
                        }
                    println("OK: SEXO DEFINIDO ${usuarioEncontrado.sexo}.\n")
                }

                4 -> {
                    var cadastroSenha: String
                    do {
                        cadastroSenha = readString("Digite Senha atualizada: ", "ERRO: A senha deve ter no mínimo 8 caracteres. Tente novamente.\n", 8)
                        val confirmarSenha = readString("Confirme Senha atualizada: ", "ERRO: A senha deve ter no mínimo 8 caracteres. Tente novamente.\n", 8)

                        when {
                            cadastroSenha != confirmarSenha -> println("ERRO: Senhas não conferem. Tente novamente.\n")
                            else -> println("OK: SENHA ATUALIZADA.\n")
                        }
                    } while (cadastroSenha != confirmarSenha)
                    usuarioEncontrado.senha = cadastroSenha
                }

                5 -> {
                    when {
                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                            usuarioEncontrado.cnpj = readString("Digite CNPJ (14 dígitos) atualizado: ", "ERRO: CNPJ inválido. Tente novamente.\n", 14)
                            println("OK: CNPJ DEFINIDO '${usuarioEncontrado.cnpj}'.\n")
                        }

                        else -> println("ERRO: Opção inválida. Tente novamente.\n")
                    }
                }

                6 -> {
                    when {
                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                            usuarioEncontrado.razaoSocial = readString("Digite Razão Social atualizada: ", "ERRO: Razão Social inválida. Tente novamente.\n", 2).uppercase()
                            println("OK: RAZÃO SOCIAL DEFINIDA '${usuarioEncontrado.razaoSocial}'.\n")
                        }

                        else -> println("ERRO: Opção inválida. Tente novamente.\n")
                    }
                }

                7 -> {
                    when {
                        usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
                            usuarioEncontrado.nomeFantasia = readString("Digite Nome Fantasia atualizado: ", "ERRO: Nome Fantasia inválido. Tente novamente.\n", 2).uppercase()
                            println("OK: NOME FANTASIA DEFINIDO '${usuarioEncontrado.nomeFantasia}'.\n")
                        }

                        else -> println("ERRO: Opção inválida. Tente novamente.\n")
                    }
                }
            }
        } while (menuAlterarUsuario)
    }

    fun visualizarUsuario(usuarioEncontrado: DadosUsuario) {
        val diferenca = java.time.Period.between(usuarioEncontrado.dataNascimento, LocalDate.now())
        val idadeAnos = diferenca.years
        val idadeMeses = diferenca.months
        val idadeDias = diferenca.days

        println("\nSEU USUÁRIO:")
        println("Nome: ${usuarioEncontrado.nome}")
        println("E-mail: ${usuarioEncontrado.email}")
        println("Data de Nascimento: ${Repositorio.formatarData(usuarioEncontrado.dataNascimento)}")
        println("Idade: $idadeAnos anos, $idadeMeses meses e $idadeDias dias")
        println("Sexo: ${usuarioEncontrado.sexo}")
        when {
            usuarioEncontrado.tipoUsuario == TipoUsuario.ORGANIZADOR -> {
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

        readString("[QUALQUER TECLA] Voltar\n", "ERRO: Um caractere é necessário. Tente novamente.\n")
    }

    fun desativarUsuario(usuarioEncontrado: DadosUsuario) {
        println("Desativar a conta? [1] SIM [2] NÃO: ")
        val desativarConta = readInt("Digite opção: ", "ERRO: Opção inválida. Tente novamente.\n", 1..2)

        when (desativarConta) {
            1 -> {
                val possuiEventos = when (usuarioEncontrado.tipoUsuario) {
                    TipoUsuario.ORGANIZADOR -> {
                        Repositorio.organizadorPossuiEventos(usuarioEncontrado.email)
                    }

                    else -> false
                }

                when (possuiEventos) {
                    true -> println("ERRO: Você tem eventos ativos. Desativação não permitida.\n")
                    false -> {
                        usuarioEncontrado.statusConta = false
                        println("OK:\nConta desativada (${usuarioEncontrado.email}).")
                        println("Usuário desconectado.\n")
                    }
                }
            }

            else -> println("OK: Operação cancelada.\n")
        }
    }
}