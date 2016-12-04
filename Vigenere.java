
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.event.* ;
import java.io.* ;
import java.util.* ;


public class Vigenere extends JFrame implements ActionListener
{
	// The char arrays holding characters
	char alphabetLCase [] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'} ;
	char alphabetUCase [] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'} ;

	// Holds the position of the D E C E P T I V E in the alphabet. This position is the key to which each letter in
	// ...the plaintext is decrypted
	private int keyIndexPosition = 0 ;

	// The key word
	final private String KEY = "DECEPTIVE" ;

	// The modified key will be based on the word deceptive and the plain-text.
	// eg abcdefghijklmnopqr will become deceptivedeceptive
	private String modifiedKey = "" ;

	// 2 strings that will hold the results
	private String encryptedResult = "" ;
	private String decryptedResult = "" ;

	//MENU-BAR
	//Create an instance of a JMenuBar
	JMenuBar MenuBar ;

	//MENU-BAR \ FILE
	//Create an instance of a JMenu
	JMenu MenuFile ;
	//Create instances of various JMenuItems for the JMenu we just created
	JMenuItem menuItemEnterText ;
	JMenuItem menuItemExit ;

	public Vigenere( String title )
	{
		// Set the title by calling the parent class, JFrame
		super( title ) ;

		MenuBar = new JMenuBar() ;
		this.setJMenuBar( MenuBar ) ;

		MenuFile = new JMenu("File") ;
		MenuBar.add(MenuFile) ;
		MenuFile.setMnemonic(KeyEvent.VK_F) ;

		menuItemEnterText = new JMenuItem("Enter Text...") ;
		MenuFile.add(menuItemEnterText) ;
		menuItemEnterText.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_E, ActionEvent.CTRL_MASK )) ;

		MenuFile.addSeparator() ;

		menuItemExit = new JMenuItem("Exit") ;
		MenuFile.add(menuItemExit) ;

		menuItemEnterText.addActionListener(this) ;
		menuItemExit.addActionListener(this) ;
	}

	public void actionPerformed( java.awt.event.ActionEvent event1 )
	{
		if (event1.getSource() == menuItemEnterText)
		{
			// input box
			String textToEncrypt = JOptionPane.showInputDialog("Enter Text To Be Encrypted...") ;

			// CAll function to set the key based on "DECEPTIVE" and the plain-text
			setKeyString( textToEncrypt ) ;
			encrypt( textToEncrypt ) ;
			decrypt( encryptedResult ) ;
		}

		if (event1.getSource() == menuItemExit)
		{
			System.exit(1) ;
		}
	}

	// Function to set the text key
	private void setKeyString( String textToEncrypt )
	{
		for ( int i=0 ; i<textToEncrypt.length() ; i++ )
		{
			modifiedKey += KEY.charAt(i%(KEY.length())) ;
		}
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

				// loop through the array and get the numerical key
				// eg D=3 E=4 C=2 E=4 P=15 T=19 I=8 V=21 E=4
				for ( int k=0 ; k<26 ; k++ )
				{
					if ((modifiedKey.charAt(i)==alphabetLCase[k])||(modifiedKey.charAt(i)==alphabetUCase[k]))
						keyIndexPosition = k ;
				}

				// loop through the two alphabet arrays
				for ( int j=0 ; j<26 ; j++ )
				{
					if ((letter==alphabetLCase[j])||(letter==alphabetUCase[j]))
					{
						// Assign the letter 3 places on in the alphabet to our result string
						// the number will need to be modded by 26 if it exceeds 26 ( 26 = array bounds )
						encryptedResult+=alphabetUCase[(j+keyIndexPosition)%26] ;

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

			// loop through the array and get the numerical key
			// eg D=3 E=4 C=2 E=4 P=15 T=19 I=8 V=21 E=4
			for ( int k=0 ; k<26 ; k++ )
			{
				if ((modifiedKey.charAt(i)==alphabetLCase[k])||(modifiedKey.charAt(i)==alphabetUCase[k]))
					keyIndexPosition = k ;
			}


			// loop through the two alphabet arrays
			for ( int j=0 ; j<26 ; j++ )
			{
				if ((letter==alphabetLCase[j])||(letter==alphabetUCase[j]))
				{
					// assign the letter 3 places back in the alphabet
					index = (j-keyIndexPosition) ;

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