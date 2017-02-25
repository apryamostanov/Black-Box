package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import groovy.transform.ToString
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*

@ToString(includeNames = true, includeFields = true)
class T_black_box_visitor extends CodeVisitorSupport {

    static final String PC_CLASS_NAME = "T_black_box_visitor"
    T_black_box_transformation p_black_box_transformation = T_logging_const.GC_NULL_OBJ_REF as T_black_box_transformation
    String p_class_name = T_logging_const.GC_EMPTY_STRING
    String p_method_name = T_logging_const.GC_EMPTY_STRING
    String p_statement_name = T_logging_const.GC_EMPTY_STRING
    String p_black_box_type = T_logging_const.GC_EMPTY_STRING
    Parameter[] p_parameters = T_logging_const.GC_SKIPPED_ARGS as Parameter[]

    T_black_box_visitor(T_black_box_transformation i_black_box_transformation, String i_class_name, String i_method_name, String i_statement_name, String i_black_box_type, Parameter[] i_parameters = T_logging_const.GC_SKIPPED_ARGS as Parameter[]) {
        p_black_box_transformation = i_black_box_transformation
        p_method_name = i_method_name
        p_class_name = i_class_name
        p_statement_name = i_statement_name
        p_black_box_type = i_black_box_type
        p_parameters = i_parameters
    }

    @Override
    void visitBlockStatement(BlockStatement i_statement) {
        final String LC_METHOD_NAME = "visitBlockStatement"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            ArrayList<Statement> l_modified_statements = i_statement.getStatements()
            for (l_statement_to_modify in l_modified_statements) {
                l_statement_to_modify.visit(this)
            }
            T_s.l().log_result(T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    @Override
    void visitForLoop(ForStatement i_statement) {
        final String LC_METHOD_NAME = "visitForLoop"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            Statement l_statement_to_modify = i_statement.getLoopBlock()
            l_statement_to_modify.visit(this)
            i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, "for_loop_cycle", p_black_box_type, p_parameters))
            T_s.l().log_result(T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    @Override
    void visitReturnStatement(ReturnStatement i_return_statement) {
        //todo return # number
        final String LC_METHOD_NAME = "visitReturnStatement"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_return_statement, "i_return_statement"), T_s.r(this, "this"))
        try {
            if ([T_logging_const.GC_BLACK_BOX_TYPE_FULL, T_logging_const.GC_BLACK_BOX_TYPE_INVOCATION].contains(p_black_box_type)) {
                i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_result", new ArgumentListExpression(new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
            }
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    @Override
    void visitWhileLoop(WhileStatement loop) {
        super.visitWhileLoop(loop)
    }

    @Override
    void visitIfElse(IfStatement ifElse) {
        super.visitIfElse(ifElse)
    }

    @Override
    void visitExpressionStatement(ExpressionStatement statement) {
        super.visitExpressionStatement(statement)
    }

    @Override
    void visitTryCatchFinally(TryCatchStatement statement) {
        super.visitTryCatchFinally(statement)
    }

    @Override
    void visitSwitch(SwitchStatement statement) {
        super.visitSwitch(statement)
    }

    @Override
    void visitCaseStatement(CaseStatement statement) {
        super.visitCaseStatement(statement)
    }

    @Override
    void visitMethodCallExpression(MethodCallExpression call) {
        super.visitMethodCallExpression(call)
    }

    @Override
    void visitStaticMethodCallExpression(StaticMethodCallExpression call) {
        super.visitStaticMethodCallExpression(call)
    }

    @Override
    void visitCatchStatement(CatchStatement statement) {
        super.visitCatchStatement(statement)
    }

}
