package com.a9ae0b01f0ffc.black_box.annotations

import groovy.transform.ToString
import org.codehaus.groovy.ast.ASTNode
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

    void visit(ASTNode[] nodes, final SourceUnit source) {
            init(nodes, source)
            VariableScopeVisitor l_variable_scope_visitor = new VariableScopeVisitor(source, true)
            source.AST.classes.each {
                l_variable_scope_visitor.visitClass(it)
            }
    }

}