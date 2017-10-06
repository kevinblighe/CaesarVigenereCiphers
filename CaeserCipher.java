
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.event.* ;
import java.io.* ;
import java.util.* ;


public class CaeserCipher extends JFrame implements ActionListener
{
	// The char arrays holding characters
	char alphabetLCase [] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'} ;
	char alphabetUCase [] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'} ;

	// 2 strings that will hold the results
	String encryptedResult = "" ;
	String decryptedResult = "" ;

	// The encryption/decryption key
	final int KEY = 3 ;

	//MENU-BAR
	//Create an instance of a JMenuBar
	JMenuBar MenuBar ;

	//MENU-BAR \ FILE
	//Create an instance of a JMenu
	JMenu MenuFile ;
	//Create instances of various JMenuItems for the JMenu we just created
	JMenuItem menuItemOpen ;
	JMenuItem menuItemEnterText ;
	JMenuItem menuItemExit ;

	public CaeserCipher( String title )
	{
		// Set the title by calling the parent class, JFrame
		super( title ) ;

		MenuBar = new JMenuBar() ;
		this.setJMenuBar( MenuBar ) ;

		MenuFile = new JMenu("File") ;
		MenuBar.add(MenuFile) ;
		MenuFile.setMnemonic(KeyEvent.VK_F) ;

		menuItemOpen = new JMenuItem("Open File...") ;
		MenuFile.add(menuItemOpen) ;
		menuItemOpen.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK )) ;

		menuItemEnterText = new JMenuItem("Enter Text...") ;
		MenuFile.add(menuItemEnterText) ;
		menuItemEnterText.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_E, ActionEvent.CTRL_MASK )) ;

		MenuFile.addSeparator() ;

		menuItemExit = new JMenuItem("Exit") ;
		MenuFile.add(menuItemExit) ;

		menuItemOpen.addActionListener(this) ;
		menuItemEnterText.addActionListener(this) ;
		menuItemExit.addActionListener(this) ;
	}

	public void actionPerformed( java.awt.event.ActionEvent event1 )
	{
		if (event1.getSource() == menuItemOpen)
		{
			JFileChooser chooser = new JFileChooser() ;

			//Create a filter using the ExampleFileFilter.java file
    			ExampleFileFilter filter = new ExampleFileFilter() ;

			//Only allow general script files to be opened
			filter.addExtension("txt") ;
			filter.addExtension("java") ;
			filter.addExtension("ini") ;
			filter.setDescription("text files only") ;

			//Assign the filter as the filter for the file chooser
			chooser.setFileFilter( filter ) ;

			int returnVal = chooser.showOpenDialog( this ) ;

			//if the user doesnt cancel
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				encrypt( chooser.getSelectedFile() ) ;
				decrypt( encryptedResult ) ;
			}
		}

		if (event1.getSource() == menuItemEnterText)
		{
			// input box
			String textToEncrypt = JOptionPane.showInputDialog("Enter Text To Be Encrypted...") ;

			encrypt( textToEncrypt ) ;
			decrypt( encryptedResult ) ;
		}

		if (event1.getSource() == menuItemExit)
		{
			System.exit(1) ;
		}
	}

	// encrypt is overloaded to accept a File or String object
	public void encrypt( File FilePath )
	{
		// reset the result
		encryptedResult = "" ;

		FileReader myReader = null ;

		int asciiCode = 0 ;

		char letter ;

		boolean foundChar = true ;

		try
		{
			// Open up the file using FileReader
			myReader = new FileReader( FilePath ) ;
		}
		catch ( java.io.FileNotFoundException FNFE ) {}

		// Note: FileReader reads in the ascii number of characters from files
		// Loop through while the ascii code is valid. ie NOT -1
		while ( asciiCode != -1 )
		{
			try
			{
				// reset foundChar
				foundChar = false ;

				// Read the file character by character. Gets the ascii code of the character
				asciiCode = myReader.read() ;

				// cast the ascii code as a 'char'
				letter = (char)asciiCode ;

				// loop through the two alphabet arrays
				for ( int i=0 ; i<26 ; i++ )
				{
					if ((letter==alphabetLCase[i])||(letter==alphabetUCase[i]))
					{
						// Assign the letter 3 places on in the alphabet to our result string
						// the number will need to be modded by 26 if it exceeds 26 ( 26 = array bounds )
						encryptedResult+=alphabetUCase[(i+KEY)%26] ;

						// ie a letter was found
						foundChar = true ;
					}
				}

				// ie if a letteroutside of a to z was found
				if (!foundChar) encryptedResult+=letter ;
			}
			catch ( Exception e ) {}
		}

		displayResults( "Encrypted Result", encryptedResult ) ;
	}

	// encrypt is overloaded to accept a File or String object
	public void encrypt( String textToEncrypt )
	{
		// reset the result
		encryptedResult = "" ;

		char letter ;

		int length=textToEncrypt.length() ;

		boolean foundChar = true ;

		// loop through each letter
		for ( int i=0 ; i<length ; i++ )
		{
			try
			{
				// reset foundChar
				foundChar = false ;

				// convert the String letter to a char
				letter = textToEncrypt.charAt(i) ;

				// loop through the two alphabet arrays
				for ( int j=0 ; j<26 ; j++ )
				{
					if ((letter==alphabetLCase[j])||(letter==alphabetUCase[j]))
					{
						// Assign the letter 3 places on in the alphabet to our result string
						// the number will need to be modded by 26 if it exceeds 26 ( 26 = array bounds )
						encryptedResult+=alphabetUCase[(j+KEY)%26] ;

						// ie a letter was found
						foundChar = true ;
					}
				}

				// ie if a letteroutside of a to z was found
				if (!foundChar) encryptedResult+=letter ;
			}
			catch ( Exception e ) {}
		}

		displayResults( "Encrypted Result", encryptedResult ) ;
	}

	// decrypt only accepts a String as parameter
	public void decrypt( String textToDecrypt )
	{
		// reset the result
		decryptedResult = "" ;

		char letter ;

		int index ;

		int length=textToDecrypt.length() ;

		boolean foundChar = true ;

		// loop through each letter
		for ( int i=0 ; i<length ; i++ )
		{
			// reset foundChar
			foundChar = false ;

			// convert the String letter to a char
			letter = textToDecrypt.charAt(i) ;

			// loop through the two alphabet arrays
			for ( int j=0 ; j<26 ; j++ )
			{
				if ((letter==alphabetLCase[j])||(letter==alphabetUCase[j]))
				{
					// assign the letter 3 places back in the alphabet
					index = (j-KEY) ;

					// If say, we have 'A' (position 0 in our array), it will change to -3
					// therefore, we have to add 26 to any array bound that's changed to a minus
					if ( index<0 ) index=index+26 ;

					decryptedResult+=alphabetUCase[index] ;

					// ie a letter was found
					foundChar = true ;
				}
			}

			// ie if a letteroutside of a to z was found
			if (!foundChar) decryptedResult+=letter ;
		}

		displayResults( "Decrypted Result", decryptedResult ) ;
	}

	private void displayResults( String title, String result )
	{
		// Display our result String variable in an JOptionPane
		JOptionPane.showMessageDialog( this, result, title, JOptionPane.INFORMATION_MESSAGE ) ;
	}
}
