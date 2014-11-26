import java.awt.Image;
import java.awt.Toolkit;

public class Student{

	private String name;
	private Image image;
	private int spot;

	public Student(String s, String i){

		name = s;
		image = Toolkit.getDefaultToolkit().createImage(i).getScaledInstance(80,100,Image.SCALE_SMOOTH);
	}

	public String getName(){

		return name;
	}

	public Image getImage(){

		return image;
	}

	public void setSpot(int i){

		spot = i;
	}

	public int getSpot(){

		return spot;
	}
}


