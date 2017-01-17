package imageViewerUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.JLabel;

import java.awt.Font;

public class ViewContentJPanel extends JPanel {
	private JPanel list_panel;
	File active_image_file;
	Timer timer;
	boolean playing;

	/**
	 * Create the panel.
	 */
	public ViewContentJPanel(JFrame mainframe,File[] imagefiles) {
		setBounds(new Rectangle(50, 50, 1000, 650));
		setLayout(null);
		
		/*
		 * All this have to be done one when the images files is not null
		 */
		if ( !(imagefiles == null || imagefiles.length == 0) ){	
			
			// set the active image to the first image of the list
			active_image_file = imagefiles[0];
			ImageIcon icon_example = new ImageIcon(active_image_file.toString());	
			playing = false;
			
			JButton btnPrevious = new JButton("< Previous");
			JButton btnPlay = new JButton("Play");
			JButton btnNext = new JButton("Next >");
			
			System.out.println("\nReceived filed");
	        System.out.println("----------------");
	        for (File f : imagefiles ){
	        	System.out.println( Arrays.asList(imagefiles).indexOf(f) + " - " + f.toString());
	        }
									
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(100, 50, 200, 550);
			add(scrollPane);
			
			list_panel = new JPanel();
			list_panel.setLayout(new BoxLayout(list_panel, BoxLayout.Y_AXIS));
			list_panel.setBorder(BorderFactory.createLineBorder(Color.black));
			scrollPane.setViewportView(list_panel);

			JButton btncurrentImage = new JButton("");	
			btncurrentImage.setBounds(330, 50, 600, 500);
			btncurrentImage.setBorder(BorderFactory.createCompoundBorder(
		               BorderFactory.createLineBorder(Color.WHITE, 5),
		               BorderFactory.createLineBorder(Color.WHITE, 5)));
			
			// get the scaled image icon to use for this button
			Dimension btnSize = new Dimension(btncurrentImage.getWidth(), btncurrentImage.getHeight());
			icon_example = generateResizedImageIcon ( icon_example,  btnSize);
			
			btncurrentImage.setIcon(icon_example);
			btncurrentImage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(e);
					System.out.println();
					System.out.println("---: " + e.getSource());				
					Object source = e.getSource();
			        if (source instanceof JButton) {
			            JButton btn = (JButton)source;
			            System.out.println("It's a button");
			            System.out.println("Icon:" + btn.getIcon().toString());		            
			        }
			        else {
			        	System.out.println("Nope....!!");
			        }
				}
			});
			add(btncurrentImage);
			
			/*
			 * Code for the previous button
			 */
			btnPrevious.setEnabled(false);
			btnPrevious.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					setFocusable(true);
					requestFocusInWindow();
					
					// TODO: display the previous image
					int current_file_index = Arrays.asList(imagefiles).indexOf(active_image_file);
					if (current_file_index != -1){
						System.out.println ("\nCurrent file index: " + current_file_index);
						if (current_file_index == 0){
							// no previous image to display
							System.out.println ("No Previous to show.");
						}
						else {
							
							// there is at least one previous image to display
							current_file_index--;
							System.out.println ("Previous file index: " + current_file_index + "\n");
							
							// change the current image and update the active file
							active_image_file = imagefiles[current_file_index];
							Dimension btnSize = new Dimension(btncurrentImage.getWidth(), btncurrentImage.getHeight());
							ImageIcon image_icon = new ImageIcon(active_image_file.toString());	
							image_icon = generateResizedImageIcon ( image_icon,  btnSize);
							btncurrentImage.setIcon(image_icon);
							
							// update buttons for the next click action
							btnNext.setEnabled(true);
							if (current_file_index == 0) btnPrevious.setEnabled(false);	
						}
					}
					else {
						System.out.println ("In Previous: Current file not found!!!");
					}
				}
			});
			btnPrevious.setBounds(449, 562, 117, 29);
			add(btnPrevious);
			
			/*
			 * Code for the play button
			 */
			btnPlay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					setFocusable(true);
					requestFocusInWindow();
					
					// TODO: play all images
					if (!playing){
						System.out.print("Started Playing...");
						active_image_file = imagefiles[0];
						timer.start();
						btnPlay.setText("Stop");
						playing = true;					
					}
					else {
						System.out.print("Stoped Playing...");
						timer.stop();
						btnPlay.setText("Play");
						playing = false;
					}
				}
			});
			btnPlay.setBounds(564, 562, 117, 29);
			add(btnPlay);
			
			/*
			 * code for the timer
			 */
			timer = new Timer(1000, new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			    	  System.out.print("Playing...");
			    	  
						// go the next picture if any
						int current_file_index = Arrays.asList(imagefiles).indexOf(active_image_file);
						if (current_file_index != -1){
							System.out.println ("\nCurrent file index: " + current_file_index);
							if (current_file_index == (imagefiles.length - 1)){
								
								// no next image to display
								System.out.println ("No Next to show.");
								
								// stop the timer
								System.out.print("Stoped Playing...");
								timer.stop();
								btnPlay.setText("Play");
								playing = false;						
							}
							else {
								
								// there is at least one previous image to display
								current_file_index++;
								System.out.println ("nEXT file index: " + current_file_index + "\n");
								
								// change the current image and update the active file
								active_image_file = imagefiles[current_file_index];
								Dimension btnSize = new Dimension(btncurrentImage.getWidth(), btncurrentImage.getHeight());
								ImageIcon image_icon = new ImageIcon(active_image_file.toString());	
								image_icon = generateResizedImageIcon ( image_icon,  btnSize);
								btncurrentImage.setIcon(image_icon);
								
								// update button for the next click action
								btnPrevious.setEnabled(true);
								if (current_file_index == imagefiles.length - 1) btnNext.setEnabled(false);					
							}
						}
						else {
							System.out.println ("In Previous: Current file not found!!!");
						}
			      }
			    });
			
			/*
			 * Code for the next button
			 */
			if (imagefiles.length == 1) btnNext.setEnabled(false);
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					setFocusable(true);
					requestFocusInWindow();
					
					// TODO: display the next image
					
					int current_file_index = Arrays.asList(imagefiles).indexOf(active_image_file);
					if (current_file_index != -1){
						System.out.println ("\nCurrent file index: " + current_file_index);
						if (current_file_index == (imagefiles.length - 1)){
							
							// no next image to display
							System.out.println ("No Next to show.");
						}
						else {
							
							// there is at least one previous image to display
							current_file_index++;
							System.out.println ("nEXT file index: " + current_file_index + "\n");
							
							// change the current image and update the active file
							active_image_file = imagefiles[current_file_index];
							Dimension btnSize = new Dimension(btncurrentImage.getWidth(), btncurrentImage.getHeight());
							ImageIcon image_icon = new ImageIcon(active_image_file.toString());	
							image_icon = generateResizedImageIcon ( image_icon,  btnSize);
							btncurrentImage.setIcon(image_icon);
							
							// update button for the next click action
							btnPrevious.setEnabled(true);
							if (current_file_index == imagefiles.length - 1) btnNext.setEnabled(false);					
						}
					}
					else {
						System.out.println ("In Previous: Current file not found!!!");
					}
				}
			});
			btnNext.setBounds(695, 562, 117, 29);
			add(btnNext);
			
			
			/*
			 * add a key listener to the panel
			 */
			
			addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void keyPressed(KeyEvent event) {
					// TODO Auto-generated method stub
					int keyCode = event.getKeyCode();
					if (keyCode == KeyEvent.VK_LEFT) {
						System.out.println("left - previous image");
						
						// TODO: display the previous image
						int current_file_index = Arrays.asList(imagefiles).indexOf(active_image_file);
						if (current_file_index != -1){
							System.out.println ("\nCurrent file index: " + current_file_index);
							if (current_file_index == 0){
								// no previous image to display
								System.out.println ("No Previous to show.");
							}
							else {
								
								// there is at least one previous image to display
								current_file_index--;
								System.out.println ("Previous file index: " + current_file_index + "\n");
								
								// change the current image and update the active file
								active_image_file = imagefiles[current_file_index];
								Dimension btnSize = new Dimension(btncurrentImage.getWidth(), btncurrentImage.getHeight());
								ImageIcon image_icon = new ImageIcon(active_image_file.toString());	
								image_icon = generateResizedImageIcon ( image_icon,  btnSize);
								btncurrentImage.setIcon(image_icon);
								
								// update buttons for the next click action
								btnNext.setEnabled(true);
								if (current_file_index == 0) btnPrevious.setEnabled(false);	
							}
						}
						else {
							System.out.println ("In Previous: Current file not found!!!");
						}
					}
					else if (keyCode == KeyEvent.VK_RIGHT) {
						System.out.println("right - next image");
						
						// TODO: display the next image
						
						int current_file_index = Arrays.asList(imagefiles).indexOf(active_image_file);
						if (current_file_index != -1){
							System.out.println ("\nCurrent file index: " + current_file_index);
							if (current_file_index == (imagefiles.length - 1)){
								
								// no next image to display
								System.out.println ("No Next to show.");
							}
							else {
								
								// there is at least one previous image to display
								current_file_index++;
								System.out.println ("nEXT file index: " + current_file_index + "\n");
								
								// change the current image and update the active file
								active_image_file = imagefiles[current_file_index];
								Dimension btnSize = new Dimension(btncurrentImage.getWidth(), btncurrentImage.getHeight());
								ImageIcon image_icon = new ImageIcon(active_image_file.toString());	
								image_icon = generateResizedImageIcon ( image_icon,  btnSize);
								btncurrentImage.setIcon(image_icon);
								
								// update button for the next click action
								btnPrevious.setEnabled(true);
								if (current_file_index == imagefiles.length - 1) btnNext.setEnabled(false);					
							}
						}
						else {
							System.out.println ("In Previous: Current file not found!!!");
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
				}	
            });
			
			setFocusable(true);
			requestFocusInWindow();
	
			CustomJButton[] buttons = generateButtons(imagefiles);
			for (CustomJButton b : buttons){
				
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						setFocusable(true);
						requestFocusInWindow();
						
						System.out.println(e);
						System.out.println();
						System.out.println("---: " + e.getSource());
						
						Object source = e.getSource();
				        if (source instanceof CustomJButton) {
				        	CustomJButton btn = (CustomJButton)source;
				            // Go ahead and do what you like
				            System.out.println("It's a button");
				            System.out.println("Icon:" + btn.getIcon().toString());
				            System.out.println("Icon:" + btn.getImagePath());
				            
				            // get the scaled image icon to use for this button
				            int current_file_index = Arrays.asList(imagefiles).indexOf(btn.getImagePath());
				            if (current_file_index != -1){
					            active_image_file = imagefiles[current_file_index];
								Dimension btnSize = new Dimension(btncurrentImage.getWidth(), btncurrentImage.getHeight());
								ImageIcon icon_example = new ImageIcon(active_image_file.toString());	
								icon_example = generateResizedImageIcon ( icon_example,  btnSize);
								btncurrentImage.setIcon(icon_example);	
								
								// update the previous & next buttons
								if (current_file_index == 0) 
									btnPrevious.setEnabled(false); 
								else
									btnPrevious.setEnabled(true); 
								if (current_file_index == (imagefiles.length -1))
									btnNext.setEnabled(false);
								else
									btnNext.setEnabled(true);
				            }
				            else {
				            	System.out.print("The image you cliked on is not recognizable.");
				            }
				        }
				        else {
				        	System.out.println("Nope....!!");
				        }
					}
				});
				
				list_panel.add(b);
			}
			
			/*
			 * Adding a mouse wheel listener
			 */
		    addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
					// TODO Auto-generated method stub
					if (e.getWheelRotation() < 0) {
			            System.out.println("Wheel down.... " + e.getWheelRotation());
			            
						// display the previous image
						int current_file_index = Arrays.asList(imagefiles).indexOf(active_image_file);
						if (current_file_index != -1){
							System.out.println ("\nCurrent file index: " + current_file_index);
							if (current_file_index == 0){
								// no previous image to display
								System.out.println ("No Previous to show.");
							}
							else {
								
								// there is at least one previous image to display
								current_file_index--;
								System.out.println ("Previous file index: " + current_file_index + "\n");
								
								// change the current image and update the active file
								active_image_file = imagefiles[current_file_index];
								Dimension btnSize = new Dimension(btncurrentImage.getWidth(), btncurrentImage.getHeight());
								ImageIcon image_icon = new ImageIcon(active_image_file.toString());	
								image_icon = generateResizedImageIcon ( image_icon,  btnSize);
								btncurrentImage.setIcon(image_icon);
								
								// update buttons for the next click action
								btnNext.setEnabled(true);
								if (current_file_index == 0) btnPrevious.setEnabled(false);	
							}
						}
						else {
							System.out.println ("In Previous: Current file not found!!!");
						}
					} 
					else {
			            System.out.println("Wheel up... " + e.getWheelRotation());
			            
						// display the next image
						
						int current_file_index = Arrays.asList(imagefiles).indexOf(active_image_file);
						if (current_file_index != -1){
							System.out.println ("\nCurrent file index: " + current_file_index);
							if (current_file_index == (imagefiles.length - 1)){
								
								// no next image to display
								System.out.println ("No Next to show.");
							}
							else {
								
								// there is at least one previous image to display
								current_file_index++;
								System.out.println ("nEXT file index: " + current_file_index + "\n");
								
								// change the current image and update the active file
								active_image_file = imagefiles[current_file_index];
								Dimension btnSize = new Dimension(btncurrentImage.getWidth(), btncurrentImage.getHeight());
								ImageIcon image_icon = new ImageIcon(active_image_file.toString());	
								image_icon = generateResizedImageIcon ( image_icon,  btnSize);
								btncurrentImage.setIcon(image_icon);
								
								// update button for the next click action
								btnPrevious.setEnabled(true);
								if (current_file_index == imagefiles.length - 1) btnNext.setEnabled(false);					
							}
						}
						else {
							System.out.println ("In Previous: Current file not found!!!");
						}
					}
				}
		      });
		}
		else {
			
			// display an import message	
			JLabel lblImportMessage = new JLabel("<html>You Need To Import A folder that Contains Images to display<br>Use the 'Import Images' Menu Item</html>");
			lblImportMessage.setHorizontalAlignment(SwingConstants.CENTER);
			lblImportMessage.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
			lblImportMessage.setBounds(175, 222, 711, 51);
			add(lblImportMessage);
		}	
	}
	
	public static CustomJButton[] generateButtons (File[] imagefiles ) {	
		int size = imagefiles.length;
		CustomJButton[] buttons = new CustomJButton[size];
		CustomJButton btnNewButton;
		ImageIcon imageIcon;
		for (int i = 0; i < size; i++){
			
			btnNewButton = new CustomJButton(imagefiles[i]);
			
			imageIcon = new ImageIcon (imagefiles[i].toString());
			Dimension boundary = new Dimension(128, 128);
			imageIcon = generateResizedImageIcon ( imageIcon, boundary);

			btnNewButton.setIcon(imageIcon);
			btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			buttons[i] = btnNewButton;
		}		
		return buttons; 
	}
	
	/*
	 * this function will return a Dimension object containing 
	 * with the width and height of the resized image, keeping the aspect ratio
	 */
	public static Dimension generateScaledDimension(Dimension imgSize, Dimension boundary) {

	    int original_width = imgSize.width;
	    int original_height = imgSize.height;
	    int bound_width = boundary.width;
	    int bound_height = boundary.height;
	    int new_width = original_width;
	    int new_height = original_height;

	    // first check if we need to scale width
	    if (original_width > bound_width) {
	        new_width = bound_width;
	        new_height = (new_width * original_height) / original_width;
	    }

	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) {
	        new_height = bound_height;
	        new_width = (new_height * original_width) / original_height;
	    }

	    return new Dimension(new_width, new_height);
	}
	
	/*
	 * Give the image icon and dimensions of a component that will contain that icon,
	 * this function will return a resized image icon that will fit in that container,
	 * and keep the resolution of the image
	 */
	
	public static ImageIcon generateResizedImageIcon (ImageIcon original_image_icon, Dimension boundary){
		
		// get need dimensions
		Dimension imgIconSize = new Dimension(original_image_icon.getIconWidth(), original_image_icon.getIconHeight());
		Dimension resized_dim =  generateScaledDimension (imgIconSize, boundary);
		
		// resize the icon image
		Image image = original_image_icon.getImage();
		Image newimg = image.getScaledInstance(resized_dim.width, resized_dim.height,  java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
}
