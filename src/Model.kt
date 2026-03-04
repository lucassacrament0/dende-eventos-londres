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

data class Ingresso(
    val id: Int,
    val idEvento: Int,
    val emailUsuario: String,
    var statusDisponibilidade: Boolean,
    val valorPago: Double
)