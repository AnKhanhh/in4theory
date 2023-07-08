package info2;

import java.util.ArrayList;
import java.util.Scanner;

public class HuffmanEncoding {
	public static void main(String[] args) {
		System.out.print("Please enter your name: ");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		StringBuffer TuMa = new StringBuffer();
		StringBuffer Ten = new StringBuffer(name);
		ArrayList<Node> NodeList = new ArrayList<>();
		ArrayList<Character> CharInName = new ArrayList<>();
		ArrayList<Float> Freq = new ArrayList<>();
		//Tách chữ cái vào trong ArrayList và tạo ArrayList chứa số lần xuất hiện của từng chữ cái
		for (int i = 0; i < Ten.length(); i++) {
			int found = 0;
			for (int e = 0; e < CharInName.size(); e++) {
				if (Ten.charAt(i) == (char) CharInName.get(e)) {
					found = 1;
					Freq.set(e, Freq.get(e) + 1);
					break;
				}
			}
			if (found == 0) {
				CharInName.add(Ten.charAt(i));
				Freq.add(1.0f);
			}
		}
		//Tạo ArrayList probability
		for (int i = 0; i < Freq.size(); i++) {
			Freq.set(i, Freq.get(i) / (float) Ten.length());
		}
		//Tạo Node Lá
		for (int i = 0; i < CharInName.size(); i++) {
			Node node = new Node();
			node.prob = Freq.get(i);
			node.Value = CharInName.get(i);
			NodeList.add(node);
		}
		//Sort Node
		while (NodeList.size() != 1) {
			Node tmp;
			for (int i = 0; i < NodeList.size(); i++) {
				for (int e = NodeList.size() - 1; e >= 1; e--)
					if (NodeList.get(e).prob < NodeList.get(e - 1).prob) {
						tmp = NodeList.get(e - 1);
						NodeList.set(e - 1, NodeList.get(e));
						NodeList.set(e, tmp);
					}
			}
			//Tạo B-tree
			Node nodeCha = new Node(NodeList.get(0), NodeList.get(1));
			nodeCha.prob = NodeList.get(0).prob + NodeList.get(1).prob;
			NodeList.set(0, nodeCha);
			NodeList.remove(1);
//            for(int i = 0; i < NodeList.size(); i++) System.out.print(String.valueOf(NodeList.get(i).prob) + " ");
//            System.out.println();
		}

		NodeList.get(0).kiemtra(NodeList.get(0));

		//for(int i = 0; i < NodeList.size(); i++) System.out.println("Value: " + String.valueOf(NodeList.get(i).Value) + " Probability: " + String.valueOf(NodeList.get(i).prob));
	}
}
