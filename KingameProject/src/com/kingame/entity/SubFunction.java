package com.kingame.entity;

public class SubFunction {
	
	private int subFunctionImageId;
	private String subFunctionName;
	
	public SubFunction(String subFunctionName, int subFunctionImageId) {
		this.subFunctionName = subFunctionName;
		this.subFunctionImageId = subFunctionImageId; 
	}
	
	public int getSubFunctionImageId() {
		return subFunctionImageId;
	}
	
	public void setSubFunctionImageId(int subFunctionImageId) {
		this.subFunctionImageId = subFunctionImageId;
	}
	
	public String getSubFunctionName() {
		return subFunctionName;
	}
	
	public void setSubFunctionName(String subFunctionName) {
		this.subFunctionName = subFunctionName;
	}
	
}
