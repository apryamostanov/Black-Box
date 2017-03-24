package com.a9ae0b01f0ffc.black_box.tests

import com.a9ae0b01f0ffc.black_box.annotations.I_black_box
import com.a9ae0b01f0ffc.black_box.annotations.I_fix_variable_scopes
import com.a9ae0b01f0ffc.black_box.implementation.T_logger
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util

@I_fix_variable_scopes
class Z {

    @I_black_box
    void q() {
        System.out.println("zzzzzzz".replace("t", "o").replace("i", "m").replace("p", "s"))
    }

}
