package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string_builder
import groovy.inspect.swingui.AstNodeToScriptVisitor
import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_1_const.*
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_3_utils.method_arguments_present

@ToString(includeNames = true, includeFields = true, includePackage = false)
@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
class T_black_box_transformation extends AbstractASTTransformation {

    private static final ClassNode PC_CATCHED_THROWABLE_TYPE = ClassHelper.make(Throwable.class)
    public static final String PC_CLASS_NAME = "T_black_box_transformation"
    private static final T_static_string_builder s = T_logging_base_5_context.s
    String p_class_name = GC_EMPTY_STRING
    String p_method_name = GC_EMPTY_STRING
    String p_statement_name = GC_EMPTY_STRING
    String p_black_box_type = GC_EMPTY_STRING
    Parameter[] p_parameters = GC_SKIPPED_ARGS as Parameter[]
    VariableScope p_variable_scope = GC_SKIPPED_ARGS as VariableScope
    static SourceUnit p_last_processed_source_unit = GC_NULL_OBJ_REF as SourceUnit
    static T_logging_base_5_context u = new T_logging_base_5_context()

    AnnotationNode get_annotation_node() {
        return p_annotation_node
    }
    private AnnotationNode p_annotation_node = GC_NULL_OBJ_REF as AnnotationNode

    void set_annotation_node(AnnotationNode p_annotation_node) {
        this.p_annotation_node = p_annotation_node
    }

