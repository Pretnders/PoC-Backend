package pretnders.api.pretnders.domain.ports.out

import pretnders.api.pretnders.domain.models.Pretnder

interface QueryPretndersOut {
    fun findByIdentifier(identifier: String) : Pretnder
    fun findIDByReference(reference: String) : Long
}