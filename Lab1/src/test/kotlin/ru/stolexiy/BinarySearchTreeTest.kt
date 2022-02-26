package ru.stolexiy

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.Collections.max
import java.util.Collections.min
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

private val treeValues: List<Int> = listOf(8, 3, 1, 6, 4, 7, 10, 14, 13)

class BinarySearchTreeTest {
    private var tree = BinarySearchTree(treeValues.first())

    @BeforeEach
    fun fillTree() {
        treeValues.stream().skip(1).forEach {
            tree.insert(BinarySearchTree(it))
        }
    }

    @Test
    fun `add node to tree test`() {
        val addValue = 15
        tree.insert(BinarySearchTree(addValue))
        assertNotNull(tree.search(addValue))
    }

    @Test
    fun `search node test`() {
        assertAll(
            { assertNotNull(tree.search(treeValues.last())) },
            { assertNull(tree.search(max(treeValues) + 1)) }
        )
    }

    @Test
    fun `tree traversal test`() {
        val treeValuesSorted = treeValues.sorted()
        val traversaledTree = tree.inorderTraversal()
        assertContentEquals(treeValuesSorted, traversaledTree)
    }

    @Test
    fun `maximum node test`() {
        assertEquals(max(treeValues), tree.maximum().value)
    }

    @Test
    fun `minimum node test`() {
        assertEquals(min(treeValues), tree.minimum().value)
    }

    @Test
    fun `delete node test`() {
        val value = 6
        tree.delete(value)
        assertNull(tree.search(value))
    }

    @Test
    fun `delete all nodes test`() {
        var tempTree: BinarySearchTree? = tree
        treeValues.forEach {
            tempTree = tempTree?.delete(it)
        }
        assertNull(tempTree)
    }

    @Test
    fun `delete node with two children test`() {
        val deleteValue = 3
        val deleteNode = tree.search(deleteValue)!!
        assertAll(
            "node hasn't two children",
            { assertNotNull(deleteNode.left) },
            { assertNotNull(deleteNode.right) }
        )
        val parent: BinarySearchTree = tree.search(deleteValue)!!.parent!!
        assertEquals(deleteValue, parent.left!!.value)
        tree.delete(deleteValue)
        assertEquals(4, parent.left!!.value)
    }

    @Test
    fun `delete node without children test`() {
        val deleteValue = 13
        val deleteNode = tree.search(deleteValue)!!
        assertAll(
            "node has child",
            { assertNull(deleteNode.left) },
            { assertNull(deleteNode.right) }
        )
        val parent: BinarySearchTree = tree.search(deleteValue)!!.parent!!
        assertEquals(deleteValue, parent.left!!.value)
        tree.delete(deleteValue)
        assertNull(parent.left)
    }



}