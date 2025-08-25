package dev.sdkforge.nfc.domain

enum class TypeNameFormat {
    /**
     * A type indicating that the payload contains a uniform resource identifier.
     */
    ABSOLUTE_URI,

    /**
     * A type indicating that the payload contains no data.
     */
    EMPTY,

    /**
     * A type indicating that the payload contains media data as defined by RFC 2046.
     */
    MEDIA,

    /**
     * A type indicating that the payload contains NFC external type data .
     */
    NFC_EXTERNAL,

    /**
     * A type indicating that the payload contains well-known NFC record type data .
     */
    NFC_WELL_KNOWN,

    /**
     * A type indicating that the payload is part of a series of records containing chunked data .
     */
    UNCHANGED,

    /**
     * A type indicating that the payload data type is unknown.
     */
    UNKNOWN,
}
