package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.commons.static_string.T_static_string
import groovy.transform.ToString
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.control.CompilePhase

@ToString(includeNames = true, includeFields = true)
class T_black_box_full_visitor extends CodeVisitorSupport {

    static final String PC_CLASS_NAME = "T_black_box_full_visitor"

    Statement create_log_statement(T_static_string i_message, Integer i_line_number) {
        final String LC_METHOD_NAME = "create_log_statement"
        String l_statement_code = T_logging_const.GC_EMPTY_STRING
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(i_message, "i_message"), T_s.r(this, "this"))
        try {
            l_statement_code = "l_logger.log_statement(l_shortcuts.s().$i_message, $i_line_number)"
            T_s.l().log_debug(T_s.s().l_statement_code_Z1, l_statement_code)
            List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
            T_s.l().log_result(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(l_statement_code, "resulting_statement"))
            return (Statement) l_resulting_statements.first()
        } catch (Throwable e_others) {
            l_logger.log_error(PC_CLASS_NAME, LC_METHOD_NAME, e_others, T_s.r(l_statement_code, "l_statement_code"))
            throw e_others
        } finally {
            T_s.l().log_exit(PC_CLASS_NAME, LC_METHOD_NAME)
        }
    }

    @Override
    void visitBlockStatement(BlockStatement i_statement) {
        final String LC_METHOD_NAME = "visitBlockStatement"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            ArrayList<Statement> l_modified_statements = i_statement.getStatements()
            for (l_statement_to_modify in l_modified_statements) {
                l_statement_to_modify.visit(this)
            }
            T_s.l().log_result(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(PC_CLASS_NAME, LC_METHOD_NAME, e_others)
            throw e_others
        } finally {
            T_s.l().log_exit(PC_CLASS_NAME, LC_METHOD_NAME)
        }
    }

    @Override
    void visitForLoop(ForStatement i_statement) {
        final String LC_METHOD_NAME = "visitForLoop"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            Statement l_statement_to_modify = i_statement.getLoopBlock()
            l_statement_to_modify.visit(this)
            BlockStatement l_modified_statements = new BlockStatement()
            l_modified_statements.addStatement(create_log_statement(T_s.s().Start_loop_cycle, i_statement.getLineNumber()))
            l_modified_statements.addStatement(l_statement_to_modify)
            l_modified_statements.addStatement(create_log_statement(T_s.s().End_loop_cycle, i_statement.getLineNumber()))
            i_statement.setLoopBlock(l_modified_statements)
            T_s.l().log_result(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(PC_CLASS_NAME, LC_METHOD_NAME, e_others)
            throw e_others
        } finally {
            T_s.l().log_exit(PC_CLASS_NAME, LC_METHOD_NAME)
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
