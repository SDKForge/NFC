package dev.sdkforge.nfc.domain

enum class NFCMifareFamily {
    /**
     * An identifier that indicates the MIFARE Ultralight® product family.
     */
    ULTRALIGHT,

    /**
     * An identifier that indicates the MIFARE Plus® product family.
     */
    PLUS,

    /**
     * An identifier that indicates a compatible ISO14443 Type A tag.
     */
    UNKNOWN,
}
