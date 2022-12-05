
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.color.*;
import java.awt.geom.*;
import java.io.*;

import javax.swing.*;
import javax.imageio.*;
import javax.swing.JOptionPane;

public class Julia extends JFrame implements ActionListener {
	String title = null;
	String fractalName = "";
	Container cp = null;

	double cr = 0.0;
	double ci = 0.0;
	String cmd = "";

	JMenuItem miOpen, miSave, miExit;
	JMenuItem miJulia;

	JuliaPanel imageSrc;
	JFileChooser fc = new JFileChooser();

	public static void main(String[] args) {
		JFrame frame = new Julia();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public Julia() {

		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);

		JMenu menu = new JMenu("File");

		miOpen = new JMenuItem("Open Fractal");
		miOpen.addActionListener(this);
		menu.add(miOpen);

		miSave = new JMenuItem("Save Fractal");
		miSave.addActionListener(this);
		menu.add(miSave);

		menu.addSeparator();

		miExit = new JMenuItem("Exit");
		miExit.addActionListener(this);
		menu.add(miExit);

		mb.add(menu);

		JMenu mSelect = new JMenu("Constant");

		JMenuItem mi = new JMenuItem("Random");
		mi.addActionListener(this);
		mSelect.add(mi);

		JMenuItem mManual = new JMenuItem("Manual");
		mManual.addActionListener(this);
		mSelect.add(mManual);

		mb.add(mSelect);

		JMenu mFractal = new JMenu("Fractal");
		mb.add(mFractal);

		JMenuItem mJulia = new JMenuItem("Julia");
		mJulia.addActionListener(this);
		mFractal.add(mJulia);
		
		JMenuItem mSin = new JMenuItem("C Sin(z)");
		mSin.addActionListener(this);
		mFractal.add(mSin);
		
		JMenuItem mCos = new JMenuItem("C Cos(z)");
		mCos.addActionListener(this);
		mFractal.add(mCos);
		
		JMenuItem mNova = new JMenuItem("Nova");
		mNova.addActionListener(this);
		mFractal.add(mNova);

		cp = this.getContentPane();
		cp.setLayout(new FlowLayout());

		imageSrc = new JuliaPanel();
		cp.add(imageSrc);

		WindowListener l = new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}

			public void windowActivated(WindowEvent ev) {
				repaint();
			}

