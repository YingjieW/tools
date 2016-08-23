/**
 * This package contains class for testing annotation.
 */
@TestAnnotation(id = 0, name = "package-info", gid = Long.class)
package com.tools.ztest.annotation;

// 包类: 声明一个包使用的公共类，强调的是包访问权限
class PackageMethodClass {
    public void print() {
        System.out.println("PackageMethodClass.print: " + System.currentTimeMillis());
    }
}

// 包常量: 只运行包内访问，适用于分包开发
class PackageConstClass {
    public static final String PACKAGE_CONST = "HELLO WORLD.";
}
