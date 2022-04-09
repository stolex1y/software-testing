package ru.stolexiy

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
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

    @BeforeEach
    fun fillTree() {
        treeValues.stream().skip(1).forEach {
            tree.insert(BinarySearchTree(it))
        }
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
    fun `search node test`() {
        assertAll(
            { assertNotNull(tree.search(treeValues.last())) },
            { assertNull(tree.search(max(treeValues) + 1)) }
        )
    }

    @Test
    fun `random ordered tree traversal test`() {
        val treeValuesSorted = treeValues.sorted()
        val traversaledTree = tree.inorderTraversal()
        assertContentEquals(treeValuesSorted, traversaledTree)
    }

    @Test
    fun `sorted tree traversal test`() {
        val treeValues = listOf(1, 2, 3, 4)
        val tree = BinarySearchTree(treeValues[0])
        treeValues.stream().skip(1).forEach {
            tree.insert(BinarySearchTree(it))
        }
        assertContentEquals(treeValues, tree.inorderTraversal())
    }

    @Test
    fun `reverse sorted tree traversal test`() {
        val treeValues = listOf(4, 3, 2, 1)
        val tree = BinarySearchTree(treeValues[0])
        treeValues.stream().skip(1).forEach {
            tree.insert(BinarySearchTree(it))
        }
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