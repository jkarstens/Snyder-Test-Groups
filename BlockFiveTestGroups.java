import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class BlockFiveTestGroups extends Applet implements ActionListener, Runnable{

	private Graphics dbGraphics;
	private Image dbImage;

	private Thread thread;
	private int time; //centiseconds
	private boolean go;

	private Student[] students;
	private int sCounter, cCounter;
	private boolean increment, choosePapers;
	private int[] r;
	private Image hat;

	private Button chooseGroupsButton, choosePaperButton;


	private Font stdFont = new Font("Dialog",Font.BOLD,12);
	private Font groupFont = new Font("Forte",Font.BOLD,30);

	public void init(){

		setSize(800,630);
		setBackground(new Color(144,238,144));

		time = 0;
		go = false;

		students = getStudents();
		randomizeGroups(students);
		sCounter = 0;
		increment = false;
		choosePapers = false;
		r = new int[5];
		for(int i : r) i = -1;
		hat = Toolkit.getDefaultToolkit().createImage("hat.png");

		chooseGroupsButton = new Button("SELECT NEW RANDOM GROUPS");
		chooseGroupsButton.addActionListener(this);
		chooseGroupsButton.setFocusable(false);
		add(chooseGroupsButton);
		choosePaperButton = new Button("SELECT RANDOM PAPERS TO GRADE");
		choosePaperButton.addActionListener(this);
		choosePaperButton.setFocusable(false);
		choosePaperButton.setEnabled(false);
		add(choosePaperButton);
	}

	public void paint(Graphics g){

		g.setColor(Color.BLACK);
		g.setFont(groupFont);
		for(int i=0; i<5; i++) g.drawString("GROUP " + (i+1),350,(113*i) + 115);

		if(go){

			if(time % 500 < 350){

				g.drawImage(students[sCounter].getImage(), 120, 400 - (time%500), this);
				g.setFont(stdFont);
				g.drawString(students[sCounter].getName(), 142, 400 - (time%500));
				increment = true;
			}

			else if(increment){

				sCounter++;
				increment = false;
				if(sCounter == students.length){

					go = false;
					chooseGroupsButton.setEnabled(true);
					choosePaperButton.setEnabled(true);
				}
			}
		}

		if(choosePapers){

			if(time < 200){

				r[0] = (int)(3.0 * Math.random());
				if(time == 0) cCounter = 0;
			}

			else if(time < 400){

				r[1] = (int)(3.0 * Math.random());
				if(time == 201) cCounter++;
			}

			else if(time < 600){

				r[2] = (int)(3.0 * Math.random());
				if(time == 402) cCounter++;
			}

			else if(time < 800){

				r[3] = (int)(3.0 * Math.random());
				if(time == 600) cCounter++;
			}

			else if(time < 1000){

				r[4] = (int)(3.0 * Math.random());
				if(time == 801) cCounter++;
			}

			for(int i=0; i<cCounter+1; i++) g.drawString(students[r[i] + (i*3)].getName(), 370, 150 + (113 * i));
		}

		int yCounter = 50;

		for(int i=0; i<sCounter; i++){

			g.drawImage(students[i].getImage(), 520+(85*(i%3)), yCounter, this);
			g.setFont(stdFont);
			g.drawString(students[i].getName(), 542+(85*(i%3)), yCounter+3);
			if( (i+1) % 3 == 0 ) yCounter += 110;
		}

		g.setFont(stdFont);
		g.drawString("Names appear above images",632,23);

		g.drawImage(hat,50,400,this);
	}

	public void update(Graphics g){

		if(dbImage == null){

			dbImage = createImage(getSize().width, getSize().height);
			dbGraphics = dbImage.getGraphics();
		}

		dbGraphics.setColor(getBackground());
		dbGraphics.fillRect(0, 0, getSize().width, getSize().height);
		dbGraphics.setColor(getForeground());
		paint(dbGraphics);

		g.drawImage(dbImage, 0, 0, this);
	}

	public void randomizeGroups(Student[] s){

		for(int i=s.length-1; i>0; i--){ //the right way to randomize an array!

			int r = (int)((i+1) * Math.random());

			Student a = s[i];
			Student b = s[r];

			s[i] = b;
			s[r] = a;

			s[i].setSpot(i);
		}
	}

	public Student[] getStudents(){

		Student[] students = new Student[15];

		students[0] = new Student("Carrie","kidz6.png");
		students[1] = new Student("Scott","kidz3.png");
		students[2] = new Student("Hannah","kids2.png");
		students[3] = new Student("Drisana","kids4.png");
		students[4] = new Student("Sarah","kids6.png");
		students[5] = new Student("Jade","kidz9.png");
		students[6] = new Student("Marisa","kidz4.png");
		students[7] = new Student("Stu","kids1.png");
		students[8] = new Student("Andrew","kidz8.png");
		students[9] = new Student("Liam","kidz2.png");
		students[10] = new Student("Elinore","kids5.png");
		students[11] = new Student("Honora","kidz1.png");
		students[12] = new Student("Jakob","kidz5.png");
		students[13] = new Student("Vijay","kidz7.png");
		students[14] = new Student("Joon","kids3.png");

		return students;
	}

	public void actionPerformed(ActionEvent e){

		Object source = e.getSource();

		if(source == chooseGroupsButton){

			sCounter = 0;
			time = 0;
			randomizeGroups(students);
			go = true;
			choosePapers = false;
			chooseGroupsButton.setEnabled(false);
			choosePaperButton.setEnabled(false);
		}

		if(source == choosePaperButton){

			choosePapers = true;
			time = 0;
			cCounter = 0;
		}
	}

	public void start(){

		if(thread == null){

			thread = new Thread(this);
			thread.start();
		}
	}

	public void run(){

		while(thread != null){

			repaint();

			try{

				Thread.sleep(20);
				if(go || choosePapers) time += 3;
			}
			catch(InterruptedException e){
			}
		}
	}

	public void stop(){

		thread = null;
	}

	public static void main(String[] args){ // run the Applet in a JFrame
		JFrame frame = new JFrame("Block Five");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().setBackground(Color.WHITE);
		Applet thisApplet = new BlockFiveTestGroups();
		frame.getContentPane().add(thisApplet, BorderLayout.CENTER);
		thisApplet.init();
		frame.setSize(thisApplet.getSize());
		thisApplet.start();
		frame.setVisible(true);
	}
}
