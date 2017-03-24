package com.a9ae0b01f0ffc.black_box.tests

import com.a9ae0b01f0ffc.black_box.annotations.I_black_box
import com.a9ae0b01f0ffc.black_box.annotations.I_fix_variable_scopes
import com.a9ae0b01f0ffc.black_box.implementation.T_logger
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util

@I_fix_variable_scopes
class Z {

   // @I_black_box
    void q() {
        T_logging_base_6_util l_util = new com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util()
        T_logger l_logger = l_util.l()
        l_logger.log_enter_method('Z', 'q', 12)
        try {
            l_logger.log_enter_statement('ExpressionStatement', 'java.lang.String m = dfgsdfg', 12)
            java.lang.String m = 'dfgsdfg'
            l_logger.log_exit_statement()
            l_logger.log_enter_statement('ExpressionStatement', 'java.lang.String w = m ', 13)
            java.lang.String w = {
                l_logger.log_enter_expression('VariableExpression', 'm', 13)
                l_logger.log_exit_expression()
                return l_logger.log_result(l_util.r(m, 'm'))
            }.call()
            l_logger.log_exit_statement()
            l_logger.log_enter_statement('ExpressionStatement', 'java.lang.System.out.println(w)', 14)
            java.lang.System.out.println(w)
            l_logger.log_exit_statement()
        }
        catch (java.lang.Throwable e_others) {
            l_logger.log_error_method('Z', 'q', 12, e_others)
            throw e_others
        }
        finally {
            l_logger.log_exit_method()
        }
    }

}
