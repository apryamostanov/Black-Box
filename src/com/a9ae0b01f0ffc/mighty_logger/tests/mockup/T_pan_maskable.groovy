package com.a9ae0b01f0ffc.mighty_logger.tests.mockup

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_maskable
import com.a9ae0b01f0ffc.mighty_logger.main.T_s
import groovy.transform.Canonical

@Canonical
class T_pan_maskable implements I_maskable{

    String p_pan = T_s.c().GC_EMPTY_STRING

    String get_pan() {
        return p_pan
    }

    void set_pan(String i_pan) {
        this.p_pan = i_pan
    }

    @Override
    String to_string_masked() {
        return p_pan.substring(0, 6)+"******"+p_pan.substring(p_pan.size()-4)
    }


}
