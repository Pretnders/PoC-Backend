package pretnders.api.pretnders_chat.persistence

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Instant

@Entity
@Table(name = "messages")
class MessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_generator")
    @SequenceGenerator(name = "messages_generator", sequenceName = "messages_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    var id: Long? = null
    @Column(name = "match_id", columnDefinition = "BIGINT", nullable = false)
    var match: Long? = null
    @Column(name = "content", columnDefinition = "TEXT", nullable = false, updatable = false)
    var content: String? = null
    @Column(name = "sender_reference", columnDefinition = "bpchar(32)", nullable = false, updatable = false)
    var senderReference: String? = null
    @Column(name = "reference", columnDefinition = "bpchar(32)", nullable = false, updatable = false)
    var reference: String? = null
    @Column(name = "reported", columnDefinition = "BOOLEAN DEFAULT false", nullable = false)
    var reported: Boolean? = false
    @Column(name = "report_treated", columnDefinition = "BOOLEAN DEFAULT false", nullable = false)
    var reportTreated: Boolean? = false
    @Column(name = "sent_at", columnDefinition = "Timestamp DEFAULT CURRENT_TIMESTAMP", unique = true, nullable =
    false)
    var timeSamp: Timestamp? = Timestamp.from(Instant.now())
    override fun toString(): String {
        return "MessagesEntity(id=$id, match=$match, content=$content, senderReference=$senderReference, reference=$reference, reported=$reported, reportTreated=$reportTreated, timeSamp=$timeSamp)"
    }


}