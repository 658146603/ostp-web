package top.ostp.web.model.annotations

import java.lang.annotation.Inherited

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Inherited
internal annotation class AuthTeacher(val value: String = "")

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Inherited
internal annotation class AuthAdmin(val value: String = "")

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Inherited
internal annotation class AuthStudent(val value: String = "")

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Inherited
internal annotation class NoAuthority(val value: String = "")