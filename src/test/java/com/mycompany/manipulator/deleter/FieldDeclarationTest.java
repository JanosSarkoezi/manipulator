package com.mycompany.manipulator.deleter;

import com.mycompany.manipulator.deleter.predicate.FieldDeclarationPredicate;
import com.mycompany.manipulator.helper.ResourceReader;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author saj
 */
public class FieldDeclarationTest {

    @Test
    public void notSpecified() throws ParseException, IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate();

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        try {
            cu.accept(new FieldDeclarationDeleter(), fdp);
            Assert.assertTrue("No IllegalStateException was thrown", false);
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void onField() throws ParseException, IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate().
                onField("value2");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onField")
                .asString();
        
        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    public void onFieldWithTypo() throws ParseException, IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate().
                onField("value22");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asString();
        
        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onFields() throws ParseException, IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate()
                .onField("value2")
                .onField("value3");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();
        
        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    public void onFieldsDuplicated() throws ParseException, IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate()
                .onField("value2")
                .onField("value3")
                .onField("value3");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();
        
        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    public void onFieldsAsArray() throws ParseException, IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate()
                .onFields(Arrays.asList("value2", "value3"));

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();
        
        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    public void onFieldsAsArrayDuplicated() throws ParseException, IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate()
                .onFields(Arrays.asList("value2", "value3", "value2"));

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();
        
        Assert.assertEquals(expectedFile, cu.toString());
    }
}
