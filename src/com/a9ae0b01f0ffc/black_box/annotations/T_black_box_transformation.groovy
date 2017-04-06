package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string_builder
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

import static com.a9ae0b01f0ffc.black_box.main.T_logging_base_4_const.*
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_1_const.*
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_3_utils.method_arguments_present

@ToString(includeNames = true, includeFields = true, includePackage = false)
@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
class T_black_box_transformation extends AbstractASTTransformation {

    private static final ClassNode PC_CATCHED_THROWABLE_TYPE = ClassHelper.make(Throwable.class)
    private static final String PC_BLACKBOX_ANNOTATION_PARAMETER_NAME = "value"
    private static final String PC_LOGGER_VARIABLE_NAME = "l_logger"
    private static final String PC_UTIL_VARIABLE_NAME = "l_util"
    public static final String PC_CLASS_NAME = "T_black_box_transformation"
    private static final T_static_string_builder s = T_logging_base_5_context.s
    private static final PC_PROC_NAME_LOG_RUN_CLOSURE = "log_run_closure"
    private static final PC_PROC_NAME_LOG_ENTER_STATEMENT = "log_enter_statement"
    private static final PC_PROC_NAME_LOG_EXIT_STATEMENT = "log_exit_statement"
    private static final PC_PROC_NAME_LOG_ENTER_METHOD = "log_enter_method"
    private static final PC_PROC_NAME_LOG_EXIT_METHOD = "log_exit_method"
    private static final PC_PROC_NAME_LOG_ERROR_METHOD = "log_error_method"
    private static final PC_PROC_NAME_PROFILE_START_METHOD = "profile_start_method"
    private static final PC_PROC_NAME_PROFILE_STOP_ANY = "profile_stop_any"
    private static final PC_PROC_NAME_LOG_ERROR_METHOD_STANDALONE = "log_error_method_standalone"
    private static final PC_PROC_NAME_GET_LOGGER = "l"
    private static final PC_PROC_NAME_CREATE_TRACE = "r"
    String p_class_name = GC_EMPTY_STRING
    String p_method_name = GC_EMPTY_STRING
    String p_statement_name = GC_EMPTY_STRING
    String p_black_box_level = GC_EMPTY_STRING
    Parameter[] p_parameters = GC_SKIPPED_ARGS as Parameter[]
    VariableScope p_variable_scope = GC_SKIPPED_ARGS as VariableScope
    static T_logging_base_6_util u = new T_logging_base_6_util()

    AnnotationNode get_annotation_node() {
        return p_annotation_node
    }
    private AnnotationNode p_annotation_node = GC_NULL_OBJ_REF as AnnotationNode

    void set_annotation_node(AnnotationNode p_annotation_node) {
        this.p_annotation_node = p_annotation_node
    }

    void visit(ASTNode[] i_ast_nodes, SourceUnit i_source_unit) {
        final String LC_METHOD_NAME = "visit"
        T_logging_base_5_context.init_custom(GC_BLACK_BOX_COMPILER_CONFIG_PATH)
        u.l().log_enter_method(PC_CLASS_NAME, LC_METHOD_NAME, GC_ZERO)
        try {
            if (u.c().GC_BLACK_BOX_ENABLED != GC_TRUE_STRING) {
                return
            }
            p_annotation_node = (AnnotationNode) i_ast_nodes[GC_FIRST_INDEX]
            ASTNode l_method_node = i_ast_nodes[GC_ONE_ONLY]
            Expression l_type_member = ((AnnotationNode) i_ast_nodes[GC_FIRST_INDEX]).getMember(PC_BLACKBOX_ANNOTATION_PARAMETER_NAME)
            String l_black_box_type = GC_BLACK_BOX_TYPE_FULL
            if (l_type_member != GC_NULL_OBJ_REF) {
                l_black_box_type = l_type_member.getText()
            }
            u.l().log_debug(u.s.Black_box_type_Z1, l_black_box_type)
            u.l().log_debug(u.s.Processing_method_Z1, l_method_node.getName())
            p_method_name = u.code2attribute(l_method_node.getName())
            p_class_name = l_method_node.getDeclaringClass().getNameWithoutPackage()
            p_statement_name = GC_STATEMENT_NAME_METHOD
            p_black_box_level = l_black_box_type
            p_parameters = l_method_node.getParameters()
            p_variable_scope = l_method_node.getVariableScope()
            T_black_box_visitor l_full_expression_visitor = new T_black_box_visitor(this)
            l_method_node.getCode().visit(l_full_expression_visitor)
            BlockStatement l_changed_block_statement = new BlockStatement()
            l_changed_block_statement.addStatement(create_shortcut_declaration())
            l_changed_block_statement.addStatement(create_logger_declaration())
            l_changed_block_statement.addStatement(decorate_method(l_method_node.getCode(), l_method_node.getParameters()))
            l_method_node.setCode(l_changed_block_statement)
            u.l().log_trace(u.ast2text(l_method_node.getCode()))
            u.l().log_debug(u.s.Finished_Processing_method_Z1, l_method_node.getName())
        } catch (Throwable e_others) {
            u.l().log_error_method(PC_CLASS_NAME, LC_METHOD_NAME, GC_ZERO, e_others)
            throw e_others
        } finally {
            u.l().log_exit_method()
        }
    }

