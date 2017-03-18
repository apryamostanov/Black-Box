package com.a9ae0b01f0ffc.black_box.implementation.formatters

import java.util.logging.LogRecord

class T_jul_formatter_simplest extends java.util.logging.Formatter {
    @Override
    String format(LogRecord record) {
        return record.message
    }
}
