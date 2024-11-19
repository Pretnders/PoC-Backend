package pretnders.api.pretnders.persistence.services

import jakarta.enterprise.context.RequestScoped
import pretnders.api.pretnders.domain.ports.out.QueryNicknamesOut
import pretnders.api.pretnders.persistence.repositories.PretndersNicknamesQueryRepository

@RequestScoped
class QueryNicknamesSpi (
    private val pretndersNicknamesQueryRepository: PretndersNicknamesQueryRepository
) : QueryNicknamesOut {

    override fun doNicknameExist(reference:String, newNickname: String): Boolean {
        return pretndersNicknamesQueryRepository.doNicknameExist(reference, newNickname)
    }
}