    Expression decorate_expression(Expression i_expression_to_decorate, String i_expression_name, String i_expression_code) {
        if (GC_BLACK_BOX_TYPE_FULL == p_black_box_level) {
            ReturnStatement l_return_statement = new ReturnStatement(i_expression_to_decorate)
            l_return_statement.setExpression(i_expression_to_decorate)
            ClosureExpression l_closure_expression = GeneralUtils.closureX(GC_NULL_OBJ_REF as Parameter[], l_return_statement)
            l_closure_expression.setVariableScope(new VariableScope())//p_variable_scope.copy())
            MethodCallExpression l_method_call_on_closure_wrapper = GeneralUtils.callX(GeneralUtils.varX(PC_LOGGER_VARIABLE_NAME), PC_PROC_NAME_LOG_RUN_CLOSURE, new ArgumentListExpression(GeneralUtils.constX(i_expression_name), GeneralUtils.constX(i_expression_code), GeneralUtils.constX(i_expression_to_decorate.getLineNumber()), l_closure_expression))
            return l_method_call_on_closure_wrapper
        } else {
            return i_expression_to_decorate
        }
    }

    Statement decorate_statement(Statement i_statement_to_decorate, String i_statement_name, String i_statement_code) {
        //Note: here we consciously use explicit method calls and not closure runner, because it has slower performance (~2 times)
        BlockStatement l_decorated_block_statement = new BlockStatement()
        if (GC_BLACK_BOX_TYPE_FULL == p_black_box_level) {
            l_decorated_block_statement.addStatement(create_log_method_call_with_traces(PC_LOGGER_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_LOG_ENTER_STATEMENT + GC_BRACKET_OPEN + GC_XML_DOUBLE_QUOTE + i_statement_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + GC_XML_DOUBLE_QUOTE + i_statement_code + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + i_statement_to_decorate.getLineNumber()))
            l_decorated_block_statement.addStatement(i_statement_to_decorate)
            l_decorated_block_statement.addStatement(create_log_method_call_with_traces(PC_LOGGER_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_LOG_EXIT_STATEMENT + GC_BRACKET_OPEN))
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
        if (GC_BLACK_BOX_TYPE_FULL == p_black_box_level) {
            l_decorated_block_statement.addStatement(create_log_method_call_with_traces(PC_LOGGER_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_LOG_ENTER_METHOD + GC_BRACKET_OPEN + GC_XML_DOUBLE_QUOTE + p_class_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + GC_XML_DOUBLE_QUOTE + p_method_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + i_method_code.getLineNumber(), i_parameters))
            l_finally_block = create_log_method_call_with_traces(PC_LOGGER_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_LOG_EXIT_METHOD + GC_BRACKET_OPEN)
            l_decorated_block_statement.addStatement(create_try_catch(PC_LOGGER_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_LOG_ERROR_METHOD + GC_BRACKET_OPEN + GC_XML_DOUBLE_QUOTE + p_class_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + GC_XML_DOUBLE_QUOTE + p_method_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + i_method_code.getLineNumber() + GC_COMMA + GC_SPACE + GC_EXCEPTION_VARIABLE_NAME, l_try_block, l_finally_block, i_parameters))
        } else if (p_black_box_level == GC_BLACK_BOX_TYPE_ERROR) {
            if (u.c().GC_PROFILE_ALL == GC_TRUE_STRING) {
                l_decorated_block_statement.addStatement(create_log_method_call_with_traces(PC_LOGGER_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_PROFILE_START_METHOD + GC_BRACKET_OPEN + GC_XML_DOUBLE_QUOTE + p_class_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + GC_XML_DOUBLE_QUOTE + p_method_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + i_method_code.getLineNumber()))
                l_finally_block = create_log_method_call_with_traces(PC_LOGGER_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_PROFILE_STOP_ANY + GC_BRACKET_OPEN)
            } else {
                l_finally_block = new EmptyStatement()
            }
            l_decorated_block_statement.addStatement(create_try_catch(PC_LOGGER_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_LOG_ERROR_METHOD_STANDALONE + GC_BRACKET_OPEN + GC_XML_DOUBLE_QUOTE + p_class_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + GC_XML_DOUBLE_QUOTE + p_method_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + i_method_code.getLineNumber() + GC_COMMA + GC_SPACE + GC_EXCEPTION_VARIABLE_NAME, l_try_block, l_finally_block, i_parameters))
        } else if (p_black_box_level == GC_BLACK_BOX_TYPE_PROFILE) {
            l_decorated_block_statement.addStatement(create_log_method_call_with_traces(PC_LOGGER_VARIABLE_NAME + PC_PROC_NAME_PROFILE_START_METHOD + GC_BRACKET_OPEN + GC_XML_DOUBLE_QUOTE + p_class_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + GC_XML_DOUBLE_QUOTE + p_method_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + i_method_code.getLineNumber()))
            l_finally_block = create_log_method_call_with_traces(PC_LOGGER_VARIABLE_NAME + PC_PROC_NAME_PROFILE_STOP_ANY + GC_BRACKET_OPEN)
            l_decorated_block_statement.addStatement(create_try_catch(PC_LOGGER_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_LOG_ERROR_METHOD_STANDALONE + GC_BRACKET_OPEN + GC_XML_DOUBLE_QUOTE + p_class_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + GC_XML_DOUBLE_QUOTE + p_method_name + GC_XML_DOUBLE_QUOTE + GC_COMMA + GC_SPACE + i_method_code.getLineNumber() + GC_COMMA + GC_SPACE + GC_EXCEPTION_VARIABLE_NAME, l_try_block, l_finally_block, i_parameters))
        } else {
            return i_method_code
        }
        return l_decorated_block_statement
    }

    static Statement create_logger_declaration() {
        return GeneralUtils.declS(GeneralUtils.varX(PC_LOGGER_VARIABLE_NAME), GeneralUtils.callX(GeneralUtils.varX(PC_UTIL_VARIABLE_NAME), PC_PROC_NAME_GET_LOGGER, new ArgumentListExpression()))
    }

    static Statement create_shortcut_declaration() {
        return GeneralUtils.declS(GeneralUtils.varX(PC_UTIL_VARIABLE_NAME), new ConstructorCallExpression(new ClassNode(GC_UTILITY_CLASS), new ArgumentListExpression()))
    }

    static Statement create_log_method_call_with_traces(String i_code_line, Parameter[] i_parameters) {
        u.l().log_trace(i_code_line)
        Parameter[] l_arguments = i_parameters
        String l_serialized_parameters = GC_EMPTY_STRING
        if (method_arguments_present(i_parameters)) {
            for (l_argument in l_arguments) {
                l_serialized_parameters += GC_COMMA + GC_SPACE + PC_UTIL_VARIABLE_NAME + GC_POINT + PC_PROC_NAME_CREATE_TRACE + GC_BRACKET_OPEN + l_argument.getName() + GC_COMMA + GC_SPACE + GC_XML_DOUBLE_QUOTE + l_argument.getName() + GC_XML_DOUBLE_QUOTE + GC_BRACKET_CLOSE
            }
        }
        String l_statement_code = i_code_line + GC_SPACE + l_serialized_parameters + GC_BRACKET_CLOSE
        u.l().log_trace(l_statement_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
        return (Statement) l_resulting_statements.first()
    }

    TryCatchStatement create_try_catch(String i_log_error_code_line, BlockStatement i_main_block, Statement i_finally_block, Parameter[] i_parameters) {
        TryCatchStatement l_try_catch_statement = new TryCatchStatement(i_main_block, i_finally_block)
        BlockStatement l_throw_block = new BlockStatement()
        l_throw_block.addStatement(create_log_method_call_with_traces(i_log_error_code_line, i_parameters))
        l_throw_block.addStatement(create_rethrow())
        l_try_catch_statement.addCatch(GeneralUtils.catchS(GeneralUtils.param(PC_CATCHED_THROWABLE_TYPE, GC_EXCEPTION_VARIABLE_NAME), l_throw_block))
        return l_try_catch_statement
    }

    Statement create_rethrow() {
        ThrowStatement l_throw_statement = GeneralUtils.throwS(GeneralUtils.varX(GC_EXCEPTION_VARIABLE_NAME))
        l_throw_statement.setSourcePosition(p_annotation_node)
        return l_throw_statement
    }

}
