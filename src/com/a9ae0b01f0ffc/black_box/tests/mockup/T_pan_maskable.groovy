package com.a9ae0b01f0ffc.black_box.tests.mockup

import com.a9ae0b01f0ffc.black_box.interfaces.I_maskable
import com.a9ae0b01f0ffc.black_box.main.T_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import groovy.transform.Canonical

@Canonical
class T_pan_maskable implements I_maskable{

    String p_pan = T_const.GC_EMPTY_STRING

    String get_pan() {
        return p_pan
    }

    void set_pan(String i_pan) {
        this.p_pan = i_pan
    }

    @Override
    String to_string_masked(String i_mask_type) {
        if (i_mask_type=="last4digits") {
            return "**********" + p_pan.substring(p_pan.size() - 4)
        } else {
            return p_pan.substring(0, 6) + "******" + p_pan.substring(p_pan.size() - 4)
        }
    }


}
