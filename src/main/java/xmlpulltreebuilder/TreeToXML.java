/**
 * \file
 * <p>
 * Sep 19, 2004
 * <p>
 * Copyright Ian Kaplan 2004, Bear Products International
 * <p>
 * You may use this code for any purpose, without restriction,
 * including in proprietary code for which you charge a fee.
 * In using this code you acknowledge that you understand its
 * function completely and accept all risk in its use.
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package xmlpulltreebuilder;

/**
 * TreeToXML
 * Sep 19, 2004
 *
 * <p>
 * Traverse the in-memory tree and build an XML representation for
 * the tree.  Leaving asside white space, this XML should be the
 * same as the original XML that was read to build the tree.
 * Or at least the same relative to the supported XML elements.
 * For example, the TreeBuilder code does not support the
 * "documentation" elements (DOCDECL).
 * </p>
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
public class TreeToXML {
    private final TreeNode mRoot;
    private StringBuffer mBuf = null;

    private void serializeAttribute(Attribute attr) {
        String attrName = attr.getName();
        mBuf.append(attrName);
        mBuf.append("=\"");
        String attrVal = attr.getValue();
        mBuf.append(attrVal);
        mBuf.append('"');
    } // serializeAttribute


    private void serializeTag(TreeNode node) {
        String tagName = node.toString();
        mBuf.append("<");
        mBuf.append(tagName);
        AttributeList attrList = ((TagNode) node).getAttrList();
        if (attrList != null) {
            for (Attribute attr : attrList) {
                mBuf.append(' ');
                serializeAttribute(attr);
            } // while
        }
        if (node.isLeaf()) {
            mBuf.append("/>");
        } else {
            mBuf.append('>');
        }
    } // serializeTag


    private void serializeNode(TreeNode node) {
        if (node != null) {
            TreeNodeType ty = node.getType();
            if (ty == TreeNodeType.TAG) {
                serializeTag(node);
            } else {
                String nodeStr = node.toString();
                if (ty == TreeNodeType.COMMENT) {
                    nodeStr = "\n<--" + nodeStr + "-->";
                }
                mBuf.append(nodeStr);
            }
        }
    } // serializeNode


    private void endTag(TreeNode root) {
        if (root != null && root.getType() == TreeNodeType.TAG) {
            String tagName = root.toString();
            mBuf.append("</");
            mBuf.append(tagName);
            mBuf.append('>');
        }
    } // endTag


    private void leavesToString(TreeNode root) {
        if (root != null) {
            for (TreeNode n = root.getChild(); n != null; n = n.getSibling()) {
                if (!n.isLeaf()) {
                    rootToString(n);
                } else {
                    serializeNode(n);
                }
            }
        }
    } // leavesToString


    private void rootToString(TreeNode root) {

        if (root != null) {
            serializeNode(root);
            if (!root.isLeaf()) {
                leavesToString(root);
                endTag(root);
            } // if root is not a leaf
        }
    }  // rootToString


    public TreeToXML(TreeNode root) {
        mRoot = root;
    }


    public String toString() {
        mBuf = new StringBuffer();
        for (TreeNode t = mRoot; t != null; t = t.getSibling()) {
            rootToString(t);
        }
        return mBuf.toString();
    } // toString

}
