package ingokuba.treespanner.object;

public class Node {
	private String name;
	private Integer id;
	private Node nextHop;
	private int msgCnt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Node getNextHop() {
		return nextHop;
	}

	public void setNextHop(Node nextHop) {
		this.nextHop = nextHop;
	}

	public int getMsgCnt() {
		return msgCnt;
	}

	public void setMsgCnt(int msgCnt) {
		this.msgCnt = msgCnt;
	}
}
