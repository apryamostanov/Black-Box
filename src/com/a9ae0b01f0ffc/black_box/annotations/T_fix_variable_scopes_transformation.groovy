package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import groovy.transform.ToString
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.classgen.VariableScopeVisitor
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import static com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context.l
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_1_const.getGC_ZERO

@ToString(includeNames = true, includeFields = true, includePackage = false)
@GroovyASTTransformation(
        phase = CompilePhase.CANONICALIZATION
)
class T_fix_variable_scopes_transformation extends AbstractASTTransformation {

    static final String PC_CLASS_NAME = "T_fix_variable_scopes_transformation"

    public void visit(ASTNode[] nodes, final SourceUnit source) {
        final String LC_METHOD_NAME = "visit"
        if (!T_logging_base_5_context.is_init()) {
            T_logging_base_5_context.init_custom("C:/COMPILE/with_logging/commons.conf")
        }
        l().log_enter_method(PC_CLASS_NAME, LC_METHOD_NAME, GC_ZERO)
        try {
            init(nodes, source)
            AnnotatedNode targetClass = (AnnotatedNode) nodes[1]
            final ClassNode classNode = (ClassNode) targetClass
            VariableScopeVisitor l_variable_scope_visitor = new VariableScopeVisitor(source, true)
            source.AST.classes.each {
                l().log_trace(it)
                l_variable_scope_visitor.visitClass(it)
            }
        } catch (Throwable e_others) {
            l().log_error_method(PC_CLASS_NAME, LC_METHOD_NAME, GC_ZERO, e_others)
            throw e_others
        } finally {
            l().log_exit_method()
        }

    }
}