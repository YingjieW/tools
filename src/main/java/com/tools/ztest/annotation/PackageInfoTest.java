package com.tools.ztest.annotation;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/23 下午3:02
 */
public class PackageInfoTest {

    public static void main(String[] args) throws Exception {
        // test package level annotation
        String packageName = "com.tools.ztest.annotation";
        Package pkg = Package.getPackage(packageName);
        if(pkg != null && pkg.isAnnotationPresent(TestAnnotation.class)) {
            TestAnnotation testAnnotation = pkg.getAnnotation(TestAnnotation.class);
            System.out.println("id = \"" + testAnnotation.id() + "\"; name = \""
                    + testAnnotation.name() + "\"; gid = \"" + testAnnotation.gid() + "\"");
        }

        // 包类测试
        PackageMethodClass packageMethodClass = new PackageMethodClass();
        packageMethodClass.print();
        // 包常量测试
        System.out.println("PackageConstClass.PACKAGE_CONST: " + PackageConstClass.PACKAGE_CONST);
    }
}
