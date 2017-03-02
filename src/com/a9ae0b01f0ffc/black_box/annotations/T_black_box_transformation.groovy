package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.conf.zero_conf.T_zero_ioc_conf
import com.a9ae0b01f0ffc.black_box.conf.zero_conf.T_zero_logger_conf
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.commons.main.T_common_const
import groovy.transform.ToString
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@ToString(includeNames = true, includeFields = true, includePackage = false)
@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
class T_black_box_transformation extends AbstractASTTransformation {

    private static final ClassNode PC_CATCHED_THROWABLE_TYPE = ClassHelper.make(Throwable.class)
    public static final String PC_CLASS_NAME = "T_black_box_transformation"
    public static final Class PC_SHORTCUT_CLASS = T_s

    AnnotationNode get_annotation_node() {
        return p_annotation_node
    }
    private AnnotationNode p_annotation_node = T_logging_const.GC_NULL_OBJ_REF as AnnotationNode

    void set_annotation_node(AnnotationNode p_annotation_node) {
        this.p_annotation_node = p_annotation_node
    }

    void visit(ASTNode[] i_ast_nodes, SourceUnit i_source_unit) {
        final String LC_METHOD_NAME = "visit"
        if (!T_s.x().is_init()) {
            T_s.x().init_custom(T_zero_ioc_conf.PC_IOC_CONF, T_zero_logger_conf.PC_LOGGER_CONF)
        }
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(this, "this"))
        try {
            if (T_s.c().GC_BLACK_BOX_ENABLED != T_common_const.GC_TRUE_STRING) {
                return
            }
            if (i_ast_nodes.length == 2 && i_ast_nodes[0] instanceof AnnotationNode && i_ast_nodes[1] instanceof AnnotatedNode) {
                p_annotation_node = (AnnotationNode) i_ast_nodes[T_common_const.GC_FIRST_INDEX]
                ASTNode l_method_node = i_ast_nodes[T_common_const.GC_ONE_ONLY]
                Expression l_type_member = ((AnnotationNode) i_ast_nodes[0]).getMember("value")
                String l_black_box_type = T_logging_const.GC_BLACK_BOX_TYPE_FULL
                if (l_type_member != T_common_const.GC_NULL_OBJ_REF) {
                    l_black_box_type = l_type_member.getText()
                }
                T_s.l().log_debug(T_s.s().Black_box_type_Z1, l_black_box_type)
                if (l_method_node instanceof MethodNode) {
                    T_s.l().log_debug(T_s.s().Processing_method_Z1, l_method_node.getName())
                    T_black_box_visitor l_full_expression_visitor = new T_black_box_visitor(this, l_method_node.getDeclaringClass().getName(), l_method_node.getName(), T_logging_const.GC_STATEMENT_NAME_METHOD, l_black_box_type, l_method_node.getParameters())
                    l_method_node.getCode().visit(l_full_expression_visitor)
                    BlockStatement l_changed_block_statement = new BlockStatement()
                    l_changed_block_statement.addStatement(create_shortcut_declaration_statement())
                    l_changed_block_statement.addStatement(create_logger_declaration_statement())
                    l_changed_block_statement.addStatement(create_l_methodname_declaration_statement(l_method_node.getName()))
                    l_changed_block_statement.addStatement(create_l_classname_declaration_statement(l_method_node.getDeclaringClass().getName()))
                    l_changed_block_statement.addStatement(decorate_statement(l_method_node.getCode(), l_method_node.getDeclaringClass().getName(), l_method_node.getName(), T_logging_const.GC_STATEMENT_NAME_METHOD, l_black_box_type, l_method_node.getParameters()))
                    l_method_node.setCode(l_changed_block_statement)
                    T_s.l().log_debug(T_s.s().Finished_Processing_method_Z1, l_method_node.getName())
                } else {
                    this.addError("@I_black_box_base should be applied to Methods.", l_method_node)
                }
            } else {
                throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(i_ast_nodes))
            }
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    BlockStatement decorate_statement(Statement i_statement_to_decorate, String i_class_name, String i_method_name, String i_statement_name, String i_black_box_type, Parameter[] i_parameters = T_logging_const.GC_SKIPPED_ARGS as Parameter[]) {
        final String LC_METHOD_NAME = "decorate_statement"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_statement_to_decorate, "i_statement_to_decorate"), T_s.r(i_class_name, "i_class_name"), T_s.r(i_method_name, "i_method_name"), T_s.r(i_statement_name, "i_statement_name"), T_s.r(i_parameters, "i_parameters"), T_s.r(this, "this"))
        try {
            BlockStatement l_decorated_block_statement = new BlockStatement()
            BlockStatement l_try_block = new BlockStatement()
            l_try_block.addStatement(i_statement_to_decorate)
            if ([T_logging_const.GC_BLACK_BOX_TYPE_FULL, T_logging_const.GC_BLACK_BOX_TYPE_INVOCATION].contains(i_black_box_type)) {
                l_decorated_block_statement.addStatement(create_log_enter_statement("log_enter", i_class_name, i_method_name, i_statement_name, i_statement_to_decorate.getLineNumber(), i_parameters))
                Statement l_finally_block = create_log_exit_statement("log_exit")
                l_decorated_block_statement.addStatement(create_try_catch_statement(l_try_block, l_finally_block, i_parameters))
            } else if (i_black_box_type == T_logging_const.GC_BLACK_BOX_TYPE_ERROR) {
                if (T_s.c().GC_PROFILE_ALL != T_common_const.GC_TRUE_STRING) {
                    Statement l_finally_block = new EmptyStatement()
                    l_decorated_block_statement.addStatement(create_try_catch_statement(l_try_block, l_finally_block, i_parameters))
                } else {
                    l_decorated_block_statement.addStatement(create_log_enter_statement("profile_enter", i_class_name, i_method_name, i_statement_name, i_statement_to_decorate.getLineNumber(), i_parameters))
                    Statement l_finally_block = create_log_exit_statement("profile_exit")
                    l_decorated_block_statement.addStatement(create_try_catch_statement(l_try_block, l_finally_block, i_parameters))
                }
            } else {
                throw new RuntimeException("Unsupported Black Box type $i_black_box_type")
            }
            T_s.l().log_result(T_s.r(l_decorated_block_statement, "l_decorated_block_statement"))
            return l_decorated_block_statement
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    Statement create_l_methodname_declaration_statement(String i_methodname) {
        final String LC_METHOD_NAME = "create_l_methodname_declaration_statement"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_methodname, "i_methodname"), T_s.r(this, "this"))
        try {
            return T_s.l().log_result(T_s.r(GeneralUtils.declS(GeneralUtils.varX("l_methodname"), GeneralUtils.constX(i_methodname)), "GeneralUtils.declS(GeneralUtils.varX(\"l_methodname\"), GeneralUtils.constX(i_methodname))")) as Statement
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    Statement create_l_classname_declaration_statement(String i_classname) {
        final String LC_METHOD_NAME = "create_l_classname_declaration_statement"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_classname, "i_classname"), T_s.r(this, "this"))
        try {
            return T_s.l().log_result(T_s.r(GeneralUtils.declS(GeneralUtils.varX("l_classname"), GeneralUtils.constX(i_classname)), "GeneralUtils.declS(GeneralUtils.varX(\"l_classname\"), GeneralUtils.constX(i_classname))")) as Statement
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    Statement create_logger_declaration_statement() {
        final String LC_METHOD_NAME = "create_logger_declaration_statement"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(this, "this"))
        try {
            return T_s.l().log_result(T_s.r(GeneralUtils.declS(GeneralUtils.varX("l_logger"), GeneralUtils.callX(GeneralUtils.varX("l_shortcuts"), "l", new ArgumentListExpression())), "GeneralUtils.declS(GeneralUtils.varX(\"l_logger\"), GeneralUtils.callX(GeneralUtils.varX(\"l_shortcuts\"), \"l\", new ArgumentListExpression()))")) as Statement
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    Statement create_shortcut_declaration_statement() {
        final String LC_METHOD_NAME = "create_shortcut_declaration_statement"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(this, "this"))
        try {
            return T_s.l().log_result(T_s.r(GeneralUtils.declS(GeneralUtils.varX("l_shortcuts"), new ConstructorCallExpression(new ClassNode(PC_SHORTCUT_CLASS), new ArgumentListExpression())), "T_s.r(GeneralUtils.declS(GeneralUtils.varX(\"l_shortcuts\"), new ConstructorCallExpression(new ClassNode(T_s.class), new ArgumentListExpression()))")) as Statement
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    Statement create_log_enter_statement(String i_log_enter_function, String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number, Parameter[] i_parameters = T_logging_const.GC_SKIPPED_ARGS as Parameter[]) {
        final String LC_METHOD_NAME = "create_log_enter_statement"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_log_enter_function, "i_log_enter_function"), T_s.r(i_class_name, "i_class_name"), T_s.r(i_method_name, "i_method_name"), T_s.r(i_statement_name, "i_statement_name"), T_s.r(i_line_number, "i_line_number"), T_s.r(i_parameters, "i_parameters"), T_s.r(this, "this"))
        try {
            String l_serialized_parameters = T_common_const.GC_EMPTY_STRING
            if (i_log_enter_function != "profile_enter") {
                if (i_parameters.size() != 0) {
                    for (l_argument in i_parameters) {
                        l_serialized_parameters += ", l_shortcuts.r(${l_argument.getName()}, \"${l_argument.getName()}\")"
                    }
                }
                l_serialized_parameters += ", l_shortcuts.r(this, \"this\")"
            }
            String l_statement_code = "l_logger.$i_log_enter_function(\"$i_class_name\", \"$i_method_name\", \"$i_statement_name\", ${i_line_number.toString()} $l_serialized_parameters)"
            T_s.l().log_debug(T_s.s().l_statement_code_Z1, l_statement_code)
            List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
            T_s.l().log_result(T_s.r(l_statement_code, "resulting_statement"))
            return (Statement) l_resulting_statements.first()
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    Statement create_log_exit_statement(String i_log_exit_funtion) {
        final String LC_METHOD_NAME = "create_log_exit_statement"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_log_exit_funtion, "i_log_exit_funtion"), T_s.r(this, "this"))
        try {
            String l_statement_code = "l_logger.$i_log_exit_funtion()"
            List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
            T_s.l().log_result(T_s.r(l_statement_code, "l_statement_code"))
            return (Statement) l_resulting_statements.first()
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    Statement create_log_error_statement(Parameter[] i_parameters) {
        final String LC_METHOD_NAME = "create_log_error_statement"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_parameters, "i_parameters"), T_s.r(this, "this"))
        try {
            Parameter[] l_arguments = i_parameters
            String l_serialized_parameters = T_common_const.GC_EMPTY_STRING
            if (l_arguments.size() != 0) {
                for (l_argument in l_arguments) {
                    l_serialized_parameters += ", l_shortcuts.r(${l_argument.getName()}, \"${l_argument.getName()}\")"
                }
            }
            String l_statement_code = "l_logger.log_error(e_others $l_serialized_parameters)"
            T_s.l().log_debug(T_s.s().l_statement_code_Z1, l_statement_code)
            List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
            T_s.l().log_result(T_s.r(((Statement) l_resulting_statements.first()).getText(), "resulting_statement"))
            return (Statement) l_resulting_statements.first()
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    TryCatchStatement create_try_catch_statement(BlockStatement i_try_block, Statement i_finally_block, Parameter[] i_parameters) {
        final String LC_METHOD_NAME = "create_try_catch_statement"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_try_block.getText(), "i_try_block"), T_s.r(i_finally_block.getText(), "i_finally_block"), T_s.r(i_parameters, "i_parameters"), T_s.r(this, "this"))
        try {
            TryCatchStatement l_try_catch_statement = new TryCatchStatement(i_try_block, i_finally_block)
            BlockStatement l_throw_block = new BlockStatement()
            l_throw_block.addStatement(create_log_error_statement(i_parameters))
            l_throw_block.addStatement(rethrow())
            l_try_catch_statement.addCatch(GeneralUtils.catchS(GeneralUtils.param(PC_CATCHED_THROWABLE_TYPE, "e_others"), l_throw_block))
            return T_s.l().log_result(T_s.r(l_try_catch_statement, "l_try_catch_statement")) as TryCatchStatement
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    Statement rethrow() {
        final String LC_METHOD_NAME = "rethrow"
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(this, "this"))
        try {
            ThrowStatement l_throw_statement = GeneralUtils.throwS(GeneralUtils.varX("e_others"))
            l_throw_statement.setSourcePosition(p_annotation_node)
            return T_s.l().log_result(T_s.r(l_throw_statement, "l_throw_statement")) as Statement
        } catch (Throwable e_others) {
            T_s.l().log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

}
