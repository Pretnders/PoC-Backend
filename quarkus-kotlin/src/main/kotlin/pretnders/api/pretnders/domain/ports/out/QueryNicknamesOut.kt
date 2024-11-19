package pretnders.api.pretnders.domain.ports.out

interface QueryNicknamesOut {
    fun doNicknameExist(reference:String, newNickname: String):Boolean
}