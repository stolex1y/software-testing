package ru.stolexiy

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.Collections.max
import java.util.Collections.min
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

private val treeValues: List<Int> = listOf(8, 3, 1, 6, 4, 7, 10, 14, 13)

internal class BinarySearchTreeTest {
    private var tree = BinarySearchTree(treeValues.first())

    private fun fillTree(treeValues: List<Int>): BinarySearchTree {
        val tree = BinarySearchTree(treeValues[0])
        treeValues.stream().skip(1).forEach {
            tree.insert(BinarySearchTree(it))
        }
        return tree
    }

    @BeforeEach
    fun fillTree() {
        tree = fillTree(treeValues)
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 2, 15])
    fun `add node to tree test`(addValue: Int) {
        assertNull(tree.search(addValue))
        tree.insert(BinarySearchTree(addValue))
        assertNotNull(tree.search(addValue))
    }

    @Test
    fun `add node sequence to tree test`() {
        val nodeList = listOf(1, 2, 3, -1)
        val hasItemFromNodeList = nodeList.map { hasItem(it) }

        assertThat(tree.inorderTraversal(), not(allOf(hasItemFromNodeList)))

        nodeList.map {
            BinarySearchTree(it)
        } .forEach(tree::insert)

        assertThat(tree.inorderTraversal(), allOf(hasItemFromNodeList))
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 2, 5, 11])
    fun `add multiple identical nodes test`(addValue: Int) {
        val identicalNodes = generateSequence { addValue }.take(3).toList()
        val hasIdenticalNodes = identicalNodes.map { hasItem(it) }
        assertThat(tree.inorderTraversal(), not(allOf(hasIdenticalNodes)))

        identicalNodes.map {
            BinarySearchTree(it)
        } .forEach(tree::insert)

        assertThat(tree.inorderTraversal(), allOf(hasIdenticalNodes))
    }

    @Test
    fun `search for existing nodes test`() {
        treeValues.shuffled().forEach {
            assertNotNull(tree.search(it))
        }
    }

    @Test
    fun `search for non-existent nodes test`() {
        assertAll(
            { assertNull(tree.search(0)) },
            { assertNull(tree.search(-1)) },
            { assertNull(tree.search(15)) },
        )
    }

    @Test
    fun `random ordered tree traversal test`() {
        val treeValuesSorted = treeValues.sorted()
        val traversal = tree.inorderTraversal()
        assertContentEquals(treeValuesSorted, traversal)
    }

    @Test
    fun `sorted tree traversal test`() {
        val treeValues = listOf(1, 2, 3, 4)
        val tree = fillTree(treeValues)
        assertContentEquals(treeValues, tree.inorderTraversal())
    }

    @Test
    fun `reverse sorted tree traversal test`() {
        val treeValues = listOf(4, 3, 2, 1)
        val tree = fillTree(treeValues)
        assertContentEquals(treeValues.sorted(), tree.inorderTraversal())
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
        treeValues.stream().skip(1).unordered().forEach {
            assertNotNull(tree.search(it))
            tree = tree.delete(it)!!
            assertNull(tree.search(it))
        }
    }

    @Test
    fun `delete all nodes test`() {
        var tempTree: BinarySearchTree? = tree
        treeValues.forEach {
            tempTree = tempTree!!.delete(it)
        }
        assertNull(tempTree)
    }

    @Test
    fun `delete node with two children test`() {
        val deletingValue = 3
        val deletingNode = tree.search(deletingValue)!!
        assertAll(
            "node hasn't two children",
            { assertNotNull(deletingNode.left) },
            { assertNotNull(deletingNode.right) }
        )
        val parent: BinarySearchTree = deletingNode.parent!!
        tree.delete(deletingValue)
        assertNull(tree.search(deletingValue))
        assertEquals(4, parent.left!!.value)
    }

    @Test
    fun `delete node with one child test`() {
        val deletingValue = 14
        val deletingNode = tree.search(deletingValue)!!
        assertAll(
            "node hasn't only one child",
            { assertNotNull(deletingNode.left) },
            { assertNull(deletingNode.right) }
        )
        val parent: BinarySearchTree = deletingNode.parent!!
        tree.delete(deletingValue)
        assertNull(tree.search(deletingValue))
        assertEquals(13, parent.right!!.value)
    }

    @Test
    fun `delete non-existent node tree test`() {
        val oldTraversal = tree.inorderTraversal()
        assertDoesNotThrow { tree.delete(-1) }
        val newTraversal = tree.inorderTraversal()
        assertContentEquals(oldTraversal, newTraversal)
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