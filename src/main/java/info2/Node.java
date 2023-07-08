package info2;

import com.sun.jdi.Value;

public class Node {
	Node NodeCon0;
	Node NodeCon1;
	char Value;
	float prob;
	StringBuffer TuMa = new StringBuffer();

	public Node(Node NodeCon0, Node NodeCon1, char Value, float prob) {
		this.NodeCon0 = NodeCon0;
		this.NodeCon1 = NodeCon1;
		this.Value = Value;
		this.prob = prob;
	}

	public Node() {}

	public Node(Node NodeCon0, Node NodeCon1) {
		this.NodeCon0 = NodeCon0;
		this.NodeCon1 = NodeCon1;
	}

	public Node getNodeCon0() {
		return NodeCon0;
	}

	public void setNodeCon0(Node nodeCon0) {
		NodeCon0 = nodeCon0;
	}

	public Node getNodeCon1() {
		return NodeCon1;
	}

	public void setNodeCon1(Node nodeCon1) {
		NodeCon1 = nodeCon1;
	}

	public char getValue() {
		return Value;
	}

	public void setValue(char value) {
		Value = value;
	}

	public float getProb() {
		return prob;
	}

	public void setProb(float prob) {
		this.prob = prob;
	}

	public void kiemtra(Node node) {
		if (node.NodeCon0 != null) {
			TuMa.append('0');
			kiemtra(node.NodeCon0);
			TuMa.deleteCharAt(TuMa.length() - 1);
		} else {
			System.out.println(node.Value + ": " + TuMa);
		}
		if (node.NodeCon1 != null) {
			TuMa.append('1');
			kiemtra(node.NodeCon1);
			TuMa.deleteCharAt(TuMa.length() - 1);
		}
	}
}

