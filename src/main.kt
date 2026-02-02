enum class Sexo { MASCULINO, FEMININO, NAO_INFORMADO }

data class Usuario(
    val nome: String,
    val dataNascimento: String,
    val sexo: Sexo,
    val email: String,
    val senha: String,
    val organizador: Boolean
)

fun main() {
    // Configurando memória local
    val listaUsuarios = mutableListOf<Usuario>()
    var continuar = true

    println("Bem-vindo ao Dendê Eventos\n")

    // Loop do menu principal
    while (continuar) {
        println("MENU PRINCIPAL")
        println("1. Cadastrar Usuário Comum")
        println("0. Sair")

        print("Digite sua opção: ")
        val opcao = readln()

        when (opcao) {
            "1" -> {
                println("\nNOVO USUÁRIO COMUM")

                print("E-mail: ")
                val email = readln()

                // Verifica se o e-mail já existe
                var emailRepetido = false
                for (usuario in listaUsuarios) {
                    if (usuario.email == email) {
                        emailRepetido = true
                    }
                }

                if (emailRepetido) {
                    println("ERRO: Este e-mail já está cadastrado!\n")
                } else {
                    // O e-mail é único, segue
                    print("Nome completo: ")
                    var nome = readln()

                    print("Data de Nascimento (DD/MM/AAAA): ")
                    var dataNascimento = readln()

                    print("Senha: ")
                    var senha = readln()

                    // Enum para garantir que funcione
                    println("Sexo: [1] MASCULINO, [2] FEMININO, [QUALQUER TECLA] PULAR")
                    print("Escolha: ")
                    val opcaoSexo = readln()

                    var sexoEscolhido = when (opcaoSexo) {
                        "1" -> Sexo.MASCULINO
                        "2" -> Sexo.FEMININO
                        else -> Sexo.NAO_INFORMADO
                    }

                    // Adicionando o usuario cadastrado
                    val novoUsuario = Usuario(
                        nome = nome,
                        dataNascimento = dataNascimento,
                        sexo = sexoEscolhido,
                        email = email,
                        senha = senha,
                        organizador = false // O usuario é comum por padrão
                    )

                    listaUsuarios.add(novoUsuario)
                    println(">> Usuário cadastrado com sucesso!\n")
                }
            }

            "0" -> {
                println("Sessão finalizada.")
                continuar = false
            }

            else -> println("Opção inválida! Tente novamente.\n")
        }
    }
}
