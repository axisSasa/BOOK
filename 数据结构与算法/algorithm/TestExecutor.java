package org.lium.algorithm;

/**
 * 因为测试流程是固定的
 * 所以选用模板方法模式
 */
public abstract class TestExecutor {
    public final void test() {
        this.printStartMsg();
        this.executeTest();
        this.assertResult();
        this.printEndMsg();
    }

    void printStartMsg() {
        System.out.println("Start Ttest");
    }
    void printEndMsg() {
        System.out.println("End Ttest");
    }
    public void prepareData() {
        System.out.println("Prepare data");
    }
    public void executeTest() {
        System.out.println("执行测试方法");
    }
    public void assertResult() {
        System.out.println("断言j结果");
    }

}
