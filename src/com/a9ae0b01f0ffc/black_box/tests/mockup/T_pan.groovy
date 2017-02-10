package com.a9ae0b01f0ffc.black_box.tests.mockup

import com.a9ae0b01f0ffc.black_box.main.T_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import groovy.transform.Canonical

@Canonical
class T_pan {

    String p_pan = T_const.GC_EMPTY_STRING

    String get_pan() {
        return p_pan
    }

    void set_pan(String i_pan) {
        this.p_pan = i_pan
    }

}
