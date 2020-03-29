package com.yidao.module_lib.anotation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;

import static com.yidao.module_lib.anotation.FragmentId.Comment_Fragment;
import static com.yidao.module_lib.anotation.FragmentId.Fans_Fragment;
import static com.yidao.module_lib.anotation.FragmentId.GrassStar_Fragment;
import static com.yidao.module_lib.anotation.FragmentId.Message_Fragment;
import static com.yidao.module_lib.anotation.FragmentId.My_Fragment;
import static com.yidao.module_lib.anotation.FragmentId.Praise_Fragment;
import static com.yidao.module_lib.anotation.FragmentId.VideoPlay_Fragment;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;


@IntDef( {VideoPlay_Fragment, GrassStar_Fragment,Message_Fragment,My_Fragment,Fans_Fragment,Praise_Fragment,Comment_Fragment})
@Retention(CLASS)
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE})
public @interface FragmentId {

    int VideoPlay_Fragment = 0;
    int GrassStar_Fragment = 1;
    int Message_Fragment = 2;
    int My_Fragment = 3;
    int Fans_Fragment = 4;
    int Praise_Fragment = 5;
    int Comment_Fragment = 6;
}
