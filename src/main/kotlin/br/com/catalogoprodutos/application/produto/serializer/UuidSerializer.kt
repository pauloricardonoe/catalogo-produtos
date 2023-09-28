package br.com.catalogoprodutos.application.produto.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID
import java.util.UUID.fromString

object UuidSerializer : KSerializer<UUID> {

    override fun deserialize(decoder: Decoder): UUID =
        fromString(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: UUID): Unit =
        encoder.encodeString(value.toString())

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(serialName = "UUID", kind = STRING)
}
