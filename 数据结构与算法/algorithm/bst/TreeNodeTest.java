package org.lium.algorithm.bst;

import org.lium.algorithm.CreateTestDataUtils;
import org.lium.algorithm.TestExecutor;

public class TreeNodeTest {

    public static void main(String[] args) {
        // 准备数据
        TreeNode treeNode = CreateTestDataUtils.getBST();
        // 前序遍历
        TestExecutor testExecutor = new TreeNodeTestExecutor(treeNode,
                "124536",
                "preorderTraversal");
        testExecutor.test();

        //后序遍历
        testExecutor = new TreeNodeTestExecutor(treeNode,
                "452631",
                "postorderTraversal");
        testExecutor.test();
    }
}
