package org.lium.algorithm.bst;

import org.lium.algorithm.TestExecutor;
import org.lium.algorithm.bst.TreeNode;

import java.lang.reflect.Method;

public class TreeNodeTestExecutor extends TestExecutor {
    private TreeNode root;
    private String actualResult;
    private String expectedResult;
    private String function;

    public TreeNodeTestExecutor(TreeNode root, String expectedResult, String function) {
        this.root = root;
        this.expectedResult = expectedResult;
        this.function = function;
    }

    @Override
    public void executeTest() {
        super.executeTest();
        // 反射拿到函数并执行
        try {
            Class clazz = Class.forName("org.lium.algorithm.bst.TreeNode");
            Method method = clazz.getDeclaredMethod(function, new Class[]{TreeNode.class});
            Object obj = clazz.newInstance();
            actualResult = (String) method.invoke(obj, root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assertResult() {
        super.assertResult();
        System.out.println(actualResult.equals(expectedResult));
    }
}
