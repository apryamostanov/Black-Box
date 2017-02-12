package com.a9ae0b01f0ffc.black_box.tests.mockup

import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box_base.implementation.annotations.I_black_box_base
import com.a9ae0b01f0ffc.commons.exceptions.E_application_exception

class T_sample_class_for_annotation_test {

    @I_black_box_base
    String do_something(String i_argument_one, String i_argument_two) {
        T_s.l().log_info(T_s.s().INSIDE_METHOD)
        return "z" + new T_pan("34234234234234234").toString()
    }

    @I_black_box_base
    void do_something2(String i_argument_one, String i_argument_two) {
        T_s.l().log_info(T_s.s().INSIDE_METHOD2)
    }

    @I_black_box_base
    String do_something3(String i_argument_one, String i_argument_two) {
        T_s.l().log_info(T_s.s().INSIDE_METHOD3)
        if (i_argument_one==i_argument_two) {
            T_s.l().log_info(T_s.s().INSIDE_METHOD5)
            if (i_argument_one==i_argument_two) {
                for (int z : 1..2) {
                    if (i_argument_one==i_argument_two) {
                        T_s.l().log_info(T_s.s().INSIDE_METHODz7)
                        return "xcvcx"
                    }
                }
                return "lfdgh"
            } else {
                T_s.l().log_info(T_s.s().INSIDE_METHOD8)
            }
            return "jrtnxdn"
        } else {
            T_s.l().log_info(T_s.s().INSIDE_METHOD6)
            if (i_argument_one!=i_argument_two) {
                T_s.l().log_info(T_s.s().INSIDE_METHOD17)
                return "irtgkjbdf"
            } else {
                T_s.l().log_info(T_s.s().INSIDE_METHOD8)
            }
        }
        T_s.l().log_info(T_s.s().INSIDE_METHOD19)
        T_s.l().log_info(T_s.s().INSIDE_METHOD4)
        return "dflkgnjdlfk"
    }


    @I_black_box_base
    String do_something4(String i_argument_one, String i_argument_two) {
        T_s.l().log_info(T_s.s().INSIDE_METHOD3)
        if (i_argument_one==i_argument_two) {
            T_s.l().log_info(T_s.s().INSIDE_METHOD5)
            if (i_argument_one==i_argument_two) {
                for (int z : 1..2) {
                    if (i_argument_one==i_argument_two) {
                        T_s.l().log_info(T_s.s().INSIDE_METHODz7)
                        throw new E_application_exception(T_s.s().SOME_SAMPLE_EXCEPTION1, "some parameter1")
                        return "xcvcx"
                    }
                }
                return "lfdghsad12"
            } else {
                T_s.l().log_info(T_s.s().INSIDE_METHOD8)
            }
            return "jrtnxdn"
        } else {
            T_s.l().log_info(T_s.s().INSIDE_METHOD6)
            if (i_argument_one!=i_argument_two) {
                T_s.l().log_info(T_s.s().INSIDE_METHOD17)
                return "irtgkjbdf"
            } else {
                T_s.l().log_info(T_s.s().INSIDE_METHOD8)
            }
        }
        T_s.l().log_info(T_s.s().INSIDE_METHOD19)
        T_s.l().log_info(T_s.s().INSIDE_METHOD4)
        return "dflkgnjdlfk"
    }


    @I_black_box_base
    String do_something5(String i_argument_one, String i_argument_two) {
        do_something4(i_argument_one, i_argument_two)
        return do_something6(i_argument_one, i_argument_two)
    }

    @I_black_box_base
    String do_something6(String i_argument_one, String i_argument_two) {
        return do_something4(i_argument_one, i_argument_two)
    }

    @I_black_box_base
    Boolean check_card_for_reissue(T_pan_maskable i_pan) {
        T_pan l_validation_pan = new T_pan(i_pan.get_pan())
        T_s.l().log_debug(T_s.s().THE_PAN_IS_ELIGIBLE_FOR_REISSUE, T_s.t(l_validation_pan, T_s.s().l_validation_pan))
        return true
    }

    @I_black_box_base
    T_pan_maskable create_new_pan(T_pan_maskable i_old_pan, Boolean i_is_create_new_pan, Integer i_duration) {
        T_s.l().log_info(T_s.s().IGNORING_DURATION_FOR_NEW_PAN)
        T_pan_maskable l_new_pan = new T_pan_maskable("44444444444444444")
        check_card_for_reissue(l_new_pan)
        return l_new_pan
    }

    @I_black_box_base
    T_pan_maskable reissue_card(String i_old_pan) {
        T_pan_maskable l_pan = new T_pan_maskable(i_old_pan)
        T_pan_maskable l_new_pan
        if (check_card_for_reissue(l_pan)) {
            l_new_pan = create_new_pan(l_pan, true, 2)
        }
        return l_new_pan
    }




}