    void visit(ASTNode[] i_ast_nodes, SourceUnit i_source_unit) {
        final String LC_METHOD_NAME = "visit"
        if (!T_logging_base_5_context.is_init()) {
            T_logging_base_5_context.init_custom("C:/COMPILE/with_logging/commons.conf")
        }
        u.l().log_enter_method(PC_CLASS_NAME, LC_METHOD_NAME, GC_ZERO)
        try {
            if (u.c().GC_BLACK_BOX_ENABLED != GC_TRUE_STRING) {
                return
            }
            if (i_ast_nodes.length == 2 && i_ast_nodes[0] instanceof AnnotationNode && i_ast_nodes[1] instanceof AnnotatedNode) {
                p_annotation_node = (AnnotationNode) i_ast_nodes[GC_FIRST_INDEX]
                ASTNode l_method_node = i_ast_nodes[GC_ONE_ONLY]
                Expression l_type_member = ((AnnotationNode) i_ast_nodes[0]).getMember("value")
                String l_black_box_type = u.GC_BLACK_BOX_TYPE_FULL
                if (l_type_member != GC_NULL_OBJ_REF) {
                    l_black_box_type = l_type_member.getText()
                }
                u.l().log_debug(u.s.Black_box_type_Z1, l_black_box_type)
                if (l_method_node instanceof MethodNode) {
                    u.l().log_debug(u.s.Processing_method_Z1, l_method_node.getName())
                    p_method_name = l_method_node.getName()
                    p_class_name = l_method_node.getDeclaringClass().getNameWithoutPackage()
                    p_statement_name = u.GC_STATEMENT_NAME_METHOD
                    p_black_box_type = l_black_box_type
                    p_parameters = l_method_node.getParameters()
                    p_variable_scope = l_method_node.getVariableScope()
                    T_black_box_visitor l_full_expression_visitor = new T_black_box_visitor(this)
                    l_method_node.getCode().visit(l_full_expression_visitor)
                    BlockStatement l_changed_block_statement = new BlockStatement()
                    l_changed_block_statement.addStatement(create_shortcut_declaration())
                    l_changed_block_statement.addStatement(create_logger_declaration())
                    l_changed_block_statement.addStatement(create_l_methodname_declaration(l_method_node.getName()))
                    l_changed_block_statement.addStatement(create_l_classname_declaration(l_method_node.getDeclaringClass().getName()))
                    l_changed_block_statement.addStatement(decorate_method(l_method_node.getCode(), l_method_node.getParameters()))
                    l_method_node.setCode(l_changed_block_statement)
                    StringWriter l_string_writer = new StringWriter()
                    l_method_node.getCode().visit(new AstNodeToScriptVisitor(l_string_writer))
                    u.l().log_trace(l_string_writer.getBuffer().toString())
                    u.l().log_debug(u.s.Finished_Processing_method_Z1, l_method_node.getName())
                } else {
                    this.addError("@I_black_box_base should be applied to Methods.", l_method_node)
                }
            } else {
                throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(i_ast_nodes))
            }
        } catch (Throwable e_others) {
            u.l().log_error_method(PC_CLASS_NAME, LC_METHOD_NAME, GC_ZERO, e_others)
            throw e_others
        } finally {
            u.l().log_exit_method()
        }
    }

    Expression decorate_expression(Expression i_expression_to_decorate, String i_expression_name, String i_expression_code) {
        Statement l_finally_block = new BlockStatement()
        if (u.GC_BLACK_BOX_TYPE_FULL == p_black_box_type) {
            BlockStatement l_decorated_block_statement = new BlockStatement()
            BlockStatement l_try_block = new BlockStatement()
            ReturnStatement l_return_statement = new ReturnStatement(i_expression_to_decorate)
            l_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_result", new ArgumentListExpression(new MethodCallExpression(new VariableExpression("l_util"), "r", new ArgumentListExpression(l_return_statement.getExpression(), new ConstantExpression(l_return_statement.getExpression().getText()))))))
            l_try_block.addStatement(l_return_statement)
            u.l().log_trace(i_expression_name, i_expression_code, i_expression_to_decorate.getLineNumber())
            u.l().log_trace("l_logger.log_enter_expression($i_expression_name, $i_expression_code, ${i_expression_to_decorate.getLineNumber()}")
            l_decorated_block_statement.addStatement(create_log_method_call_with_traces("l_logger.log_enter_expression(\"$i_expression_name\", \"$i_expression_code\", ${i_expression_to_decorate.getLineNumber()}"))
            l_finally_block.addStatement(create_log_method_call_with_traces("l_logger.log_exit_expression("))
            l_decorated_block_statement.addStatement(create_try_catch("l_logger.log_error_expression($u.GC_EXCEPTION_VARIABLE_NAME", l_try_block, l_finally_block))
            ClosureExpression l_closure_expression = GeneralUtils.closureX(GC_NULL_OBJ_REF as Parameter[], l_decorated_block_statement)
            l_closure_expression.setVariableScope(p_variable_scope.copy())
            MethodCallExpression l_method_call_on_closure = GeneralUtils.callX(l_closure_expression, "call", new ArgumentListExpression())
            return l_method_call_on_closure
        } else {
            return i_expression_to_decorate
        }
    }

    Statement decorate_statement(Statement i_statement_to_decorate, String i_statement_name) {
        BlockStatement l_decorated_block_statement = new BlockStatement()
        BlockStatement l_try_block = new BlockStatement()
        l_try_block.addStatement(i_statement_to_decorate)
        Statement l_finally_block
        if (u.GC_BLACK_BOX_TYPE_FULL == p_black_box_type) {
            l_decorated_block_statement.addStatement(create_log_method_call_with_traces("l_logger.log_enter_statement(\"$i_statement_name\", ${i_statement_to_decorate.getLineNumber()}"))
            l_finally_block = create_log_method_call_with_traces("l_logger.log_exit_statement(")
            l_decorated_block_statement.addStatement(create_try_catch("l_logger.log_error_statement($u.GC_EXCEPTION_VARIABLE_NAME", l_try_block, l_finally_block))
            return l_decorated_block_statement
        } else {
            return i_statement_to_decorate
        }
    }

    Statement decorate_method(Statement i_method_code, Parameter[] i_parameters = GC_SKIPPED_ARGS as Parameter[]) {
        BlockStatement l_decorated_block_statement = new BlockStatement()
        BlockStatement l_try_block = new BlockStatement()
        l_try_block.addStatement(i_method_code)
        Statement l_finally_block
        if (u.GC_BLACK_BOX_TYPE_FULL == p_black_box_type) {
            l_decorated_block_statement.addStatement(create_log_method_call_with_traces("l_logger.log_enter_method(\"$p_class_name\", \"$p_method_name\", ${i_method_code.getLineNumber()}", i_parameters))
            l_finally_block = create_log_method_call_with_traces("l_logger.log_exit_method(")
            l_decorated_block_statement.addStatement(create_try_catch("l_logger.log_error_method(\"$p_class_name\", \"$p_method_name\", ${i_method_code.getLineNumber()}", l_try_block, l_finally_block, i_parameters))
        } else if (p_black_box_type == u.GC_BLACK_BOX_TYPE_ERROR) {
            if (u.c().GC_PROFILE_ALL == GC_TRUE_STRING) {
                l_decorated_block_statement.addStatement(create_log_method_call_with_traces("l_logger.profile_start_method(\"$p_class_name\", \"$p_method_name\", ${i_method_code.getLineNumber()}"))
                l_finally_block = create_log_method_call_with_traces("l_logger.profile_stop_any(")
            } else {
                l_finally_block = new EmptyStatement()
            }
            l_decorated_block_statement.addStatement(create_try_catch("l_logger.log_error_method(\"$p_class_name\", \"$p_method_name\", ${i_method_code.getLineNumber()}", l_try_block, l_finally_block, i_parameters))
        } else if (p_black_box_type == u.GC_BLACK_BOX_TYPE_PROFILE) {
            l_decorated_block_statement.addStatement(create_log_method_call_with_traces("l_logger.profile_start_method(\"$p_class_name\", \"$p_method_name\", ${i_method_code.getLineNumber()}"))
            l_finally_block = create_log_method_call_with_traces("l_logger.profile_stop_any(")
            l_decorated_block_statement.addStatement(create_try_catch("l_logger.log_error_method(\"$p_class_name\", \"$p_method_name\", ${i_method_code.getLineNumber()}", l_try_block, l_finally_block, i_parameters))
        } else {
            return i_method_code
        }
        return l_decorated_block_statement
    }

    static Statement create_l_methodname_declaration(String i_methodname) {
        return GeneralUtils.declS(GeneralUtils.varX("l_methodname"), GeneralUtils.constX(i_methodname))
    }

    static Statement create_l_classname_declaration(String i_classname) {
        return GeneralUtils.declS(GeneralUtils.varX("l_classname"), GeneralUtils.constX(i_classname))
    }

    static Statement create_logger_declaration() {
        return GeneralUtils.declS(GeneralUtils.varX("l_logger"), GeneralUtils.callX(GeneralUtils.varX("l_util"), "l", new ArgumentListExpression()))
    }

    static Statement create_shortcut_declaration() {
        return GeneralUtils.declS(GeneralUtils.varX("l_util"), new ConstructorCallExpression(new ClassNode(u.GC_UTILITY_CLASS), new ArgumentListExpression()))
    }

    static Statement create_log_method_call_with_traces(String i_code_line, Parameter[] i_parameters) {
        u.l().log_trace(i_code_line)
        Parameter[] l_arguments = i_parameters
        String l_serialized_parameters = GC_EMPTY_STRING
        if (method_arguments_present(i_parameters)) {
            for (l_argument in l_arguments) {
                l_serialized_parameters += ", l_util.r(${l_argument.getName()}, \"${l_argument.getName()}\")"
            }
        }
        String l_statement_code = "$i_code_line $l_serialized_parameters)"
        u.l().log_trace(l_statement_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
        return (Statement) l_resulting_statements.first()
    }

    TryCatchStatement create_try_catch(String i_log_error_code_line, BlockStatement i_main_block, Statement i_finally_block, Parameter[] i_parameters) {
        TryCatchStatement l_try_catch_statement = new TryCatchStatement(i_main_block, i_finally_block)
        BlockStatement l_throw_block = new BlockStatement()
        l_throw_block.addStatement(create_log_method_call_with_traces(i_log_error_code_line, i_parameters))
        l_throw_block.addStatement(create_rethrow())
        l_try_catch_statement.addCatch(GeneralUtils.catchS(GeneralUtils.param(PC_CATCHED_THROWABLE_TYPE, u.GC_EXCEPTION_VARIABLE_NAME), l_throw_block))
        return l_try_catch_statement
    }

    Statement create_rethrow() {
        ThrowStatement l_throw_statement = GeneralUtils.throwS(GeneralUtils.varX(u.GC_EXCEPTION_VARIABLE_NAME))
        l_throw_statement.setSourcePosition(p_annotation_node)
        return l_throw_statement
    }

}
