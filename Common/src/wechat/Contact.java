package wechat;

public abstract class Contact {
	public abstract int getId();
	public abstract void setId(int id);
	public abstract String getName();
	public abstract void setName(String name);
	public abstract void pushMessage();
	public abstract void pullMessage();
}
