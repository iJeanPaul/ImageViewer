package imageViewerUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ImageViewer extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageViewer frame = new ImageViewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImageViewer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("JP - Image Viewer");
		setBounds(100, 50, 1000, 650);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnImport = new JMenu("Import");
		menuBar.add(mnImport);
		
		/*
		 * When clicked, this should allow the user to select a folder containing
		 * images they want displayed. 
		 */
		JMenuItem mntmImages = new JMenuItem("Images");
		mntmImages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println ("Importing images....");
				
				JFileChooser fileopen = new JFileChooser();
				fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int ret = fileopen.showDialog(null, "Open Folder");
				
				// get image extensions supported by java
				String[] image_extensions = ImageIO.getReaderFormatNames();		
				
				// check if the folder is opened
			    if (ret == JFileChooser.APPROVE_OPTION) {
			 
			    	// get the folder's path			    	
			    	String current_dir_path = fileopen.getCurrentDirectory().toString();
			    	String selected_folder_path = fileopen.getSelectedFile().toString();
			        System.out.println("Current Dir Path: " +  current_dir_path);
			        System.out.println("Selected Folder Path: " +  selected_folder_path);
			        	
			        System.out.println ("Selected files --> " + fileopen.getSelectedFiles().length);
			        System.out.println ("SELECTED FILE --> " + fileopen.getSelectedFile());
			        System.out.println ("SELECTED FILE EXISTS --> " + fileopen.getSelectedFile().exists());
			        
			        // Get images files: if no folder is selected, get images files from the current directory
			        // otherwise, get files from inside the selected folder
			        // TODO: revisit this, there is a bug!!!!
			
			        String folder_path;
			        if (fileopen.getSelectedFile() != null && fileopen.getSelectedFile().exists())
			        	folder_path = selected_folder_path;
			        else
			        	folder_path = current_dir_path;   
			        
			        // build a file name name filter so that we can get only image files from the selected folder
			        System.out.println("\nAvailable files");
			        System.out.println("---------------");
			        FilenameFilter fileNameFilter = new FilenameFilter() {
			        	@Override
			            public boolean accept(File dir, String name) {
			               if(name.lastIndexOf('.') > 0)
			               {
			            	  // check if the current files's extension matches any of the iImageIO formats
			                  int lastIndex = name.lastIndexOf('.');
			                  String str = name.substring(lastIndex + 1);
			                  System.out.println(name + " : " + str);		                  
			                  if(Arrays.asList(image_extensions).contains(str))
			                  {
			                     return true;
			                  }
			               }
			               return false;
			            }
			        };		        
			        File[] files = new File (folder_path).listFiles(fileNameFilter);
			        System.out.println("\nLoaded files");
			        System.out.println("------------");
			        for (File f : files ){
			        	System.out.println(f);
			        }
			        
			        // TODO: at this point we need to reload the JPane so that it gets updated images
			    }
			    else {
			    	System.out.println ("No selection!");
			    }			
			}
		});
		mnImport.add(mntmImages);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
