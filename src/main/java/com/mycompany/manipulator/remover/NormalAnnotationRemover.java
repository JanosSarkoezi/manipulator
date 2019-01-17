package com.mycompany.manipulator.remover;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.mycompany.manipulator.remover.mediator.annotation.NormalAnnotationAttributeMediator;
import com.mycompany.manipulator.remover.mediator.interfaces.AnnotationMediator;
import com.mycompany.manipulator.remover.mediator.interfaces.AttributeMediator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Diese Klasse representiert eine Annotation Entferner. Sie ist für Annotationen gedacht, die {@code 0 - n} Schlüssel
 * Wert Paare hat. Für die Benutzung dieser Klasse ein paar Beispiele:
 * <p>
 * Betrachte folgende Klasse:
 *
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
 *     {@literal @}Column(nullable = false)
 *      public void method2(@MyAnnotation(value = "42") String value) {
 *          System.out.println("method2");
 *      }
 *  }
 * </pre>
 * <ul><li>
 * Wenn die Annotation {@code @Column} überall entfent werden soll, so kann diese Kasse wie folgt benutzt
 * werden:
 *
 * <pre>
 * CompilationUnit cu = JavaParser.parse(source);
 *
 * new NormalAnnotationRemover(cu)
 *     .forAnnotation("Column")
 *     .remove();
 * </pre>
 * </li>
 * <li>
 * Wenn die Annotation {@code @Column(name = "value")} über der Methode
 * {@code methode1} entfent werden soll, so kann diese Kasse wie folgt benutzt
 * werden:
 *
 * <pre>
 * CompilationUnit cu = JavaParser.parse(source);
 *
 * new NormalAnnotationRemover(cu)
 *     .forAnnotation("Column")
 *     .withAttribute("name", "value")
 *     .onMethod("method1")
 *     .remove();
 * </pre>
 * </li></ul>
 * Das Ergebnis ist dann in dem Objekt {@code cu} zu finden.
 * <p>
 * <p>
 * Die Funktionalität kann mit der folgenden Grammatik beschrieben werden:
 * <pre>
 *     NormalAnnotation ::= forAnnotation withAttribute? (onMetod | onParameter | onField | onClass)?
 * </pre>
 * Diese Grammatik kann visuell auf der Seite <a href="http://www.bottlecaps.de/rr/ui">http://www.bottlecaps.de/rr/ui</a>
 * betrachtet werden.
 *
 * @author saj
 */
public class NormalAnnotationRemover implements AnnotationMediator {
    private CompilationUnit cu;

    /**
     * Eim Konstruktor.
     *
     * @param cu Ein {@link CompilationUnit}.
     */
    public NormalAnnotationRemover(CompilationUnit cu) {
        this.cu = cu;

    }

    @Override
    public AttributeMediator forAnnotation(String annotationName) {
        List<NormalAnnotationExpr> annotations = cu.findAll(NormalAnnotationExpr.class).stream()
                .filter(c -> c.getName().getId().equals(annotationName))
                .collect(Collectors.toList());

        return new NormalAnnotationAttributeMediator(annotations);
    }
}
