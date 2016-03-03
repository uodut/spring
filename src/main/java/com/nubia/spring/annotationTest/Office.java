package com.nubia.spring.annotationTest;

public class Office {
	private String officeNo ="001";

    //省略 get/setter

    @Override
    public String toString() {
        return "officeNo:" + officeNo;
    }

	public String getOfficeNo() {
		return officeNo;
	}

	public void setOfficeNo(String officeNo) {
		this.officeNo = officeNo;
	}
}
