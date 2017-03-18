package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_4_const
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import groovy.transform.ToString
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.VariableScope
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils

import java.lang.reflect.Method

@ToString(includeNames = true, includeFields = true)
class T_black_box_visitor extends CodeVisitorSupport {

    static
    final HashMap<String, String> PC_LOGGING_AUTOREPLACEMENT_FUNCTIONS = ["log_info": "info", "log_debug": "debug", "log_warning": "warning", "log_receive_tcp": "receive_tcp", "log_trace": "traces"]
    static final String PC_AUTOREPLACEMENT_TARGET_FUNCTION = "log_generic_automatic"
    static final ArrayList<String> PC_LOGGING_FUNCTIONS_WITH_MESSAGE = ["log_info", "log_warning", "log_debug"]

    static final String PC_CLASS_NAME = "T_black_box_visitor"
    T_black_box_transformation p_black_box_transformation = T_logging_base_4_const.GC_NULL_OBJ_REF as T_black_box_transformation


    T_black_box_visitor(T_black_box_transformation i_black_box_transformation) {
        p_black_box_transformation = i_black_box_transformation
    }

    @Override
    void visitMethodCallExpression(MethodCallExpression i_method_call_expression) {
        final String LC_METHOD_NAME = "visitMethodCallExpression"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_method_enter(PC_CLASS_NAME, LC_METHOD_NAME, i_method_call_expression.getLineNumber(), T_logging_base_6_util.r(i_method_call_expression, "i_method_call_expression"))
        try {
            super.visitMethodCallExpression(i_method_call_expression)
            MethodCallExpression l_method_call_expression = i_method_call_expression
            if (PC_LOGGING_AUTOREPLACEMENT_FUNCTIONS.keySet().contains(l_method_call_expression.getMethodAsString())) {
                Expression l_orig_args = l_method_call_expression.getArguments()
                ArrayList<Expression> l_modified_args = new ArrayList<Expression>()
                if (l_orig_args instanceof ArgumentListExpression) {
                    l_modified_args.add(GeneralUtils.constX(PC_LOGGING_AUTOREPLACEMENT_FUNCTIONS.get(l_method_call_expression.getMethodAsString())))
//event type
                    l_modified_args.add(GeneralUtils.constX(l_method_call_expression.getLineNumber()))
                    if (PC_LOGGING_FUNCTIONS_WITH_MESSAGE.contains(l_method_call_expression.getMethodAsString())) {
                        l_modified_args.add(l_orig_args.getExpressions().first()) //message
                    }
                    l_modified_args.add(GeneralUtils.constX(p_black_box_transformation.p_class_name))
                    l_modified_args.add(GeneralUtils.constX(p_black_box_transformation.p_method_name))
                    for (Expression l_argument in l_orig_args.getExpressions()) {
                        if (l_argument == l_orig_args.getExpressions().first()) {
                            if (!PC_LOGGING_FUNCTIONS_WITH_MESSAGE.contains(l_method_call_expression.getMethodAsString())) {
                                l_modified_args.add(l_orig_args.getExpressions().first()) //not a message
                            }
                        } else {
                            l_modified_args.add(GeneralUtils.callX(l_method_call_expression.getObjectExpression(), "r", GeneralUtils.args(l_argument, GeneralUtils.constX(l_argument.getText()))))
                        }
                    }
                    MethodCallExpression l_new_method_call_expression = GeneralUtils.callX(l_method_call_expression.getObjectExpression(), PC_AUTOREPLACEMENT_TARGET_FUNCTION, GeneralUtils.args(l_modified_args))
                    l_method_call_expression.setMethod(l_new_method_call_expression.getMethod())
                    l_method_call_expression.setArguments(l_new_method_call_expression.getArguments())
                }
            }
        } catch (Throwable e_others) {
            l_logger.log_method_error(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_5_context.GC_ZERO, e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_method_exit()
        }
    }

    @Override
    void visitBinaryExpression(BinaryExpression i_binary_expression) {
        final String LC_METHOD_NAME = "visitBinaryExpression"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_method_enter(PC_CLASS_NAME, LC_METHOD_NAME, i_binary_expression.getLineNumber(), T_logging_base_6_util.r(i_binary_expression, "i_binary_expression"))
        try {
            super.visitBinaryExpression(i_binary_expression)
            if (T_logging_base_4_const.GC_BLACK_BOX_TYPE_FULL == p_black_box_transformation.p_black_box_type) {
                BlockStatement l_decorated_block_statement = new BlockStatement()
                //l_decorated_block_statement.setVariableScope(p_black_box_transformation.p_variable_scope.copy())
                BlockStatement l_try_block = new BlockStatement()
                //l_try_block.setVariableScope(p_black_box_transformation.p_variable_scope.copy())
                ReturnStatement l_return_statement = new ReturnStatement(i_binary_expression.getRightExpression())
                l_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_result", new ArgumentListExpression(new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(l_return_statement.getExpression(), new ConstantExpression(l_return_statement.getExpression().getText()))))))
                l_try_block.addStatement(l_return_statement)
                //l_decorated_block_statement.addStatement(p_black_box_transformation.create_shortcut_declaration_statement())
                //l_decorated_block_statement.addStatement(p_black_box_transformation.create_logger_declaration_statement())
                l_decorated_block_statement.addStatement(p_black_box_transformation.create_log_enter_statement("log_statement_enter", p_black_box_transformation.p_class_name, p_black_box_transformation.p_method_name, i_binary_expression.getLeftExpression().getText(), i_binary_expression.getLineNumber()))
                String l_right_statement_name = T_logging_base_5_context.GC_EMPTY_STRING
                if (i_binary_expression.getRightExpression() instanceof MethodCallExpression) {
                    l_right_statement_name = ((MethodCallExpression) i_binary_expression.getRightExpression()).getMethodAsString()
                } else if (i_binary_expression.getRightExpression() instanceof ConstantExpression) {
                    l_right_statement_name = ((ConstantExpression) i_binary_expression.getRightExpression()).getConstantName()
                } else if (i_binary_expression.getRightExpression() instanceof VariableExpression) {
                    l_right_statement_name = ((VariableExpression) i_binary_expression.getRightExpression()).getAccessedVariable().getName()
                }
                if (l_right_statement_name != T_logging_base_5_context.GC_EMPTY_STRING) {
                    l_decorated_block_statement.addStatement(p_black_box_transformation.create_log_enter_statement("log_statement_enter", p_black_box_transformation.p_class_name, p_black_box_transformation.p_method_name, l_right_statement_name, i_binary_expression.getLineNumber()))
                }
                BlockStatement l_finally_block = new BlockStatement()
                l_decorated_block_statement.addStatement(p_black_box_transformation.create_try_catch_statement(p_black_box_transformation.p_class_name, p_black_box_transformation.p_method_name, i_binary_expression.getLeftExpression().getText(), i_binary_expression.getLineNumber(), l_try_block, l_finally_block))
                if (l_right_statement_name != T_logging_base_5_context.GC_EMPTY_STRING) {
                    l_finally_block.addStatement(p_black_box_transformation.create_log_exit_statement("log_statement_exit"))
                }
                l_finally_block.addStatement(p_black_box_transformation.create_log_exit_statement("log_statement_exit"))
                ClosureExpression l_closure_expression = GeneralUtils.closureX(null, l_decorated_block_statement)
                l_closure_expression.setVariableScope(p_black_box_transformation.p_variable_scope.copy())
                MethodCallExpression l_method_call_on_closure = GeneralUtils.callX(l_closure_expression, "call", new ArgumentListExpression())
                i_binary_expression.setRightExpression(l_method_call_on_closure)
            }
        } catch (Throwable e_others) {
            l_logger.log_method_error(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_5_context.GC_ZERO, e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_method_exit()
        }
    }

    @Override
    void visitBlockStatement(BlockStatement i_statement) {
        final String LC_METHOD_NAME = "visitBlockStatement"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_method_enter(PC_CLASS_NAME, LC_METHOD_NAME, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitBlockStatement(i_statement)
            ArrayList<Statement> l_modified_statements = new ArrayList<Statement>()
            for (l_statement_to_modify in i_statement.getStatements()) {
                l_logger.log_debug(T_logging_base_6_util.s.Statement_to_modify, l_statement_to_modify)
                if (l_statement_to_modify instanceof ForStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "for"))
                } else if (l_statement_to_modify instanceof WhileStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "while"))
                } else if (l_statement_to_modify instanceof IfStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "if"))
                } else if (l_statement_to_modify instanceof ExpressionStatement) {
                    l_logger.log_debug(T_logging_base_6_util.s.getExpression, l_statement_to_modify.getExpression())
                    if (l_statement_to_modify.getExpression() instanceof MethodCallExpression) {
                        MethodCallExpression l_method_call_expression = l_statement_to_modify.getExpression() as MethodCallExpression
                        if (!(PC_LOGGING_AUTOREPLACEMENT_FUNCTIONS.keySet().contains(l_method_call_expression.getMethodAsString()) || PC_AUTOREPLACEMENT_TARGET_FUNCTION == l_method_call_expression.getMethodAsString())) {
                            l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, l_method_call_expression.getMethodAsString()))
                        } else {
                            l_modified_statements.add(l_statement_to_modify)
                        }
                    } else {
                        l_modified_statements.add(l_statement_to_modify)
                    }
                } else if (l_statement_to_modify instanceof SwitchStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "switch"))
                } else if (l_statement_to_modify instanceof BlockStatement) {
                    l_modified_statements.add(l_statement_to_modify)
                } else {
                    l_modified_statements.add(l_statement_to_modify) //todo: add warning here
                }
            }
            i_statement.getStatements().clear()
            i_statement.getStatements().addAll(l_modified_statements)
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_method_error(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_5_context.GC_ZERO, e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_method_exit()
        }
    }

    @Override
    void visitClosureExpression(ClosureExpression i_statement) {
        final String LC_METHOD_NAME = "visitClosureExpression"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_method_enter(PC_CLASS_NAME, LC_METHOD_NAME, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitClosureExpression(i_statement)
            BlockStatement l_changed_block_statement = new BlockStatement()
            T_black_box_transformation l_black_box_transformation = new T_black_box_transformation()
            l_black_box_transformation.p_black_box_type = p_black_box_transformation.p_black_box_type
            l_black_box_transformation.p_class_name = p_black_box_transformation.p_class_name
            l_black_box_transformation.p_method_name = p_black_box_transformation.p_method_name
            l_black_box_transformation.set_annotation_node(p_black_box_transformation.get_annotation_node())
            //l_changed_block_statement.addStatement(l_black_box_transformation.create_shortcut_declaration_statement())
            //l_changed_block_statement.addStatement(l_black_box_transformation.create_logger_declaration_statement())
            //l_changed_block_statement.addStatement(l_black_box_transformation.create_l_methodname_declaration_statement(p_black_box_transformation.p_method_name))
            //l_changed_block_statement.addStatement(l_black_box_transformation.create_l_classname_declaration_statement(p_black_box_transformation.p_class_name))
            l_changed_block_statement.addStatement(l_black_box_transformation.decorate_statement(i_statement.getCode(), T_logging_base_6_util.GC_STATEMENT_NAME_CLOSURE, i_statement.getParameters()))
            i_statement.setCode(l_changed_block_statement)
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_method_error(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_5_context.GC_ZERO, e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_method_exit()
        }
    }

    @Override
    void visitForLoop(ForStatement i_statement) {
        final String LC_METHOD_NAME = "visitForLoop"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_method_enter(PC_CLASS_NAME, LC_METHOD_NAME, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitForLoop(i_statement)
            Statement l_statement_to_modify = i_statement.getLoopBlock()
            i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, "iteration"))
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_method_error(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_5_context.GC_ZERO, e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_method_exit()
        }
    }

    @Override
    void visitReturnStatement(ReturnStatement i_return_statement) {
        //todo return # number
        final String LC_METHOD_NAME = "visitReturnStatement"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_method_enter(PC_CLASS_NAME, LC_METHOD_NAME, i_return_statement.getLineNumber(), T_logging_base_6_util.r(i_return_statement, "i_return_statement"))
        try {
            super.visitReturnStatement(i_return_statement)
            if (T_logging_base_4_const.GC_BLACK_BOX_TYPE_FULL == p_black_box_transformation.p_black_box_type) {
                i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_result", new ArgumentListExpression(new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
            }
        } catch (Throwable e_others) {
            l_logger.log_method_error(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_5_context.GC_ZERO, e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_method_exit()
        }
    }

    @Override
    void visitWhileLoop(WhileStatement i_statement) {
        final String LC_METHOD_NAME = "visitWhileLoop"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_method_enter(PC_CLASS_NAME, LC_METHOD_NAME, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitWhileLoop(i_statement)
            Statement l_statement_to_modify = i_statement.getLoopBlock()
            i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, "iteration"))
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_method_error(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_5_context.GC_ZERO, e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_method_exit()
        }
    }

    @Override
    void visitIfElse(IfStatement i_statement) {
        final String LC_METHOD_NAME = "visitIfElse"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_method_enter(PC_CLASS_NAME, LC_METHOD_NAME, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitIfElse(i_statement)
            i_statement.setIfBlock(p_black_box_transformation.decorate_statement(i_statement.getIfBlock(), "then"))
            i_statement.setElseBlock(p_black_box_transformation.decorate_statement(i_statement.getElseBlock(), "else"))
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_method_error(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_5_context.GC_ZERO, e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_method_exit()
        }
    }

    @Override
    void visitSwitch(SwitchStatement i_statement) {
        final String LC_METHOD_NAME = "visitSwitch"
        I_logger l_logger = T_logging_base_5_context.l()
        l_logger.log_method_enter(PC_CLASS_NAME, LC_METHOD_NAME, i_statement.getLineNumber(), T_logging_base_6_util.r(i_statement, "i_statement"))
        try {
            super.visitSwitch(i_statement)
            for (l_statement_to_modify in i_statement.getCaseStatements()) {
                l_statement_to_modify.setCode(p_black_box_transformation.decorate_statement(l_statement_to_modify.getCode(), "case"))
            }
            i_statement.setDefaultStatement(p_black_box_transformation.decorate_statement(i_statement.getDefaultStatement(), "default"))
            T_logging_base_5_context.l().log_result(T_logging_base_6_util.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_method_error(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_base_5_context.GC_ZERO, e_others)
            throw e_others
        } finally {
            T_logging_base_5_context.l().log_method_exit()
        }
    }

}
