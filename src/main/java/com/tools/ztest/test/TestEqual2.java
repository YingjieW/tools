package com.tools.ztest.test;

/**
 * Descripe: http://blog.csdn.net/javazejian/article/details/51348320
 *
 * @author yingjie.wang
 * @since 16/8/23 下午4:32
 */
public class TestEqual2 {

    /**
     * 父类
     */
    static class Person {
        protected String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public boolean equals(Object object){
            if( null == object) {
                return false;
            }
            if(this == object) {
                return true;
            }
            if(!this.getClass().equals(object.getClass())) {
                return false;
            }
            Person person = (Person) object;
            if(person.getName() == null || this.name == null) {
                return false;
            }
            return this.name.equalsIgnoreCase(person.getName());
        }
    }

    /**
     * 子类
     */
    static class Employee extends Person {
        private int id;

        public Employee(String name, int id) {
            super(name);
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public boolean equals(Object object){
            if( null == object) {
                return false;
            }
            if(this == object) {
                return true;
            }
            if(!this.getClass().equals(object.getClass())) {
                return false;
            }
            Employee employee = (Employee) object;
            if(employee.getName() == null || this.getName() == null) {
                return false;
            }
            return this.getName().equalsIgnoreCase(employee.getName()) && this.getId() == employee.getId();
        }
    }

    /**
     * 测试脚本
     */
    public static void main(String[] args) throws Exception {
        Employee e1 = new Employee("Tomcat", 23);
        Employee e2 = new Employee("Tomcat", 24);
        Employee e3 = new Employee("Tomcat", 24);
        Person p1 = new Person("Tomcat");
        System.out.println(p1.equals(e1));
        System.out.println(p1.equals(e2));
        System.out.println(e1.equals(e2));
        System.out.println(e2.equals(e3));
    }
}
