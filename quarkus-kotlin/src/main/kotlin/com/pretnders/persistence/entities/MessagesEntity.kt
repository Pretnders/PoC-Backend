package com.pretnders.persistence.entities

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "messages")
class MessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admins_generator")
    @SequenceGenerator(name = "admins_generator", sequenceName = "admins_seq", allocationSize = 1)
    @Column(name = "message_id", updatable = false)
    var id: Long? = null
    @Column(name = "match_id", columnDefinition = "integer", nullable = false)
    var match: Long? = null
    @Column(name = "content", columnDefinition = "TEXT", nullable = false, updatable = false)
    var content: String? = null
    @Column(name = "reported", columnDefinition = "Boolean DEFAULT false", nullable = false)
    var reported: Boolean? = null
    @Column(name = "report_treated", columnDefinition = "Boolean DEFAULT false", nullable = false)
    var reportTreated: Boolean? = null
    @Column(name = "sent_at", columnDefinition = "Timestamp DEFAULT CURRENT_TIMESTAMP", unique = true, nullable =
    false)
    var timeSamp: Timestamp? = null
}