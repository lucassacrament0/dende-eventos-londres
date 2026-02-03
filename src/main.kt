enum class Sexo { MASCULINO, FEMININO, NAO_INFORMADO }
enum class Tipo { COMUM, ORGANIZADOR }

data class Usuario(
    var nome: String,
    var dataNascimento: String,
    var sexo: Sexo,
    val email: String,
    var senha: String,
    val organizador: Tipo,
    var cnpj: String? = null,
    var razaoSocial: String? = null,
    var nomeFantasia: String? = null
)

fun main() {
    // Configurando memória local
    val listaUsuarios = mutableListOf<Usuario>()
    var continuar = true

    println("Bem-vindo ao Dendê Eventos\n")

    // Loop do menu principal
    while (continuar) {
        println("MENU PRINCIPAL")
        println("1. Cadastrar Usuário")
        println("0. Sair")

        print("Digite sua opção: ")
        val opcao = readln()

        when (opcao) {
            "1" -> {
                println("\nNOVO USUÁRIO")

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
                    // O e-mail é único, recebe as informações de cadastro
                    print("Nome completo: ")
                    var nome = readln()

                    print("Data de Nascimento (DD/MM/AAAA): ")
                    var dataNascimento = readln()

                    print("Senha: ")
                    var senha = readln()

                    // Utiliza o enum para limitar as opções
                    println("Sexo: [1] MASCULINO, [2] FEMININO, [QUALQUER TECLA] PULAR")
                    print("Escolha: ")
                    val opcaoSexo = readln()

                    var sexoEscolhido = when (opcaoSexo) {
                        "1" -> Sexo.MASCULINO
                        "2" -> Sexo.FEMININO
                        else -> Sexo.NAO_INFORMADO
                    }
                    // Verifica se é usuário organizador
                    println("Você é organizador de eventos?\nDigite: [1] SIM, [QUALQUER TECLA] NÃO")
                    print("Digite: ")
                    val tipoUsuario = readln()

                    var organizador = when (tipoUsuario) {
                        "1" -> Tipo.ORGANIZADOR
                        else -> Tipo.COMUM
                    }
                    // Variaveis para empresas
                    var empresa: String
                    var cnpj: String? = null
                    var razaoSocial: String? = null
                    var nomeFantasia: String? = null

                    // Para os organizadores...
                    if (tipoUsuario == "1") {

                        // Verifica se tem empresa
                        println("Você possui uma empresa? [1] SIM [QUALQUER TECLA] NÃO")
                        print("Digite: ")
                        empresa = readln()

                        if (empresa == "1") {

                            print("CNPJ: ")
                            cnpj = readln()

                            print("Razão Social: ")
                            razaoSocial = readln()

                            print("Nome Fantasia: ")
                            nomeFantasia = readln()
                        }
                    }
                    // Adicionando o usuario cadastrado
                    val novoUsuario = Usuario(
                        nome = nome,
                        dataNascimento = dataNascimento,
                        sexo = sexoEscolhido,
                        email = email,
                        senha = senha,
                        organizador = organizador,
                        cnpj = cnpj,
                        razaoSocial = razaoSocial,
                        nomeFantasia = nomeFantasia
                    )

                    listaUsuarios.add(novoUsuario)
                    println(">> Usuário cadastrado com sucesso!\n")
                }
            }

            "0" -> {
                println("Sessão finalizada.")
                continuar = false
            }

            else -> {
                println("Opção inválida! Tente novamente.\n")
            }
        }
    }
}