			public void windowStateChanged(WindowEvent ev) {
				repaint();
			}

		};

		ComponentListener k = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				repaint();
			}
		};

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				return;

			}
		};

		this.addWindowListener(l);
		this.addComponentListener(k);
		this.addMouseListener(mouseListener);
	}

	public void actionPerformed(ActionEvent ev) {

		cmd = ev.getActionCommand();

		if ("Open Fractal".equals(cmd)) {
			int retval = fc.showOpenDialog(this);
			if (retval == JFileChooser.APPROVE_OPTION) {
				imageSrc.setImage(null);

				try {
					BufferedImage bi = ImageIO.read(fc.getSelectedFile());

					imageSrc.SetTitle("Unknown", "Unknown");
					imageSrc.setImage(bi);

					pack();

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		else if ("Save Fractal".equals(cmd)) {
			if (imageSrc == null) {
				imageSrc.SetTitle(Double.toString(cr), Double.toString(ci));
				imageSrc.createImage(cr, ci, cmd);
				imageSrc.setImage(imageSrc.getImage());
			}
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int retval = fc.showSaveDialog(this);
			if (retval == JFileChooser.APPROVE_OPTION) {
				try {
					File s = fc.getSelectedFile();
					String path = s.getPath();
					double r = cr;
					double i = ci;
					String fileName = path + "\\" + fractalName + " Fractal- CR=" + Double.toString(r) + "- CI="
							+ Double.toString(i) + ".bmp";
					File output = new File(fileName);

					ImageIO.write(imageSrc.getImage(), "bmp", output);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		} else if ("Exit".equals(cmd)) {
			System.exit(0);
		} else if ("Random".equals(cmd)) {
			cr = Math.random();
			if (cr < 0.5)
				cr = -cr;
			ci = Math.random();
			if (ci < 0.5)
				ci = -ci;

		}
		if ("Manual".equals(cmd)) {

			float cr1 = Float.parseFloat(JOptionPane.showInputDialog(null, "Input Real Component of C"));
			float ci1 = Float.parseFloat(JOptionPane.showInputDialog(null, "Input Imaginary Component of C"));

			cr = cr1;
			ci = ci1;

		}
		if ("Julia".equals(cmd) || "C Cos(z)".equals(cmd) || "C Sin(z)".equals(cmd) || "Nova".equals(cmd)) {
			fractalName = cmd;
			imageSrc.SetTitle(Double.toString(cr), Double.toString(ci));
			imageSrc.createImage(cr, ci, cmd);
			imageSrc.setImage(imageSrc.getImage());
			pack();
		}
	}

}

class JuliaPanel extends JPanel {
	BufferedImage image = null;

	int w = 1200;
	int h = 1000;

	int[] rgb = new int[3];

	float real = 0f;
	float imaginary = 0f;

	String Title01 = "";
	String Title02 = " Dimension 1200X1000 Pixels";
	String Title03 = " ";
	String Title04 = " ";

	public JuliaPanel() {
		image = null;
		setPreferredSize(new Dimension(1200, 1000));

	}

	public JuliaPanel(BufferedImage bi) {
		image = bi;
		setPreferredSize(new Dimension(1200, 1000));

	}

	public void SetTitle(String t3, String t4) {

		Title03 = " CR =  " + t3;
		Title04 = " CI  =  " + t4;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font f = new Font("SansSerif", Font.BOLD, 12);
		g2.setFont(f);

		if (image != null) {
			g2.drawString(Title01, 20, 60);
			g2.drawString(Title02, 20, 90);
			g2.drawString(Title03, 20, 120);
			g2.drawString(Title04, 20, 150);

			g2.drawImage(image, 200, 10, this);
		}

		else {
			g2.drawString(" ", 20, 20);
			g2.drawString(" No Fractal ", 20, 20);
		}

	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage bi) {
		image = bi;
		invalidate();
		repaint();
	}

	public void createImage(double cr, double ci, String cmd) {

		Complex c = new Complex(cr, ci);
		w = 1000;
		h = 1000;
		Title01 = "F(Z) = " + cmd;
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();

		float xmin = -2.0f;
		float ymin = -2.0f;
		float xscale = 4.0f / w;
		float yscale = 4.0f / h;
		Complex z = new Complex();

		for (int i = 0; i < h; i++) {

			for (int j = 0; j < w; j++) {
				z.a = xmin + j * xscale;
				z.b = ymin + i * yscale;

				int count = (int) iterCount(z, c, cmd);

				rgb[0] = ((count) & 0x7) << 5; // blue component
				rgb[1] = ((count >> 3) & 0x7) << 5; // green component
				rgb[2] = ((count >> 6) & 0x7) << 5; // red component*/

				raster.setPixel((h - 1 - j), i, rgb);

			}

		}

	}

	private double iterCount(Complex z, Complex c, String cmd) {
		double t = 0;
		if ("Julia".equals(cmd))
			t = julia(z, c);
		else if ("C Cos(z)".equals(cmd))
			t = cosz(z, c);
		else if ("C Sin(z)".equals(cmd))
			t = sinz(z, c);
		else if ("Nova".equals(cmd))
			t = nova(z, c);
		return t;
	}

	private int julia(Complex z, Complex c) {
		int max = 512;

		double lengthsq = 0;
		int iterations = 0;
		while ((lengthsq < 2.0) && (iterations < max)) {
			z = (z.times(z)).plus(c);
			lengthsq = z.magnitude();
			iterations++;
		}
		return iterations;
	}

	private int cosz(Complex z, Complex c) {
		int max = 512;

		double lengthsq = 0;
		int iterations = 0;
		while ((lengthsq < 2.0) && (iterations < max)) {
			z = c.times(z.cos());
			lengthsq = z.magnitude();
			iterations++;
		}
		return iterations;
	}

	private int sinz(Complex z, Complex c) {
		int max = 512;

		double lengthsq = 0;
		int iterations = 0;
		while ((lengthsq < 2.0) && (iterations < max)) {
			z = c.times(z.sin());
			lengthsq = z.magnitude();
			iterations++;
		}
		return iterations;
	}

	private int nova(Complex z, Complex c) {
		int max = 512;

		double lengthsq = 0;
		int iterations = 0;
		Complex one = new Complex(1, 0);
		while ((lengthsq < 2.0) && (iterations < max)) {
			Complex zm1 = z.minus(one);
			Complex w = zm1.times(zm1).times(zm1);
			Complex v = z.times(z).scale(3);
			z = z.minus(w.divide(v)).plus(c);
			lengthsq = z.magnitude();
			iterations++;
		}
		return iterations;
	}

}