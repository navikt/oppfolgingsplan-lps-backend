package no.nav.syfo.kafka.consumers.altinnkanal

import no.nav.altinnkanal.avro.ReceivedMessage
import no.nav.syfo.ApplicationState
import no.nav.syfo.environment.KafkaEnv
import no.nav.syfo.kafka.ALTINNKANAL_TOPIC
import no.nav.syfo.kafka.consumerProperties
import no.nav.syfo.kafka.pollDurationInMillis
import no.nav.syfo.service.AltinnLPSService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Suppress("TooGenericExceptionCaught")
class LPSKafkaConsumer(
    val env: KafkaEnv,
    val altinnLPSService: AltinnLPSService
) {
    private val log: Logger = LoggerFactory.getLogger(LPSKafkaConsumer::class.qualifiedName)
    private val kafkaListener: KafkaConsumer<String, ReceivedMessage>

    init {
        val kafkaConfig = consumerProperties(env)
        kafkaListener = KafkaConsumer(kafkaConfig)
        kafkaListener.subscribe(listOf(ALTINNKANAL_TOPIC))
    }

    fun listen(appState: ApplicationState) {
        while (appState.running) {
            kafkaListener.poll(pollDurationInMillis).forEach { record ->
                try {
                    val storedLpsUuid = receiveAndPersistLpsFromAltinn(record)
                    kafkaListener.commitSync()
                    altinnLPSService.processLpsPlan(storedLpsUuid)
                } catch (e: Exception) {
                    log.error("Error encountered while processing LPS from altinn-kanal-2: ${e.message}", e)
                }
            }
        }
    }

    private fun receiveAndPersistLpsFromAltinn(record: ConsumerRecord<String, ReceivedMessage>): UUID {
        val receivedMessage = record.value()
        val archiveReference = receivedMessage.getArchiveReference()
        val payload = receivedMessage.getXmlMessage()
        return altinnLPSService.persistLpsPlan(archiveReference, payload)
    }
}
