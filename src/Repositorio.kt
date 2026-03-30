import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Repositorio {
    val listaUsuarios = mutableListOf<DadosUsuario>()
    val listaEventos = mutableListOf<DadosEvento>()
    val listaIngressos = mutableListOf<DadosIngresso>()

    val dataHoje: LocalDateTime = LocalDateTime.now()
    val dataHoraFormatada: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val dataFormatada: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun formatarDataHora(data: LocalDateTime): String = data.format(dataHoraFormatada)
    fun formatarData(data: LocalDate): String = data.format(dataFormatada)

    fun verificarEmailRepetido(cadastroEmail: String): Boolean {
        return listaUsuarios.any { it.email == cadastroEmail }
    }

    fun adicionarDadosUsuario(usuario: DadosUsuario) {
        listaUsuarios.add(usuario)
    }

    fun buscarUsuarioCadastrado(buscarEmail: String, buscarSenha: String): DadosUsuario? {
        val usuarioEncontrado: DadosUsuario? = listaUsuarios.find { it.email == buscarEmail && it.senha == buscarSenha }
        return usuarioEncontrado
    }

    fun organizadorPossuiEventos(emailOrganizador: String): Boolean {
        return listaEventos.any { it.organizadorEmail == emailOrganizador && it.statusEvento }
    }

    fun adicionarDadosIngresso(ingresso: DadosIngresso) {
        listaIngressos.add(ingresso)
    }

    fun contarIngressosVendidos(idEvento: Int): Int {
        return listaIngressos.count { it.idEvento == idEvento && !it.statusDisponibilidade }
    }

    fun listarIngressosVendidos(idEvento: Int): List<DadosIngresso> {
        return listaIngressos.filter { it.idEvento == idEvento && !it.statusDisponibilidade }
    }

    fun cancelarIngressosEvento(idEvento: Int) {
        val ingressosParaCancelar = listarIngressosVendidos(idEvento)
        ingressosParaCancelar.forEach { it.statusDisponibilidade = true }
    }

    fun listarIngressosReembolsados(idEvento: Int): List<DadosIngresso> {
        val ingressosReembolsados = listarIngressosVendidos(idEvento)
        return ingressosReembolsados
    }

    fun valorIngressosReembolsados(idEvento: Int): Double {
        val ingressosParaReembolso = listarIngressosReembolsados(idEvento)
        val totalReembolsado = ingressosParaReembolso.sumOf { it.valorPago }
        return totalReembolsado
    }

    fun buscarProximoIngressoId(): Int {
        return listaIngressos.maxByOrNull { it.id }?.id?.plus(1) ?: 1
    }

    fun listarIngressosUsuario(emailUsuario: String): List<DadosIngresso> {
        return listaIngressos.filter { it.emailUsuario == emailUsuario }
    }

    fun ingressosUsuarioOrdenados(emailUsuario: String): List<DadosIngresso> {
        val ingressosUsuario = listarIngressosUsuario(emailUsuario)
        val ingressosAtivos = mutableListOf<DadosIngresso>()
        val ingressosInativos = mutableListOf<DadosIngresso>()
        ingressosUsuario.forEach { ingresso ->
            val eventoEncontrado = listaEventos.find { evento -> evento.id == ingresso.idEvento }
            val dataEvento = eventoEncontrado!!.dataInicio
            when {
                ingresso.statusDisponibilidade || dataEvento.isBefore(dataHoje) -> ingressosInativos.add(ingresso)
                else -> ingressosAtivos.add(ingresso)
            }
        }
        val ordenarIngressos = compareBy<DadosIngresso>(
            { ingresso -> listaEventos.find { evento -> evento.id == ingresso.idEvento }?.dataInicio },
            { ingresso -> listaEventos.find { evento -> evento.id == ingresso.idEvento }?.nome }
        )
        val ingressosOrdenados = ingressosAtivos.sortedWith(ordenarIngressos) + ingressosInativos.sortedWith(ordenarIngressos)
        return ingressosOrdenados
    }

    fun buscarEventoDoIngresso(ingresso: DadosIngresso): DadosEvento {
        return listaEventos.find { it.id == ingresso.idEvento } ?: throw Exception("ERRO: Evento não encontrado.")
    }

    fun buscarIngressoUsuario(emailUsuario: String, idIngresso: Int): DadosIngresso {
        return listaIngressos.find { ingresso -> ingresso.emailUsuario == emailUsuario && ingresso.id == idIngresso } ?: throw Exception("ERRO: Ingresso não encontrado.")
    }

    fun adicionarDadosEvento(evento: DadosEvento) {
        listaEventos.add(evento)
    }

    fun eventosDisponiveisOrdenados(dataHoje: LocalDateTime): List<DadosEvento> {
        val eventosDisponiveis = listaEventos.filter { evento ->
            val dataEvento = evento.dataInicio
            val ingressosVendidos = listaIngressos.count { ingresso ->
                ingresso.idEvento == evento.id && !ingresso.statusDisponibilidade
            }

            evento.statusEvento && (dataEvento.isAfter(dataHoje) || dataEvento.isEqual(dataHoje)) && ingressosVendidos < evento.capacidadeMax
        }
        val eventosOrdenados = eventosDisponiveis.sortedWith(
            compareBy({ evento -> evento.dataInicio }, { evento -> evento.nome })
        )
        return eventosOrdenados
    }

    fun buscarEventosAtivosOrganizador(emailOrganizador: String): List<DadosEvento> {
        return listaEventos.filter { it.organizadorEmail == emailOrganizador && it.statusEvento }
    }

    fun buscarEventoPrincipalOrganizador(idPrincipal: Int, emailOrganizador: String): Boolean {
        return listaEventos.any { it.id == idPrincipal && it.organizadorEmail == emailOrganizador && it.statusEvento }
    }

    fun buscarProximoEventoId(): Int {
        return listaEventos.maxByOrNull { it.id }?.id?.plus(1) ?: 1
    }

    fun buscarEventosOrganizador(emailOrganizador: String): List<DadosEvento> {
        return listaEventos.filter { it.organizadorEmail == emailOrganizador }
    }

    fun buscarIdEventoOrganizador(idEvento: Int, emailOrganizador: String): DadosEvento {
        return listaEventos.find { it.id == idEvento && it.organizadorEmail == emailOrganizador } ?: throw Exception("ERRO: Evento não encontrado.")
    }

    fun vincularEventoPrincipal(idEventoPrincipal: Int, eventoAlterando: Int, emailOrganizador: String): Boolean {
        return listaEventos.any {
            it.id == idEventoPrincipal && it.id != eventoAlterando && it.organizadorEmail == emailOrganizador && it.statusEvento
        }
    }

    fun eventosOrganizadorOrdenados(emailOrganizador: String): List<DadosEvento> {
        val eventosOrganizador = buscarEventosOrganizador(emailOrganizador)
        val eventosOrdenados = eventosOrganizador.sortedWith(
            compareBy({ evento -> evento.dataInicio }, { evento -> evento.nome })
        )
        return eventosOrdenados
    }

    fun buscarIdEventoAtivo(idEvento: Int): DadosEvento? {
        return listaEventos.find { it.id == idEvento && it.statusEvento }
    }
}