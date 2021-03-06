/*
 *  Copyright 2008 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.mybatis.generator.internal;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * @author Jeff Butler
 * 
 */
public class DefaultCommentGenerator implements CommentGenerator {

    private Properties properties;
    private boolean suppressDate;
    private boolean suppressAllComments;

    public DefaultCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
    }

    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
    	compilationUnit.addFileCommentLine("/******************************************************************************");
    	compilationUnit.addFileCommentLine("*    @project_name   ： nzoths");
    	compilationUnit.addFileCommentLine("*    @file_name      ： "+compilationUnit.getType().getFullyQualifiedName()+".java");
    	compilationUnit.addFileCommentLine("*    @package_name   ： "+compilationUnit.getType().getPackageName());
    	compilationUnit.addFileCommentLine("*    @author         ：wuxingx");
    	compilationUnit.addFileCommentLine("*------------------------------------------------------------------------------");
    	compilationUnit.addFileCommentLine("*    VERSION       DATE          BY       CHANGE/COMMENT");
    	compilationUnit.addFileCommentLine("*      1.0        2018.04      wuxingx         create");
    	compilationUnit.addFileCommentLine("*------------------------------------------------------------------------------");
    	compilationUnit.addFileCommentLine("******************************************************************************/");
        return;
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and
     * when it was generated.
     */
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }

    }

    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
        return;
    }

    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        
        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do
     * not wish to include the Javadoc tag - however, if you do not include the
     * Javadoc tag then the Java merge capability of the eclipse plugin will
     * break.
     * 
     * @param javaElement
     *            the java element
     */
    protected void addJavadocTag(JavaElement javaElement,
            boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     * 
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else {
            return new Date().toString();
        }
    }

    public void addClassComment(InnerClass innerClass,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        
//        StringBuilder sb = new StringBuilder();
//
//        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
//        innerClass.addJavaDocLine(" * This class was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        innerClass.addJavaDocLine(sb.toString());

//        addJavadocTag(innerClass, false);

//        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addEnumComment(InnerEnum innerEnum,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$
        innerEnum
                .addJavaDocLine(" * This enum was generated by MyBatis Generator."); //$NON-NLS-1$

        sb.append(" * This enum corresponds to the database table "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());

        addJavadocTag(innerEnum, false);

        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addFieldComment(Field field,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

//        StringBuilder sb = new StringBuilder();
//
//        field.addJavaDocLine("/**"); //$NON-NLS-1$
//        field
//                .addJavaDocLine(" * This field was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This field corresponds to the database column "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        sb.append('.');
//        sb.append(introspectedColumn.getActualColumnName());
//        field.addJavaDocLine(sb.toString());
//
//        addJavadocTag(field, false);
//
//        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

//        StringBuilder sb = new StringBuilder();
//
//        field.addJavaDocLine("/** "); //$NON-NLS-1$
//        field.addJavaDocLine(" * This field was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This field corresponds to the database table "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        field.addJavaDocLine(sb.toString());
//
//        addJavadocTag(field, false);
//
//        field.addJavaDocLine(" */"); //$NON-NLS-1$
        
    }

    public void addGeneralMethodComment(Method method,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }


        method.addJavaDocLine("/**"); //$NON-NLS-1$
        method.addJavaDocLine(" * @Description ： XXX"); //$NON-NLS-1$
        for (Parameter param : method.getParameters()) {
        	method.addJavaDocLine(" * @param       ： "+param.getType().getFullyQualifiedName()); //$NON-NLS-1$
		}
        
        method.addJavaDocLine(" * @return      ： "+method.getReturnType().getFullyQualifiedName()); //$NON-NLS-1$
        method.addJavaDocLine(" * @throws      ： "+method.getExceptions().toString()); //$NON-NLS-1$
        method.addJavaDocLine(" * @author      ： wuxingx"); //$NON-NLS-1$
        method.addJavaDocLine(" * @Date        ： "+new SimpleDateFormat("yyyy.MM.dd").format(new Date())); //$NON-NLS-1$


        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addGetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**"); //$NON-NLS-1$
        method.addJavaDocLine(" * @Description ： "+introspectedTable.getFullyQualifiedTable()+"."+introspectedColumn.getActualColumnName()+" Get Method"); //$NON-NLS-1$
        
        method.addJavaDocLine(" * @return      ： "+method.getReturnType().getFullyQualifiedName()); //$NON-NLS-1$
        method.addJavaDocLine(" * @author      ：wuxingx"); //$NON-NLS-1$
        method.addJavaDocLine(" * @Date        ： "+new SimpleDateFormat("yyyy.MM.dd").format(new Date())); //$NON-NLS-1$
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addSetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**"); //$NON-NLS-1$
        method.addJavaDocLine(" * @Description ： "+introspectedTable.getFullyQualifiedTable()+"."+introspectedColumn.getActualColumnName()+" Set Method"); //$NON-NLS-1$
        for (Parameter param : method.getParameters()) {
        	method.addJavaDocLine(" * @param       ： "+param.getType().getFullyQualifiedName()); //$NON-NLS-1$
		}
        
        method.addJavaDocLine(" * @return      ： Void"); //$NON-NLS-1$
        method.addJavaDocLine(" * @author      ： wuxingx"); //$NON-NLS-1$
        method.addJavaDocLine(" * @Date        ： "+new SimpleDateFormat("yyyy.MM.dd").format(new Date())); //$NON-NLS-1$
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addClassComment(InnerClass innerClass,
            IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

//        StringBuilder sb = new StringBuilder();
//
//        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
//        innerClass.addJavaDocLine(" * This class was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        innerClass.addJavaDocLine(sb.toString());
//
//        addJavadocTag(innerClass, markAsDoNotDelete);
//
//        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
}
