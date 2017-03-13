package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_4_const
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import groovy.transform.ToString
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils

@ToString(includeNames = true, includeFields = true)
class T_black_box_visitor extends CodeVisitorSupport {

    static final String PC_CLASS_NAME = "T_black_box_visitor"
    T_black_box_transformation p_black_box_transformation = T_logging_base_4_const.GC_NULL_OBJ_REF as T_black_box_transformation


    T_black_box_visitor(T_black_box_transformation i_black_box_transformation) {
        p_black_box_transformation = i_black_box_transformation
    }

    @Override
    void visitBlockStatement(BlockStatement i_statement) {
        final String LC_METHOD_NAME = "visitBlockStatement"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_4_const.GC_STATEMENT_NAME_METHOD, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            for (l_statement_to_visit in i_statement.getStatements()) {
                l_statement_to_visit.visit(this)
            }
            ArrayList<Statement> l_modified_statements = new ArrayList<Statement>()
            for (l_statement_to_modify in i_statement.getStatements()) {
                if (l_statement_to_modify instanceof ForStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "for"))
                } else if (l_statement_to_modify instanceof WhileStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "while"))
                } else if (l_statement_to_modify instanceof IfStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "if"))
                } else if (l_statement_to_modify instanceof ExpressionStatement) {
                    l_logger.log_debug(T_logging_base_6_util.s.l_statement_to_modify_getExpression_Z1, l_statement_to_modify.getExpression())
                    if (l_statement_to_modify.getExpression() instanceof DeclarationExpression) {
                        VariableExpression l_variable_expression = ((DeclarationExpression)l_statement_to_modify.getExpression()).getVariableExpression()
                        l_modified_statements.add(GeneralUtils.declS(l_variable_expression, new EmptyExpression()))
                        Statement l_assignment_statement = GeneralUtils.assignS(l_variable_expression, ((DeclarationExpression)l_statement_to_modify.getExpression()).getRightExpression())
                        l_assignment_statement.setLineNumber(l_statement_to_modify.getLineNumber())
                        Statement l_modified_statement = p_black_box_transformation.decorate_statement(l_assignment_statement, "declaration")
                        l_modified_statement.setLineNumber(l_statement_to_modify.getLineNumber())
                        l_modified_statements.add(l_modified_statement)
                    } else if (l_statement_to_modify.getExpression() instanceof MethodCallExpression) {
                        l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, ((MethodCallExpression)l_statement_to_modify.getExpression()).getMethodAsString()))
                    } else if (l_statement_to_modify.getExpression() instanceof StaticMethodCallExpression) {
                        l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, ((StaticMethodCallExpression)l_statement_to_modify.getExpression()).getMethodAsString()))
                    } else {
                        l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, l_statement_to_modify.getExpression().getClass().getSimpleName()))
                    }
                } else if (l_statement_to_modify instanceof SwitchStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "switch"))
                } else {
                    l_modified_statements.add(l_statement_to_modify)
                }
            }
            i_statement.getStatements().clear()
            i_statement.getStatements().addAll(l_modified_statements)
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_exit()
        }
    }

    @Override
    void visitClosureExpression(ClosureExpression i_statement) {
        final String LC_METHOD_NAME = "visitClosureExpression"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_4_const.GC_STATEMENT_NAME_METHOD, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            BlockStatement l_changed_block_statement = new BlockStatement()
            T_black_box_transformation l_black_box_transformation = new T_black_box_transformation()
            l_black_box_transformation.p_black_box_type = p_black_box_transformation.p_black_box_type
            l_black_box_transformation.p_class_name = p_black_box_transformation.p_class_name
            l_black_box_transformation.p_method_name = p_black_box_transformation.p_method_name
            l_black_box_transformation.set_annotation_node(p_black_box_transformation.get_annotation_node())
            l_changed_block_statement.addStatement(l_black_box_transformation.create_shortcut_declaration_statement())
            l_changed_block_statement.addStatement(l_black_box_transformation.create_logger_declaration_statement())
            l_changed_block_statement.addStatement(l_black_box_transformation.create_l_methodname_declaration_statement(p_black_box_transformation.p_method_name))
            l_changed_block_statement.addStatement(l_black_box_transformation.create_l_classname_declaration_statement(p_black_box_transformation.p_class_name))
            l_changed_block_statement.addStatement(l_black_box_transformation.decorate_statement(i_statement.getCode(), T_logging_base_6_util.GC_STATEMENT_NAME_CLOSURE, i_statement.getParameters()))
            i_statement.setCode(l_changed_block_statement)
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_exit()
        }
    }

    @Override
    void visitForLoop(ForStatement i_statement) {
        final String LC_METHOD_NAME = "visitForLoop"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_4_const.GC_STATEMENT_NAME_METHOD, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitForLoop(i_statement)
            Statement l_statement_to_modify = i_statement.getLoopBlock()
            i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, "iteration"))
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_exit()
        }
    }

    @Override
    void visitReturnStatement(ReturnStatement i_return_statement) {
        //todo return # number
        final String LC_METHOD_NAME = "visitReturnStatement"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_4_const.GC_STATEMENT_NAME_METHOD, i_return_statement.getLineNumber(), T_logging_base_6_util.r(i_return_statement, "i_return_statement"))
        try {
            if ([T_logging_base_4_const.GC_BLACK_BOX_TYPE_FULL, T_logging_base_4_const.GC_BLACK_BOX_TYPE_INVOCATION].contains(p_black_box_transformation.p_black_box_type)) {
                i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_result", new ArgumentListExpression(new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
            }
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_exit()
        }
    }

    @Override
    void visitWhileLoop(WhileStatement i_statement) {
        final String LC_METHOD_NAME = "visitWhileLoop"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_4_const.GC_STATEMENT_NAME_METHOD, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitWhileLoop(i_statement)
            Statement l_statement_to_modify = i_statement.getLoopBlock()
            i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, "iteration"))
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_exit()
        }
    }

    @Override
    void visitIfElse(IfStatement i_statement) {
        final String LC_METHOD_NAME = "visitIfElse"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_4_const.GC_STATEMENT_NAME_METHOD, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitIfElse(i_statement)
            i_statement.setIfBlock(p_black_box_transformation.decorate_statement(i_statement.getIfBlock(), "then"))
            i_statement.setElseBlock(p_black_box_transformation.decorate_statement(i_statement.getElseBlock(), "else"))
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_exit()
        }
    }

    @Override
    void visitSwitch(SwitchStatement i_statement) {
        final String LC_METHOD_NAME = "visitSwitch"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_4_const.GC_STATEMENT_NAME_METHOD, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitSwitch(i_statement)
            for (l_statement_to_modify in i_statement.getCaseStatements()) {
                l_statement_to_modify.setCode(p_black_box_transformation.decorate_statement(l_statement_to_modify.getCode(), "case"))
            }
            i_statement.setDefaultStatement(p_black_box_transformation.decorate_statement(i_statement.getDefaultStatement(), "default"))
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_exit()
        }
    }

}
