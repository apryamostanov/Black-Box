package com.a9ae0b01f0ffc.black_box.tests.mockup

import com.a9ae0b01f0ffc.black_box.interfaces.I_non_sensitive
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import groovy.transform.Canonical

@Canonical
class T_pan_non_sensitive implements I_non_sensitive{

    String p_pan = T_logging_const.GC_EMPTY_STRING

    String get_pan() {
        return p_pan
    }

    void set_pan(String i_pan) {
        this.p_pan = i_pan
    }



}
