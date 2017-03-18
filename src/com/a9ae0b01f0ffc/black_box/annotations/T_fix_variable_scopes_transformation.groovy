package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import groovy.transform.ToString
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.classgen.VariableScopeVisitor
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@ToString(includeNames = true, includeFields = true, includePackage = false)
@GroovyASTTransformation(
        phase = CompilePhase.CANONICALIZATION
)
class T_fix_variable_scopes_transformation extends AbstractASTTransformation {

    public void visit(ASTNode[] nodes, final SourceUnit source) {
        if (!T_logging_base_5_context.x().is_init()) {
            T_logging_base_5_context.x().init_custom("C:/COMPILE/with_logging/commons.conf")
        }
        init(nodes, source)
        AnnotatedNode targetClass = (AnnotatedNode) nodes[1]
        final ClassNode classNode = (ClassNode) targetClass
        VariableScopeVisitor l_variable_scope_visitor = new VariableScopeVisitor(source ,true)
        source.AST.classes.each {
            T_logging_base_5_context.l().log_trace(it)
            l_variable_scope_visitor.visitClass(it)
        }
    }

}
