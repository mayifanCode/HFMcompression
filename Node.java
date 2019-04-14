package com.myf.HFMcompression1223;



public class Node<T> implements Comparable<Node<T>>{

	private T data;
	private int weight;
	private Node<T> left;
	private Node<T> right;
	
	public Node(T data,int weight)
	{
		this.data=data;
		this.weight=weight;
	}
	
	
	/**
	 * ��ȡ�ڵ�����
	 */
	public String toString()
	{
		return "data:"+data+"   "+"weight:"+weight;
	}

	/**
	 * �ڵ�Ȩֵ�ȽϷ���
	 * @param o
	 * @return
	 */
	public int compareTo(Node<T> o) {
       
		if(this.weight>o.weight)
			return 1;
		else if(this.weight<o.weight)
			return -1;
		return 0;
	}
	
	public void setData(T data)
	{
		this.data=data;
	}
	
	public void setWeight(int weight)
	{
		this.weight=weight;
	}
	
	public T getData()
	{
		return data;
	}
	
	public int getWeight()
	{
		return weight;
	}
	
	public void setLeft(Node<T> node)
	{
		this.left=node;
	}
	
	public void setRight(Node<T> node)
	{
		this.right=node;
	}
	
	public Node<T> getLeft()
	{
		return this.left;
	}
	
	public Node<T> getRight()
	{
		return this.right;
	}
	
	
}