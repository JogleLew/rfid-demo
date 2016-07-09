package RFID;

import java.io.Serializable;

public class RFIDObject implements Serializable{
    private String RFIDSerialNumber;
    private String name;
    private String studentNumber;

    public RFIDObject(String RFIDSerialNumber, String name, String studentNumber){
        this.RFIDSerialNumber = RFIDSerialNumber;
        this.name = name;
        this.studentNumber = studentNumber;
    }

    public String getSerialNumber(){
        return RFIDSerialNumber;
    }

    public String getName(){
        return name;
    }

    public String getStudentNumber(){
        return studentNumber;
    }

    
    @Override
    public String toString(){
    	return name;
    }
    
    @Override
    public int hashCode(){
    	return (RFIDSerialNumber+name+studentNumber).hashCode();
    }
    
    @Override
    public boolean equals(Object obj){
    	if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RFIDObject o2 = (RFIDObject) obj;
        return (RFIDSerialNumber.equals(o2.RFIDSerialNumber)&&name.equals(o2.name)
        		&&studentNumber.equals(o2.studentNumber));
    }

}

