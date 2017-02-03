package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.I_object_with_guid

class T_object_with_guid implements I_object_with_guid{

    String p_guid = UUID.randomUUID()

    @Override
    String get_guid() {
        return p_guid
    }
}
