package ru.stolexiy

class BinarySearchTree(
    _value: Int,
    _left: BinarySearchTree? = null,
    _right: BinarySearchTree? = null,
    _parent: BinarySearchTree? = null
) {
    var value: Int = _value
        private set
    var left: BinarySearchTree? = _left
        private set
    var right: BinarySearchTree? = _right
        private set
    var parent: BinarySearchTree? = _parent
        private set

    fun inorderTraversal() = inorderTraversal(this)
    fun search(value: Int) = search(this, value)
    fun minimum() = minimum(this)
    fun maximum() = maximum(this)
    fun insert(adding: BinarySearchTree) = insert(this, adding)

    fun next() = next(this)
    fun prev() = prev(this)

    fun delete(value: Int) = delete(this, value)

    private fun delete(_root: BinarySearchTree?, value: Int): BinarySearchTree? {
        var root = _root
        if (root == null)
            return null
        if (value < root.value)
            root.left = delete(root.left, value)
        else if (value > root.value)
            root.right = delete(root.right, value)
        else if (root.left != null && root.right != null) {
            root.value = minimum(root.right!!).value
            root.right = delete(root.right, root.value)
        } else {
            root =
                if (root.left != null)
                    root.left
                else if (root.right != null)
                    root.right
                else
                    null
        }
        return root
    }

    private fun prev(_tree: BinarySearchTree): BinarySearchTree? {
        var tree = _tree
        if (tree.left != null)
            return maximum(tree.left!!)
        var parent = tree.parent
        while (parent != null && tree == parent.left) {
            tree = parent
            parent = parent.parent
        }
        return parent
    }

    private fun next(_tree: BinarySearchTree): BinarySearchTree? {
        var tree = _tree
        if (tree.right != null)
            return minimum(tree.right!!)
        var parent = tree.parent
        while (parent != null && tree == parent.right) {
            tree = parent
            parent = parent.parent
        }
        return parent
    }

    private fun insert(_tree: BinarySearchTree, adding: BinarySearchTree) {
        var tree: BinarySearchTree = _tree
        while (true) {
            if (adding.value >= tree.value) {
                if (tree.right != null)
                    tree = tree.right!!
                else {
                    adding.parent = tree
                    tree.right = adding
                    break
                }
            } else {
                if (tree.left != null)
                    tree = tree.left!!
                else {
                    adding.parent = tree
                    tree.left = adding
                    break
                }
            }
        }
    }

    private fun maximum(tree: BinarySearchTree): BinarySearchTree {
        return if (tree.right == null)
            tree
        else
            maximum(tree.right!!)
    }

    private fun minimum(tree: BinarySearchTree): BinarySearchTree {
        return if (tree.left == null)
            tree
        else
            minimum(tree.left!!)
    }

    private fun inorderTraversal(tree: BinarySearchTree?): List<Int> {
        val result = mutableListOf<Int>()
        if (tree != null) {
            result.addAll(inorderTraversal(tree.left))
            result.add(tree.value)
            result.addAll(inorderTraversal(tree.right))
        }
        return result
    }

    private fun search(tree: BinarySearchTree?, value: Int): BinarySearchTree? {
        if (tree == null || value == tree.value)
            return tree
        return if (value < tree.value)
            search(tree.left, value)
        else
            search(tree.right, value)
    }
}