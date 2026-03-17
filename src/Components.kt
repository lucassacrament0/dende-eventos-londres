fun readInt(mensagem: String, mensagemErro: String, intervalo: IntRange = 0..Int.MAX_VALUE): Int {
    var valorFinal: Int? = null

    do {
        print(mensagem)
        when (val entradaUsuario = readln().toIntOrNull()) {
            null -> println(mensagemErro)
            in intervalo -> valorFinal = entradaUsuario
            else -> println(mensagemErro)
        }
    } while (valorFinal == null)

    return valorFinal
}

fun readDouble(mensagem: String, mensagemErro: String, valorMinimo: Double = 0.0, valorMaximo: Double = Double.MAX_VALUE): Double {
    var valorFinal: Double? = null

    do {
        print(mensagem)
        when (val entradaUsuario = readln().toDoubleOrNull()) {
            null -> println(mensagemErro)
            in valorMinimo..valorMaximo -> valorFinal = entradaUsuario
            else -> println(mensagemErro)
        }
    } while (valorFinal == null)

    return valorFinal
}

fun readString(mensagem: String, mensagemErro: String, tamanhoMinimo: Int = 1): String {
    var valorFinal: String? = null

    do {
        print(mensagem)
        val entradaUsuario = readln()

        when {
            entradaUsuario.length >= tamanhoMinimo -> valorFinal = entradaUsuario
            else -> println(mensagemErro)
        }
    } while (valorFinal == null)

    return valorFinal
}

fun printTable(cabecalho: String, colunas: List<String>, linhas: List<List<String?>>) {
    println("\n$cabecalho")

    val larguraColunas = colunas.mapIndexed { index, title ->
        val largurasLinhas = linhas.maxOfOrNull { it[index]?.length ?: 0 } ?: 0
        maxOf(title.length, largurasLinhas)
    }

    val separadores = larguraColunas.joinToString("-+-", "-+-", "-+-") { "-".repeat(it) }

    val linhaCabecalho = colunas.mapIndexed { index, title ->
        title.padEnd(larguraColunas[index])
    }.joinToString(" | ", " | ", " | ")

    println(separadores); println(linhaCabecalho); println(separadores)

    linhas.forEach { row ->
        val linhaFormatada = row.mapIndexed { index, cell ->
            (cell ?: "").padEnd(larguraColunas[index])
        }.joinToString(" | ", " | ", " | ")
        println(linhaFormatada)
    }
    println(separadores)
}