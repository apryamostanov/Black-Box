package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.main.T_s
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

    Statement create_log_statement(Statement i_statement) {
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, "create_log_statement", T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            String l_statement_code = "l_logger.log_statement(\"${i_statement.getClass().getSimpleName()}\", \"${i_statement.getText()}\")"
            T_s.l().log_debug(T_s.s().l_statement_code_Z1, l_statement_code)
            List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
            T_s.l().log_exit(PC_CLASS_NAME, "create_log_statement", T_s.r(((Statement) l_resulting_statements.first()).getText(), "resulting_statement"))
            return (Statement) l_resulting_statements.first()
        } catch (Throwable e_others) {
            l_logger.log_exception(PC_CLASS_NAME, "create_log_statement", e_others)
            throw e_others
        }
    }

    @Override
    void visitBlockStatement(BlockStatement i_statement) {
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, "visitBlockStatement", T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            ArrayList<Statement> l_modified_statements = new ArrayList<Statement>()
            for (l_statement_to_modify in i_statement.getStatements()) {
                l_modified_statements.add(create_log_statement(l_statement_to_modify))
                l_statement_to_modify.visit(this)
                l_modified_statements.add(l_statement_to_modify)
            }
            i_statement.getStatements().clear()
            i_statement.getStatements().addAll(l_modified_statements)
            super.visitBlockStatement(i_statement)
            T_s.l().log_exit(PC_CLASS_NAME, "visitBlockStatement", T_s.r(i_statement.getText(), "modified_expression_text"))
        } catch (Throwable e_others) {
            l_logger.log_exception(PC_CLASS_NAME, "visitBlockStatement", e_others)
            throw e_others
        }
    }

    @Override
    void visitForLoop(ForStatement forLoop) {
        super.visitForLoop(forLoop)
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
