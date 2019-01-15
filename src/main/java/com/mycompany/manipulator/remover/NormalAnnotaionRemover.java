package com.mycompany.manipulator.remover;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Diese Klasse representiert eine Annotation Entferner. Für die Benutzung ein Beispiel:
 * <p>
 * Betrachte folgende Klasse:
 * <pre>
 *  package com.mycompany.manipulator;
 *
 *  import com.mycompany.manipulator.deleter.MyAnnotation;
 *  import javax.persistence.Column;
 *  import javax.persistence.NamedQueries;
 *  import javax.persistence.NamedQuery;
 *
 * {@literal @}NamedQueries({
 *     {@literal @}NamedQuery(name = "asdf", query = "asdf"),
 *     {@literal @}NamedQuery(name = "qwer", query = "qwer")
 *  })
 *  public class ClassForNormalAnnotation {
 *
 *     {@literal @}Column(name = "value")
 *      private String value;
 *
 *     {@literal @}Column(name = "value")
 *      public void method1() {
 *          System.out.println("method1");
 *      }
 *
 *      public void method2(@MyAnnotation(value = "42") String value) {
 *          System.out.println("method2");
 *      }
 *  }
 * </pre>
 * Wenn die normale Annotation {@code @Column(name = "value")} über der Methode {@code methode1} entfent
 * werden soll, so kann diese Kasse wie folgt benutz werden:
 * <pre>
 *  CompilationUnit cu = JavaParser.parse(source);
 *
 *  new NormalAnnotaionRemover(cu)
 *          .forAnnotation("Column")
 *          .withAttribute("name", "value")
 *          .onMethod("method1")
 *          .remove();
 *
 *  cu.toString();
 * </pre>
 * Das Ergebnis ist dann in dem Objekt {@code cu} zu finden.
 *
 * @author saj
 */
public class NormalAnnotaionRemover {
    private CompilationUnit cu;
    private String methodName;
    private String annotation;
    private String key;
    private String value;

    /**
     * Eim Konstruktor.
     *
     * @param cu Ein {@link CompilationUnit}.
     */
    public NormalAnnotaionRemover(CompilationUnit cu) {
        this.cu = cu;
    }

    public void remove() {
        Predicate<MemberValuePair> keyPredicate = c -> c.getName().getId().equals(key);
        Predicate<MemberValuePair> valuePredicate = c -> c.getValue().asStringLiteralExpr().getValue().equals(value);

        List<Node> nodes = cu.findAll(MethodDeclaration.class).stream()
                .filter(c -> c.getName().getId().equals(methodName))
                .map(MethodDeclaration::getChildNodes)
                .flatMap(Collection::stream)
                .filter(c -> c instanceof NormalAnnotationExpr)
                .map(c -> (NormalAnnotationExpr) c)
                .filter(c -> c.getName().getId().equals(annotation))
                .map(NormalAnnotationExpr::getPairs)
                .flatMap(Collection::stream)
                .filter(keyPredicate.and(valuePredicate))
                .map(c -> c.getParentNode().get())
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);
    }

    public NormalAnnotaionRemover onMethod(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public NormalAnnotaionRemover forAnnotation(String annotation) {
        this.annotation = annotation;
        return this;
    }

    public NormalAnnotaionRemover withAttribute(String key, String value) {
        this.key = key;
        this.value = value;

        return this;
    }
}
